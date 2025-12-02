package com.app.widgts.timepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;


import com.app.mylibrary.R;
import com.app.interfaces.IPickerViewData;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 3d滾輪控件
 */
public class WheelView extends View {

    public enum ACTION { // 點擊，滑翔(滑到盡頭)，拖拽事件
        CLICK, FLING, DAGGLE
    }

    public enum DividerType { // 分隔線類型
        FILL, WRAP
    }

    private DividerType dividerType;//分隔線類型

    ACTION action_now;
    Context context;

    Handler handler;
    private GestureDetector gestureDetector;
    OnItemSelectedListener onItemSelectedListener;

    private boolean isOptions = false;
    private boolean isCenterLabel = true;

    // Timer mTimer;
    ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    Paint paintOuterText;
    Paint paintCenterText;
    Paint paintIndicator;

    WheelAdapter adapter;

    private String label;//附加單位
    int textSize;//選項的文字大小
    int maxTextWidth;
    int maxTextHeight;
    float itemHeight;//每行高度

    Typeface typeface = Typeface.MONOSPACE;//字體樣式，默認是等寬字體

    int textColorOut = 0xFFa8a8a8;
    int textColorCenter = 0xFF2a2a2a;
    int dividerColor = 0xFFd5d5d5;

    // 條目間距倍數
    float lineSpacingMultiplier = 1.6F;
    boolean isLoop;

    // 第一條線Y坐標值
    float firstLineY;
    //第二條線Y坐標
    float secondLineY;
    //中間label繪制的Y坐標
    float centerY;

    //滾動總高度y值
    float totalScrollY;
    //初始化默認選中項
    int initPosition;
    //選中的Item是第幾個
    private int selectedItem;
    int preCurrentIndex;
    //滾動偏移值,用於記錄滾動了多少個item
    int change;

    // 繪制幾個條目，實際上第一項和最後一項Y軸壓縮成0%了，所以可見的數目實際為9
    int itemsVisible = 9;

    int measuredHeight;// WheelView 控件高度
    int measuredWidth;// WheelView 控件寬度

    // 半圓周長
    int halfCircumference;
    // 半徑
    int radius;

    private int mOffset = 0;
    private float previousY = 0;
    long startTime = 0;

    // 修改這個值可以改變滑行速度
    private static final int VELOCITYFLING = 5;
    int widthMeasureSpec;

    private int mGravity = Gravity.CENTER;
    private int drawCenterContentStart = 0;//中間選中文字開始繪制位置
    private int drawOutContentStart = 0;//非中間文字開始繪制位置
    private static final float SCALECONTENT = 0.7F;//非中間文字則用此控制高度，壓扁形成3d錯覺
    private float CENTERCONTENTOFFSET;//偏移量

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
       /* textColorOut = getResources().getColor(R.color.pickerview_wheelview_textcolor_out);
        textColorCenter =getResources().getColor(R.color.pickerview_wheelview_textcolor_center);
        dividerColor = getResources().getColor(R.color.pickerview_wheelview_textcolor_out);*/

        textSize = getResources().getDimensionPixelSize(R.dimen.textSize_20sp);//默認大小

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（0.75/1.0/1.5/2.0/3.0）

        if (density < 1) {//根據密度不同進行適配
            CENTERCONTENTOFFSET = 2.4F;
        } else if (1 <= density && density < 2) {
            CENTERCONTENTOFFSET = 3.6F;
        } else if (1 <= density && density < 2) {
            CENTERCONTENTOFFSET = 4.5F;
        } else if (2 <= density && density < 3) {
            CENTERCONTENTOFFSET = 6.0F;
        } else if (density >= 3) {
            CENTERCONTENTOFFSET = density * 2.5F;
        }


        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.pickerview, 0, 0);
            mGravity = a.getInt(R.styleable.pickerview_pickerview_gravity, Gravity.CENTER);
            textColorOut = a.getColor(R.styleable.pickerview_pickerview_textColorOut, textColorOut);
            textColorCenter = a.getColor(R.styleable.pickerview_pickerview_textColorCenter, textColorCenter);
            dividerColor = a.getColor(R.styleable.pickerview_pickerview_dividerColor, dividerColor);
            textSize = a.getDimensionPixelOffset(R.styleable.pickerview_pickerview_textSize, textSize);
            lineSpacingMultiplier = a.getFloat(R.styleable.pickerview_pickerview_lineSpacingMultiplier, lineSpacingMultiplier);
            a.recycle();//回收內存
        }

        judgeLineSpae();

        initLoopView(context);
    }

    /**
     * 判斷間距是否在1.0-2.0之間
     */
    private void judgeLineSpae() {
        if (lineSpacingMultiplier < 1.2f) {
            lineSpacingMultiplier = 1.2f;
        } else if (lineSpacingMultiplier > 2.0f) {
            lineSpacingMultiplier = 2.0f;
        }
    }

    private void initLoopView(Context context) {
        this.context = context;
        handler = new MessageHandler(this);
        gestureDetector = new GestureDetector(context, new LoopViewGestureListener(this));
        gestureDetector.setIsLongpressEnabled(false);

        isLoop = true;

        totalScrollY = 0;
        initPosition = -1;

        initPaints();

    }

    private void initPaints() {
        paintOuterText = new Paint();
        paintOuterText.setColor(textColorOut);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTypeface(typeface);
        paintOuterText.setTextSize(textSize);

        paintCenterText = new Paint();
        paintCenterText.setColor(textColorCenter);
        paintCenterText.setAntiAlias(true);
        paintCenterText.setTextScaleX(1.1F);
        paintCenterText.setTypeface(typeface);
        paintCenterText.setTextSize(textSize);


        paintIndicator = new Paint();
        paintIndicator.setColor(dividerColor);
        paintIndicator.setAntiAlias(true);

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void remeasure() {//重新測量
        if (adapter == null) {
            return;
        }

        measureTextWidthHeight();

        //半圓的周長 = item高度乘以item數目-1
        halfCircumference = (int) (itemHeight * (itemsVisible - 1));
        //整個圓的周長除以PI得到直徑，這個直徑用作控件的總高度
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        //求出半徑
        radius = (int) (halfCircumference / Math.PI);
        //控件寬度，這里支持weight
        measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        //計算兩條橫線 和 選中項畫筆的基線Y位置
        firstLineY = (measuredHeight - itemHeight) / 2.0F;
        secondLineY = (measuredHeight + itemHeight) / 2.0F;
        centerY = secondLineY - (itemHeight - maxTextHeight) / 2.0f - CENTERCONTENTOFFSET;

        //初始化顯示的item的position
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (adapter.getItemsCount() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }
        preCurrentIndex = initPosition;
    }

    /**
     * 計算最大length的Text的寬高度
     */
    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < adapter.getItemsCount(); i++) {
            String s1 = getContentText(adapter.getItem(i));
            paintCenterText.getTextBounds(s1, 0, s1.length(), rect);

            int textWidth = rect.width();

            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
            paintCenterText.getTextBounds("\u661F\u671F", 0, 2, rect); // 星期的字符編碼（以它為標準高度）

            maxTextHeight = rect.height() + 2;

        }
        itemHeight = lineSpacingMultiplier * maxTextHeight;
    }

    void smoothScroll(ACTION action) {//平滑滾動的實現
        cancelFuture();
        action_now = action;
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            mOffset = (int) ((totalScrollY % itemHeight + itemHeight) % itemHeight);
            if ((float) mOffset > itemHeight / 2.0F) {//如果超過Item高度的一半，滾動到下一個Item去
                mOffset = (int) (itemHeight - (float) mOffset);
            } else {
                mOffset = -mOffset;
            }
        }
        //停止的時候，位置有偏移，不是全部都能正確停止到中間位置的，這里把文字位置挪回中間去
        mFuture = mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }

    protected final void scrollBy(float velocityY) {//滾動慣性的實現
        cancelFuture();
        mFuture = mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0, VELOCITYFLING, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    /**
     * 設置是否循環滾動
     *
     * @param cyclic 是否循環
     */
    public final void setCyclic(boolean cyclic) {
        isLoop = cyclic;
    }

    public final void setTypeface(Typeface font) {
        typeface = font;
        paintOuterText.setTypeface(typeface);
        paintCenterText.setTypeface(typeface);
    }

    public final void setTextSize(float size) {
        if (size > 0.0F) {
            textSize = (int) (context.getResources().getDisplayMetrics().density * size);
            paintOuterText.setTextSize(textSize);
            paintCenterText.setTextSize(textSize);
        }
    }

    public final void setCurrentItem(int currentItem) {
        this.initPosition = currentItem;
        totalScrollY = 0;//回歸頂部，不然重設setCurrentItem的話位置會偏移的，就會顯示出不對位置的數據
        invalidate();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        this.onItemSelectedListener = OnItemSelectedListener;
    }

    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter() {
        return adapter;
    }

    public final int getCurrentItem() {
        return selectedItem;
    }

    protected final void onItemSelected() {
        if (onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200L);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (adapter == null) {
            return;
        }
        //可見的item數組
        Object visibles[] = new Object[itemsVisible];
        //滾動的Y值高度除去每行Item的高度，得到滾動了多少個item，即change數
        change = (int) (totalScrollY / itemHeight);

        try {
            //滾動中實際的預選中的item(即經過了中間位置的item) ＝ 滑動前的位置 ＋ 滑動相對位置
            preCurrentIndex = initPosition + change % adapter.getItemsCount();

        } catch (ArithmeticException e) {
            Log.e("WheelView", "出錯了！adapter.getItemsCount() == 0，聯動數據不匹配");
        }
        if (!isLoop) {//不循環的情況
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {
                preCurrentIndex = adapter.getItemsCount() - 1;
            }
        } else {//循環
            if (preCurrentIndex < 0) {//舉個例子：如果總數是5，preCurrentIndex ＝ －1，那麽preCurrentIndex按循環來說，其實是0的上面，也就是4的位置
                preCurrentIndex = adapter.getItemsCount() + preCurrentIndex;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {//同理上面,自己腦補一下
                preCurrentIndex = preCurrentIndex - adapter.getItemsCount();
            }
        }
        //跟滾動流暢度有關，總滑動距離與每個item高度取余，即並不是一格格的滾動，每個item不一定滾到對應Rect里的，這個item對應格子的偏移值
        float itemHeightOffset = (totalScrollY % itemHeight);

        // 設置數組中每個元素的值
        int counter = 0;
        while (counter < itemsVisible) {
            int index = preCurrentIndex - (itemsVisible / 2 - counter);//索引值，即當前在控件中間的item看作數據源的中間，計算出相對源數據源的index值
            //判斷是否循環，如果是循環數據源也使用相對循環的position獲取對應的item值，如果不是循環則超出數據源範圍使用""空白字符串填充，在界面上形成空白無數據的item項
            if (isLoop) {
                index = getLoopMappingIndex(index);
                visibles[counter] = adapter.getItem(index);
            } else if (index < 0) {
                visibles[counter] = "";
            } else if (index > adapter.getItemsCount() - 1) {
                visibles[counter] = "";
            } else {
                visibles[counter] = adapter.getItem(index);
            }

            counter++;

        }

        //繪制中間兩條橫線
        if (dividerType == DividerType.WRAP) {//橫線長度僅包裹內容
            float startX;
            float endX;

            if (TextUtils.isEmpty(label)) {//隱藏Label的情況
                startX = (measuredWidth - maxTextWidth) / 2 - 12;
            } else {
                startX = (measuredWidth - maxTextWidth) / 4 - 12;
            }

            if (startX <= 0) {//如果超過了WheelView的邊緣
                startX = 10;
            }
            endX = measuredWidth - startX;
            canvas.drawLine(startX, firstLineY, endX, firstLineY, paintIndicator);
            canvas.drawLine(startX, secondLineY, endX, secondLineY, paintIndicator);
        } else {
            canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, paintIndicator);
            canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, paintIndicator);
        }

        //只顯示選中項Label文字的模式，並且Label文字不為空，則進行繪制
        if (!TextUtils.isEmpty(label) && isCenterLabel) {
            //繪制文字，靠右並留出空隙
            int drawRightContentStart = measuredWidth - getTextWidth(paintCenterText, label);
            canvas.drawText(label, drawRightContentStart - CENTERCONTENTOFFSET, centerY, paintCenterText);
        }

        counter = 0;
        while (counter < itemsVisible) {
            canvas.save();
            // 弧長 L = itemHeight * counter - itemHeightOffset
            // 求弧度 α = L / r  (弧長/半徑) [0,π]
            double radian = ((itemHeight * counter - itemHeightOffset)) / radius;
            // 弧度轉換成角度(把半圓以Y軸為軸心向右轉90度，使其處於第一象限及第四象限
            // angle [-90°,90°]
            float angle = (float) (90D - (radian / Math.PI) * 180D);//item第一項,從90度開始，逐漸遞減到 -90度

            // 計算取值可能有細微偏差，保證負90°到90°以外的不繪制
            if (angle >= 90F || angle <= -90F) {
                canvas.restore();
            } else {
                //獲取內容文字
                String contentText;

                //如果是label每項都顯示的模式，並且item內容不為空、label 也不為空
                if (!isCenterLabel && !TextUtils.isEmpty(label) && !TextUtils.isEmpty(getContentText(visibles[counter]))) {
                    contentText = getContentText(visibles[counter]) + label;
                } else {
                    contentText = getContentText(visibles[counter]);
                }

                reMeasureTextSize(contentText);
                //計算開始繪制的位置
                measuredCenterContentStart(contentText);
                measuredOutContentStart(contentText);
                float translateY = (float) (radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                //根據Math.sin(radian)來更改canvas坐標系原點，然後縮放畫布，使得文字高度進行縮放，形成弧形3d視覺差
                canvas.translate(0.0F, translateY);
                canvas.scale(1.0F, (float) Math.sin(radian));
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    // 條目經過第一條線
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1.0F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    // 條目經過第二條線
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1.0F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    // 中間條目
                    //canvas.clipRect(0, 0, measuredWidth,   maxTextHeight);
                    //讓文字居中
                    float Y = maxTextHeight - CENTERCONTENTOFFSET;//因為圓弧角換算的向下取值，導致角度稍微有點偏差，加上畫筆的基線會偏上，因此需要偏移量修正一下
                    canvas.drawText(contentText, drawCenterContentStart, Y, paintCenterText);

                    int preSelectedItem = adapter.indexOf(visibles[counter]);

                    selectedItem = preSelectedItem;

                } else {
                    // 其他條目
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                }
                canvas.restore();
                paintCenterText.setTextSize(textSize);
            }
            counter++;
        }
    }

    /**
     * 根據文字的長度 重新設置文字的大小 讓其能完全顯示
     *
     * @param contentText
     */
    private void reMeasureTextSize(String contentText) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
        int width = rect.width();
        int size = textSize;
        while (width > measuredWidth) {
            size--;
            //設置2條橫線中間的文字大小
            paintCenterText.setTextSize(size);
            paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
            width = rect.width();
        }
        //設置2條橫線外面的文字大小
        paintOuterText.setTextSize(size);
    }


    //遞歸計算出對應的index
    private int getLoopMappingIndex(int index) {
        if (index < 0) {
            index = index + adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        } else if (index > adapter.getItemsCount() - 1) {
            index = index - adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        }
        return index;
    }

    /**
     * 根據傳進來的對象獲取getPickerViewText()方法，來獲取需要顯示的值
     *
     * @param item 數據源的item
     * @return 對應顯示的字符串
     */
    private String getContentText(Object item) {
        if (item == null) {
            return "";
        } else if (item instanceof IPickerViewData) {
            return ((IPickerViewData) item).getPickerViewText();
        } else if (item instanceof Integer) {
            //如果為整形則最少保留兩位數.
            return String.format(Locale.getDefault(), "%02d", (int) item);
        }
        return item.toString();
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER://顯示內容居中
                if (isOptions || label == null || label.equals("") || !isCenterLabel) {
                    drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                } else {//只顯示中間label時，時間選擇器內容偏左一點，留出空間繪制單位標簽
                    drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.25);
                }
                break;
            case Gravity.LEFT:
                drawCenterContentStart = 0;
                break;
            case Gravity.RIGHT://添加偏移量
                drawCenterContentStart = measuredWidth - rect.width() - (int) CENTERCONTENTOFFSET;
                break;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        paintOuterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER:
                if (isOptions || label == null || label.equals("") || !isCenterLabel) {
                    drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                } else {//只顯示中間label時，時間選擇器內容偏左一點，留出空間繪制單位標簽
                    drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.25);
                }
                break;
            case Gravity.LEFT:
                drawOutContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawOutContentStart = measuredWidth - rect.width() - (int) CENTERCONTENTOFFSET;
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                cancelFuture();
                previousY = event.getRawY();
                break;
            //滑動中
            case MotionEvent.ACTION_MOVE:

                float dy = previousY - event.getRawY();
                previousY = event.getRawY();
                totalScrollY = totalScrollY + dy;

                // 邊界處理。
                if (!isLoop) {
                    float top = -initPosition * itemHeight;
                    float bottom = (adapter.getItemsCount() - 1 - initPosition) * itemHeight;


                    if (totalScrollY - itemHeight * 0.25 < top) {
                        top = totalScrollY - dy;
                    } else if (totalScrollY + itemHeight * 0.25 > bottom) {
                        bottom = totalScrollY - dy;
                    }

                    if (totalScrollY < top) {
                        totalScrollY = (int) top;
                    } else if (totalScrollY > bottom) {
                        totalScrollY = (int) bottom;
                    }
                }
                break;
            //完成滑動，手指離開屏幕
            case MotionEvent.ACTION_UP:

            default:
                if (!eventConsumed) {//未消費掉事件

                    /**
                     * TODO<關於弧長的計算>
                     *
                     * 弧長公式： L = α*R
                     * 反余弦公式：arccos(cosα) = α
                     * 由於之前是有順時針偏移90度，
                     * 所以實際弧度範圍α2的值 ：α2 = π/2-α    （α=[0,π] α2 = [-π/2,π/2]）
                     * 根據正弦余弦轉換公式 cosα = sin(π/2-α)
                     * 代入，得： cosα = sin(π/2-α) = sinα2 = (R - y) / R
                     * 所以弧長 L = arccos(cosα)*R = arccos((R - y) / R)*R
                     */

                    float y = event.getY();
                    double L = Math.acos((radius - y) / radius) * radius;
                    //item0 有一半是在不可見區域，所以需要加上 itemHeight / 2
                    int circlePosition = (int) ((L + itemHeight / 2) / itemHeight);
                    float extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight;
                    //已滑動的弧長值
                    mOffset = (int) ((circlePosition - itemsVisible / 2) * itemHeight - extraOffset);

                    if ((System.currentTimeMillis() - startTime) > 120) {
                        // 處理拖拽事件
                        smoothScroll(ACTION.DAGGLE);
                    } else {
                        // 處理條目點擊事件
                        smoothScroll(ACTION.CLICK);
                    }
                }
                break;
        }

        invalidate();
        return true;
    }

    /**
     * 獲取Item個數
     *
     * @return item個數
     */
    public int getItemsCount() {
        return adapter != null ? adapter.getItemsCount() : 0;
    }

    /**
     * 附加在右邊的單位字符串
     *
     * @param label 單位
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void isCenterLabel(Boolean isCenterLabel) {
        this.isCenterLabel = isCenterLabel;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int getTextWidth(Paint paint, String str) {//計算文字寬度
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public void setIsOptions(boolean options) {
        isOptions = options;
    }


    public void setTextColorOut(int textColorOut) {
        if (textColorOut != 0) {
            this.textColorOut = textColorOut;
            paintOuterText.setColor(this.textColorOut);
        }
    }

    public void setTextColorCenter(int textColorCenter) {
        if (textColorCenter != 0) {

            this.textColorCenter = textColorCenter;
            paintCenterText.setColor(this.textColorCenter);
        }
    }

    public void setDividerColor(int dividerColor) {
        if (dividerColor != 0) {
            this.dividerColor = dividerColor;
            paintIndicator.setColor(this.dividerColor);
        }
    }

    public void setDividerType(DividerType dividerType) {
        this.dividerType = dividerType;
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        if (lineSpacingMultiplier != 0) {


            this.lineSpacingMultiplier = lineSpacingMultiplier;
            judgeLineSpae();

        }
    }


    public ACTION getAction_now() {
        return action_now;
    }


}