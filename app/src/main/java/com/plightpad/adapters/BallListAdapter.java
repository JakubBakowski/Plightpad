package com.plightpad.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.plightpad.LanesActivity;
import com.plightpad.R;
import com.plightpad.sugardomain.BallSugar;
import com.plightpad.tools.AnimationUtils;
import com.plightpad.tools.IconUtils;
import com.plightpad.tools.ZoomInUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class BallListAdapter extends RecyclerView.Adapter<BallListAdapter.ViewHolder> {

    private final Activity context;
    private final List<BallSugar> balls;
    private boolean isChoosingState;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ballImageView;
        public ImageView hardnessImage;
        public ImageView weightImage;
        public ImageView sizeImage;
        public TextView ballName;
        public TextView weight;
        public TextView hardness;
        public TextView size;
        public RelativeLayout ballItemLayout;
        public boolean isBallZoomed;
        public PhotoViewAttacher imageAttacher;

        public ViewHolder(View v) {
            super(v);
            ballImageView = (CircleImageView) v.findViewById(R.id.ball_image_view);
            ballName = (TextView) v.findViewById(R.id.ball_name);
            weight = (TextView) v.findViewById(R.id.ball_weight_info_text);
            size = (TextView) v.findViewById(R.id.ball_size_info_text);
            hardness = (TextView) v.findViewById(R.id.ball_hardness_info_text);
            sizeImage = (ImageView) v.findViewById(R.id.ball_size_info_icon);
            weightImage = (ImageView) v.findViewById(R.id.ball_weight_info_icon);
            hardnessImage = (ImageView) v.findViewById(R.id.ball_hardness_info_icon);
            ballItemLayout = (RelativeLayout) v.findViewById(R.id.ball_item_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BallListAdapter(Activity context, List<BallSugar> balls, boolean isChoosingState) {
        this.context = context;
        this.balls = balls;
        this.isChoosingState = isChoosingState;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BallListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ball_list_item, parent, false);
        BallListAdapter.ViewHolder vh = new BallListAdapter.ViewHolder(rl);
        return vh;
    }

    @Override
    public int getItemCount() {
        return balls.size();
    }

    @Override
    public void onBindViewHolder(BallListAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(IconUtils.bitmapToByteArray(IconUtils.getHardnessIcon(context).toBitmap()))
                .asBitmap()
                .into(holder.hardnessImage);
        Glide.with(context)
                .load(IconUtils.bitmapToByteArray(IconUtils.getWeightIcon(context).toBitmap()))
                .asBitmap()
                .into(holder.weightImage);
        Glide.with(context)
                .load(IconUtils.bitmapToByteArray(IconUtils.getSizeIcon(context).toBitmap()))
                .asBitmap()
                .into(holder.sizeImage);
        try {
            loadPicture(holder.ballImageView, balls.get(position).getImagePath());
        } catch (NullPointerException npe) {
            Log.i("BallListAdapter", npe.getMessage());
        }
        holder.ballName.setText(balls.get(position).getMyName());
        holder.hardness.setText(String.valueOf(balls.get(position).getHardness()));
        holder.weight.setText(String.valueOf(balls.get(position).getWeight()));
        holder.size.setText(String.valueOf(balls.get(position).getSize()));
        if (isChoosingState) {
            holder.ballItemLayout.setOnClickListener(s -> {
                ((LanesActivity) context).saveBallToActualLane(balls.get(position));
            });
        }
        holder.ballImageView.setOnClickListener(s -> scaleImageUp(holder.ballImageView, position));
    }

    private boolean scaleImageUp(View view, int position){
        Rect actualRect = new Rect();
        view.getGlobalVisibleRect(actualRect);
        AnimationUtils.animateScaleUp(context, view, actualRect, position);
        return true;
    }

    private void loadPicture(CircleImageView view, String path) {
        try {
            byte[] buffer;
            FileInputStream fis = context.openFileInput(path);
            buffer = new byte[(int) fis.getChannel().size()];
            fis.read(buffer);
            Glide.with(context)
                    .load(buffer)
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            view.setImageBitmap(resource);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


