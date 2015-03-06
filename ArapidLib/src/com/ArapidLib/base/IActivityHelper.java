package com.ArapidLib.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by yushilong on 2015/3/4.
 */
public interface IActivityHelper {
    Context getIContext();

    Activity getIActivity();

    int getIContentViewResId();

    View getIContentView();

    void IOnCreate();

    void IOnDestory();

    void findIViews();

    void findIViews(View view);//for fragment
}
