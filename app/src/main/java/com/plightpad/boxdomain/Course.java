package com.plightpad.boxdomain;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@RequiredArgsConstructor
public class Course implements Serializable {

    @Exclude
    @Id
    public long id;

    @NonNull
    public String name;

    @NonNull
    public String city;

    @NonNull
    public String country;

    @NonNull
    public String imagePath;

    @NonNull
    public Integer bestScore;

    @NonNull
    public String address;

    @NonNull
    @Convert(converter = SurfaceTypeConverter.class, dbType = String.class)
    public SurfaceType surfaceType;

    @NonNull
    public Double lanesLength;

    @NonNull
    public String pushedKey;

    @Exclude
    @Backlink
    public ToMany<Lane> lanes;

    @NonNull
    @Transient
    public List<Lane> firebaseLanes;

}
