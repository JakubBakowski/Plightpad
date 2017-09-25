package com.plightpad.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.plightpad.LanesActivity;
import com.plightpad.MenuActivity;
import com.plightpad.controllers.SugarBallsController;
import com.plightpad.sugardomain.BallSugar;
import com.plightpad.tools.DrawableUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Szczypiorek on 15.08.2017.
 */

public class CompressAndSaveBallImageTask extends AsyncTask<String, Void, String> {

    private Bitmap ballPhoto;
    private Context context;
    private BallSugar bs;
    private boolean isLanesActivity;

    public CompressAndSaveBallImageTask(Context context, Bitmap ballPhoto, BallSugar bs, boolean isLanesActivity) {
        this.ballPhoto = ballPhoto;
        this.context = context;
        this.bs = bs;
        this.isLanesActivity = isLanesActivity;
    }

    @Override
    protected String doInBackground(String... params) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ballPhoto = DrawableUtils.scaleDown(ballPhoto, 600.0f, true);
        ballPhoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            FileOutputStream fos = context.openFileOutput(bs.getId().toString(), Context.MODE_PRIVATE);
            fos.write(bos.toByteArray());
            bs.setImagePath(bs.getId().toString());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SugarBallsController.saveBall(bs);
        ballPhoto = null;

        if(isLanesActivity) {
            ((LanesActivity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ((LanesActivity) context).hideProgressDialog();
                }
            });
        } else {
            ((MenuActivity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ((MenuActivity) context).hideProgressDialog();
                }
            });
        }

        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        if(isLanesActivity) {
            ((LanesActivity) context).updateImages(1, null);
        }
        else{
            ((MenuActivity) context).initializeBallsList(null);
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}
