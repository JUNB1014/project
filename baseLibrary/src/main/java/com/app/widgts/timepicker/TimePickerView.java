package com.app.widgts.timepicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.app.mylibrary.R;
import com.app.widgts.WarpLinearLayout;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 時間選擇器
 * Created by Sai on 15/11/22.
 * Updated by XiaoSong on 2017-2-22.
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener, WheelTime.RollDay {
    private int layoutRes;
    private CustomListener customListener;

    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR, MONTH_DAY_HOUR_MIN, YEAR_MONTH, YEAR_MONTH_DAY_HOUR_MIN, YEAR_MONTH_DAY_HOUR
    } // 六種選擇模式，年月日時分秒，年月日，時分，月日時分，年月，年月日時分

    WheelTime wheelTime; //自定義控件
    private Button btnSubmit, btnCancel; //確定、取消按鈕
    private TextView tvTitle;//標題
    private OnTimeSelectListener timeSelectListener;//回調接口
    private int gravity = Gravity.CENTER;//內容顯示位置 默認居中
    private TimePickerView.Type type;// 顯示類型

    private WarpLinearLayout mygroup;

    private String Str_Submit;//確定按鈕字符串
    private String Str_Cancel;//取消按鈕字符串
    private String Str_Title;//標題字符串

    private int Color_Submit;//確定按鈕顏色
    private int Color_Cancel;//取消按鈕顏色
    private int Color_Title;//標題顏色

    private int Color_Background_Wheel;//滾輪背景顏色
    private int Color_Background_Title;//標題背景顏色

    private Drawable Drawable_Background_Title;//標題背景顏色

    private int Size_Submit_Cancel;//確定取消按鈕大小
    private int Size_Title;//標題字體大小
    private int Size_Content;//內容字體大小

    private Calendar date;//當前選中時間
    private Calendar startDate;//開始時間
    private Calendar endDate;//終止時間
    private int startYear;//開始年份
    private int endYear;//結尾年份

    private boolean ycyclic, mcyclic, dcyclic;//是否循環
    private boolean cancelable;//是否能取消
    private boolean isCenterLabel;//是否只顯示中間的label

    private int textColorOut; //分割線以外的文字顏色
    private int textColorCenter; //分割線之間的文字顏色
    private int dividerColor; //分割線的顏色
    private int backgroudId; //顯示時的外部背景色顏色,默認是灰色


    // 條目間距倍數 默認1.6
    private float lineSpacingMultiplier = 1.6F;
    private boolean isDialog;//是否是對話框模式
    private String label_year, label_month, label_day, label_hours, label_mins, label_seconds;
    private WheelView.DividerType dividerType;//分隔線類型

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private Context mContext;


    private String selectIdcard = "";

    //構造方法
    public TimePickerView(Builder builder) {
        super(builder.context);
        this.timeSelectListener = builder.timeSelectListener;
        this.gravity = builder.gravity;
        this.type = builder.type;
        this.Str_Submit = builder.Str_Submit;
        this.Str_Cancel = builder.Str_Cancel;
        this.Str_Title = builder.Str_Title;
        this.Color_Submit = builder.Color_Submit;
        this.Color_Cancel = builder.Color_Cancel;
        this.Color_Title = builder.Color_Title;
        this.Color_Background_Wheel = builder.Color_Background_Wheel;
        this.Color_Background_Title = builder.Color_Background_Title;
        this.Drawable_Background_Title = builder.Drawable_Background_Title;
        this.Size_Submit_Cancel = builder.Size_Submit_Cancel;
        this.Size_Title = builder.Size_Title;
        this.Size_Content = builder.Size_Content;
        this.startYear = builder.startYear;
        this.endYear = builder.endYear;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.date = builder.date;
        this.ycyclic = builder.ycyclic;
        this.mcyclic = builder.mcyclic;
        this.dcyclic = builder.dcyclic;
        this.isCenterLabel = builder.isCenterLabel;
        this.cancelable = builder.cancelable;
        this.label_year = builder.label_year;
        this.label_month = builder.label_month;
        this.label_day = builder.label_day;
        this.label_hours = builder.label_hours;
        this.label_mins = builder.label_mins;
        this.label_seconds = builder.label_seconds;
        this.textColorCenter = builder.textColorCenter;
        this.textColorOut = builder.textColorOut;
        this.dividerColor = builder.dividerColor;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.lineSpacingMultiplier = builder.lineSpacingMultiplier;
        this.isDialog = builder.isDialog;
        this.dividerType = builder.dividerType;
        this.backgroudId = builder.backgroudId;
        this.decorView = builder.decorView;
        this.mContext = builder.context;
        initView(builder.context);


    }


    //建造器
    public static class Builder {
        private int layoutRes = R.layout.pickerview_time;
        private CustomListener customListener;
        private Context context;
        private OnTimeSelectListener timeSelectListener;

        private TimePickerView.Type type = Type.ALL;//顯示類型 默認全部顯示
        private int gravity = Gravity.CENTER;//內容顯示位置 默認居中

        private String Str_Submit;//確定按鈕文字
        private String Str_Cancel;//取消按鈕文字
        private String Str_Title;//標題文字

        private int Color_Submit;//確定按鈕顏色
        private int Color_Cancel;//取消按鈕顏色
        private int Color_Title;//標題顏色

        private int Color_Background_Wheel;//滾輪背景顏色
        private int Color_Background_Title;//標題背景顏色

        private Drawable Drawable_Background_Title;//標題背景顏色

        private int Size_Submit_Cancel = 17;//確定取消按鈕大小
        private int Size_Title = 18;//標題字體大小
        private int Size_Content = 18;//內容字體大小
        private Calendar date;//當前選中時間
        private Calendar startDate;//開始時間
        private Calendar endDate;//終止時間
        private int startYear;//開始年份
        private int endYear;//結尾年份

        private boolean ycyclic = false;//是否循環
        private boolean mcyclic = false;//是否循環
        private boolean dcyclic = false;//是否循環
        private boolean cancelable = true;//是否能取消
        private boolean isCenterLabel = true;//是否只顯示中間的label
        public ViewGroup decorView;//顯示pickerview的根View,默認是activity的根view
        private int textColorOut; //分割線以外的文字顏色
        private int textColorCenter; //分割線之間的文字顏色
        private int dividerColor; //分割線的顏色
        private int backgroudId; //顯示時的外部背景色顏色,默認是灰色
        private WheelView.DividerType dividerType;//分隔線類型
        // 條目間距倍數 默認1.6
        private float lineSpacingMultiplier = 1.6F;

        private boolean isDialog;//是否是對話框模式


        private String label_year, label_month, label_day, label_hours, label_mins, label_seconds;//單位

        //Required
        public Builder(Context context, OnTimeSelectListener listener) {
            this.context = context;
            this.timeSelectListener = listener;
        }

        //Option
        public Builder setType(TimePickerView.Type type) {
            this.type = type;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setSubmitText(String Str_Submit) {
            this.Str_Submit = Str_Submit;
            return this;
        }


        public Builder isDialog(boolean isDialog) {
            this.isDialog = isDialog;
            return this;
        }

        public Builder setCancelText(String Str_Cancel) {
            this.Str_Cancel = Str_Cancel;
            return this;
        }

        public Builder setTitleText(String Str_Title) {
            this.Str_Title = Str_Title;
            return this;
        }

        public Builder setSubmitColor(int Color_Submit) {
            this.Color_Submit = Color_Submit;
            return this;
        }

        public Builder setCancelColor(int Color_Cancel) {
            this.Color_Cancel = Color_Cancel;
            return this;
        }

        /**
         * 必須是viewgroup
         * 設置要將pickerview顯示到的容器id
         *
         * @param decorView
         * @return
         */
        public Builder setDecorView(ViewGroup decorView) {
            this.decorView = decorView;
            return this;
        }

        public Builder setBgColor(int Color_Background_Wheel) {
            this.Color_Background_Wheel = Color_Background_Wheel;
            return this;
        }

        public Builder setTitleBgColor(int Color_Background_Title) {
            this.Color_Background_Title = Color_Background_Title;
            return this;
        }

        public Builder setDrawable_Background_Title(Drawable drawable_Background_Title) {
            Drawable_Background_Title = drawable_Background_Title;
            return this;
        }

        public Builder setTitleColor(int Color_Title) {
            this.Color_Title = Color_Title;
            return this;
        }

        public Builder setSubCalSize(int Size_Submit_Cancel) {
            this.Size_Submit_Cancel = Size_Submit_Cancel;
            return this;
        }

        public Builder setTitleSize(int Size_Title) {
            this.Size_Title = Size_Title;
            return this;
        }

        public Builder setContentSize(int Size_Content) {
            this.Size_Content = Size_Content;
            return this;
        }

        /**
         * 因為系統Calendar的月份是從0-11的,所以如果是調用Calendar的set方法來設置時間,月份的範圍也要是從0-11
         *
         * @param date
         * @return
         */
        public Builder setDate(Calendar date) {
            this.date = date;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener customListener) {
            this.layoutRes = res;
            this.customListener = customListener;
            return this;
        }

        public Builder setRange(int startYear, int endYear) {
            this.startYear = startYear;
            this.endYear = endYear;
            return this;
        }


        /**
         * 設置起始時間
         * 因為系統Calendar的月份是從0-11的,所以如果是調用Calendar的set方法來設置時間,月份的範圍也要是從0-11
         *
         * @return
         */

        public Builder setRangDate(Calendar startDate, Calendar endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            return this;
        }


        /**
         * 設置間距倍數,但是只能在1.2-2.0f之間
         *
         * @param lineSpacingMultiplier
         */
        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier) {
            this.lineSpacingMultiplier = lineSpacingMultiplier;
            return this;
        }

        /**
         * 設置分割線的顏色
         *
         * @param dividerColor
         */
        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        /**
         * 設置分割線的類型
         *
         * @param dividerType
         */
        public Builder setDividerType(WheelView.DividerType dividerType) {
            this.dividerType = dividerType;
            return this;
        }

        /**
         * //顯示時的外部背景色顏色,默認是灰色
         *
         * @param backgroudId
         */

        public Builder setBackgroudId(int backgroudId) {
            this.backgroudId = backgroudId;
            return this;
        }

        /**
         * 設置分割線之間的文字的顏色
         *
         * @param textColorCenter
         */
        public Builder setTextColorCenter(int textColorCenter) {
            this.textColorCenter = textColorCenter;
            return this;
        }

        /**
         * 設置分割線以外文字的顏色
         *
         * @param textColorOut
         */
        public Builder setTextColorOut(int textColorOut) {
            this.textColorOut = textColorOut;
            return this;
        }

        public Builder isCyclic(boolean ycyclic, boolean mcyclic, boolean dcyclic) {
            this.ycyclic = ycyclic;
            this.mcyclic = mcyclic;
            this.dcyclic = dcyclic;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setLabel(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
            this.label_year = label_year;
            this.label_month = label_month;
            this.label_day = label_day;
            this.label_hours = label_hours;
            this.label_mins = label_mins;
            this.label_seconds = label_seconds;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            this.isCenterLabel = isCenterLabel;
            return this;
        }


        public TimePickerView build() {
            return new TimePickerView(this);
        }
    }


    private void initView(Context context) {
        setDialogOutSideCancelable(cancelable);
        initViews(backgroudId);
        init();
        initEvents();
        if (customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);

            //常用被保人選項
            mygroup = (WarpLinearLayout) findViewById(R.id.mygroup);

            //頂部標題
            tvTitle = (TextView) findViewById(R.id.tvTitle);

            //確定和取消按鈕
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            btnCancel = (Button) findViewById(R.id.btnCancel);

            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);

            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //設置文字
            btnSubmit.setText(TextUtils.isEmpty(Str_Submit) ? context.getResources().getString(R.string.pickerview_submit) : Str_Submit);
            btnCancel.setText(TextUtils.isEmpty(Str_Cancel) ? context.getResources().getString(R.string.pickerview_cancel) : Str_Cancel);
            tvTitle.setText(TextUtils.isEmpty(Str_Title) ? "" : Str_Title);//默認為空

            //設置文字顏色
            btnSubmit.setTextColor(Color_Submit == 0 ? pickerview_timebtn_nor : Color_Submit);
            btnCancel.setTextColor(Color_Cancel == 0 ? pickerview_timebtn_nor : Color_Cancel);
            tvTitle.setTextColor(Color_Title == 0 ? pickerview_topbar_title : Color_Title);

            //設置文字大小
            btnSubmit.setTextSize(Size_Submit_Cancel);
            btnCancel.setTextSize(Size_Submit_Cancel);
            tvTitle.setTextSize(Size_Title);

            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);
            rv_top_bar.setBackgroundColor(Color_Background_Title == 0 ? pickerview_bg_topbar : Color_Background_Title);

            rv_top_bar.setBackgroundDrawable(Drawable_Background_Title);


        } else {
            customListener.customLayout(LayoutInflater.from(context).inflate(layoutRes, contentContainer));
        }
        // 時間轉輪 自定義控件
        LinearLayout timePickerView = (LinearLayout) findViewById(R.id.timepicker);

        timePickerView.setBackgroundColor(Color_Background_Wheel == 0 ? bgColor_default : Color_Background_Wheel);

        wheelTime = new WheelTime(timePickerView, type, gravity, Size_Content, this);

        if (startYear != 0 && endYear != 0 && startYear <= endYear) {
            setRange();
        }

        if (startDate != null && endDate != null) {
            if (startDate.getTimeInMillis() <= endDate.getTimeInMillis()) {
                setRangDate();
            }
        } else if (startDate != null && endDate == null) {
            setRangDate();
        } else if (startDate == null && endDate != null) {
            setRangDate();
        }

        setTime();
        wheelTime.setLabels(label_year, label_month, label_day, label_hours, label_mins, label_seconds);

        setOutSideCancelable(cancelable);
        wheelTime.setCyclic(ycyclic, mcyclic, dcyclic);
        wheelTime.setDividerColor(dividerColor);
        wheelTime.setDividerType(dividerType);
        wheelTime.setLineSpacingMultiplier(lineSpacingMultiplier);
        wheelTime.setTextColorOut(textColorOut);
        wheelTime.setTextColorCenter(textColorCenter);
        wheelTime.isCenterLabel(isCenterLabel);
    }


    /**
     * 設置默認時間
     */
    public void setDate(Calendar date) {
        this.date = date;
        setTime();
    }

    /**
     * 設置可以選擇的時間範圍, 要在setTime之前調用才有效果
     */
    private void setRange() {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);

    }

    /**
     * 設置可以選擇的時間範圍, 要在setTime之前調用才有效果
     */
    private void setRangDate() {
        wheelTime.setRangDate(startDate, endDate);
        //如果設置了時間範圍
        if (startDate != null && endDate != null) {
            //判斷一下默認時間是否設置了，或者是否在起始終止時間範圍內
            if (date == null || date.getTimeInMillis() < startDate.getTimeInMillis()
                    || date.getTimeInMillis() > endDate.getTimeInMillis()) {
                date = startDate;
            }
        } else if (startDate != null) {
            //沒有設置默認選中時間,那就拿開始時間當默認時間
            date = startDate;
        } else if (endDate != null) {
            date = endDate;
        }
    }

    /**
     * 設置選中時間,默認選中當前時間
     */
    private void setTime() {
        int year, month, day, hours, minute, seconds;

        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);
        } else {
            year = date.get(Calendar.YEAR);
            month = date.get(Calendar.MONTH);
            day = date.get(Calendar.DAY_OF_MONTH);
            hours = date.get(Calendar.HOUR_OF_DAY);
            minute = date.get(Calendar.MINUTE);
            seconds = date.get(Calendar.SECOND);
        }


        wheelTime.setPicker(year, month, day, hours, minute, seconds);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            if (wheelTime.isSelectFinish()) {
                returnData();
            }
        }
        dismiss();
    }

    public void returnData() {
        if (timeSelectListener != null) {
            try {
                Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
//                selectBirthday(date);
                timeSelectListener.onTimeSelect(date, clickView, selectIdcard);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date, View v, String idcard);
    }

    @Override
    public boolean isDialog() {
        return isDialog;
    }


    @Override
    public void resultRoll() {

        Date date = null;
        try {
            date = WheelTime.dateFormat.parse(wheelTime.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
