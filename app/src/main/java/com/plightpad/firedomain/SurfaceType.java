package com.plightpad.firedomain;

import lombok.AllArgsConstructor;

/**
 * Created by Szczypiorek on 16.07.2017.
 */

@AllArgsConstructor
public enum SurfaceType {

    FELT ("Felt"),
    ETERNIT ("Eternit"),
    CONCRETE ("Concrete");

    private String name;

    public String toString(){
        return this.name;
    }
}
