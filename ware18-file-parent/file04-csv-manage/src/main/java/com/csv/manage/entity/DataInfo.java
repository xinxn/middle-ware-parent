package com.csv.manage.entity;

public class DataInfo {
    private Integer id ;
    private String cityName ;
    private String spell ;

    public DataInfo(Integer id, String cityName, String spell) {
        this.id = id;
        this.cityName = cityName;
        this.spell = spell;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", spell='" + spell + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }
}
