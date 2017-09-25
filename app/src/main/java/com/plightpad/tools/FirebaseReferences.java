package com.plightpad.tools;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Szczypiorek on 19.07.2017.
 */

public class FirebaseReferences {

    // INITIALIZATION OF FIREBASE
//        private DatabaseReference databaseReference;
//
//    in onCreate(...) :
//    databaseReference = FirebaseDatabase.getInstance().getReference();




//    SETTING SOME VALUES INTO :    MAIN_ROOT -> lanes -> setHere
//    DatabaseReference lanesReference = databaseReference.child(FirebaseReferences.LANES_REFERENCE).push();
//        lanesReference.setValue(new Lane(2, "babla", 3, "srabla"));





    // GETTING LIST OF VALUES
//    DatabaseReference lanesReference = databaseReference.child(FirebaseReferences.LANES_REFERENCE);
//        lanesReference.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            dataSnapshot.getChildren().forEach(s -> {
//                doSomething();
//            });
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    });


    public static final String UNIQUE_IDS ="uniqueIds";
    public static final String LANES_REFERENCE ="lanes";
    public static final String COURSE_REFERENCE ="courses";

//    STORAGE
    public static final String LANES_IMAGES_REFERENCE ="images/lanes";
    public static final String COURSE_IMAGES_REFERENCE ="images/courses";



}
