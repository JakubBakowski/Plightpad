package com.plightpad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.ldoublem.loadingviewlib.view.LVBlock;
import com.plightpad.adapters.BallListAdapter;
import com.plightpad.adapters.LanesListAdapter;
import com.plightpad.controllers.SugarBallsController;
import com.plightpad.controllers.SugarLanesController;
import com.plightpad.firedomain.Course;
import com.plightpad.firedomain.Lane;
import com.plightpad.items.CardDataImpl;
import com.plightpad.items.ItemsCountView;
import com.plightpad.overridenec.ECBackgroundSwitcherViewOverriden;
import com.plightpad.overridenec.ECCardDataOverriden;
import com.plightpad.overridenec.ECPagerViewAdapterOverriden;
import com.plightpad.overridenec.ECPagerViewOverriden;
import com.plightpad.sugardomain.BallSugar;
import com.plightpad.sugardomain.LaneSugar;
import com.plightpad.tasks.CompressAndSaveNotesImageTask;
import com.plightpad.tools.AnimationUtils;
import com.plightpad.tools.DialogUtils;
import com.plightpad.tools.GalleryUtils;
import com.plightpad.tools.IconUtils;
import com.plightpad.tools.IntentTags;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Setter;
import mehdi.sakout.fancybuttons.FancyButton;

public class LanesActivity extends AppCompatActivity {

    private static final int NUMBER_OF_LANES = 18;

    @BindView(R.id.ec_pager_element)
    public ECPagerViewOverriden ecPagerView;

    @BindView(R.id.ec_bg_switcher_element)
    ECBackgroundSwitcherViewOverriden ecBackgroundSwitcherView;

    @BindView(R.id.items_count_view)
    public ItemsCountView itemsCountView;

    @BindView(R.id.choose_the_ball_for_lane)
    RelativeLayout ballListLayout;

    @BindView(R.id.list)
    RecyclerView ballListView;

    private boolean ballListToggled;
    private List<Bitmap> bitmaps;
    private List<Lane> courseLanes;
    private ECPagerViewAdapterOverriden adapter;
    private List<LaneSugar> courseLanesSugar;
    private boolean isSearch;
    @Setter
    private boolean choosingNotesPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanes);
        ButterKnife.bind(this);

        initializeActivity();
    }

    private void initializeActivity() {
        initializeLanesData();
        initializeLaneBitmaps(courseLanes);
        hideProgressDialog();
        LanesActivity.this.initializeCardView();
        initializeListeners();
        initializeBallsList();
    }

    private void initializeLanesData() {
        isSearch = getIntent().getExtras().getBoolean(IntentTags.IS_SEARCH_COURSE_ACTIVITY);
        bitmaps = new ArrayList<>();
        courseLanes = new ArrayList<>();
        if (isSearch) {
            courseLanes = ((Course) getIntent().getSerializableExtra(IntentTags.COURSE)).getLanes();
        } else {
            Long courseSugar = getIntent().getLongExtra("COURSE_SUGAR_ID", 1L);
            courseLanesSugar = SugarLanesController.getSugarLanesByCourseId(courseSugar);
            courseLanes = Lane.parseLaneSugarList(courseLanesSugar);
        }
        for (int i = 0; i < NUMBER_OF_LANES; i++) {
            bitmaps.add(null);
        }
    }

    private Bitmap ballPhoto;
    private Bitmap notesPhoto;

    private void initializeListeners() {
        attachPhotoToBallBtn.setOnClickListener(s -> GalleryUtils.pickPhoto(this));
        saveBallBtn.setOnClickListener(s -> saveBall(getActualLaneSugar()));
    }

    private LaneSugar getActualLaneSugar() {
        for (LaneSugar laneSugar : courseLanesSugar) {
            if (laneSugar.getNumber() == actualLane) {
                return laneSugar;
            }
        }
        return null;
    }

    private void initializeLaneBitmaps(final List<Lane> finalCourseLanes) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        for (Lane s : finalCourseLanes) {
            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(s.getImagePath()))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(metrics.widthPixels, metrics.heightPixels) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            bitmaps.set(s.getNumber() - 1, bitmap); // Do something with bitmap here.
                            updateImages(s.getNumber() - 1, bitmap);
                        }
                    });
        }
    }

    public void updateImages(int position, Bitmap bitmap) {
        if (bitmap != null) {
            CardDataImpl cdi = (CardDataImpl) dataset.get(position);
            cdi.setHeadBackgroundResource(bitmap);
            cdi.setMainBackgroundResource(bitmap);
            dataset.set(position, cdi);
        }
        adapter = initializeECPagerViewAdapter();
        ecPagerView.setPagerViewAdapter(adapter);
        ecBackgroundSwitcherView.invalidate();
        ecPagerView.invalidate();
//        NIE JESTEM PEWIEN CZY NIE WYSTARCZY TYLKO TO, POTESTUJE POZNIEJ
//        adapter.notifyDataSetChanged();
    }

    private List<ECCardDataOverriden> dataset;
    private int actualLane = 1;

    private void initializeCardView() {
        AtomicInteger ac = new AtomicInteger(0);
        dataset = new ArrayList<>();
        if (!courseLanes.isEmpty()) {
            for (Lane s : courseLanes) {
                Bitmap b1 = bitmaps.get(ac.get());
                Bitmap b2 = bitmaps.get(ac.get());
                if (courseLanesSugar != null) {
                    dataset.add(new CardDataImpl(s.getName(), s.getName(), s.getName(), b1, b2, courseLanesSugar.get(ac.getAndIncrement())));
                } else {
                    dataset.add(new CardDataImpl(s.getName(), s.getName(), s.getName(), b1, b2, new LaneSugar()));
                    ac.getAndIncrement();
                }
            }
        }
        adapter = initializeECPagerViewAdapter();
        LanesActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ecPagerView.setPagerViewAdapter(adapter);
                ecPagerView.setBackgroundSwitcherView(ecBackgroundSwitcherView);
                ecPagerView.setOnCardSelectedListener(new ECPagerViewOverriden.OnCardSelectedListener() {
                    @Override
                    public void cardSelected(int newPosition, int oldPosition, int totalElements) {
                        actualLane = newPosition + 1;
                        itemsCountView.update(newPosition, oldPosition, totalElements);
                    }
                });
            }
        });
    }

    private ECPagerViewAdapterOverriden initializeECPagerViewAdapter() {
        return new ECPagerViewAdapterOverriden(this, dataset) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, ListView list, final ECCardDataOverriden data) {
                final CardDataImpl cardData = (CardDataImpl) data;
                List<CardDataImpl> cardDatas = new ArrayList<>();
                cardDatas.add(cardData);
//                 Create adapter for list inside a card and set adapter to card content
                LanesListAdapter lanesData = new LanesListAdapter(LanesActivity.this, cardDatas);
                list.setAdapter(lanesData);
                list.setDivider(getResources().getDrawable(R.drawable.list_divider));
//                list.setDividerHeight((int) Utils.pxFromDp(getApplicationContext(), 0.5f));
                list.setDividerHeight(0);
                list.setSelector(R.color.transparent);
                list.setBackgroundColor(Color.WHITE);
                list.setCacheColorHint(Color.TRANSPARENT);

                // Add gradient to root header view
                View gradient = new View(getApplicationContext());
                gradient.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
                gradient.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_head_gradient));
                head.addView(gradient);

                // Inflate main header layout and attach it to header root view
                inflaterService.inflate(R.layout.header_card_lanes, head);

                // Set header data from data object
                TextView title = (TextView) head.findViewById(R.id.lane_title);
                title.setText(cardData.getCardTitle());
                CircleImageView ballImageView = (CircleImageView) head.findViewById(R.id.ball_image_view);
                if (cardData.getLane().getBall() != null) {
                    if (cardData.getLane().getBall().getImagePath() != null) {
                        new Thread(new LoadImagesFromStorage(LanesActivity.this, cardData.getLane().getBall().getImagePath(), ballImageView)).start();
                    } else {
                        ballImageView.setImageBitmap(IconUtils.getBallIconGreen(LanesActivity.this).toBitmap());
                    }
                } else {
                    ballImageView.setImageBitmap(IconUtils.getBallIconGreen(LanesActivity.this).toBitmap());
                }
                TextView message = (TextView) head.findViewById(R.id.ball_name);
                if (cardData.getLane().getBall() != null) {
                    if (cardData.getLane().getBall().getMyName() != null) {
                        message.setText(cardData.getLane().getBall().getMyName());
                    } else {
                        message.setText(getResources().getString(R.string.no_ball_added));
                    }
                } else {
                    message.setText(getResources().getString(R.string.no_ball_added));
                }

                // Add onclick listener to card header for toggle card state
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (!isSearch) {
                            ecPagerView.toggle();
                        }
                    }
                });
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (isEditDisplayed) {
            hideEditBallDialog();
        } else if (ballListToggled) {
            hideBallListView();
        } else if (!ecPagerView.collapse()) {
            super.onBackPressed();
        }
    }

    @BindView(R.id.loading_login_activity)
    LVBlock loadingIndicator;

    @BindView(R.id.loading_relative_layout)
    RelativeLayout loadingLayout;

    @BindView(R.id.edit_the_ball_whole_layout)
    RelativeLayout editBallLayout;

    @BindView(R.id.edit_the_ball_whole_layout_inner)
    RelativeLayout editBallLayoutInner;

    @BindView(R.id.ec_view_layout)
    RelativeLayout ecViewsLayout;

    @BindView(R.id.save_ball)
    FancyButton saveBallBtn;

    @BindView(R.id.add_ball_photo)
    FancyButton attachPhotoToBallBtn;

    private boolean isEditDisplayed;

    public void hideProgressDialog() {
        loadingLayout.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
    }

    public void hideEditBallDialog() {
        AnimationUtils.animateFadeOut(this, editBallLayout);
        AnimationUtils.animateScaleOut(this, editBallLayoutInner);
        isEditDisplayed = false;
        ecViewsLayout.setEnabled(true);
    }

    @BindView(R.id.own_ball_name)
    public TextInputLayout ballOwnNameTil;

    @BindView(R.id.ball_size)
    public TextInputLayout ballSizeTil;

    @BindView(R.id.ball_weight)
    public TextInputLayout ballWeightTil;

    @BindView(R.id.ball_manufacturer)
    public TextInputLayout ballManufacturerTil;

    @BindView(R.id.ball_hardness)
    public TextInputLayout ballHardnessTil;

    @BindView(R.id.ball_model)
    public TextInputLayout ballModelTil;

    private void saveBall(LaneSugar lane) {
        if (validateBallForm()) {
            BallSugar bs = new BallSugar(
                    ballOwnNameTil.getEditText().getText() != null ? ballOwnNameTil.getEditText().getText().toString() : "",
                    ballManufacturerTil.getEditText().getText() != null ? ballManufacturerTil.getEditText().getText().toString() : "",
                    ballModelTil.getEditText().getText() != null ? ballManufacturerTil.getEditText().getText().toString() : "",
                    Double.valueOf(ballWeightTil.getEditText().getText().toString()),
                    Double.valueOf(ballSizeTil.getEditText().getText().toString()),
                    Double.valueOf(ballHardnessTil.getEditText().getText().toString()));
            saveLaneAndBall(bs, lane);
        }
    }

    private void saveLaneAndBall(BallSugar bs, LaneSugar lane) {
        lane.setBall(bs);
        SugarBallsController.saveBall(bs);
        SugarLanesController.updateLaneBall(lane);
        ecPagerView.toggle();
        updateImages(1, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String filePath = ((List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH)).get(0);
            Glide.with(this)
                    .load(new File(filePath))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            if(choosingNotesPhoto){
                                notesPhoto = bitmap;
                                choosingNotesPhoto = false;
                                saveNotesPhotoAndAttachToTheLane();
                                ecPagerView.toggle();
                            } else {
                                ballPhoto = bitmap;
                            }
                        }
                    });
        }
    }

    private void saveNotesPhotoAndAttachToTheLane() {
        new CompressAndSaveNotesImageTask(this, notesPhoto, getActualLaneSugar()).execute();
    }

    private boolean validateBallForm() {
        if (ballOwnNameTil.getEditText().getText().toString().equals("") && ballModelTil.getEditText().getText().toString().equals("")) {
            DialogUtils.showBasicMessageDialog(this, getResources().getString(R.string.edit_ball_failed), false);
            return false;
        } else {
            return true;
        }
    }

    public class LoadImagesFromStorage implements Runnable {

        private Context context;
        private String path;
        private ImageView destinationImageView;

        private LoadImagesFromStorage(Context context, String filePath, ImageView destinationImageView) {
            this.context = context;
            this.path = filePath;
            this.destinationImageView = destinationImageView;
        }

        @Override
        public void run() {
            try {
                FileInputStream fis = context.openFileInput(path);
                byte[] buffer = new byte[(int) fis.getChannel().size()];
                fis.read(buffer);
                LanesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(LanesActivity.this)
                                .load(buffer)
                                .asBitmap()
                                .fitCenter()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        destinationImageView.setImageBitmap(resource);
                                        LanesActivity.this.ecPagerView.invalidate();
                                        LanesActivity.this.ecBackgroundSwitcherView.invalidate();
                                    }
                                });
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void showBallListDialog() {
        ballListToggled = true;
        ballListLayout.setVisibility(View.VISIBLE);
        AnimationUtils.animateFadeIn(this, ballListLayout);
    }

    public void hideBallListView() {
        AnimationUtils.animateFadeOut(this, ballListLayout);
        ballListToggled = false;
    }

    public void initializeBallsList() {
        List<BallSugar> ballSugars = SugarBallsController.getAllBalls();
        BallListAdapter bla = new BallListAdapter(this, ballSugars, true);
        ballListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        ballListView.setLayoutManager(mLayoutManager);
        ballListView.setAdapter(bla);
    }

    public void saveBallToActualLane(BallSugar bs) {
        saveLaneAndBall(bs, getActualLaneSugar());
        hideBallListView();
    }

    public void loadImageIntoView(String path, ImageView imageView){
        new Thread(new LoadImagesFromStorage(LanesActivity.this, path, imageView)).start();
    }

}
