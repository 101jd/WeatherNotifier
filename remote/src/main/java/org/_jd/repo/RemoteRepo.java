package org._jd.repo;

import org._jd.domain.weatherobj.MinMaxTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RemoteRepo {

    @Autowired
    JdbcTemplate template;

    public Integer getMaxTemperature(String location) {
        return getMinMax(location).getMax();
    }

    public void refreshMax(String location, Integer temperature) {
        if (temperature > getMaxTemperature(location))
            template.update("UPDATE minmaxtemp SET max = ? WHERE location = ?",
                    temperature, location);

    }

    public Integer getMinTemperature(String location) {
        return getMinMax(location).getMin();
    }

    public void refreshMin(String location, Integer temperature) {
        if (temperature < getMinTemperature(location))
            template.update("UPDATE minmaxtemp SET min = ? WHERE location = ?",
                    temperature, location);
    }

    public Integer getTemperature(String name) {
        return null;
    }

    private Integer mod(int val){
        return val < 0 ? val * -1 : val;
    }


    public MinMaxTemp getMinMax(String location){
        String query = "SELECT * FROM minmaxtemp";
        RowMapper<MinMaxTemp> mapper = (rs, rowNum) -> {
            MinMaxTemp minMaxTemp = new MinMaxTemp();

            minMaxTemp.setLocation(rs.getString("location"));
            minMaxTemp.setMin(rs.getInt("min"));
            minMaxTemp.setMax(rs.getInt("max"));

            return minMaxTemp;
        };

        return template.query(query, mapper).stream()
                .filter(loc -> loc.getLocation().equals(location))
                .findFirst().orElse(null);
    }

    public void InitMinmax(String location, Integer temperature){
        template.update("INSERT INTO minmaxtemp VALUES(?, ?, ?)",location, temperature, temperature);
    }
}
