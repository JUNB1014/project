package com.app.widgts.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class MeiZuMonthView extends MonthView {

    /**
     * 自定義魅族標記的文本畫筆
     */
    private Paint mTextPaint = new Paint();

    /**
     * 自定義魅族標記的圓形背景
     */
    private Paint mSchemeBasicPaint = new Paint();
    private Paint jieriPaint = new Paint();
    private float mRadio;
    private int mPadding;
    private float mSchemeBaseLine;
    private float circleWidth ;
    private float leftPadding;

    /**
     * 圓點半徑
     */
    private float mPointRadius;
    /**
     * 背景圓點
     */
    private Paint mPointPaint = new Paint();

    public MeiZuMonthView(Context context) {
        super(context);

        mTextPaint.setTextSize(dipToPx(context, 14));
        mOtherMonthLunarTextPaint.setTextSize(dipToPx(context, 10));
        jieriPaint.setTextSize(dipToPx(context, 10));
        jieriPaint.setAntiAlias(true);
        jieriPaint.setTextAlign(Paint.Align.CENTER);
        jieriPaint.setColor(0xff3853E8);

        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);
        leftPadding = dipToPx(getContext(), 5);
        mRadio = dipToPx(getContext(), 7);
        mPadding = dipToPx(getContext(), 4);
        circleWidth = dipToPx(getContext(), 41);
        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mRadio - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);

        mPointRadius = dipToPx(context, 2);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(0xff3853E8);
    }




    /**
     * 繪制選中的日子
     * @param canvas    canvas
     * @param calendar  日歷日歷calendar
     * @param x         日歷Card x起點坐標
     * @param y         日歷Card y起點坐標
     * @param hasScheme hasScheme 非標記的日期
     * @return 返回true 則會繼續繪制onDrawScheme，因為這里背景色不是是互斥的，所以返回true，返回false，則點擊scheme標記的日子，則不繼續繪制onDrawScheme，自行選擇即可
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setStyle(Paint.Style.FILL);
        mSelectedPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedPaint.setFakeBoldText(true);
        mSelectedPaint.setColor(0xff3853E8);
        canvas.drawCircle(x + mItemWidth / 2    , y + mItemWidth / 2  + leftPadding / 2    , circleWidth / 2,  mSelectedPaint);
        return true;
    }

    /**
     * 繪制標記的事件日子
     * @param canvas   canvas
     * @param calendar 日歷calendar
     * @param x        日歷Card x起點坐標
     * @param y        日歷Card y起點坐標
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        mSchemeBasicPaint.setColor(calendar.getSchemeColor());

        canvas.drawCircle(x + mItemWidth - mPadding - mRadio / 2, y + mPadding + mRadio, mRadio, mSchemeBasicPaint);
        //畫小紅點
        canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight -mPadding, mPointRadius, mPointPaint);

        if (!TextUtils.isEmpty(calendar.getScheme())) {
            canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mRadio, y + mPadding + mSchemeBaseLine, mTextPaint);
        }
    }

    /**
     * 繪制文本
     * @param canvas     canvas
     * @param calendar   日歷calendar
     * @param x          日歷Card x起點坐標
     * @param y          日歷Card y起點坐標
     * @param hasScheme  是否是標記的日期
     * @param isSelected 是否選中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 6;
        if (isSelected) {//優先繪制選擇的
                canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                        mSelectTextPaint);
                canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);

        } else if (hasScheme) {//否則繪制具有標記的
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
        } else {//最好繪制普通文本
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

            if(TextUtils.isEmpty(calendar.getGregorianFestival())){
                canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                        calendar.isCurrentDay() ? mCurDayLunarTextPaint :
                                calendar.isCurrentMonth() ? mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
            }else{
                canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                        calendar.isCurrentDay() ? mCurDayLunarTextPaint : jieriPaint);
            }
        }
    }



    /**
     * dp轉px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

