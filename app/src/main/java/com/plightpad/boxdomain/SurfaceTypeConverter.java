package com.plightpad.boxdomain;

import io.objectbox.converter.PropertyConverter;

/**
 * Created by Szczypiorek on 24.09.2017.
 */

public class SurfaceTypeConverter implements PropertyConverter<SurfaceType, String> {
    @Override
    public SurfaceType convertToEntityProperty(String surfaceTypeName) {
        return SurfaceType.getSurfaceTypeByName(surfaceTypeName);
    }

    @Override
    public String convertToDatabaseValue(SurfaceType surfaceType) {
        return surfaceType.getName();
    }
}