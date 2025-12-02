package com.app.utils;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
   
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equalsIgnoreCase(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    /**
     * 逗號拼接的字符串轉集合
     *
     * @param str
     * @return
     */
    public static List strToList(String str) {
        List<String> list = new ArrayList<>();
        if (!StringUtil.isEmpty(str)) {
            String[] strings = str.split(",");
            list = Arrays.asList(strings);
        }

        return list;
    }

    /**
     * String集合轉字符串
     *
     * @param stringList 集合
     * @param symbol     拼接符號
     */

    public static String ListToStr(List<String> stringList, String symbol) {

        String str = " ";
        StringBuffer sb = new StringBuffer();
        if (stringList != null && stringList.size() > 0) {
            for (int i = 0; i < stringList.size(); i++) {
                sb.append(stringList.get(i) + symbol);
            }
        }
        if (sb != null && sb.length() > 0) {
            str = sb.substring(0, sb.length() - 1);
        }
        return str;
    }


    /**
     * 判斷是否為漢字
     *
     * @param string
     * @return
     */

    public static boolean isChinese(String string) {
        int n = 0;
        for (int i = 0; i < string.length(); i++) {
            n = (int) string.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 1.判斷字符串是否僅為數字:
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        if (StringUtil.isEmpty(str)) {

            return false;

        } else {
            for (int i = str.length(); --i >= 0; ) {

                if (!Character.isDigit(str.charAt(i))) {

                    return false;
                }
            }
            return true;
        }
    }


    /**
     * 格式化數據返回 小數點後兩位表示
     */
    public static String toTwoDianString(Double doubleValue) {
        if (doubleValue == null) {
            return "0";
        }
        DecimalFormat format = new DecimalFormat("#0.00");
        String result = format.format(doubleValue);
//        if (result.substring(result.length() - 2, result.length()).equals("00")) {
//            result = result.substring(0, result.length() - 3);
//        }
        return result;
    }

    /**
     * 驗證手機格式
     */
    public static boolean isMobileNO(String mobiles) {
        return !TextUtils.isEmpty(mobiles) && mobiles.startsWith("1") && mobiles.length() == 11;
//        String telRegex = "(((13[0-9]{1})|(15[0123456789]{1})|145|147|(17[0123456789]{1})|(18[0123456789]{1}))+\\d{8})";
//        if (TextUtils.isEmpty(mobiles))
//            return false;
//        else
//            return mobiles.matches(telRegex);
    }

    /**
     * 驗證碼
     */
    public static boolean isCode(String code) {
        return !TextUtils.isEmpty(code) && code.length() == 6;
    }


    /**
     * 驗證郵箱格式
     */
    public static boolean isEmail(String strEmail) {
//        String strPattern = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
//        String strPattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//        Pattern p = Pattern.compile(strPattern);
//        Matcher m = p.matcher(strEmail);
//        boolean b = m.matches();
//        return b;

        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正則表達式的模式 編譯正則表達式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正則表達式的匹配器
        Matcher m = p.matcher(strEmail);
        //進行正則匹配\
        return m.matches();

    }

    /**
     * 設置字符串
     *
     * @param str
     * @return
     */
    public static String getContent(String str) {

        if (isEmpty(str)) {
            return "--";
        } else {
            return str;
        }
    }

    /**
     * 設置字符串
     *
     * @param str
     * @return
     */
    public static String getContentEmpty(String str) {

        if (isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static boolean isPassword(String password) {
        String strPattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(password);
        boolean b = m.matches();
        return b;
    }

    public static void setPasswordEditInputType(EditText editText, int length) {
        String type = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        editText.setKeyListener(DigitsKeyListener.getInstance(type));
        InputFilter[] filters = {new InputFilter.LengthFilter(length)};
        editText.setFilters(filters);

    }

    public static void setNumEditInputType(EditText editText, int length) {
        String type = "0123456789";
        editText.setKeyListener(DigitsKeyListener.getInstance(type));
        InputFilter[] filters = {new InputFilter.LengthFilter(length)};
        editText.setFilters(filters);
    }

    public String addblankinmiddle(String str) {
        //字符串長度
        int strlenth = str.length();
        //需要加空格數量
        int blankcount = 0;
        //判斷字符串長度
        if (strlenth <= 4) {
            blankcount = 0;
        } else {
            blankcount = strlenth % 4 > 0 ? strlenth / 4 : str.length() / 4 - 1; //需要加空格數量
        }
        //插入空格
        if (blankcount > 0) {
            for (int i = 0; i < blankcount; i++) {
                str = str.substring(0, (i + 1) * 4 + i) + " " + str.substring((i + 1) * 4 + i, strlenth + i);
            }
        } else {

        }
        //返回
        return str;
    }

    /**
     * 按長度格式化中文字符
     *
     * @param input
     * @param num
     * @return
     */
    public static String getChineseStringByWeiShu(String input, int num) {
        if (input == null || input.length() == 0) {
            return "";
            // return
            // MyApplication.instance.getResources().getString(R.string.default_value);
        }
        char[] ch = input.toCharArray();
        StringBuffer output = new StringBuffer();
        double valueLength = 0;
        int endCount = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                // 中文字符長度為1
                valueLength += 1;
            } else {
                valueLength += 0.5;
            }
            output.append(c);
            if (valueLength >= num) {
                endCount = i;
                break;
            }
        }
        if (valueLength < num) {
            return output.toString();
        } else if (endCount == ch.length - 1) {
            return output.toString();
        } else {
            return output.toString() + "...";
        }
    }

    /**
     * 計算字符長度
     *
     * @param input
     * @return
     */
    public static int getChineseLength(String input) {
        if (input == null || input.length() == 0) {
            return 0;
        }
        char[] ch = input.toCharArray();
        // StringBuffer output = new StringBuffer();
        int valueLength = 0;
        // int endCount = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                // 中文字符長度為1
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 根據Unicode編碼完美的判斷中文漢字和符號
     *
     * @param c 需要判斷的字符
     * @return 是否是中文字符
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 驗證密碼
     */
    public static boolean isPwd(String pwd) {
        if (isEmpty(pwd)) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < pwd.length(); i++) {
            // 內容只能是數字字符
            if (!Character.isDigit(pwd.charAt(i))
                    && !Character.isLetter(pwd.charAt(i))) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * Converts an InputStream to String
     *
     * @param
     * @return
     * @throws IOException
     */
    public static String Inputstr2Str_Reader(InputStream in) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(in);
        char buf[] = new char[20];
        int nBufLen = 0;
        try {
            nBufLen = isr.read(buf);
            while (nBufLen != -1) {
                sb.append(new String(buf, 0, nBufLen));
                nBufLen = isr.read(buf);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Integer轉字符串
     *
     * @param value
     * @return
     */
    public static Integer stringTransInt(String value) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }

    /**
     * 字符串為空的時候，返回"— —"
     *
     * @param content
     * @return
     */
    public static String showStringContent(String content) {
        if (isEmpty(content)) {
            return "—";
            // return
            // MyApplication.instance.getResources().getString(R.string.default_value);
        }
        return content;
    }

    public static String getModileNumber(String number) {
        if (StringUtil.isEmpty(number)) {
            return "";
        }
        String newNumber = "";
        if (number.length() > 7) {
            newNumber = number.substring(0, 3) + "****"
                    + number.substring(7, number.length());
        }
        return newNumber;
    }

    /**
     * 校驗銀行卡卡號
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 從不含校驗位的銀行卡卡號采用 Luhm 校驗算法獲得校驗位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果傳的不是數據返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static String onToTwo(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return num + "";
    }

    /**
     * 傳入20161209235959
     * 輸出2016年12月09日23:59:59
     *
     * @param time
     * @return
     */
    public static String startModleTime(String time) {
        String mTime = null;

        String year = time.substring(0, 4);
        String Month = time.substring(4, 6);
        String day = time.substring(6, 8);
        String Time1 = time.substring(8, 10);
        String Time2 = time.substring(10, 12);
        String Time3 = time.substring(12, 14);
        mTime = "自" + year + "年" + Month + "月" + day + "日" + Time1 + ":" + Time2 + ":" + Time3 + "起";

        return mTime;
    }

    /**
     * 傳入2016-12-09 23:59:59
     * 輸出2016年12月09日
     *
     * @param time
     * @return
     */
    public static String getSampleTime(String time) {

        if (time.length() < 10) {
            return time;
        }

        String mTime = null;

        String year = time.substring(0, 4);
        String Month = time.substring(5, 7);
        String day = time.substring(8, 10);
        mTime = year + "年" + Month + "月";

        return mTime;
    }

    /**
     * 傳入2016-12-09 23:59:59
     * 輸出2016/12/09
     *
     * @param time
     * @return
     */
    public static String getSampleTime2(String time) {

        if (time.length() < 10) {
            return time;
        }

        String mTime = null;

        String year = time.substring(0, 4);
        String Month = time.substring(5, 7);
        String day = time.substring(8, 10);
        mTime = year + "/" + Month + "/" + day;

        return mTime;
    }

    /**
     * 傳入2016年12月09日
     * 輸出2016.12.09
     *
     * @param time
     * @return
     */
    public static String getSelectTimeStyle(String time) {

        if (time.length() < 10) {
            return time;
        }

        String mTime = null;

        String year = time.substring(0, 4);
        String Month = time.substring(5, 7);
        String day = time.substring(8, 10);
        mTime = year + "." + Month + "." + day;

        return mTime;
    }

    /**
     * 傳入20161209235959
     * 輸出 至2016年12月09日23:59:59止
     *
     * @param time
     * @return
     */
    public static String ModleTime(String time) {
        String mTime = null;

        String year = time.substring(0, 4);
        String Month = time.substring(4, 6);
        String day = time.substring(6, 8);
        String Time1 = time.substring(8, 10);
        String Time2 = time.substring(10, 12);
        String Time3 = time.substring(12, 14);
        mTime = "至" + year + "年" + Month + "月" + day + "日 " + Time1 + ":" + Time2 + ":" + Time3 + "止";

        return mTime;
    }


    /**
     * 傳入 20161209235959
     * 輸出 2016年12月09日 23:59:59
     *
     * @param time
     * @return
     */
    public static String ModleTime_Z(String time) {
        String mTime = null;

        String year = time.substring(0, 4);
        String Month = time.substring(4, 6);
        String day = time.substring(6, 8);
        String Time1 = time.substring(8, 10);
        String Time2 = time.substring(10, 12);
        String Time3 = time.substring(12, 14);
        mTime = year + "年" + Month + "月" + day + "日 " + Time1 + ":" + Time2 + ":" + Time3;

        return mTime;
    }

    /**
     * 傳入20161209235959
     * 輸出 2016年12月09日
     *
     * @param time
     * @return
     */
    public static String getModleTime(String time) {
        if (StringUtil.isEmpty(time) || time.length() < 12) {
            return "";
        }
        String mTime = null;
        String year = time.substring(0, 4);
        String Month = time.substring(4, 6);
        String day = time.substring(6, 8);
        String Time1 = time.substring(8, 10);
        String Time2 = time.substring(10, 12);
        String Time3 = time.substring(12, 14);
        mTime = year + "年" + Month + "月" + day + "日";

        return mTime;
    }

    /**
     * 傳入20161209
     * 輸出 2016-12-09
     *
     * @param time
     * @return
     */
    public static String getModleTimeToNew1(String time) {
        if (StringUtil.isEmpty(time) || time.length() < 8) {
            return "";
        }
        String mTime = null;
        String year = time.substring(0, 4);
        String Month = time.substring(4, 6);
        String day = time.substring(6, 8);

        mTime = year + "-" + Month + "-" + day;

        return mTime;
    }


    /**
     * 實現將初始日期時間2012年07月02日 16:45 拆分成20120702164545
     */
    public static String getCalendarByInintData(String initDateTime) {
        String ToTime;
        // 將初始日期時間2012年07月02日 拆分成年 月 日
        String date = spliteString(initDateTime, "日", "index", "front"); // 日期
        String yearStr = spliteString(date, "年", "index", "front"); // 年份
        String monthAndDay = spliteString(date, "年", "index", "back"); // 月日
        String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
        String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日
        ToTime = yearStr + "" + monthStr + "" + dayStr + "000000";
        return ToTime;
    }

    /**
     * 實現將初始日期時間2012年07月02日拆分成20120702
     */
    public static String getCalendarByInintDatas(String initDateTime) {
        String ToTime;
        // 將初始日期時間2012年07月02日 拆分成年 月 日
        String date = spliteString(initDateTime, "日", "index", "front"); // 日期
        String yearStr = spliteString(date, "年", "index", "front"); // 年份
        String monthAndDay = spliteString(date, "年", "index", "back"); // 月日
        String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
        String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日
        ToTime = yearStr + "" + monthStr + "" + dayStr;
        return ToTime;
    }


    /**
     * 截取子串
     *
     * @param srcStr      源串
     * @param pattern     匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    public static String spliteString(String srcStr, String pattern, String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出現的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最後一個匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }

    /**
     * 半角轉換為全角
     *
     * @param input 包含半角字符的字符串
     * @return 全角字符
     */
    public static String DBCToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 獲取中文的個數
     *
     * @param input
     * @return
     */
    public static int getChineseLengths(String input) {
        int l = input.length();
        int blen = 0;
        for (int i = 0; i < l; i++) {
            if ((input.charAt(i) & 0xff00) != 0) {
                blen++;
            }
        }
        return blen;
    }

    /**
     * 隱藏手機號碼
     *
     * @param input
     * @return
     */
    public static String phoneNoHide(String input) {
        if (input != null) {
            int l = input.length();
            if (l == 11) {
                StringBuilder stringBuilder = new StringBuilder(input);
                return stringBuilder.replace(3, 7, "****").toString();
            }
        }
        return "";
    }

    /**
     * 隱藏身份證號碼
     *
     * @param input 身份證號
     * @return 隱藏後的串
     */
    public static String IDCardNoHide(String input) {

        return IDCardNoHide(input, "****************");
    }

    /**
     * 隱藏身份證號碼
     *
     * @param input   身份證號
     * @param replace 替換字符
     * @return 隱藏後的串
     */
    public static String IDCardNoHide(String input, String replace) {
        if (input != null) {
            int l = input.length();
            if (l == 18) {
                StringBuilder stringBuilder = new StringBuilder(input);
                return stringBuilder.replace(1, 17, replace).toString();
            } else if (l == 15) {
                StringBuilder stringBuilder = new StringBuilder(input);
                return stringBuilder.replace(1, 14, replace).toString();
            }
        }
        return "";
    }

    /**
     * 判斷是否為url
     */
    public static boolean isURL(String input) {
        return input.matches("[a-zA-z]+://[^\\s]*");

    }


    /**
     * 獲取現在時間
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        Date currentTime = null;
        try {
            currentTime = formatter.parse(str);
            String dateString = format1.format(currentTime);
            return dateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "";
    }

    public static long getStringTime(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        try {
            return formatter.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 隨機指定範圍內N個不重覆的數
     * 最簡單最基本的方法
     *
     * @param min 指定範圍最小值
     * @param max 指定範圍最大值
     * @param n   隨機數個數
     */
    public static int[] randomCommon(int min, int max, int n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        //初始化給定範圍的待選數組
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待選數組0到(len-2)隨機一個下標
            index = Math.abs(rd.nextInt() % len--);
            //將隨機到的數放入結果集
            result[i] = source[index];
            //將待選數組中被隨機到的數，用待選數組(len-1)下標對應的數替換
            source[index] = source[len];
        }
        return result;
    }

    /**
     * 加減控件 獲取價格|下單時，摘除“份”字
     *
     * @param oldNumber
     * @return
     */
    public static String getAddSubtractNum(String oldNumber) {
        String str = "";
        if (oldNumber.contains("份")) {
            str = oldNumber.substring(0, oldNumber.indexOf("份"));
        } else {
            str = oldNumber;
        }
        return str;
    }

    /**
     * 去除小數點 XX.0
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最後一位是.則去掉
        }
        return s;
    }

    /**
     * 價格工具類 分轉換成元
     *
     * @param price
     * @return
     */
    public static String getPriceStr(String price) {
        float value = Float.parseFloat(price) / 100;
        return subZeroAndDot(value + "");
    }

    /**
     * 看好添加產品頁，已選產品的name
     *
     * @param oldString
     * @return
     */
    public static String getNameString(String oldString) {
        if (TextUtils.isEmpty(oldString)) {
            return "";
        }
        if (oldString.length() > 8) {
            return oldString.substring(0, 7) + "...";
        }
        return oldString;
    }

    /**
     * @param priceUnit
     * @return
     */
    public static void priceUnit(String priceUnit, TextView textView) {
        if (priceUnit.contains("起")) {
            int index = priceUnit.indexOf("起");
            SpannableStringBuilder ssb = new SpannableStringBuilder(priceUnit);
            ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, index, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            textView.setText(ssb);
        } else {
            textView.setText(priceUnit);
        }
    }

    @SuppressLint("ResourceAsColor")
    public static void priceUnit2(String priceUnit, TextView textView, @Nullable int color, int styleSpan) {
        if (priceUnit.contains("起")) {
            int index = priceUnit.indexOf("起");
            SpannableStringBuilder ssb = new SpannableStringBuilder(priceUnit);
            ssb.setSpan(new ForegroundColorSpan(color), index, priceUnit.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(new StyleSpan(styleSpan), index, priceUnit.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, index, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            textView.setText(ssb);
        } else {
            textView.setText(priceUnit);
        }
    }

    @SuppressLint("ResourceAsColor")
    public static void priceUnit3(String priceUnit, TextView textView, @Nullable int color, int styleSpan) {
        if (priceUnit.contains("起")) {
            int index = priceUnit.indexOf("起");
            SpannableStringBuilder ssb = new SpannableStringBuilder(priceUnit);
            ssb.setSpan(new ForegroundColorSpan(color), index, priceUnit.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(new StyleSpan(styleSpan), index, priceUnit.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, index, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(new RelativeSizeSpan(0.8f), index, priceUnit.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            textView.setText(ssb);
        } else {
            textView.setText(priceUnit);
        }
    }

    @SuppressLint("ResourceAsColor")
    public static void priceUnit4(String msg, TextView textView, int priceLength, @Nullable int leftColor, @Nullable int rightColor) {


        SpannableString spannaString = new SpannableString(msg);
        spannaString.setSpan(new ForegroundColorSpan(leftColor), 0, msg.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (msg.contains("起")) {
            spannaString.setSpan(new ForegroundColorSpan(rightColor), msg.length() - 1, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannaString.setSpan(new AbsoluteSizeSpan(24, true), 0, priceLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannaString.setSpan(new AbsoluteSizeSpan(11, true), priceLength, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannaString);
    }

    @SuppressLint("ResourceAsColor")
    public static void priceUnit5(String msg, String key, TextView textView, int priceLength, @Nullable int leftColor, @Nullable int rightColor, int leftsize, int rightsize) {


        SpannableString spannaString = new SpannableString(msg);
        spannaString.setSpan(new ForegroundColorSpan(leftColor), 0, msg.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (msg.contains(key)) {
            spannaString.setSpan(new ForegroundColorSpan(rightColor), msg.indexOf(key), msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannaString.setSpan(new AbsoluteSizeSpan(leftsize, true), 0, priceLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannaString.setSpan(new AbsoluteSizeSpan(rightsize, true), priceLength, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannaString);
    }

    @SuppressLint("ResourceAsColor")
    public static void priceUnit6(String msg, String key, TextView textView, int leftsize, int rightsize) {
        SpannableString spannaString = new SpannableString(msg);
        spannaString.setSpan(new AbsoluteSizeSpan(rightsize), 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (msg.contains(key)) {
            spannaString.setSpan(new AbsoluteSizeSpan(leftsize), 0, msg.indexOf(key), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannaString.setSpan(new StyleSpan(Typeface.BOLD), 0, msg.indexOf(key), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        textView.setText(spannaString);
    }

    @SuppressLint("ResourceAsColor")
    public static void setString(String msg, String key, TextView textView) {
        if (msg.contains(key) && msg.indexOf(key) >= 0) {
            SpannableString spannaString = new SpannableString(msg);
            spannaString.setSpan(new StyleSpan(Typeface.BOLD), 0, msg.indexOf(key), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannaString);
        }
    }


    //讀取數據
    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    @SuppressLint("ResourceAsColor")
    public static void setString2(String str1, String str2, String str3, int color1, int color2, TextView textView) {
        SpannableString spannaString = new SpannableString(str1 + str2 + str3);
        spannaString.setSpan(new ForegroundColorSpan(color1), str1.length(), (str1 + str2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannaString);
    }
}
