package com.plightpad.repository;

import com.plightpad.boxdomain.Course;

import java.util.List;

import io.objectbox.Box;



public class CoursesController extends BasicObjectBoxController{

    private static Box<Course> courseBox = boxStore.boxFor(Course.class);

    public static void save(Course course) {
        courseBox.put(course);
    }

    public static List<Course> getAllCourses(){
        return courseBox.getAll();
    }

    public static Course getCourseByid(long id){
        return courseBox.get(id);
    }

    public static void deleteAll(){
        courseBox.removeAll();
    }
}
