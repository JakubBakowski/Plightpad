package com.plightpad.sugardomain;

import com.orm.SugarRecord;
import com.plightpad.firedomain.Course;
import com.plightpad.firedomain.Lane;
import com.plightpad.firedomain.SurfaceType;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Szczypiorek on 09.08.2017.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseSugar extends SugarRecord<CourseSugar> implements Serializable{

    private String name;
    private String city;
    private String country;
    private String imagePath;
    private Integer bestScore;
    private String address;
    private String surfaceType;
    private Double lanesLength;
    private String pushedKey;

    public CourseSugar(Course course){
        this.name = course.getName();
        this.city = course.getCity();
        this.country = course.getCountry();
        this.imagePath = course.getImagePath();
        this.bestScore = course.getBestScore();
        this.surfaceType = course.getSurfaceType().toString();
        this.address = course.getAddress();
        this.lanesLength = course.getLanesLength();
        this.pushedKey = course.getPushedKey();
    }

}
