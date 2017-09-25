package com.plightpad.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.plightpad.LanesActivity;

import net.alhazmy13.mediapicker.Image.ImagePicker;

/**
 * Created by Szczypiorek on 15.08.2017.
 */

public class GalleryUtils {

    public static void pickPhoto(Context context){
        new ImagePicker.Builder((Activity)context)
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

}
