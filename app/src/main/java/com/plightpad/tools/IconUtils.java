package com.plightpad.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.text.DisplayContext;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.typeicons_typeface_library.Typeicons;
import com.plightpad.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by mabak on 08.08.2017.
 */

public class IconUtils {

    public static IconicsDrawable getCloseIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(FontAwesome.Icon.faw_window_close)
                .color(context.getResources().getColor(R.color.th3))
                .sizeDp(24);
    }
    public static IconicsDrawable getSettingsIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_cog_outline)
                .color(context.getResources().getColor(R.color.white))
                .sizeDp(24);
    }
    public static IconicsDrawable getPlusIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(FontAwesome.Icon.faw_plus)
                .color(context.getResources().getColor(R.color.th3))
                .sizeDp(24);
    }

    public static IconicsDrawable getBallIconGreen(Context context) {
        return new IconicsDrawable(context)
                .icon(FontAwesome.Icon.faw_eercast)
                .color(context.getResources().getColor(R.color.th3))
                .sizeDp(24);
    }

    public static IconicsDrawable getBallIconWhite(Context context) {
        return new IconicsDrawable(context)
                .icon(FontAwesome.Icon.faw_eercast)
                .color(context.getResources().getColor(R.color.white))
                .sizeDp(24);
    }

    public static IconicsDrawable getEditIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_edit)
                .color(context.getResources().getColor(R.color.th3))
                .sizeDp(24);
    }

    public static IconicsDrawable getWeightIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(FontAwesome.Icon.faw_balance_scale)
                .color(context.getResources().getColor(R.color.th3))
                .sizeDp(24);
    }

    public static IconicsDrawable getSizeIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_arrow_maximise)
                .color(context.getResources().getColor(R.color.th3))
                .sizeDp(24);
    }

    public static IconicsDrawable getHardnessIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(FontAwesome.Icon.faw_diamond)
                .color(context.getResources().getColor(R.color.th3))
                .sizeDp(24);
    }

    public static IconicsDrawable getSuccessIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_tick)
                .color(context.getResources().getColor(R.color.accept_btn_color))
                .sizeDp(32);
    }

    public static IconicsDrawable getFailedIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_delete)
                .color(context.getResources().getColor(R.color.decline_btn_color))
                .sizeDp(32);
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public static IconicsDrawable getCourseIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_directions)
                .color(context.getResources().getColor(R.color.white))
                .sizeDp(14);
    }

    public static IconicsDrawable getProfileIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_user_outline)
                .color(context.getResources().getColor(R.color.white))
                .sizeDp(14);
    }
    public static IconicsDrawable getLogoutIcon(Context context) {
        return new IconicsDrawable(context)
                .icon(Typeicons.Icon.typ_lock_open_outline)
                .color(context.getResources().getColor(R.color.black))
                .sizeDp(24);
    }

}
