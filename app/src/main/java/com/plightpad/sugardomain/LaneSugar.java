package com.plightpad.sugardomain;

import com.orm.SugarRecord;
import com.plightpad.firedomain.Course;
import com.plightpad.firedomain.Lane;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Szczypiorek on 09.08.2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LaneSugar extends SugarRecord<LaneSugar> {

    String name;
    int number;
    String imagePath;
    CourseSugar course;
    BallSugar ball;
    String notes;
    String notesImagePath;

    public LaneSugar(Lane lane, CourseSugar courseSugar){
        this.name = lane.getName();
        this.imagePath = lane.getImagePath();
        this.number = lane.getNumber();
        this.course = courseSugar;
    }

}
