package com.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.app.mylibrary.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;


public class GlideUtils {

    static GlideUtils instance;

    public GlideUtils() {
    }

    public static GlideUtils getInstance() {
        if (null == instance) {
            synchronized (GlideUtils.class) {
                if (null == instance) {
                    instance = new GlideUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 加載矩形圖片文件到 imageview上
     *
     * @param context   上下文
     * @param imageView 要加載到那個控件上
     * @param url       圖片的url地址
     */
    public void loadImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加載矩形圖片文件到 imageview上
     *
     * @param context   上下文
     * @param imageView 要加載到那個控件上
     * @param url       圖片的url地址
     */
    public void loadImage(Context context, String url, ImageView imageView, int imgv_default) {
        if (imgv_default == 0) {
            imgv_default = R.mipmap.ic_launcher;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(imgv_default)
                .error(imgv_default);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加載圖片文件到 imageview上
     *
     * @param context   上下文
     * @param imageView 要加載到那個控件上
     * @param file      圖片文件
     */
    public void loadImageForFile(Context context, ImageView imageView, File file) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)                 //出錯顯示的圖片
                .priority(Priority.HIGH)                     //優先級高
                .diskCacheStrategy(DiskCacheStrategy.NONE);  //禁止磁盤緩存

        Glide.with(context)
                .load(file)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加載圓形圖片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加載圓形帶圓邊圖片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadCircleWithFrameImage(Context context, String url, ImageView imageView, int borderWidth, int borderColor) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new GlideCircleWithFrameTransform(borderWidth, borderColor))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加載圓角圖片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadRoundImage(Context context, String url, ImageView imageView, int corner) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(corner))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加載上圓角圖片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadTopRoundImage(Context context, String url, ImageView imageView, int corner) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new GlideRoundTransform(corner))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        Glide.with(context).load(url).apply(options).into(imageView);
    }


    /**
     * 加載GIF動圖
     *
     * @param mContext  上下文
     * @param url       圖片地址
     * @param imageView imageView控件
     */
    public static void loadImageGIF(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .asGif()
                .load(url)
                //設置緩存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }
}
