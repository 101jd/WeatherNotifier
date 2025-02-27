package org._jd.domain;

public enum NotifyParameter {
    MINMAXTEMP("MINMAXTEMP"), STORM("STORM"), TEMPDIFF("TEMPDIFF");

    private String value;
    NotifyParameter(String value){
        this.value = value;
    }

    public String get(){
        return this.value;
    }
}
