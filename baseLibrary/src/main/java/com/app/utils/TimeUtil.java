package com.app.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeUtil {
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 傳入當前時間，獲取到次日淩晨時長
     *
     * @return
     */
    public static Long getSecondsNowToNext() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        // 改成這樣就好了
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }


    /**
     * 將時間轉換成需要的格式 00:00:00
     *
     * @param time 秒
     * @return
     */

    public static String formatLongToTimeStr(Long time) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        String hour2 = "";
        String minute2 = "";
        String second2 = "";
        second = time.intValue();
        if (second > 60) {
            minute = second / 60;    //整數部分---分鐘
            second = second % 60;    //余數部分---秒
        }
        if (minute > 60) {
            hour = minute / 60;     //整數部分---時
            minute = minute % 60;   //余數部分---分鐘
        }
        if ((hour + "").length() == 1) {  //時
            hour2 = "0" + hour;
        } else {
            hour2 = hour + "";
        }
        if ((minute + "").length() == 1) {//分
            minute2 = "0" + minute;
        } else {
            minute2 = minute + "";
        }
        if ((second + "").length() == 1) {//秒
            second2 = "0" + second;
        } else {
            second2 = second + "";
        }
        String strtime = hour2 + ":" + minute2 + ":" + second2;
        return strtime;
    }


    /**
     * 獲取當前日期
     *
     * @param format 格式
     * @return
     */
    //獲取當前日期 yyyy-MM-dd
    public static String getTodayData(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);//設置日期格式
        String str = df.format(new Date());// new Date()為獲取當前系統時間
        return str;
    }


    // string類型轉換為date類型
    // strTime要轉換的string類型的時間，formatType要轉換的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH時mm分ss秒，
    // strTime的時間格式必須要與formatType的時間格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 把獲取的時間從一種格式轉換成另一種格式
     *
     * @param time
     * @param formatBefor
     * @param formatAfter
     * @return
     */
    public static String getFormatTime(String time, String formatBefor, String formatAfter) {
        if (!TextUtils.isEmpty(time) && !TextUtils.isEmpty(formatBefor) && !TextUtils.isEmpty(formatAfter)) {
            Date date = stringToDate(time, formatBefor);
            SimpleDateFormat simpleDateFormatAfter = new SimpleDateFormat(formatAfter);
            return simpleDateFormatAfter.format(date);
        }
        return "";
    }


    public static String stringToString(String time, String beforFormatType, String toFormatType) {
        if (StringUtil.isEmpty(time) || StringUtil.isEmpty(beforFormatType) || StringUtil.isEmpty(toFormatType))
            return "";
        Date date = stringToDate(time, beforFormatType);
        return dateToString(date, toFormatType);
    }

    // date類型轉換為String類型
    // formatType格式為yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH時mm分ss秒
    // data Date類型的時間
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * 幾天之後
     *
     * @param begin_time 開始日期 yyyy-MM-dd
     * @param days_after 幾天之後
     */
    public void getAfterDate(String begin_time, int days_after) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date_begin = null;
        try {
            date_begin = dateFormat.parse(begin_time);
            calendar.setTime(date_begin);
            //將日期設置為幾天之後    單位由第一個參數控制
            calendar.add(Calendar.DAY_OF_YEAR, days_after);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //獲取的新時間
        String day_new = dateFormat.format(calendar.getTime());

    }

    /**
     * 將日期格式的字符串轉換為長整型
     *
     * @param date
     * @param format
     * @return
     */
    public static long str2Long(String date, String format) {
        try {
            if (!TextUtils.isEmpty(date)) {
                if (TextUtils.isEmpty(format)) {
                    format = FORMAT;
                }
                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
                return formatter.parse(date).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
