package com.plightpad.tools;

import android.app.Activity;
import android.content.Context;

import net.alhazmy13.mediapicker.Image.ImagePicker;

public class GalleryUtils {

    public static void pickOnePhoto(Context context){
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
