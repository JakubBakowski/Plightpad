package com.plightpad.boxdomain;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Szczypiorek on 19.09.2017.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LaneResult {

    @Id
    long id;

    int value;
    ToOne<Lane> lane;
    ToOne<CourseResult> result;

    public LaneResult(int value){
        this.value = value;
    }

}
