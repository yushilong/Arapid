package com.arapid;

import android.support.v7.widget.Toolbar;
import com.ArapidLib.base.BaseActivity;

/**
 * Created by yushilong on 2015/3/4.
 */
public abstract class HupuActivity extends BaseActivity {
    private Toolbar mToolbar;

    @Override
    public void findIViews() {
        if (isShowToolbar()) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            /**
             * 设置导航返回按钮是否可用，true的同时需在Manifest的该Activity属性中（4.0以上(不包含4.0）添加android:parentActivityName="com.example.myfirstapp.MainActivity"，
             * 4.0以下添加（Parent activity meta-data to support 4.0 and lower）
             * <meta-data
             * android:name="android.support.PARENT_ACTIVITY"
             * android:value="com.example.myfirstapp.MainActivity" />
             */
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 是否显示ToolBar
     *
     * @return
     */
    public boolean isShowToolbar() {
        return true;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}