package com.plightpad.boxdomain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import solid.functions.Action1;
import solid.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public enum SurfaceType implements Serializable {

    FELT("Felt"),
    ETERNIT("Eternit"),
    CONCRETE("Concrete");

    private String name;

    public static SurfaceType getSurfaceTypeByName(String name) {
        return Stream.stream(SurfaceType.values())
                .filter(e -> e.name.equals(name))
                .first()
                .or(SurfaceType.CONCRETE);
    }
}
