package com.arapid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by yushilong on 2015/3/4.
 */
public class DetailActivity extends ActionBarActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setShowHideAnimationEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
////        mToolbar.setLogo(R.drawable.menu_icon_notice);
        mToolbar.setNavigationIcon(R.drawable.menu_icon_qiut);
    }
}
