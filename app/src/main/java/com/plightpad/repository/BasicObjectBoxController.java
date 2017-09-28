package com.plightpad.repository;

import com.plightpad.App;

import io.objectbox.BoxStore;

/**
 * Created by Szczypiorek on 19.09.2017.
 */

public class BasicObjectBoxController {

    protected static BoxStore boxStore = (App.getApp()).getBoxStore();

}
