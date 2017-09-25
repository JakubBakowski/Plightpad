package com.plightpad.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.plightpad.LanesActivity;
import com.plightpad.R;
import com.plightpad.items.CardDataImpl;
import com.plightpad.overridenec.ECCardContentListItemAdapterOverriden;
import com.plightpad.sugardomain.BallSugar;
import com.plightpad.tools.GalleryUtils;
import com.plightpad.tools.IconUtils;

import java.io.File;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Szczypiorek on 01.08.2017.
 */

public class LanesListAdapter extends ECCardContentListItemAdapterOverriden<CardDataImpl> {

    public Context context;

    public LanesListAdapter(@NonNull Context context, @NonNull List<CardDataImpl> objects) {
        super(context, R.layout.expanded_lanes_list_item, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.expanded_lanes_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.addBallPhotoBtn = (FancyButton) rowView.findViewById(R.id.choose_ball_from_list);
            viewHolder.addBallPhotoBtn.setOnClickListener(s -> {
                showBallsToChoose();
            });
            viewHolder.addNotesPhotoBtn = (FancyButton) rowView.findViewById(R.id.attach_notes_photo);
            viewHolder.addNotesPhotoBtn.setOnClickListener(s -> {
                startChoosingPhoto();
            });
            viewHolder.ballHardnessIcon = (ImageView) rowView.findViewById(R.id.ball_hardness_info_icon);
            viewHolder.ballSizeIcon = (ImageView) rowView.findViewById(R.id.ball_size_info_icon);
            viewHolder.ballWeightIcon = (ImageView) rowView.findViewById(R.id.ball_weight_info_icon);
            viewHolder.notesImage = (ImageView) rowView.findViewById(R.id.notes_image);
            viewHolder.ballWeightText = (TextView) rowView.findViewById(R.id.ball_weight_info_text);
            viewHolder.ballSizeText = (TextView) rowView.findViewById(R.id.ball_size_info_text);
            viewHolder.ballHardnessText = (TextView) rowView.findViewById(R.id.ball_hardness_info_text);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        CardDataImpl cdi = getItem(position);
        if (cdi != null && cdi.getLane() != null) {
            if (cdi.getLane().getBall() != null) {
                BallSugar ball = cdi.getLane().getBall();
                viewHolder.ballWeightText.setText(String.valueOf(ball.getWeight()));
                viewHolder.ballHardnessText.setText(String.valueOf(ball.getHardness()));
                viewHolder.ballSizeText.setText(String.valueOf(ball.getSize()));
                loadImageWithGlide(IconUtils.bitmapToByteArray(IconUtils.getHardnessIcon(context).toBitmap()), viewHolder.ballHardnessIcon);
                loadImageWithGlide(IconUtils.bitmapToByteArray(IconUtils.getWeightIcon(context).toBitmap()), viewHolder.ballWeightIcon);
                loadImageWithGlide(IconUtils.bitmapToByteArray(IconUtils.getSizeIcon(context).toBitmap()), viewHolder.ballSizeIcon);
            }
            if (cdi.getLane().getNotesImagePath() != null) {
                loadImageWithGlideFromStorage(cdi.getLane().getNotesImagePath(), viewHolder.notesImage);
            }
        }
        return rowView;
    }

    private void loadImageWithGlide(byte[] imageBytes, ImageView imageView) {
        Glide.with(context)
                .load(imageBytes)
                .asBitmap()
                .into(imageView);
    }

    private void loadImageWithGlideFromStorage(String filePath, ImageView imageView) {
        ((LanesActivity)context).loadImageIntoView(filePath, imageView);
    }

    static class ViewHolder {
        ImageView ballWeightIcon;
        ImageView ballHardnessIcon;
        ImageView ballSizeIcon;
        ImageView notesImage;
        TextView ballWeightText;
        TextView ballSizeText;
        TextView ballHardnessText;
        FancyButton addBallPhotoBtn;
        FancyButton addNotesPhotoBtn;
    }

    private void showBallsToChoose() {
        if (context instanceof LanesActivity) {
            ((LanesActivity) context).showBallListDialog();
        }
    }

    private void startChoosingPhoto() {
        if (context instanceof LanesActivity) {
            ((LanesActivity) context).setChoosingNotesPhoto(true);
        }
        GalleryUtils.pickPhoto(context);
    }

}