package com.app.widgts.timepicker;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.app.mylibrary.R;

import java.util.List;

/**
 * 條件選擇器
 * Created by Sai on 15/11/22.
 */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {

    WheelOptions<T> wheelOptions;
    private int layoutRes;
    private CustomListener customListener;
    private Button btnSubmit, btnCancel; //確定、取消按鈕
    private TextView tvTitle;
    private RelativeLayout rv_top_bar;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private OnOptionsSelectListener optionsSelectListener;

    private String Str_Submit;//確定按鈕文字
    private String Str_Cancel;//取消按鈕文字
    private String Str_Title;//標題文字

    private int Color_Submit;//確定按鈕顏色
    private int Color_Cancel;//取消按鈕顏色
    private int Color_Title;//標題顏色

    private int Color_Background_Wheel;//滾輪背景顏色
    private int Color_Background_Title;//標題背景顏色

    private int Size_Submit_Cancel;//確定取消按鈕大小
    private int Size_Title;//標題文字大小
    private int Size_Content;//內容文字大小

    private int textColorOut; //分割線以外的文字顏色
    private int textColorCenter; //分割線之間的文字顏色
    private int dividerColor; //分割線的顏色
    private int backgroundId; //顯示時的外部背景色顏色,默認是灰色
    // 條目間距倍數 默認1.6
    private float lineSpacingMultiplier = 1.6F;
    private boolean isDialog;//是否是對話框模式

    private boolean cancelable;//是否能取消
    private boolean linkage;//是否聯動

    private boolean isCenterLabel;//是否只顯示中間的label

    private String label1;//單位
    private String label2;
    private String label3;

    private boolean cyclic1;//是否循環
    private boolean cyclic2;
    private boolean cyclic3;

    private Typeface font;//字體樣式

    private int option1;//默認選中項
    private int option2;
    private int option3;

    private int xoffset_one;//x軸偏移量
    private int xoffset_two;
    private int xoffset_three;

    private WheelView.DividerType dividerType;//分隔線類型

    //構造方法
    public OptionsPickerView(Builder builder) {
        super(builder.context);
        this.optionsSelectListener = builder.optionsSelectListener;
        this.Str_Submit = builder.Str_Submit;
        this.Str_Cancel = builder.Str_Cancel;
        this.Str_Title = builder.Str_Title;

        this.Color_Submit = builder.Color_Submit;
        this.Color_Cancel = builder.Color_Cancel;
        this.Color_Title = builder.Color_Title;
        this.Color_Background_Wheel = builder.Color_Background_Wheel;
        this.Color_Background_Title = builder.Color_Background_Title;

        this.Size_Submit_Cancel = builder.Size_Submit_Cancel;
        this.Size_Title = builder.Size_Title;
        this.Size_Content = builder.Size_Content;

        this.cyclic1 = builder.cyclic1;
        this.cyclic2 = builder.cyclic2;
        this.cyclic3 = builder.cyclic3;

        this.cancelable = builder.cancelable;
        this.linkage = builder.linkage;
        this.isCenterLabel = builder.isCenterLabel;

        this.label1 = builder.label1;
        this.label2 = builder.label2;
        this.label3 = builder.label3;

        this.font = builder.font;

        this.option1 = builder.option1;
        this.option2 = builder.option2;
        this.option3 = builder.option3;
        this.xoffset_one = builder.xoffset_one;
        this.xoffset_two = builder.xoffset_two;
        this.xoffset_three = builder.xoffset_three;

        this.textColorCenter = builder.textColorCenter;
        this.textColorOut = builder.textColorOut;
        this.dividerColor = builder.dividerColor;
        this.lineSpacingMultiplier = builder.lineSpacingMultiplier;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.isDialog = builder.isDialog;
        this.dividerType = builder.dividerType;
        this.backgroundId = builder.backgroundId;
        this.decorView = builder.decorView;
        initView(builder.context);
    }


    //建造器
    public static class Builder {
        private int layoutRes = R.layout.pickerview_options;
        private CustomListener customListener;
        private Context context;
        private OnOptionsSelectListener optionsSelectListener;

        private String Str_Submit;//確定按鈕文字
        private String Str_Cancel;//取消按鈕文字
        private String Str_Title;//標題文字

        private int Color_Submit;//確定按鈕顏色
        private int Color_Cancel;//取消按鈕顏色
        private int Color_Title;//標題顏色

        private int Color_Background_Wheel;//滾輪背景顏色
        private int Color_Background_Title;//標題背景顏色

        private int Size_Submit_Cancel = 17;//確定取消按鈕大小
        private int Size_Title = 18;//標題文字大小
        private int Size_Content = 18;//內容文字大小

        private boolean cancelable = true;//是否能取消
        private boolean linkage = true;//是否聯動
        private boolean isCenterLabel = true;//是否只顯示中間的label

        private int textColorOut; //分割線以外的文字顏色
        private int textColorCenter; //分割線之間的文字顏色
        private int dividerColor; //分割線的顏色
        private int backgroundId; //顯示時的外部背景色顏色,默認是灰色
        public ViewGroup decorView;//顯示pickerview的根View,默認是activity的根view
        // 條目間距倍數 默認1.6
        private float lineSpacingMultiplier = 1.6F;
        private boolean isDialog;//是否是對話框模式

        private String label1;
        private String label2;
        private String label3;

        private boolean cyclic1 = false;//是否循環，默認否
        private boolean cyclic2 = false;
        private boolean cyclic3 = false;

        private Typeface font;

        private int option1;//默認選中項
        private int option2;
        private int option3;

        private int xoffset_one;//x軸偏移量
        private int xoffset_two;
        private int xoffset_three;

        private WheelView.DividerType dividerType;//分隔線類型

        //Required
        public Builder(Context context, OnOptionsSelectListener listener) {
            this.context = context;
            this.optionsSelectListener = listener;
        }

        //Option

        public Builder setSubmitText(String Str_Cancel) {
            this.Str_Submit = Str_Cancel;
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

        public Builder isDialog(boolean isDialog) {
            this.isDialog = isDialog;
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
         * 顯示時的外部背景色顏色,默認是灰色
         *
         * @param backgroundId
         * @return
         */
        public Builder setBackgroundId(int backgroundId) {
            this.backgroundId = backgroundId;
            return this;
        }

        /**
         * 必須是viewgroup
         * 設置要將pickerview顯示到的容器
         *
         * @param decorView
         * @return
         */
        public Builder setDecorView(ViewGroup decorView) {
            this.decorView = decorView;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener listener) {
            this.layoutRes = res;
            this.customListener = listener;
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

        public Builder setContentTextSize(int Size_Content) {
            this.Size_Content = Size_Content;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        /**
         * 此方法已廢棄
         * 不聯動的情況下，請調用 setNPicker 方法。
         */
        @Deprecated
        public Builder setLinkage(boolean linkage) {
            this.linkage = linkage;
            return this;
        }

        public Builder setLabels(String label1, String label2, String label3) {
            this.label1 = label1;
            this.label2 = label2;
            this.label3 = label3;
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

        public Builder setTypeface(Typeface font) {
            this.font = font;
            return this;
        }

        public Builder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
            this.cyclic1 = cyclic1;
            this.cyclic2 = cyclic2;
            this.cyclic3 = cyclic3;
            return this;
        }

        public Builder setSelectOptions(int option1) {
            this.option1 = option1;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2) {
            this.option1 = option1;
            this.option2 = option2;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2, int option3) {
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            return this;
        }

        public Builder setTextXOffset(int xoffset_one, int xoffset_two, int xoffset_three) {
            this.xoffset_one = xoffset_one;
            this.xoffset_two = xoffset_two;
            this.xoffset_three = xoffset_three;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            this.isCenterLabel = isCenterLabel;
            return this;
        }

        public OptionsPickerView build() {
            return new OptionsPickerView(this);
        }
    }


    private void initView(Context context) {
        setDialogOutSideCancelable(cancelable);
        initViews(backgroundId);
        init();
        initEvents();
        if (customListener == null) {
            LayoutInflater.from(context).inflate(layoutRes, contentContainer);

            //頂部標題
            tvTitle = (TextView) findViewById(R.id.tvTitle);
            rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);

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

            //設置color
            btnSubmit.setTextColor(Color_Submit == 0 ? pickerview_timebtn_nor : Color_Submit);
            btnCancel.setTextColor(Color_Cancel == 0 ? pickerview_timebtn_nor : Color_Cancel);
            tvTitle.setTextColor(Color_Title == 0 ? pickerview_topbar_title : Color_Title);
            rv_top_bar.setBackgroundColor(Color_Background_Title == 0 ? pickerview_bg_topbar : Color_Background_Title);

            //設置文字大小
            btnSubmit.setTextSize(Size_Submit_Cancel);
            btnCancel.setTextSize(Size_Submit_Cancel);
            tvTitle.setTextSize(Size_Title);
            tvTitle.setText(Str_Title);
        } else {
            customListener.customLayout(LayoutInflater.from(context).inflate(layoutRes, contentContainer));
        }

        // ----滾輪布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.optionspicker);
        optionsPicker.setBackgroundColor(Color_Background_Wheel == 0 ? bgColor_default : Color_Background_Wheel);

        wheelOptions = new WheelOptions(optionsPicker, linkage);
        wheelOptions.setTextContentSize(Size_Content);
        wheelOptions.setLabels(label1, label2, label3);
        wheelOptions.setTextXOffset(xoffset_one, xoffset_two, xoffset_three);

        wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);
        wheelOptions.setTypeface(font);

        setOutSideCancelable(cancelable);

        if (tvTitle != null) {
            tvTitle.setText(Str_Title);
        }

        wheelOptions.setDividerColor(dividerColor);
        wheelOptions.setDividerType(dividerType);
        wheelOptions.setLineSpacingMultiplier(lineSpacingMultiplier);
        wheelOptions.setTextColorOut(textColorOut);
        wheelOptions.setTextColorCenter(textColorCenter);
        wheelOptions.isCenterLabel(isCenterLabel);
    }


    /**
     * 設置默認選中項
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        this.option1 = option1;
        SetCurrentItems();
    }


    public void setSelectOptions(int option1, int option2) {
        this.option1 = option1;
        this.option2 = option2;
        SetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        SetCurrentItems();
    }

    private void SetCurrentItems() {
        if (wheelOptions != null) {
            wheelOptions.setCurrentItems(option1, option2, option3);
        }
    }

    public void setPicker(List<T> optionsItems) {
        this.setPicker(optionsItems, null, null);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items) {
        this.setPicker(options1Items, options2Items, null);
    }

    public void setPicker(List<T> options1Items,
                          List<List<T>> options2Items,
                          List<List<List<T>>> options3Items) {

        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        SetCurrentItems();
    }


    //不聯動情況下調用
    public void setNPicker(List<T> options1Items,
                           List<T> options2Items,
                           List<T> options3Items) {

        wheelOptions.setNPicker(options1Items, options2Items, options3Items);
        SetCurrentItems();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    //抽離接口回調的方法
    public void returnData() {
        if (optionsSelectListener != null) {
            int[] optionsCurrentItems = wheelOptions.getCurrentItems();
            optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], clickView);
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int options2, int options3, View v);
    }

    @Override
    public boolean isDialog() {
        return isDialog;
    }
}
