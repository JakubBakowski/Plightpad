package com.plightpad.data;

import android.graphics.drawable.Drawable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Szczypiorek on 16.09.2017.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlerterContent {

    private static final int DEFAULT_DURATION = 3000;

    String title;
    String message;
    Drawable icon;
    int color;
    int duration;

    public AlerterContent(String title, String message, Drawable icon, int color){
        this.title = title;
        this.message = message;
        this.icon = icon;
        this.color = color;
        this.duration = DEFAULT_DURATION;
    }
}
