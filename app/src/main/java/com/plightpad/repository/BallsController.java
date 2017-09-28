package com.plightpad.repository;

import com.plightpad.boxdomain.Ball;

import java.util.List;

import io.objectbox.Box;

/**
 * Created by Szczypiorek on 13.08.2017.
 */

public class BallsController extends BasicObjectBoxController{

    private static Box<Ball> ballBox = boxStore.boxFor(Ball.class);

    public static void saveBall(Ball bs){
        ballBox.put(bs);
    }

    public static List<Ball> getAllBalls(){
        return ballBox.getAll();
    }

    public static Ball getBalLById(long id){
        return ballBox.get(id);
    }

}
