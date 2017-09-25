package com.plightpad.items;

/**
 * Created by Marcin on 16.07.2017.
 */

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.plightpad.firedomain.Course;
import com.plightpad.sugardomain.CourseSugar;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ListItem {

    @Getter
    private static List<ListItem> actualListOfAllItems = new ArrayList<>();

    private DatabaseReference databaseReference;

    @NonNull private String title;
    @NonNull private String country;
    @NonNull private String city;
    @NonNull private String type;
    @NonNull private String laneLength;
    @NonNull private String bestScore;
    @NonNull private String link;
    private Course course;

    public ListItem(String name, String country, String city, String s, String s1, String s2, String imagePath, Course course) {
        this.title = name;
        this.country = country;
        this.city = city;
        this.type = s;
        this.laneLength = s1;
        this.bestScore = s2;
        this.link = imagePath;
        this.course = course;
    }

    private static ListItem createListItem(Course course){
        return new ListItem(course.getName(), course.getCountry(),
                course.getCity(), course.getSurfaceType().toString(), course.getLanesLength().toString(),
                course.getBestScore().toString(), course.getImagePath(), course);
    }

    public static void addCourseToListOfItems(Course course){
        actualListOfAllItems.add(createListItem(course));
    }

    public static void clearListOfItems(){
        actualListOfAllItems.clear();
    }

    public static boolean unPresent(Course course){
        return !actualListOfAllItems.contains(createListItem(course));
    }

    public static Course dataSnapshotToCourse(Object object){
        DataSnapshot dataSnapshot = (DataSnapshot) object;
        return dataSnapshot.getValue(Course.class);
    }

    public static void addSugarCourses(List<CourseSugar> sugarCourse){
        for (CourseSugar courseSugar : sugarCourse) {
            addCourseToListOfItems(new Course(courseSugar));
        }
    }
}