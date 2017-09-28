package com.plightpad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.plightpad.adapters.AdminListAdapter;
import com.plightpad.boxdomain.Course;
import com.plightpad.boxdomain.Lane;
import com.plightpad.boxdomain.SurfaceType;
import com.plightpad.tools.DrawableUtils;
import com.plightpad.tools.FirebaseReferences;
import com.plightpad.tools.Utils;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mehdi.sakout.fancybuttons.FancyButton;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = "AdminActivity";

    private static final int NUMBER_OF_LANES = 18;

    private ArrayList<Uri> pickedImagePaths;
    private List<String> laneTitles;
    private List<Bitmap> bitmaps;

    @BindView(R.id.admin_add_new_course_lanes_photos_btn)
    FancyButton pickLanesImagesBtn;

    @BindView(R.id.admin_add_new_course_photo_btn)
    FancyButton pickCourseImageBtn;

    @BindView(R.id.admin_list_view)
    RecyclerView recyclerView;

    @BindView(R.id.admin_add_ready_course)
    FancyButton addReadyCourseBtn;

    @BindView(R.id.admin_course_image_view)
    ImageView courseImageView;

    @BindView(R.id.admin_add_address_name)
    TextInputLayout tilAddress;

    @BindView(R.id.admin_add_city)
    TextInputLayout tilCity;

    @BindView(R.id.admin_add_course_name)
    TextInputLayout tilCourseName;

    @BindView(R.id.admin_add_country_name)
    TextInputLayout tilCountry;

    @BindView(R.id.admin_add_lanes_length)
    TextInputLayout tilLanesLength;

    @BindView(R.id.admin_add_best_score)
    TextInputLayout tilBestScore;

    private Uri courseImageUri;
    private LinearLayoutManager mLayoutManager;
    private AdminListAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        initializeLists();
        initializeListeners();
    }

    private void initializeLists() {
        pickedImagePaths = new ArrayList<>();
        laneTitles = new ArrayList<>();
        bitmaps = new ArrayList<>();
    }

    private void initializeListeners() {
        addReadyCourseBtn.setVisibility(View.GONE);
        pickLanesImagesBtn.setOnClickListener(s -> pickImageFromGalleryForLanes());
        pickCourseImageBtn.setOnClickListener(s -> pickImageFromGalleryForCourse());
        addReadyCourseBtn.setOnClickListener(s -> validateForm());
    }

    private void initializeListView() {
        if (laneTitles.isEmpty()) {
            for (int i = 0; i < NUMBER_OF_LANES; i++) {
                laneTitles.add(getResources().getString(R.string.lane_for_admin_list_view) + " " + String.valueOf(i + 1));
            }
        }
        if (!pickedImagePaths.isEmpty()) {
            Observable<Uri> bitmapsObservable = Observable.fromArray(pickedImagePaths.toArray(new Uri[pickedImagePaths.size()]));
            bitmapsObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Uri>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) { }
                        @Override
                        public void onNext(@NonNull Uri uri) {
                            try {
                                bitmaps.add(DrawableUtils.getResizedBitmap(MediaStore.Images.Media.getBitmap(AdminActivity.this.getContentResolver(), uri), Utils.dpToPx(128), Utils.dpToPx(128)));
                            } catch (IOException e) {
                                Log.d(TAG, "Nie powiodło się");
                                Toast.makeText(AdminActivity.this, getResources().getString(R.string.admin_adding_image_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(TAG, "Nie powiodło się");
                            Toast.makeText(AdminActivity.this, getResources().getString(R.string.admin_adding_image_failed), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onComplete() {
                            adapter = new AdminListAdapter(AdminActivity.this, laneTitles, bitmaps, NUMBER_OF_LANES);
                            recyclerView.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(AdminActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(adapter);
                        }
        });
        } else {
            Toast.makeText(this, "Nie wyjszło", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImageFromGalleryForLanes() {
        pickedImagePaths = new ArrayList<>();
        FishBun.with(AdminActivity.this)
                .MultiPageMode()
                .setPickerCount(50)
                .setPickerSpanCount(4)
                .setActionBarColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"), true)
                .setActionBarTitleColor(Color.parseColor("#000000"))
                .setArrayPaths(pickedImagePaths)
                .setAlbumSpanCount(1, 2)
                .setButtonInAlbumActivity(true)
                .setCamera(true)
                .setMinCount(NUMBER_OF_LANES)
                .setMaxCount(NUMBER_OF_LANES)
                .exceptGif(true)
                .setReachLimitAutomaticClose(true)
                .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.ic_activity_active))
                .setOkButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_a_photo_gray_100dp))
                .setAllViewTitle("Wszystkie Twoje zdjęcia")
                .setActionBarTitle("Pick photos")
                .textOnImagesSelectionLimitReached("You can't select any more.")
                .textOnNothingSelected("I need a photo!")
                .startAlbum();
    }

    private void pickImageFromGalleryForCourse() {
        pickedImagePaths = new ArrayList<>();
        FishBun.with(AdminActivity.this)
                .MultiPageMode()
                .setPickerCount(50)
                .setPickerSpanCount(4)
                .setActionBarColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"), true)
                .setActionBarTitleColor(Color.parseColor("#000000"))
                .setArrayPaths(pickedImagePaths)
                .setAlbumSpanCount(1, 2)
                .setButtonInAlbumActivity(true)
                .setCamera(true)
                .setMinCount(1)
                .setMaxCount(1)
                .exceptGif(true)
                .setReachLimitAutomaticClose(true)
                .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.ic_activity_active))
                .setOkButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_a_photo_gray_100dp))
                .setAllViewTitle("Wszystkie Twoje zdjęcia")
                .setActionBarTitle("Pick photos")
                .textOnImagesSelectionLimitReached("You can't select any more.")
                .textOnNothingSelected("I need a photo!")
                .startAlbum();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    pickedImagePaths = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    if (pickedImagePaths.size() == NUMBER_OF_LANES) {
                        initializeListView();
                        pickLanesImagesBtn.setVisibility(View.GONE);
                        addReadyCourseBtn.setVisibility(View.VISIBLE);
                    } else {
                        try {
                            Glide.with(this)
                                    .load(DrawableUtils.bitmapToByte(DrawableUtils.getResizedBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImagePaths.get(0)), Utils.dpToPx(128), Utils.dpToPx(128))))
                                    .override(Utils.dpToPx(128), Utils.dpToPx(128))
                                    .into(courseImageView);
                            courseImageUri = pickedImagePaths.get(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
        }
    }

    public void validateForm() {
        if (adapter.getEditTextStringsList().size() == NUMBER_OF_LANES
                && pickedImagePaths.size() == NUMBER_OF_LANES
                && tilCity.getEditText().getText() != null
                && !tilCountry.getEditText().getText().toString().isEmpty()
                && !tilBestScore.getEditText().getText().toString().isEmpty()
                && !tilCourseName.getEditText().getText().toString().isEmpty()
                && !tilLanesLength.getEditText().getText().toString().isEmpty()
                && !tilAddress.getEditText().getText().toString().isEmpty()) {
            saveCourseIntoFirebase();

        } else {
            Toast.makeText(this, getResources().getString(R.string.admin_add_course_not_valid_names), Toast.LENGTH_SHORT).show();
        }
    }

    private static final String JPEG_EXTENSION = ".jpg";

    public void saveCourseIntoFirebase() {
        DatabaseReference courseReference = databaseReference.child(FirebaseReferences.COURSE_REFERENCE).push();
        List<Lane> lanes = new ArrayList<>();
        List<String> laneNames = adapter.getEditTextStringsList();
        String extension;
        try {
            extension = courseImageUri.toString().substring(courseImageUri.toString().lastIndexOf("."));
        } catch(Exception e){
            extension = JPEG_EXTENSION;
        }
        List<String> imagePaths = getFirebaseStorageImagePaths(courseReference.getKey(), extension);
        int laneCounter = 1;
        for (String laneName : laneNames) {
            lanes.add(new Lane(laneName, laneCounter, imagePaths.get(laneCounter - 1)));
            laneCounter++;
        }
        String destinationImagePath = new StringBuilder(FirebaseReferences.COURSE_IMAGES_REFERENCE)
                .append("/")
                .append(courseReference.getKey())
                .append(extension)
                .toString();
        Course course = new Course(
                tilCourseName.getEditText().getText().toString(),
                tilCity.getEditText().getText().toString(),
                tilCountry.getEditText().getText().toString(),
                destinationImagePath,
                Integer.valueOf(tilBestScore.getEditText().getText().toString()), tilAddress.getEditText().getText().toString(),
                SurfaceType.CONCRETE, Double.valueOf(tilLanesLength.getEditText().getText().toString()),
                courseReference.getKey(), lanes);
        course.lanes.addAll(lanes);
        courseReference.setValue(course).addOnSuccessListener(s -> goBackToMenu());
        StorageReference sRef = FirebaseStorage.getInstance().getReference().child(destinationImagePath);
        sRef.putFile(courseImageUri);
    }

    private void goBackToMenu() {
        Toast.makeText(this, getResources().getString(R.string.admin_successful_course_adding), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MenuActivity.class));
    }

    public List<String> getFirebaseStorageImagePaths(String coursePushedKey, String extension) {
        List<String> imagePaths = new ArrayList<>();
        StorageReference sRef = FirebaseStorage.getInstance().getReference();
        int i = 0;
        String stringUriTmp;
        for (Uri u : pickedImagePaths) {
            stringUriTmp = getLaneImagesStoragePath(coursePushedKey, String.valueOf(i+1) + extension);
            imagePaths.add(stringUriTmp);
            sRef.child(stringUriTmp).putFile(u).addOnFailureListener(s -> Toast.makeText(this, getResources().getString(R.string.admin_adding_image_failed), Toast.LENGTH_SHORT).show());
            i++;
        }
        return imagePaths;
    }

    private String getLaneImagesStoragePath(String key, String imageFile) {
        return new StringBuilder().append(FirebaseReferences.LANES_IMAGES_REFERENCE).append("/").append(key).append("/").append(imageFile).toString();
    }

}
