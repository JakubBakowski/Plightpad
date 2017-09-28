package com.plightpad.boxdomain;

import com.plightpad.repository.ResultController;

import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mabak on 17.08.2017.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CourseResult {

    @Id
    long id;

    String name;
    int wholeResult = 0;
    @Backlink
    ToMany<LaneResult> laneResults;

    public CourseResult(String name) {
        this.name = name;
    }

    public CourseResult(String name, int wholeResult) {
        this.name = name;
        this.wholeResult = wholeResult;
    }

    public CourseResult() {

    }

    public CourseResult(String name, int i, List<Integer> arrayList) {
        this.name = name;
        this.wholeResult = i;
    }

    public void addResult(int points){
        ResultController.addLaneResultToCourseResultByItsId(getId(), new LaneResult(points));
    }

    public void removeResult(){
        ResultController.removeLaneResultFromCourseResultByItsId(getId(), laneResults.get(laneResults.size()-1));
    }

    public int lastValue(){
        return laneResults.get(laneResults.size()-1).getValue();
    }
}
