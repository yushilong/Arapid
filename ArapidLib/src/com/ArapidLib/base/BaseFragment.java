package com.ArapidLib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements IActivityHelper {
    private View containerView;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (containerView == null) {
            if (getIContentView() != null) {
                containerView = getIContentView();
            } else {
                containerView = inflater.inflate(getIContentViewResId(), container, false);
            }
        }
        IOnCreate();
        ViewGroup parent = (ViewGroup) containerView.getParent();
        if (parent != null) {
            parent.removeView(containerView);
        }
        return containerView;
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
        findIViews(containerView);
    }

    @Override
    public abstract void findIViews(View view);
}