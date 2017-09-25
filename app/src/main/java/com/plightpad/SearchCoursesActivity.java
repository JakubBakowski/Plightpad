package com.plightpad;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plightpad.adapters.FoldingCellListAdapter;
import com.plightpad.firedomain.Course;
import com.plightpad.items.ListItem;
import com.plightpad.tools.DrawableUtils;
import com.plightpad.tools.FirebaseReferences;
import com.plightpad.tools.Utils;
import com.plightpad.widget.CanaroTextView;
import com.ramotion.foldingcell.FoldingCell;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;
import static com.plightpad.items.ListItem.addCourseToListOfItems;

public class SearchCoursesActivity extends AppCompatActivity {

    private static final String TAG = "SearchCoursesActivity";

    private static final long RIPPLE_DURATION = 150;
    private static final long GUILLOTINE_ANIMATION_DURATION = 250;
    @BindView(R.id.search_courses_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_courses_root)
    FrameLayout root;
    @BindView(R.id.search_courses_content_hamburger)
    View contentHamburger;
    @BindView(R.id.search_courses_list)
    public ListView foldingListView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_courses);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        initializeGuillotine();
        initializeFoldingList();
    }

    private void initializeGuillotine() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setDuration(GUILLOTINE_ANIMATION_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
        guillotineMenu.findViewById(R.id.profile_group).setOnClickListener(s -> {
            CanaroTextView canaroTextView = (CanaroTextView) guillotineMenu.findViewById(R.id.profile_group_canaro);
            ImageView imageView = (ImageView) guillotineMenu.findViewById(R.id.profile_group_image);
            canaroTextView.setTextColor(getResources().getColor(R.color.selected_item_color));
            imageView.setColorFilter(getResources().getColor(R.color.selected_item_color));
            startActivity(new Intent(this, MenuActivity.class));
        });

        ImageView profileImageView = (ImageView) guillotineMenu.findViewById(R.id.profile_image_view);

        try {
            Uri uri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
            if (uri != null) {
                Glide.with(this)
                        .load(uri)
                        .into(profileImageView);
            } else {
                setAlternativeProfilePhoto(profileImageView);
            }
        } catch (Exception e) {
            Log.i(TAG, "Brak zdjęcia");
            setAlternativeProfilePhoto(profileImageView);
        }

        guillotineMenu.findViewById(R.id.settings_group).setOnClickListener(s -> {
            CanaroTextView canaroTextView = (CanaroTextView) guillotineMenu.findViewById(R.id.settings_group_canaro);
            ImageView iv = (ImageView) guillotineMenu.findViewById(R.id.settings_group_image);
            canaroTextView.setTextColor(getResources().getColor(R.color.selected_item_color));
            iv.setColorFilter(getResources().getColor(R.color.selected_item_color));
            startActivity(new Intent(this, MenuActivity.class));
        });
    }

    private void setAlternativeProfilePhoto(ImageView iv) {
        Glide.with(this)
                .load(DrawableUtils.bitmapToByte(DrawableUtils.drawableToBitmap(getResources().getDrawable(R.drawable.google_logo))))
                .override(Utils.dpToPx(128), Utils.dpToPx(128))
                .into(iv);
    }

    private void initializeFoldingList() {
        ListItem item = new ListItem();
        databaseLoadCourses();
        final List<ListItem> items = item.getActualListOfAllItems();

        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items, true, null);

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
                ListItem.clearListOfItems();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    data.add(snapshot.getValue(Course.class));
                    addCourseToListOfItems(snapshot.getValue(Course.class));
                    ((BaseAdapter) foldingListView.getAdapter()).notifyDataSetChanged();
                }
//                addCourseToListOfItems(c);
//                data.stream().map(ListItem::dataSnapshotToCourse).filter(ListItem::unPresent).forEach(c -> {
//                    addCourseToListOfItems(c);
//                    ((BaseAdapter) foldingListView.getAdapter()).notifyDataSetChanged();
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
