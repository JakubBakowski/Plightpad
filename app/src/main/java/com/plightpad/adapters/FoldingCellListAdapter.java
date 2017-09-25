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
import com.plightpad.LanesActivity;
import com.plightpad.R;
import com.plightpad.controllers.SugarCoursesController;
import com.plightpad.data.AlerterContent;
import com.plightpad.items.ListItem;
import com.plightpad.sugardomain.CourseSugar;
import com.plightpad.tools.DialogUtils;
import com.plightpad.tools.IconUtils;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;


public class FoldingCellListAdapter extends ArrayAdapter<ListItem> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private static final String COMMA = ", ";
    private boolean isSearch;

    private Context context;
    private List<CourseSugar> courseSugars;

    public FoldingCellListAdapter(Context context, List<ListItem> objects, boolean isSearch, List<CourseSugar> courseSugars) {
        super(context, 0, objects);
        this.context = context;
        this.isSearch = isSearch;
        this.courseSugars = courseSugars;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItem item = getItem(position);
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;

        if (cell == null) {
            // tworzymy potrzebne gówno
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell_layout, parent, false);
            // przypisujemy do viewHoldera to co będziemy zmieniać - do zastapnienia przez BindView
            viewHolder.title = (TextView) cell.findViewById(R.id.title);
            viewHolder.countryCity = (TextView) cell.findViewById(R.id.country_city);
            viewHolder.titleFolded = (TextView) cell.findViewById(R.id.title_Folded);
            viewHolder.cityFolded = (TextView) cell.findViewById(R.id.city_Folded);
            viewHolder.countryFolded = (TextView) cell.findViewById(R.id.country_Folded);
            viewHolder.bestScore = (TextView) cell.findViewById(R.id.bestScore);
            viewHolder.laneLength = (TextView) cell.findViewById(R.id.laneLength);
            viewHolder.type = (TextView) cell.findViewById(R.id.type);
//            viewHolder.link = (TextView) cell.findViewById(R.id.link);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.contentRequestBtn);
            viewHolder.saveCourseToLocalDatabaseBtn = (TextView) cell.findViewById(R.id.saveCourseToLocalDatabaseBtn);
            viewHolder.unfoldedImage = (ImageView) cell.findViewById(R.id.head_image);
//            viewHolder.foldedImage = (ImageView)cell.findViewById(R.id.folded_image_view);
            viewHolder.layoutImageBg = (RelativeLayout) cell.findViewById(R.id.relative_inner_cell_content);

            ViewTarget<RelativeLayout, GlideDrawable> viewTarget = new ViewTarget<RelativeLayout, GlideDrawable>(viewHolder.layoutImageBg) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    this.view.setBackgroundDrawable(resource.getCurrent());
                }
            };

            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(item.getLink()))
                    .bitmapTransform(new FitCenter(context), new BlurTransformation(context, 25), new VignetteFilterTransformation(context, new PointF(0.5f, 0.5f),
                            new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f))
                    .into(viewTarget);
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(item.getLink()))
//                    .bitmapTransform(new BlurTransformation(context))
                    .asBitmap()
                    .fitCenter()
                    .into(viewHolder.unfoldedImage);
//            try {
//                viewHolder.bitmap = Glide.with(context)
//                        .using(new FirebaseImageLoader())
//                        .load(FirebaseStorage.getInstance().getReference().child(item.getLink()))
//                        .asBitmap()
//                        .into(-1,-1)
//                        .get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
            cell.setTag(viewHolder);
        } else {
            // z domysłow sadze ze sprawdzamy czy rozwinieta czy zwinieta
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        //poddalem się narazie xd
        // zrobic funkcje
        viewHolder.title.setText(item.getTitle());
        viewHolder.countryCity.setText(item.getCountry() + COMMA + item.getCity());
        viewHolder.type.setText(context.getResources().getString(R.string.type_of_course_folding) + item.getType());
        viewHolder.laneLength.setText(context.getResources().getString(R.string.course_lane_length_folding) + item.getLaneLength());
        viewHolder.bestScore.setText(context.getResources().getString(R.string.best_score_folding) + item.getBestScore());
//        viewHolder.link.setText(item.getLink());
        viewHolder.titleFolded.setText(item.getTitle());
        viewHolder.countryFolded.setText(item.getCountry() + COMMA);
        viewHolder.cityFolded.setText(item.getCity());
        viewHolder.contentRequestBtn.setOnClickListener(s -> launchActivity(item, position));
        viewHolder.saveCourseToLocalDatabaseBtn.setOnClickListener(s -> {
            SugarCoursesController.saveSugarCourse(item.getCourse());
            AlerterContent alerterContent = new AlerterContent(
                    context.getResources().getString(R.string.alerter_default_title),
                    context.getResources().getString(R.string.successfull_course_adding),
                    IconUtils.getSuccessIcon(context),
                    ContextCompat.getColor(context, R.color.accept_btn_color)
            );
            DialogUtils.showSimpleAlerter(context, alerterContent);
//            DialogUtils.showBasicMessageDialog(context, context.getResources().getString(R.string.successfull_course_adding), true);
        });
        if (isSearch) {
            viewHolder.saveCourseToLocalDatabaseBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.saveCourseToLocalDatabaseBtn.setVisibility(View.GONE);
        }

        //to gowno do przemyslenia co z tego jest potrzebne
        // set custom btn handler for list item from that item

        return cell;
    }

    // simple methods for register cell_layout state changes
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

    // Przechowywacz
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

    private void launchActivity(ListItem item, int position) {
        Intent intent = new Intent(context, LanesActivity.class);
        intent.putExtra("COURSE", item.getCourse());
        intent.putExtra("IS_SEARCH", isSearch);
        if (courseSugars != null) {
            intent.putExtra("COURSE_SUGAR_ID", courseSugars.get(position).getId());
        }
        context.startActivity(intent);
    }
}