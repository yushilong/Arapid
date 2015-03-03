package com.ArapidLib;

import android.content.Context;

/**
 * Created by yushilong on 2015/3/3.
 */
public class ArapidLibApplication {
    Context context;
    static ArapidLibApplication _instance;

    public void init(Context context) {
        setContext(context);
    }
    public static ArapidLibApplication getInstance(){
        if (_instance==null){
            synchronized (ArapidLibApplication.class){
                if (_instance==null)
                    _instance = new ArapidLibApplication();
            }
        }
        return _instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
