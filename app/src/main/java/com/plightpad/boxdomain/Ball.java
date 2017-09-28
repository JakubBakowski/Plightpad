package com.plightpad.boxdomain;

import java.io.Serializable;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Ball implements Serializable {

    @Id
    long id;

    String myName;
    String manufacturer;
    String model;
    double weight;
    double size;
    double hardness;
    String imagePath;
    @Backlink
    ToMany<Lane> lanes;

    public Ball(String myName, String manufacturer, String model, double weight, double size, double hardness) {
        this.myName = myName;
        this.manufacturer = manufacturer;
        this.model = model;
        this.weight = weight;
        this.size = size;
        this.hardness = hardness;
    }

}
