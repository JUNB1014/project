package com.app.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import com.app.widgts.CustomDialog;
import com.app.widgts.TipsDialog;
import com.app.widgts.ZhuiXingDialog;

/**
 *
 */
public class DialogUtil {

    /*兩個按鈕帶標題*/
    public static CustomDialog showDialog(final Context context, CustomDialog dialog, int button, String title, String message, String PositiveMessage, String NegetiveMessage, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener Negativelistener) {

        if (dialog == null) {
            CustomDialog.Builder builder = new CustomDialog.Builder(context, button);
            builder.setMessage(message);
            builder.setTitle(title);
            builder.setPositiveButton(PositiveMessage, positiveListener);
            builder.setNegativeButton(NegetiveMessage, Negativelistener);


            dialog = builder.create();

            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ScreenUtils.getScreenW(context) - DisplayUtil.dip2px(context, 76);
            dialog.getWindow().setAttributes(params);
        }

        return dialog;
    }

    /*兩個按鈕帶標題*/
    public static ZhuiXingDialog showZhuiXingDialog(final Context context, ZhuiXingDialog dialog, int button, String title, String PositiveMessage, String NegetiveMessage, DialogInterface.OnClickListener positiveListener, ZhuiXingDialog.GetContentListener Negativelistener) {

        if (dialog == null) {
            ZhuiXingDialog.Builder builder = new ZhuiXingDialog.Builder(context, button);
            builder.setTitle(title);
            builder.setPositiveButton(PositiveMessage, positiveListener);
            builder.setNegativeButton(NegetiveMessage, Negativelistener);


            dialog = builder.create();

            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ScreenUtils.getScreenW(context) - DisplayUtil.dip2px(context, 76);
            dialog.getWindow().setAttributes(params);
        }

        return dialog;
    }


    /*提示*/
    public static TipsDialog showTipDialog(final Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {


        TipsDialog.Builder builder = new TipsDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setOnClickListener(onClickListener);
        TipsDialog dialog = builder.create();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtils.getScreenW(context) - DisplayUtil.dip2px(context, 76);
        dialog.getWindow().setAttributes(params);

        return dialog;
    }
}
