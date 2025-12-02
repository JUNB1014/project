package com.app.widgts.timepicker;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;


import com.app.mylibrary.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wsg on 17/5/16.
 */

public class TimePickerUtil {

    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;

    private int order;
    private TimePickerView mYearAndMonth;

    /**
     * 控件type类型
     *
     * @version 3.5.8
     * update by wangyb
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String mType) {
        type = mType;
    }



    /****
     * 时间选择
     * @param context
     * @param selectedDate
     * @param startDate
     * @param endDate
     * @param callback
     * @param isContainIime 是否包含时分秒
     */
    public void setDatePicker(Context context, TimePickerView.Type type, Calendar selectedDate, Calendar startDate, Calendar endDate, final TimePickerCallback callback) {

        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }
        if (startDate == null) {
            startDate = Calendar.getInstance();
            startDate.set(1900, 1, 1);
        }

//        LogDevUtils.e("最早"+startDate.get(Calendar.YEAR)+"<>"+startDate.get(Calendar.MONTH)+"<>"+startDate.get(Calendar.DAY_OF_MONTH));

        if (endDate == null) {
            endDate = Calendar.getInstance();
            endDate.set(2100, 12, 29);
        }
//        LogDevUtils.e("最晚"+endDate.get(Calendar.YEAR)+"<>"+endDate.get(Calendar.MONTH)+"<>"+endDate.get(Calendar.DAY_OF_MONTH));

        if (type == null) {
            type = TimePickerView.Type.YEAR_MONTH_DAY;
        }
        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v, String idcard) {
                //选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                callback.setTimeCallback(order, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), idcard);

            }
        })
                .setType(type)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(15)
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false, false, false)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#0088ff"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#666666"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setDrawable_Background_Title(context.getResources().getDrawable(R.drawable.shape_dialog_bottom_top))//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRange(1900, 2050)//默认是1900-2100年
                .setDividerColor(Color.parseColor("#e3e3e3"))//设置分割线的颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setTextColorCenter(Color.parseColor("#333333"))//设置分割线之间的文字的颜色
                .setTextColorOut(Color.parseColor("#999999"))
                .setLineSpacingMultiplier(1.6f)//越大越高
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setDividerType(WheelView.DividerType.WRAP)//分割线是否分开
                .build();

    }


    public void initOptionPicker(Context context, List<String> options1Items, final OptionSelectistener listener) {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                listener.onSelect(options1);
            }
        })
                .setTitleText("")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.WHITE)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.RED)
                .setTextColorCenter(Color.parseColor("#666666"))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(Color.parseColor("#00000000")) //设置外部遮罩颜色
                .build();
        //pvOptions.setSelectOptions(1,1);
        pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    public void showOpPicker() {
        if (pvOptions != null) {
            pvOptions.show();
        }
    }

    /**
     * @param order 0：出生日期；1：起保时间  2：xxxx年xx月时间
     */
    public void showTimePicker(int order) {
        switch (order) {
            case 0:
            case 1:
            case 3:
            case 5:
            case 9:
                if (pvTime != null) {
                    this.order = order;
                    pvTime.show();
                }
                break;
            default:
                if (mYearAndMonth != null) {
                    this.order = order;
                    mYearAndMonth.show();
                }
                break;
        }
    }


    /**
     * 二级时间控件  xxxx年xx月
     *
     * @param context
     * @param selectedDate 默认选择日期
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param callback
     */
    public void setDatePickerYearAndMouth(Context context, Calendar selectedDate, Calendar startDate, Calendar endDate, final TimePickerCallback callback) {

        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }
        if (startDate == null) {
            startDate = Calendar.getInstance();
            startDate.set(1900, 0, 1);
        }

//        LogDevUtils.e("最早"+startDate.get(Calendar.YEAR)+"<>"+startDate.get(Calendar.MONTH)+"<>"+startDate.get(Calendar.DAY_OF_MONTH));

        if (endDate == null) {
            endDate = Calendar.getInstance();
            endDate.set(2100, 11, 29);
        }
//        LogDevUtils.e("最晚"+endDate.get(Calendar.YEAR)+"<>"+endDate.get(Calendar.MONTH)+"<>"+endDate.get(Calendar.DAY_OF_MONTH));

        mYearAndMonth = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v, String idcard) {
                //选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                callback.setTimeCallback(order, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 0, 0, idcard);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH)//默认显示年月
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(15)
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false, true, false)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.RED)//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#999999"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRange(1900, 2050)//默认是1900-2100年
                .setDividerColor(Color.parseColor("#FFFFFF"))//设置分割线的颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setTextColorCenter(Color.parseColor("#666666"))//设置分割线之间的文字的颜色
                .setTextColorOut(Color.parseColor("#999999"))
                .setLineSpacingMultiplier(1.6f)//越大越高
                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年","月","日","时","分","秒")
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setDividerType(WheelView.DividerType.WRAP)//分割线是否分开
                .build();
    }

    public void setInfoSettingPicker(Context context, Calendar selectedDate, Calendar startDate, Calendar endDate, final TimePickerCallback callback, ViewGroup ResLayout, int custermLayoutId, CustomListener listener) {

        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }
        if (startDate == null) {
            startDate = Calendar.getInstance();
            startDate.set(1900, 1, 1);
        }


        if (endDate == null) {
            endDate = Calendar.getInstance();
            endDate.set(3099, 12, 29);
        }

        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v, String idcard) {
                //选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                callback.setTimeCallback(order, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 0, 0, idcard);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(15)
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false, true, true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.RED)//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#999999"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRange(1900, 2050)//默认是1900-2100年
                .setDividerColor(Color.parseColor("#FFFFFF"))//设置分割线的颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setTextColorCenter(Color.parseColor("#666666"))//设置分割线之间的文字的颜色
                .setTextColorOut(Color.parseColor("#999999"))
                .setLineSpacingMultiplier(1.6f)//越大越高
                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年","月","日","时","分","秒")
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setDividerType(WheelView.DividerType.FILL)//分割线是否分开
                .setDecorView(ResLayout)
                .setLayoutRes(custermLayoutId, listener)
                .build();
    }


}
