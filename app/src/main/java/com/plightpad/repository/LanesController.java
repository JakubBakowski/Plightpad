package com.plightpad.repository;

import com.plightpad.boxdomain.Course;
import com.plightpad.boxdomain.Lane;
import java.util.List;
import io.objectbox.Box;

public class LanesController extends BasicObjectBoxController{

    private static Box<Lane> lanesBox = boxStore.boxFor(Lane.class);

    public static void saveSugarLane(Lane lane){
        lanesBox.put(lane);
    }

    public static List<Lane> getAllSugarLanes(){
        return lanesBox.getAll();
    }

    public static List<Lane> getLanesByCourseId(Long id){
        Course course = CoursesController.getCourseByid(id);
        return course.lanes;
    }

    public static Lane getLaneById(long id){
        return lanesBox.get(id);
    }

    public static void updateLane(Lane lane){
        lanesBox.put(lane);
    }

}
