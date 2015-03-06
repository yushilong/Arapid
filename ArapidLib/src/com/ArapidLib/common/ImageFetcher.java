package com.ArapidLib.common;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by yushilong on 2015/3/6.
 */
public class ImageFetcher {
    public static final int IMAGE_DEFAULT_SIZE = 500;
    public static final int IMAGE_DEFAULT_ROUND = 10;
    public static final int IMAGE_DEFAULT_LOADING_RESID = 0;
    public static final int IMAGE_DEFAULT_LOADING_FAIL_RESID = 0;

    public static DisplayImageOptions.Builder getBuilder() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.showImageOnLoading(IMAGE_DEFAULT_LOADING_RESID);
        builder.showImageForEmptyUri(IMAGE_DEFAULT_LOADING_FAIL_RESID);
        builder.showImageOnFail(IMAGE_DEFAULT_LOADING_FAIL_RESID);
        builder.displayer(new FadeInBitmapDisplayer(500));
        builder.imageScaleType(ImageScaleType.EXACTLY);
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        return builder;
    }

    public static DisplayImageOptions getOptions() {
        return getBuilder().build();
    }

    public static DisplayImageOptions getRoundOptions() {
        return getBuilder().displayer(new RoundedBitmapDisplayer(IMAGE_DEFAULT_ROUND)).build();
    }

    public static void display(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView, getOptions());
    }

    public static void displayR(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView, getRoundOptions());
    }
}
