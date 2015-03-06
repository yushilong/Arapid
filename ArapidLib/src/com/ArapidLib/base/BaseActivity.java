package com.ArapidLib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by yushilong on 2015/3/4.
 */
public abstract class BaseActivity extends ActionBarActivity implements IActivityHelper{
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        if (getIContentView()==null){
            setContentView(getIContentViewResId());
        }else {
            setContentView(getIContentView());
        }
        IOnCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IOnDestory();
    }

    @Override
    public Context getIContext() {
        return activity;
    }

    @Override
    public Activity getIActivity() {
        return activity;
    }

    @Override
    public abstract int getIContentViewResId();

    @Override
    public View getIContentView() {
        return null;
    }

    @Override
    public void IOnCreate() {
        AppManager.getAppManager().addActivity(this);
        findIViews();
    }

    @Override
    public void IOnDestory() {
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public abstract void findIViews();

    @Override
    public void findIViews(View view) {

    }
}