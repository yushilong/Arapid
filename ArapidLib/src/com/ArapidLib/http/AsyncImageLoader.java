package com.ArapidLib.http;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yushilong on 2015/1/26.
 */
public class AsyncImageLoader {
    public static final int IMAGE_STUB = 0;
    public static final int IMAGE_EMPTY = 0;
    public static final int IMAGE_ERROR = 0;
    public static final int IMAGE_DEFAULT_ROUNDE = 5;
    private ImageLoadingListener imageLoadingListener;
    private DisplayImageOptions displayImageOptions, roundDisplayImageOptions;
    private static AsyncImageLoader _instance;
    private static Object lock = new Object();

    private DisplayImageOptions getOptions(int round) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(IMAGE_STUB)
                .showImageForEmptyUri(IMAGE_EMPTY)
                .showImageOnFail(IMAGE_ERROR)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(round > 0 ? new RoundedBitmapDisplayer(round) : new SimpleBitmapDisplayer())
                .build();
        return options;
    }

    private DisplayImageOptions getRoundDisplayImageOptions(int round) {
        synchronized (this) {
            if (roundDisplayImageOptions == null)
                roundDisplayImageOptions = getOptions(round > 0 ? round : IMAGE_DEFAULT_ROUNDE);
        }
        return roundDisplayImageOptions;
    }

    private DisplayImageOptions getDisplayImageOptions() {
        synchronized (this) {
            if (displayImageOptions == null)
                displayImageOptions = getOptions(0);
        }
        return displayImageOptions;
    }

    private ImageLoadingListener getImageLoadingListener() {
        synchronized (this) {
            if (imageLoadingListener == null)
                imageLoadingListener = new AnimateFirstDisplayListener();
        }
        return imageLoadingListener;
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public void showImage(String imgUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(imgUrl, imageView, getDisplayImageOptions(), getImageLoadingListener());
    }

    public void showRoundImage(String imgUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(imgUrl, imageView, getRoundDisplayImageOptions(0), getImageLoadingListener());
    }

    public void showRoundImage(String imgUrl, ImageView imageView, int round) {
        ImageLoader.getInstance().displayImage(imgUrl, imageView, getRoundDisplayImageOptions(round), getImageLoadingListener());
    }

    public static AsyncImageLoader getInstance() {
        synchronized (lock) {
            if (_instance == null)
                _instance = new AsyncImageLoader();
        }
        return _instance;
    }
}
