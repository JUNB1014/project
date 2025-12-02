//package com.app.demo.utils;
//
//import android.content.Context;
//
//import com.app.utils.SharedPreferencesUtil;
//import com.app.utils.StringUtil;
//import com.orhanobut.logger.Logger;
//
//
//
//public class UserManager {
//
//
//    /**
//     * 判斷用戶是否登錄
//     *
//     * @return
//     */
//    public static boolean isLogin(Context context) {
//        return !StringUtil.isEmpty(SharedPreferencesUtil.getData(context, "user", "user_id", ""));
//    }
//
//    public static String getUserId(Context context) {
//        String id = SharedPreferencesUtil.getData(context, "user", "user_id", "");
//        Logger.e("-------id" + id);
//        return id;
//
//    }
//
//    public static String getUserName(Context context) {
//        return SharedPreferencesUtil.getData(context, "user", "name", "");
//
//    }
//
//    public static int getUserType(Context context) {
//        return SharedPreferencesUtil.getData(context, "user", "type", 0);
//
//    }
//    public static String getUserPhoto(Context context) {
//        return SharedPreferencesUtil.getData(context, "user", "photo", "");
//    }
//
//
//}
