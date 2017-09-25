package com.plightpad.controllers;

import com.plightpad.sugardomain.LaneSugar;

import java.util.List;

/**
 * Created by Szczypiorek on 09.08.2017.
 */

public class SugarLanesController {

    public static void saveSugarLane(LaneSugar lane){
        lane.save();
    }

    public static List<LaneSugar> getAllSugarLanes(){
        return LaneSugar.listAll(LaneSugar.class);
    }

    public static List<LaneSugar> getSugarLanesByCourseId(Long id){
        String query = "Select * from LANE_SUGAR where course = ?";
        List<LaneSugar> ls = LaneSugar.findWithQuery(LaneSugar.class, query, String.valueOf(id));
        return ls;
    }

    public static LaneSugar getLaneById(long id){
        return LaneSugar.findById(LaneSugar.class, id);
    }

    public static void updateLaneBall(LaneSugar lane){
        LaneSugar ls = LaneSugar.findById(LaneSugar.class, lane.getId());
        ls.setBall(lane.getBall());
        ls.save();
    }

}
