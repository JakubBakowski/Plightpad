package com.plightpad.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.iconics.view.IconicsImageView;
import com.plightpad.LoginActivity;
import com.plightpad.R;
import com.plightpad.adapters.ResultListAdapter;
import com.plightpad.data.AlerterContent;
import com.plightpad.boxdomain.CourseResult;
import com.tapadoo.alerter.Alerter;

import java.util.List;

public class DialogUtils {

    public static void showBasicMessageDialog(Context context, String message, boolean positive) {
        View dialog = LayoutInflater.from(context).inflate(R.layout.basic_dialog, null);
        new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .build()
                .show();
        ((TextView) dialog.findViewById(R.id.dialog_message)).setText(message);
        ((TextView) dialog.findViewById(R.id.dialog_dismiss_help)).setText(context.getResources().getString(R.string.dialog_help_message));
        ((IconicsImageView) dialog.findViewById(R.id.upload_video_dialog_icon)).setIcon(positive ? IconUtils.getSuccessIcon(context) : IconUtils.getFailedIcon(context));
    }

    public static void showResultDismissDialog(Context context, String message, boolean positive) {
        View dialog = LayoutInflater.from(context).inflate(R.layout.result_dialog, null);
        new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .build()
                .show();
        ((TextView) dialog.findViewById(R.id.dialog_message)).setText(message);
        ((TextView) dialog.findViewById(R.id.dialog_dismiss_help)).setText(context.getResources().getString(R.string.dialog_help_message));
        ((IconicsImageView) dialog.findViewById(R.id.upload_video_dialog_icon)).setIcon(positive ? IconUtils.getSuccessIcon(context) : IconUtils.getFailedIcon(context));
    }

    public static void showLogoutDialog(Context context) {
        View dialog = LayoutInflater.from(context).inflate(R.layout.logout_dialog_layout, null);
        SwipeDismissDialog builder = new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .build()
                .show();
        ((IconicsImageView) dialog.findViewById(R.id.logout_icon)).setIcon(IconUtils.getLogoutIcon(context));
        (dialog.findViewById(R.id.cancel_dialog_logout)).setOnClickListener(s -> {
            dialog.setVisibility(View.GONE);
            builder.dismiss();
        });
        (dialog.findViewById(R.id.logout_dialog)).setOnClickListener(s -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            context.startActivity(new Intent(context, LoginActivity.class));
        });
    }
    public static void showResultDialog (Context context, List<CourseResult> playerList) {
        View dialog = LayoutInflater.from(context).inflate(R.layout.round_dialog, null);
        new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .build()
                .show();
        ListView listView;
        listView= (ListView) dialog.findViewById(R.id.result_list_dialog);
        ResultListAdapter personListViewAdapter = new ResultListAdapter(context,playerList);

        listView.setAdapter(personListViewAdapter);
    }

    public static void showSimpleAlerter(Context context, AlerterContent alerterContent){
        Alerter.create((Activity)context)
                .setTitle(alerterContent.getTitle())
                .setText(alerterContent.getMessage())
                .setIcon(alerterContent.getIcon())
                .setBackgroundColorInt(alerterContent.getColor())
                .enableSwipeToDismiss()
                .setDuration(alerterContent.getDuration())
                .show();
    }

}
