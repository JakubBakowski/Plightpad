package com.plightpad.adapters;

/**
 * Created by Marcin on 16.07.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plightpad.LanesActivity;
import com.plightpad.R;
import com.plightpad.boxdomain.Lane;
import com.plightpad.repository.CoursesController;
import com.plightpad.data.AlerterContent;
import com.plightpad.boxdomain.Course;
import com.plightpad.tools.DialogUtils;
import com.plightpad.tools.IconUtils;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
import solid.stream.Stream;

import static solid.collectors.ToList.toList;


public class FoldingCellListAdapter extends ArrayAdapter<Course> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private static final String COMMA = ", ";
    private boolean isSearch;

    private Context context;
    private List<Course> courses;

    public FoldingCellListAdapter(Context context, List<Course> objects, boolean isSearch, List<Course> courses) {
        super(context, 0, objects);
        this.context = context;
        this.isSearch = isSearch;
        this.courses = courses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Course course = getItem(position);
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;

        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell_layout, parent, false);
            viewHolder.title = (TextView) cell.findViewById(R.id.title);
            viewHolder.countryCity = (TextView) cell.findViewById(R.id.country_city);
            viewHolder.titleFolded = (TextView) cell.findViewById(R.id.title_Folded);
            viewHolder.cityFolded = (TextView) cell.findViewById(R.id.city_Folded);
            viewHolder.countryFolded = (TextView) cell.findViewById(R.id.country_Folded);
            viewHolder.bestScore = (TextView) cell.findViewById(R.id.bestScore);
            viewHolder.laneLength = (TextView) cell.findViewById(R.id.laneLength);
            viewHolder.type = (TextView) cell.findViewById(R.id.type);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.contentRequestBtn);
            viewHolder.saveCourseToLocalDatabaseBtn = (TextView) cell.findViewById(R.id.saveCourseToLocalDatabaseBtn);
            viewHolder.unfoldedImage = (ImageView) cell.findViewById(R.id.head_image);
            viewHolder.layoutImageBg = (RelativeLayout) cell.findViewById(R.id.relative_inner_cell_content);

            ViewTarget<RelativeLayout, GlideDrawable> viewTarget = new ViewTarget<RelativeLayout, GlideDrawable>(viewHolder.layoutImageBg) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    this.view.setBackgroundDrawable(resource.getCurrent());
                }
            };

            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(course.imagePath))
                    .bitmapTransform(new FitCenter(context), new BlurTransformation(context, 25), new VignetteFilterTransformation(context, new PointF(0.5f, 0.5f),
                            new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f))
                    .into(viewTarget);
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(course.imagePath))
                    .asBitmap()
                    .fitCenter()
                    .into(viewHolder.unfoldedImage);
            cell.setTag(viewHolder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        viewHolder.title.setText(course.name);
        viewHolder.countryCity.setText(course.country + COMMA + course.city);
        viewHolder.type.setText(context.getResources().getString(R.string.type_of_course_folding) + course.surfaceType);
        viewHolder.laneLength.setText(context.getResources().getString(R.string.course_lane_length_folding) + course.lanesLength);
        viewHolder.bestScore.setText(context.getResources().getString(R.string.best_score_folding) + course.bestScore);
        viewHolder.titleFolded.setText(course.name);
        viewHolder.countryFolded.setText(course.country + COMMA);
        viewHolder.cityFolded.setText(course.city);
        viewHolder.contentRequestBtn.setOnClickListener(s -> launchActivity(course, position));
        viewHolder.saveCourseToLocalDatabaseBtn.setOnClickListener(s -> {
            course.lanes.addAll(Stream.stream(course.firebaseLanes).map(lane -> new Lane(lane.name, lane.number, lane.imagePath)).collect(toList()));
            CoursesController.save(course);
            AlerterContent alerterContent = new AlerterContent(
                    context.getResources().getString(R.string.alerter_default_title),
                    context.getResources().getString(R.string.successfull_course_adding),
                    IconUtils.getSuccessIcon(context),
                    ContextCompat.getColor(context, R.color.accept_btn_color)
            );
            DialogUtils.showSimpleAlerter(context, alerterContent);
        });
        if (isSearch) {
            viewHolder.saveCourseToLocalDatabaseBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.saveCourseToLocalDatabaseBtn.setVisibility(View.GONE);
        }
        
        return cell;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    private static class ViewHolder {
        TextView title;
        TextView countryCity;
        TextView type;
        TextView laneLength;
        TextView bestScore;
        TextView link;
        TextView contentRequestBtn;
        TextView saveCourseToLocalDatabaseBtn;
        TextView titleFolded;
        TextView countryFolded;
        TextView cityFolded;
        ImageView foldedImage;
        ImageView unfoldedImage;
        RelativeLayout layoutImageBg;
        Bitmap bitmap;
    }

    private void launchActivity(Course course, int position) {
        Intent intent = new Intent(context, LanesActivity.class);
        intent.putExtra(LanesActivity.COURSE_ID, course.id);
        intent.putExtra(LanesActivity.IS_SEARCH_COURSE_ACTIVITY, isSearch);
        if(isSearch){
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            intent.putExtra(LanesActivity.COURSE, gson.toJson(course.firebaseLanes));
        }
        context.startActivity(intent);
    }
}