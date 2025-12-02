//package com.app.demo.utils;
//
//
//import android.content.Context;
//import android.util.Log;
//
//
//import com.alibaba.fastjson.JSON;
//import com.app.beans.UserBean;
//import com.app.demo.MyApplication;
//import com.app.demo.dbBean.PointBean;
//import com.app.utils.TimeUtil;
//import com.app.utils.ToastUtil;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 數據庫工具類：連接數據庫用、獲取數據庫數據用
// * 相關操作數據庫的方法均可寫在該類
// */
//public class DBUtils {
//
//    private static String driver = "com.mysql.jdbc.Driver";// MySql驅動
//
//    private static String user = "root";// 用戶名
//    private static String password = "";// 密碼
//    private static String sjk = "22042501";// 數據庫名稱
//    private static String ip = "192.168.0.107";//ip 地址
//
//    public final static String tag = "--------------";
//
//    //連接數據庫的函數
//    private static Connection getConn(String dbName) {
//
//        Connection connection = null;
//        try {
//            Class.forName(driver);// 動態加載類
//            // 嘗試建立到給定數據庫URL的連接，連接格式：驅動名稱+ip地址+端口號+數據庫名稱+用戶名+密碼
//            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName, user, password);
//            Log.e("-------------", "連接成功");
//        } catch (Exception e) {
////            ToastUtil.showToast(MyApplication.getInstance(), "數據庫鏈接失敗");
//            Log.e("-------------", "連接失敗");
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static List<UserBean> getUserList() {
//
//        List<UserBean> list = new ArrayList<>();
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//
//        try {
//            // mysql簡單的查詢語句。這里是根據MD_CHARGER表的NAME字段來查詢某條記錄
//            String sql = "select * from UserBean";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                if (ps != null) {
//                    // 設置上面的sql語句中的？的值為name
//                    // ps.setString(1, name);
//                    // 執行sql查詢語句並返回結果集
//                    ResultSet rs = ps.executeQuery();
//                    if (rs != null) {
//                        int count = rs.getMetaData().getColumnCount();
//                        Log.e("DBUtils", "-------count：" + count);
//                        while (rs.next()) {
//                            // 注意：下標是從1開始的
//                            Map<String, Object> map = new HashMap<>();
//                            for (int i = 1; i <= count; i++) {
//                                String field = rs.getMetaData().getColumnName(i);
//                                map.put(field, rs.getString(field));
//                            }
//                            UserBean userBean = JSON.parseObject(JSON.toJSONString(map), UserBean.class);
//                            list.add(userBean);
//                            Log.e("DBUtils", "-------user：" + userBean.toString());
//                        }
//                        connection.close();
//                        ps.close();
//                        return list;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return null;
//                }
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("DBUtils", "異常：" + e.getMessage());
//            return null;
//        }
//
//    }
//
//
//    public static void insertUser(Context context, String user_id, String name, String password) {
//
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String time = System.currentTimeMillis() + "";
//            String id = time.substring(time.length() - 5, time.length() - 1);
//            String sql = "INSERT INTO UserBean (id,user_id, name,password) VALUES ('" + id + "','" + user_id + "','" + name + "','" + password + "')";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//                if (ps != null) {
//                    ToastUtil.showToast(context, "添加成功");
//                    Log.e("-------------", "insert success");
//                } else {
//                    ToastUtil.showToast(context, "添加失敗");
//                    Log.e("-------------", "insert faile");
//                }
//            } else {
//                ToastUtil.showToast(context, "數據庫鏈接失敗");
//                Log.e("-------------", "連接失敗");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("DBUtils", "異常：" + e.getMessage());
//        } catch (ExceptionInInitializerError e) {
//            e.printStackTrace();
//            Log.e("DBUtils2", "異常：" + e.getMessage());
//        }
//    }
//
//
//    public static void updateUser(String id, String name, String pwd) {
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String sql = "UPDATE UserBean SET name = '" + name + "',password = '" + pwd + "' WHERE user_id = '" + id + "'  ";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(tag, "異常：" + e.getMessage());
//        }
//    }
//
//    public static void updatePhoto(String photo, String user_id) {
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String sql = "UPDATE UserBean SET photo = '" + photo + "' WHERE user_id = '" + user_id + "'  ";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//                ToastUtil.showToast(MyApplication.getInstance(), "修改成功");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(tag, "異常：" + e.getMessage());
//        }
//    }
//
//
//    public static void delUser(String id) {
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String sql = "delete from UserBean where user_id = '" + id + "'  ";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(tag, "異常：" + e.getMessage());
//        }
//    }
//
//
//
//    public static void updatePwd(String pwd, String user_id) {
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String sql = "UPDATE UserBean SET password = '" + pwd + "' WHERE user_id = '" + user_id + "'  ";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//                ToastUtil.showToast(MyApplication.getInstance(), "修改成功");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(tag, "異常：" + e.getMessage());
//        }
//    }
//
//
//    public static void updateName(String name, String user_id) {
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String sql = "UPDATE UserBean SET name = '" + name + "' WHERE user_id = '" + user_id + "'  ";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//                ToastUtil.showToast(MyApplication.getInstance(), "修改成功");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(tag, "異常：" + e.getMessage());
//        }
//    }
//
//
//    public static List<PointBean> getPointList() {
//
//        List<PointBean> list = new ArrayList<>();
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//
//        try {
//            // mysql簡單的查詢語句。這里是根據MD_CHARGER表的NAME字段來查詢某條記錄
//            String sql = "select * from PointBean";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                if (ps != null) {
//                    // 設置上面的sql語句中的？的值為name
//                    // ps.setString(1, name);
//                    // 執行sql查詢語句並返回結果集
//                    ResultSet rs = ps.executeQuery();
//                    if (rs != null) {
//                        int count = rs.getMetaData().getColumnCount();
//                        Log.e("DBUtils", "-------count：" + count);
//                        while (rs.next()) {
//                            // 注意：下標是從1開始的
//                            Map<String, Object> map = new HashMap<>();
//                            for (int i = 1; i <= count; i++) {
//                                String field = rs.getMetaData().getColumnName(i);
//                                map.put(field, rs.getString(field));
//                            }
//                            PointBean userBean = JSON.parseObject(JSON.toJSONString(map), PointBean.class);
//                            list.add(userBean);
//                            Log.e("DBUtils", "-------user：" + userBean.toString());
//                        }
//                        connection.close();
//                        ps.close();
//                        return list;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return null;
//                }
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("DBUtils", "異常：" + e.getMessage());
//            return null;
//        }
//
//    }
//
//
//    public static void insertPoint(Context context, String name, double lat, double lon) {
//
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String time = TimeUtil.getTodayData("yyyy-MM-dd HH:mm:ss");
//            String user_id = UserManager.getUserId(context);
//            String user_name = UserManager.getUserName(context);
//            String t = System.currentTimeMillis() + "";
//            String id = t.substring(t.length() - 5, t.length() - 1);
//            String sql = "INSERT INTO PointBean (id,name,lat,lon) VALUES ('" + id + "','" + name + "','" + lat + "','" + lon + "')";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//                if (ps != null) {
//                    ToastUtil.showToast(context, "添加成功");
//                    Log.e("-------------", "insert success");
//                } else {
//                    ToastUtil.showToast(context, "添加失敗");
//                    Log.e("-------------", "insert faile");
//                }
//            } else {
//                ToastUtil.showToast(context, "數據庫鏈接失敗");
//                Log.e("-------------", "連接失敗");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("DBUtils", "異常：" + e.getMessage());
//        } catch (ExceptionInInitializerError e) {
//            e.printStackTrace();
//            Log.e("DBUtils2", "異常：" + e.getMessage());
//        }
//    }
//
//    public static void delPoint(String id) {
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String sql = "delete from PointBean where id = '" + id + "'  ";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(tag, "異常：" + e.getMessage());
//        }
//    }
//
//
//    public static void updatePoint(String id, double lat, double lon) {
//        // 根據數據庫名稱，建立連接
//        Connection connection = getConn(sjk);
//        try {
//            String sql = "UPDATE PointBean SET lat = '" + lat + "',lon = '" + lon + "' WHERE id = '" + id + "'  ";
//            if (connection != null) {// connection不為null表示與數據庫建立了連接
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.executeUpdate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(tag, "異常：" + e.getMessage());
//        }
//    }
//
//}
//
//
