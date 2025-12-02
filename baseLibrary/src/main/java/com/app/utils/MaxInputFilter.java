package com.app.utils;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MaxInputFilter implements InputFilter {

    /**
     * 正則表達式：以0或正整數開頭後跟0或1個(小數點後面跟0到2位數字)
     */
    private static final String FORMAT = "^(0|[1-9]\\d*)(\\.\\d{0,%s})?$";

    /**
     * 正則表達式：0-9.之外的字符
     */
    private static final Pattern SOURCE_PATTERN = Pattern.compile("[^0-9.]");

    /**
     * 默認保留小數點後2位
     */
    private Pattern mPattern = Pattern.compile(String.format(FORMAT, "2"));

    /**
     * 允許輸入的最大金額
     */
    private double maxValue = Integer.MAX_VALUE;

    private EditText editText;

    private String maxValueStr;

    /**
     * 設置保留小數點後的位數，默認保留2位
     *
     * @param length
     */
    public void setDecimalLength(int length) {
        mPattern = Pattern.compile(String.format(FORMAT, length));
    }

    /**
     * 設置允許輸入的最大金額
     *
     * @param maxValue
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }


    /**
     * 設置允許輸入的最大金額
     *
     * @param maxValueStr
     */
    public void setMaxValueStr(String maxValueStr) {
        this.maxValueStr = maxValueStr;
    }


    public void setEditMaxContent(EditText editText) {
        this.editText = editText;
    }

    /**
     * 當系統使用source的start到end的字串替換dest字符串中的dstart到dend位置的內容時，會調用本方法
     *
     * @param source 新輸入的字符串
     * @param start  新輸入的字符串起始下標，一般為0（刪除時例外）
     * @param end    新輸入的字符串終點下標，一般為source長度-1（刪除時例外）
     * @param dest   輸入之前文本框內容
     * @param dstart 原內容起始坐標，一般為dest長度（刪除時例外）
     * @param dend   原內容終點坐標，一般為dest長度（刪除時例外）
     * @return 
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        // 刪除時不用處理
        if (TextUtils.isEmpty(source)) {
            return null;
        }

        // 不接受數字、小數點之外的字符
        if (SOURCE_PATTERN.matcher(source).find()) {
            return "";
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder(dest);
        ssb.replace(dstart, dend, source, start, end);
        Matcher matcher = mPattern.matcher(ssb);
        if (matcher.find()) {
            String str = matcher.group();
            Logger.e("---------匹配到的字符串=%s" + str);

            //驗證輸入金額的大小
            double value = Double.parseDouble(str);
            if (value > maxValue) {
                editText.setText(maxValueStr);
                editText.setSelection(editText.getText().toString().length());
//                return "";
            }
            return source;
        } else {
            Logger.e("---不匹配的字符串=%s" + ssb.toString());
            return "";
        }
    }
}
