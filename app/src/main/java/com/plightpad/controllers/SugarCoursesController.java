package com.plightpad.controllers;

import com.orm.SugarCursorFactory;
import com.orm.query.Select;
import com.plightpad.firedomain.Course;
import com.plightpad.firedomain.Lane;
import com.plightpad.sugardomain.BallSugar;
import com.plightpad.sugardomain.CourseSugar;
import com.plightpad.sugardomain.LaneSugar;

import java.util.List;

/**
 * Created by Szczypiorek on 09.08.2017.
 */

public class SugarCoursesController {

    public static void saveSugarCourse(Course c) {
        CourseSugar cs = new CourseSugar(c);
        cs.save();
        for (Lane lane : c.getLanes()) {
            new LaneSugar(lane, cs).save();
        }

        List<LaneSugar> lanes = LaneSugar.listAll(LaneSugar.class);
        lanes.clear();
    }

    public static List<CourseSugar> getAllSugarCourses(){
        return CourseSugar.listAll(CourseSugar.class);
    }

    public static CourseSugar getByIdSugarCourse(long id){
        return CourseSugar.findById(CourseSugar.class, id);
    }

}
