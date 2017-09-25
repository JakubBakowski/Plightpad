package com.plightpad.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.plightpad.LanesActivity;
import com.plightpad.MenuActivity;
import com.plightpad.controllers.SugarBallsController;
import com.plightpad.controllers.SugarLanesController;
import com.plightpad.sugardomain.LaneSugar;
import com.plightpad.tools.DrawableUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Szczypiorek on 15.08.2017.
 */

public class CompressAndSaveNotesImageTask extends AsyncTask<String, Void, String> {

    private Bitmap notesPhoto;
    private Context context;
    private LaneSugar laneSugar;

    public CompressAndSaveNotesImageTask(Context context, Bitmap notesPhoto, LaneSugar laneSugar) {
        this.notesPhoto = notesPhoto;
        this.context = context;
        this.laneSugar = laneSugar;
    }

    @Override
    protected String doInBackground(String... params) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        notesPhoto = DrawableUtils.scaleDown(notesPhoto, 600.0f, true);
        notesPhoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            String imagePath = laneSugar.getId().toString() + new Date().toString().replace(" ", "");
            FileOutputStream fos = context.openFileOutput(imagePath, Context.MODE_PRIVATE);
            fos.write(bos.toByteArray());
            laneSugar.setNotesImagePath(imagePath);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SugarLanesController.saveSugarLane(laneSugar);
        notesPhoto = null;

        ((LanesActivity) context).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ((LanesActivity) context).hideProgressDialog();
            }
        });
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        ((LanesActivity) context).updateImages(1, null);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}
