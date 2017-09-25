package com.plightpad.controllers;

import com.orm.SugarApp;
import com.plightpad.sugardomain.BallSugar;
import com.plightpad.sugardomain.CourseSugar;
import com.plightpad.sugardomain.LaneSugar;

/**
 * Created by Szczypiorek on 09.08.2017.
 */

public class SugarController {

    public static void deleteAll(){
        LaneSugar.deleteAll(LaneSugar.class);
        CourseSugar.deleteAll(CourseSugar.class);
        BallSugar.deleteAll(BallSugar.class);
    }

    public static void createDatabase(){
    }

}
