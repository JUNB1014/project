package com.app.widgts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;



public class FullScreenVideoView extends VideoView {

    //主要用於這個直接new出來的對象
    public FullScreenVideoView(Context context) {
        super(context);
    }
    //主要用於xml文件中，支持自定義屬性
    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //也是主要用於xml文件中，支持自定義屬性，同時支持style樣式
    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec 包含兩個主要的內容 1、 測量模式，2、 測量大小
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width,height);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
