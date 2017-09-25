package com.plightpad.sugardomain;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.sql.Blob;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Szczypiorek on 16.07.2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BallSugar extends SugarRecord<BallSugar> implements Serializable {

    String myName;
    String manufacturer;
    String model;
    double weight;
    double size;
    double hardness;
    String imagePath;

    public BallSugar(String myName, String manufacturer, String model, double weight, double size, double hardness){
        this.myName = myName;
        this.manufacturer = manufacturer;
        this.model = model;
        this.weight = weight;
        this.size = size;
        this.hardness = hardness;
    }

}
