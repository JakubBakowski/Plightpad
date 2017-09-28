package com.plightpad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plightpad.adapters.FoldingCellListAdapter;
import com.plightpad.boxdomain.Course;
import com.plightpad.tools.DrawableUtils;
import com.plightpad.tools.FirebaseReferences;
import com.plightpad.tools.Utils;
import com.ramotion.foldingcell.FoldingCell;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import solid.stream.Stream;

import static android.widget.Toast.makeText;
import static solid.collectors.ToList.toList;

public class SearchCoursesActivity extends AppCompatActivity {

    private static final String TAG = "SearchCoursesActivity";

    @BindView(R.id.search_courses_list)
    public ListView foldingListView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_courses);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseLoadCourses();
    }

    private void initializeFoldingList(List<Course> courses) {
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, courses, true, null);

        adapter.setDefaultRequestBtnClickListener(a -> makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show());
        foldingListView.setAdapter(adapter);

        foldingListView.setOnItemClickListener((adapterView, view, pos, l) -> {
            ((FoldingCell) view).toggle(false);
            adapter.registerToggle(pos);
        });
    }

    private void databaseLoadCourses() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference courseReference = databaseReference.child(FirebaseReferences.COURSE_REFERENCE);

        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses = Stream.stream(dataSnapshot.getChildren()).map(s -> s.getValue(Course.class)).collect(toList());
                initializeFoldingList(courses);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

}
