package com.arapid;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends HupuActivity
{
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    public int getIContentViewResId() {
        return R.layout.home;
    }

    @Override
    public void findIViews() {
        super.findIViews();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getToolbar(), R.string.menu_open, R.string.menu_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override public boolean onMenuItemClick(MenuItem menuItem)
            {
                Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                switch (menuItem.getItemId()){
                    case R.id.menu_next:
                        startActivity(new Intent(MainActivity.this,DetailActivity.class));
                }
                return false;
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
