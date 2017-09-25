package com.plightpad.firedomain;

import com.orm.SugarRecord;
import com.plightpad.sugardomain.CourseSugar;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Szczypiorek on 16.07.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Course implements Serializable{

    private String name;
    private String city;
    private String country;
    private String imagePath;
    private Integer bestScore;
    private String address;
    private SurfaceType surfaceType;
    private Double lanesLength;
    private List<Lane> lanes;
    private String pushedKey;

    public Course(String name, String city, String country, String imagePath, Integer bestScore, String address, SurfaceType surfaceType, Double lanesLength){
        this.name = name;
        this.city = city;
        this.country = country;
        this.imagePath = imagePath;
        this.bestScore = bestScore;
        this.address = address;
        this.surfaceType = surfaceType;
        this.lanesLength = lanesLength;
    }

    public Course(CourseSugar cs){
        this.name = cs.getName();
        this.city = cs.getCity();
        this.country = cs.getCountry();
        this.imagePath = cs.getImagePath();
        this.bestScore = cs.getBestScore();
        this.address = cs.getAddress();
        this.surfaceType = getSurfaceTypeByValue(cs.getSurfaceType());
        this.lanesLength = cs.getLanesLength();
        this.pushedKey = cs.getPushedKey();
    }

    public SurfaceType getSurfaceTypeByValue(String value){
        return SurfaceType.valueOf(value.toUpperCase());
    }

}
