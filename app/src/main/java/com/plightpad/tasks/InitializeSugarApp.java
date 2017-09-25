package com.plightpad.tasks;

import android.os.AsyncTask;

import com.plightpad.sugardomain.BallSugar;

/**
 * Created by Szczypiorek on 23.08.2017.
 */

public class InitializeSugarApp extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        BallSugar bs = new BallSugar();
        bs.save();
        String[] args = {bs.getId().toString()};
        BallSugar.deleteAll(BallSugar.class, "Id = ?", args);
        return null;
    }
}
