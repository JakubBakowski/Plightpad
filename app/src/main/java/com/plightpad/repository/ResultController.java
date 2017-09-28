package com.plightpad.repository;

import com.plightpad.boxdomain.CourseResult;
import com.plightpad.boxdomain.LaneResult;

import java.util.List;

import io.objectbox.Box;

/**
 * Created by Szczypiorek on 19.09.2017.
 */

public class ResultController extends BasicObjectBoxController{

    private static Box<CourseResult> resultBox = boxStore.boxFor(CourseResult.class);

    public static void save(CourseResult courseResult){
        resultBox.put(courseResult);
    }

    public static void delete(long id){
        resultBox.remove(id);
    }

    public static void update(CourseResult courseResult){
        resultBox.put(courseResult);
    }

    public static CourseResult getResultById(long id){
        return resultBox.get(id);
    }

    public static List<CourseResult> getAllResults(){
        return resultBox.getAll();
    }

    public static void addLaneResultToCourseResultByItsId(long id, LaneResult laneResult){
        resultBox.get(id).getLaneResults().add(laneResult);
        resultBox.put(resultBox.get(id));
    }

    public static void removeLaneResultFromCourseResultByItsId(long id, LaneResult laneResult){
        resultBox.get(id).getLaneResults().remove(laneResult);
        resultBox.put(resultBox.get(id));
    }

}
