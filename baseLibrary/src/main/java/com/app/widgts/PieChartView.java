package com.app.widgts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import com.app.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 餅狀統計圖，帶有標注線，都可以自行設定其多種參數選項
 * <p/>
 * Created By: Seal.Wu
 */
public class PieChartView extends View {

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    /**
     * 餅圖半徑
     */
    private float pieChartCircleRadius = 100;

    private float textBottom;
    /**
     * 記錄文字大小
     */
    private float mTextSize = 14;

    /**
     * 餅圖所占矩形區域（不包括文字）
     */
    private RectF pieChartCircleRectF = new RectF();

    /**
     * 餅狀圖信息列表
     */
    private List<PieceDataHolder> pieceDataHolders = new ArrayList<>();


    /**
     * 標記線長度
     */
    private float markerLineLength = 30f;

    public PieChartView(Context context) {
        super(context);
        init(null, 0);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PieChartView, defStyle, 0);
        pieChartCircleRadius = a.getDimension(R.styleable.PieChartView_circle_Radius, pieChartCircleRadius);
        mTextSize = a.getDimension(R.styleable.PieChartView_text_Size, mTextSize) / getResources().getDisplayMetrics().density;
        a.recycle();
        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getContext().getResources().getDisplayMetrics()));

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.descent - fontMetrics.ascent;
        textBottom = fontMetrics.bottom;
    }

    /**
     * 設置餅狀圖的半徑
     *
     * @param pieChartCircleRadius 餅狀圖的半徑（px）
     */
    public void setPieChartCircleRadius(int pieChartCircleRadius) {

        this.pieChartCircleRadius = pieChartCircleRadius;

        invalidate();
    }

    /**
     * 設置標記線的長度
     *
     * @param markerLineLength 標記線的長度（px）
     */
    public void setMarkerLineLength(int markerLineLength) {
        this.markerLineLength = markerLineLength;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initPieChartCircleRectF();

        drawAllSectors(canvas);

    }

    private void drawAllSectors(Canvas canvas) {
        float sum = 0f;
        for (PieceDataHolder pieceDataHolder : pieceDataHolders) {
            sum += pieceDataHolder.value;
        }

        float sum2 = 0f;
        for (PieceDataHolder pieceDataHolder : pieceDataHolders) {
            float startAngel = sum2 / sum * 360 - 90;  //設置起始位置  從最頂部開始
            sum2 += pieceDataHolder.value;
            float sweepAngel = pieceDataHolder.value / sum * 360;
            drawSector(canvas, pieceDataHolder.color, startAngel, sweepAngel);
            drawMarkerLineAndText(canvas, pieceDataHolder.color, startAngel + sweepAngel / 2, pieceDataHolder.marker);
        }
    }

    private void initPieChartCircleRectF() {
        pieChartCircleRectF.left = getWidth() / 2 - pieChartCircleRadius;
        pieChartCircleRectF.top = getHeight() / 2 - pieChartCircleRadius;
        pieChartCircleRectF.right = pieChartCircleRectF.left + pieChartCircleRadius * 2;
        pieChartCircleRectF.bottom = pieChartCircleRectF.top + pieChartCircleRadius * 2;
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.(sp)
     */
    public float getTextSize() {
        return mTextSize;
    }

    /**
     * Sets the view's text dimension attribute value. In the PieChartView view, this dimension
     * is the font size.
     *
     * @param textSize The text dimension attribute value to use.(sp)
     */
    public void setTextSize(float textSize) {
        mTextSize = textSize;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * 設置餅狀圖要顯示的數據
     *
     * @param data 列表數據
     */
    public void setData(List<PieceDataHolder> data) {

        if (data != null) {
            pieceDataHolders.clear();
            pieceDataHolders.addAll(data);
        }

        invalidate();
    }

    /**
     * 繪制扇形
     *
     * @param canvas     畫布
     * @param color      要繪制扇形的顏色
     * @param startAngle 起始角度
     * @param sweepAngle 結束角度
     */
    protected void drawSector(Canvas canvas, int color, float startAngle, float sweepAngle) {

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);

        canvas.drawArc(pieChartCircleRectF, startAngle, sweepAngle, true, paint);

    }

    /**
     * 繪制標注線和標記文字
     *
     * @param canvas      畫布
     * @param color       標記的顏色
     * @param rotateAngel 標記線和水平相差旋轉的角度
     */
    protected void drawMarkerLineAndText(Canvas canvas, int color, float rotateAngel, String text) {
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(3);

        Path path = new Path();
        path.close();
        path.moveTo(getWidth() / 2, getHeight() / 2);
        //拐角的x.y 坐標
        final float x = (float) (getWidth() / 2 + (markerLineLength + pieChartCircleRadius) * Math.cos(Math.toRadians(rotateAngel)));
        final float y = (float) (getHeight() / 2 + (markerLineLength + pieChartCircleRadius) * Math.sin(Math.toRadians(rotateAngel)));
        path.lineTo(x, y);
        float landLineX;  //折線之後的水平線終點橫坐標

        if (270f > rotateAngel && rotateAngel > 90f) {  //
            landLineX = mTextPaint.measureText(text) + 30;
            //            landLineX = x - 30;
        } else {
            landLineX = getWidth() - mTextPaint.measureText(text) - 30;
            //            landLineX = x + 30;
        }
        path.lineTo(landLineX, y);
        canvas.drawPath(path, paint);
        mTextPaint.setColor(color);
        if (270f > rotateAngel && rotateAngel > 90f) {
            float textWidth = mTextPaint.measureText(text);
            canvas.drawText(text, landLineX - textWidth, y + mTextHeight / 2 - textBottom, mTextPaint);

        } else {
            canvas.drawText(text, landLineX, y + mTextHeight / 2 - textBottom, mTextPaint);
        }

    }

    /**
     * 餅狀圖每塊的信息持有者
     */
    public static final class PieceDataHolder {

        /**
         * 每塊扇形的值的大小
         */
        private float value;

        /**
         * 扇形的顏色
         */
        private int color;

        /**
         * 每塊的標記
         */
        private String marker;


        public PieceDataHolder(float value, int color, String marker) {
            this.value = value;
            this.color = color;
            this.marker = marker;
        }
    }

}
