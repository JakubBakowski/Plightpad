package com.plightpad.firedomain;

import com.plightpad.sugardomain.LaneSugar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class Lane implements Serializable{

    String name;
    int number;
    String imagePath;

    public static List<Lane> parseLaneSugarList(List<LaneSugar> laneSugars){
        List<Lane> lanes = new ArrayList<>();
        for (LaneSugar laneSugar : laneSugars) {
            lanes.add(new Lane(laneSugar));
        }
        return lanes;
    }

    public Lane(LaneSugar laneSugar){
        this.name = laneSugar.getName();
        this.number = laneSugar.getNumber();
        this.imagePath = laneSugar.getImagePath();
    }
}
