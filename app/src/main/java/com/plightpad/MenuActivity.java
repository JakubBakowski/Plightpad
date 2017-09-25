package com.plightpad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.ldoublem.loadingviewlib.view.LVBlock;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.typeicons_typeface_library.Typeicons;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.plightpad.adapters.BallListAdapter;
import com.plightpad.adapters.FoldingCellListAdapter;
import com.plightpad.controllers.SugarBallsController;
import com.plightpad.controllers.SugarCoursesController;
import com.plightpad.items.ListItem;
import com.plightpad.sugardomain.BallSugar;
import com.plightpad.sugardomain.CourseSugar;
import com.plightpad.tasks.CompressAndSaveBallImageTask;
import com.plightpad.tools.DialogUtils;
import com.plightpad.tools.DrawableUtils;
import com.plightpad.tools.GalleryUtils;
import com.plightpad.tools.IconUtils;
import com.plightpad.tools.Utils;
import com.ramotion.foldingcell.FoldingCell;

import com.shawnlin.numberpicker.NumberPicker;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;
import mehdi.sakout.fancybuttons.FancyButton;

import static android.widget.Toast.makeText;
import static com.google.common.util.concurrent.Runnables.doNothing;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivity";

    private static final int PLIGHTS_COUNT = 3;
    @BindView(R.id.app_bar)
    public AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        initUI();
        initializeBoomMenu();
        initializeViewStates();
    }

    private void initializeViewStates() {
        addBallPhotoBtn.setOnClickListener(s -> GalleryUtils.pickPhoto(this));
        saveBallBtn.setOnClickListener(s -> saveBall());
        editBallRelativeLayout.setVisibility(View.GONE);
        editBallTitle.setText(getResources().getString(R.string.add_ball));
    }

    private void initializeBoomMenu() {
        IconicsDrawable iconMain = new IconicsDrawable(this)
                .icon(Typeicons.Icon.typ_arrow_repeat)
                .contourColor(Color.BLACK)
                .color(getResources().getColor(R.color.white))
                .sizeDp(24);
        IconicsDrawable iconGoForward = new IconicsDrawable(this)
                .icon(Typeicons.Icon.typ_directions)
                .color(getResources().getColor(R.color.white))
                .sizeDp(14);
        IconicsDrawable iconRound = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_bullseye)
                .color(getResources().getColor(R.color.white))
                .sizeDp(14);
        IconicsDrawable iconRoundnc = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_user_plus)
                .color(getResources().getColor(R.color.white))
                .sizeDp(14);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
        bmb.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Bottom);
        bmb.setButtonBottomMargin(240);
        bmb.setNormalColor(getResources().getColor(R.color.guillotine_background));
        bmb.setButtonHorizontalMargin(100);
        bmb.setBoomEnum(BoomEnum.RANDOM);
        bmb.setUnableColor(getResources().getColor(R.color.th3));
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int i, BoomButton boomButton) {

            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

                bmb.setNormalColor(getResources().getColor(R.color.guillotine_background));
                bmb.setDotRadius(10);
            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {
                bmb.setNormalColor(getResources().getColor(R.color.transparent));
                bmb.setDotRadius(0);
            }

            @Override
            public void onBoomDidShow() {

            }
        });
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        bmb.addBuilder(new TextOutsideCircleButton.Builder()
                .normalImageDrawable(iconGoForward)
                .textSize(18)
                .normalColor(Color.parseColor(colors[0]))
                .normalText(getResources().getString(R.string.FAB_forward))
                .imagePadding(new Rect(Util.dp2px(7), Util.dp2px(7), Util.dp2px(7), Util.dp2px(7)))
                .listener(s -> startActivity(new Intent(this, SearchCoursesActivity.class)))
        );
        bmb.addBuilder(new TextOutsideCircleButton.Builder()
                .normalImageDrawable(IconUtils.getBallIconWhite(this))
                .textSize(18)
                .imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalText(getResources().getString(R.string.add_ball_text))
                .listener(s -> showBallAddingDialog())
                .normalColor(Color.parseColor(colors[1]))
        );

        bmb.addBuilder(new TextOutsideCircleButton.Builder()
                        .normalImageDrawable(iconRound)
                        .normalText(getResources().getString(R.string.FAB_new_round))
                        .normalColor(Color.parseColor(colors[2]))

                        .textSize(18)
                        .imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                        .listener(i -> {
                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
                            View customView = inflater.inflate(R.layout.number_chooser, null);
                            new MaterialStyledDialog.Builder(this)
                                    .setTitle(getResources().getString(R.string.set_number_of_players))
                                    .setCustomView(customView)
                                    .setIcon(iconRoundnc)
                                    .withDialogAnimation(true)
                                    .setScrollable(true)
                                    .setPositiveText(getResources().getString(R.string.FAB_apply_new_round))
                                    .setNegativeText(getResources().getString(R.string.FAB_cancer))
                                    .setCancelable(true)
                                    .withDivider(true)
                                    .onNegative((d, p) -> doNothing())
                                    .onPositive((d, p) -> {
                                        Intent in = new Intent(this, RoundSettingsActivity.class);
                                        NumberPicker numberPicker = (NumberPicker) customView.findViewById(R.id.number_picker);
                                        in.putExtra("number_picker_value", numberPicker.getValue());
                                        startActivity(in);
                                    })
                                    .show();

                        })
        );
    }


    private void setAlternativeProfilePhoto(ImageView iv) {
        Glide.with(this)
                .load(DrawableUtils.bitmapToByte(DrawableUtils.drawableToBitmap(getResources().getDrawable(R.drawable.google_logo))))
                .override(Utils.dpToPx(128), Utils.dpToPx(128))
                .into(iv);
    }

    @Override
    public void onBackPressed() {
        if (addBallToggle) {
            hideAddBallWindow();
        } else {
            DialogUtils.showLogoutDialog(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (courseListView != null) {
            initializeFoldingList(null);
        }
    }

    private ListView courseListView;

    private void initializeFoldingList(View view) {
        ListItem item = new ListItem();
        List<CourseSugar> courseList = SugarCoursesController.getAllSugarCourses();
        item.clearListOfItems();
        item.addSugarCourses(courseList);
        final List<ListItem> items = item.getActualListOfAllItems();

        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items, false, courseList);
        if (view != null) {
            courseListView = (ListView) view.findViewById(R.id.list);
        }
        adapter.setDefaultRequestBtnClickListener(a -> makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show());
        courseListView.setAdapter(adapter);

        courseListView.setOnItemClickListener((adapterView, v, pos, l) -> {
            ((FoldingCell) v).toggle(false);
            adapter.registerToggle(pos);
        });
    }

    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return PLIGHTS_COUNT;
            }


            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.menu_course_list, null, false);
                switch (position) {
                    case 0: {
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.menu_course_list, null, false);
                        initializeFoldingList(view);
                        break;
                    }
                    case 1: {
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.menu_ball_list, null, false);
                        initializeBallsList(view);
                        break;
                    }
                    case 2: {
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_profile, null, false);
                        initializeProfileSettings(view);
                        break;
                    }
                }
                container.addView(view);
                return view;
            }
        });
        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        navigationTabBar.setInactiveColor(Color.WHITE);
        models.add(
                new NavigationTabBar.Model.Builder(
                        IconUtils.getCourseIcon(this),
                        Color.parseColor(colors[0]))
                        .title(getResources().getString(R.string.course))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        IconUtils.getBallIconGreen(this),
                        Color.parseColor(colors[1]))
                        .title(getResources().getString(R.string.ball))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        IconUtils.getProfileIcon(this),
                        Color.parseColor(colors[2]))
                        .title(getResources().getString(R.string.my_profile))
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
                appBarLayout.setBackgroundColor(model.getColor());
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                appBarLayout.setBackgroundColor(model.getColor());
            }
        });
        viewPager.setCurrentItem(0);
    }

    private RecyclerView ballListView;

    public void initializeBallsList(View view) {
        List<BallSugar> ballSugars = SugarBallsController.getAllBalls();
        BallListAdapter bla = new BallListAdapter(this, ballSugars, false);
        if (view != null) {
            ballListView = (RecyclerView) view.findViewById(R.id.list);
        }
        ballListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        ballListView.setLayoutManager(mLayoutManager);
        ballListView.setAdapter(bla);
    }

    private void initializeProfileSettings(View view) {
        CircleImageView profileImageView = (CircleImageView) view.findViewById(R.id.profile_photo_image_view);
        FancyButton profileSettings = (FancyButton) view.findViewById(R.id.edit_settings_btn);

        TextView profileName = (TextView) view.findViewById(R.id.profile_name_tv);

        try {
            profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        } catch (NullPointerException e) {
            profileName.setText(getResources().getString(R.string.default_name));
        }
        Uri uri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        if (uri != null) {
            Glide.with(this)
                    .load(uri)
                    .into(profileImageView);
        } else {
            Glide.with(this)
                    .load(DrawableUtils.bitmapToByte(DrawableUtils.drawableToBitmap(getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_portrait))))
                    .asBitmap()
                    .into(profileImageView);
        }
    }

    @BindView(R.id.edit_ball_main_layout)
    RelativeLayout editBallRelativeLayout;

    @BindView(R.id.edit_ball_title)
    TextView editBallTitle;

    @BindView(R.id.add_ball_photo)
    FancyButton addBallPhotoBtn;

    @BindView(R.id.save_ball)
    FancyButton saveBallBtn;

    @BindView(R.id.own_ball_name)
    TextInputLayout ballOwnNameTil;

    @BindView(R.id.ball_model)
    TextInputLayout ballModelTil;

    @BindView(R.id.ball_weight)
    TextInputLayout ballWeightTil;

    @BindView(R.id.ball_manufacturer)
    TextInputLayout ballManufacturerTil;

    @BindView(R.id.ball_size)
    TextInputLayout ballSizeTil;

    @BindView(R.id.ball_hardness)
    TextInputLayout ballHardnessTil;

    private boolean addBallToggle;
    private Bitmap ballPhoto;

    private void showBallAddingDialog() {
        com.plightpad.tools.AnimationUtils.animateFadeIn(this, editBallRelativeLayout);
        editBallRelativeLayout.setVisibility(View.VISIBLE);
        addBallToggle = true;
    }

    private void hideAddBallWindow() {
        com.plightpad.tools.AnimationUtils.animateFadeOut(this, editBallRelativeLayout);
        addBallToggle = false;
    }

    private void saveBall() {
        if (validateBallForm()) {
            BallSugar bs = new BallSugar(
                    ballOwnNameTil.getEditText().getText() != null ? ballOwnNameTil.getEditText().getText().toString() : "",
                    ballManufacturerTil.getEditText().getText() != null ? ballManufacturerTil.getEditText().getText().toString() : "",
                    ballModelTil.getEditText().getText() != null ? ballManufacturerTil.getEditText().getText().toString() : "",
                    Double.valueOf(ballWeightTil.getEditText().getText().toString()),
                    Double.valueOf(ballSizeTil.getEditText().getText().toString()),
                    Double.valueOf(ballHardnessTil.getEditText().getText().toString()));
            SugarBallsController.saveBall(bs);
            if (ballPhoto != null) {
                showProgressDialog();
                new CompressAndSaveBallImageTask(MenuActivity.this, ballPhoto, bs, false).execute();
            } else {
                initializeBallsList(null);
            }
            hideAddBallWindow();
        }
    }

    private boolean validateBallForm() {
        if (ballOwnNameTil.getEditText().getText().toString().equals("") && ballModelTil.getEditText().getText().toString().equals("")) {
            DialogUtils.showBasicMessageDialog(this, getResources().getString(R.string.edit_ball_failed), false);
            return false;
        } else {
            return true;
        }
    }

    @BindView(R.id.loading_login_activity)
    LVBlock loadingIndicator;

    @BindView(R.id.loading_relative_layout)
    RelativeLayout loadingLayout;

    public void showProgressDialog() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);
        loadingIndicator.setViewColor(getResources().getColor(R.color.thC));
        loadingIndicator.isShadow(true);
        loadingIndicator.setShadowColor(getResources().getColor(R.color.semi_transparent_black));
        loadingIndicator.startAnim();
    }

    public void hideProgressDialog() {
        loadingLayout.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
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
                            ballPhoto = bitmap;
                        }
                    });
        }
    }

}
