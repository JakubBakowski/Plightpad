package com.plightpad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.shawnlin.numberpicker.NumberPicker;

import devlight.io.library.ntb.NavigationTabBar;

import java.util.ArrayList;

import static android.widget.Toast.makeText;
import static com.google.common.util.concurrent.Runnables.doNothing;


public class MenuActivityTest extends Activity {
    //    @BindView(R.id.list)
//    public ListView foldingListView;
    final static private int PLIGHTS_COUNT = 3;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);

        initializeBoomMenu();
        initUI();
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
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.edit_the_ball, null, false);
                switch (position) {
                    case 0: {
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.edit_the_ball, null, false);
                        break;
                    }
                    case 1: {
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.edit_the_ball, null, false);
                        break;
                    }
                    case 2: {
//                        initializeBoomMenu();
//                        initializeFoldingList();
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.edit_the_ball, null, false);
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
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.plus),
                        Color.parseColor(colors[0]))
                        .title("Heart")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.plus),
                        Color.parseColor(colors[1]))
                        .title("Cup")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.plus),
                        Color.parseColor(colors[2]))
                        .title("Diploma")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);


    }

    //    private void initializeFoldingList() {
//        ListItem item = new ListItem();
////        databaseLoadCourses();
//        List<Course> courseList = CoursesController.getAllCourses();
////        item.addCourseToListOfItems();
//        item.clearListOfItems();
//        item.addSugarCourses(courseList);
//        final List<ListItem> items = item.getActualListOfAllItems();
//
//        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items, false, courseList);
//
//        adapter.setDefaultRequestBtnClickListener(a -> makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show());
//        foldingListView.setAdapter(adapter);
//
//        foldingListView.setOnItemClickListener((adapterView, view, pos, l) -> {
//            ((FoldingCell) view).toggle(false);
//            adapter.registerToggle(pos);
//        });
//    }
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
        bmb.addBuilder(new TextOutsideCircleButton.Builder()
                        .normalImageDrawable(iconMain)
                        .textSize(18)
                        .imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                        .normalText("Reset kolorow")
                        .listener(s -> startActivity(new Intent(this, MenuActivity.class)))

        );
        bmb.addBuilder(new TextOutsideCircleButton.Builder()
                        .normalImageDrawable(iconGoForward)
                        .textSize(18)
//                .isRound(false)
//                .shadowCornerRadius(Util.dp2px(20))
//                .buttonCornerRadius(Util.dp2px(20))
                        .normalText(getResources().getString(R.string.FAB_forward))
                        .imagePadding(new Rect(Util.dp2px(7), Util.dp2px(7), Util.dp2px(7), Util.dp2px(7)))
                        .listener(s -> startActivity(new Intent(this, SearchCoursesActivity.class)))
        );

        bmb.addBuilder(new TextOutsideCircleButton.Builder()
//                .isRound(false)
//                .shadowCornerRadius(Util.dp2px(20))
//                .buttonCornerRadius(Util.dp2px(20))
                        .normalImageDrawable(iconRound)
                        .normalText(getResources().getString(R.string.FAB_new_round))
                        .textSize(18)
                        .imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                        .listener(i -> {
                            Intent in = new Intent(this, RoundSettingsActivity.class);
                            in.putExtra("number_picker_value", 3);
                            startActivity(in);
                        })
        );
    }
}
