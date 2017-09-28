package com.plightpad.boxdomain;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Lane implements Serializable {

    @Exclude
    @Id
    public long id;

    @Expose
    public String name;

    @Expose
    public int number;

    @Expose
    public String imagePath;

    @Exclude
    public ToOne<Course> course;

    @Exclude
    public ToOne<Ball> ball;

    @Exclude
    public String notes;

    @Exclude
    public String notesImagePath;

    public Lane(String name, int number, String imagePath) {
        this.name = name;
        this.number = number;
        this.imagePath = imagePath;
    }
}
