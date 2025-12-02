package com.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.app.mylibrary.R;
import com.app.interfaces.I_itemSelectedListener;
import com.app.widgts.MyAlertDialog;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.List;

/**
 * Created by seven on 2017/2/22.
 */

public class ItemChooseUtil {

    public static void showItemWheel(Context context, final List list, String title, int position, final I_itemSelectedListener i_itemSelectedListener) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.loopview_item, null);
        final LoopView loopView = (LoopView) contentView.findViewById(R.id.loopview);
        loopView.setNotLoop();//設置是否循環播放
        loopView.setListener(new OnItemSelectedListener() {//滾動監聽
            @Override
            public void onItemSelected(int index) {
            }
        });
        loopView.setDividerColor(Color.parseColor("#E3E3E3"));
        loopView.setCenterTextColor(Color.parseColor("#43496a"));
        loopView.setItems(list);//設置原始數據
        loopView.setCurrentPosition(position); //設置初始位置
        loopView.setTextSize(15);//設置字體大小
        final MyAlertDialog dialog1 = new MyAlertDialog(context)
                .builder()
                .setView(contentView);
        dialog1.setTitle(title);
        dialog1.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog1.setPositiveButton("確定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i_itemSelectedListener.onItemSelected(loopView.getSelectedItem());
            }
        });
        dialog1.show();
    }

//    public static void showItemWheel(Context context, final List list, String title, int position, final I_itemSelectedListener i_itemSelectedListener) {
//        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                i_itemSelectedListener.onItemSelected(options1);
//            }
//        })
//                .setTitleText(title)
//                .setCancelText("取消")//取消按鈕文字
//                .setSubmitText("確定")//確認按鈕文字
//                .setSubCalSize(15)
//                .setContentTextSize(15)//滾輪文字大小
//                .setTitleSize(15)//標題文字大小
//                .setDividerColor(Color.LTGRAY)//設置分割線的顏色
//                .setSubmitColor(Color.parseColor("#0088ff"))//確定按鈕文字顏色
//                .setCancelColor(Color.parseColor("#666666"))//取消按鈕文字顏色
//                .setDividerColor(Color.parseColor("#e3e3e3"))//設置分割線的顏色
//                .setTextColorCenter(Color.parseColor("#333333"))//設置分割線之間的文字的顏色
//                .setTextColorOut(Color.parseColor("#999999"))
//                .setSelectOptions(0)//默認選中項
//                .setBgColor(Color.WHITE)
//                .setLineSpacingMultiplier(1.6f)//越大越高
//                .setTitleBgColor(Color.WHITE)
//                .setTitleColor(Color.WHITE)
//                .setDividerType(WheelView.DividerType.FILL)//分割線是否分開
//                .isCenterLabel(true) //是否只顯示中間選中項的label文字，false則每項item全部都帶有label。
//                .setLabels("", "", "")
//                .setBackgroundId(Color.parseColor("#00000000")) //設置外部遮罩顏色
//                .build();
//        pvOptions.setSelectOptions(position);
//        pvOptions.setPicker(list);//一級選擇器
//        pvOptions.show();
//    }


}

