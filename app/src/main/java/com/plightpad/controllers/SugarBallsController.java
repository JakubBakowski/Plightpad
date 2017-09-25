package com.plightpad.controllers;

import com.plightpad.sugardomain.BallSugar;

import java.util.List;

/**
 * Created by Szczypiorek on 13.08.2017.
 */

public class SugarBallsController {

    public static void saveBall(BallSugar bs){
        bs.save();
    }

    public static List<BallSugar> getAllBalls(){
        return BallSugar.listAll(BallSugar.class);
    }

    public static BallSugar getBalLById(long id){
        return BallSugar.findById(BallSugar.class, id);
    }

}
