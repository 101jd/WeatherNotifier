package org._jd.repo;

import org._jd.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotesRepo {
    @Autowired
    JdbcTemplate template;

    public List<Note> getAllNotes(){
        RowMapper<Note> mapper = ((rs, rowNum) -> {
            Note n = new Note();
            n.setEmail(rs.getString("email"));
            n.setNote(rs.getString("note"));

            return n;
        });
        return template.query("SELECT * FROM notes", mapper);

    }


    public boolean addNote(Note note){
        try {

            if (getAllNotes().stream()
                    .filter(note1 -> note1.equals(note)).findAny().orElse(null) != null) {
                template.update("UPDATE notes SET note = ? WHERE email = ?", note.getNote(), note.getEmail());
            } else {
                template.update("INSERT INTO notes VALUES(?, ?)", note.getEmail(), note.getNote());
            }
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }
}
