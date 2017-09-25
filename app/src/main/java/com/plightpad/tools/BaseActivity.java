package com.plightpad.tools;

/**
 * Created by Szczypiorek on 10.07.2017.
 */

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.ldoublem.loadingviewlib.view.LVBlock;
import com.plightpad.R;

public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public LVBlock mProgressDialog;


    @Override
    public void onStop() {
        super.onStop();
    }

}
