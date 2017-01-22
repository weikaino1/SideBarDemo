package com.example.x6.sidebardemo;

import java.util.List;

/**
 * Created by x6 on 2017/1/18.
 */
public class CitylistDto {
    private String AZ;
    private List<CitiesDto> cities;

    public String getAZ() {
        return AZ;
    }

    public void setAZ(String aZ) {
        AZ = aZ;
    }

    public List<CitiesDto> getCities() {
        return cities;
    }

    public void setCities(List<CitiesDto> cities) {
        this.cities = cities;
    }
}
