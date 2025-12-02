package com.app.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.base.BaseActivity;
import com.app.base.BaseFragment;
import com.app.demo.fragments.MainFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {






    Fragment mFragment; //顯示當前 的Fragment
    BaseFragment fragment2; //宣告fragment2


    @Override
    protected void onCreate(Bundle savedInstanceState) {  //初始化 Activity，設置內容視圖，綁定視圖，禁用滑動，並初始化數據
        super.onCreate(savedInstanceState);    //調用onCreate
        setContentView(R.layout.activity_main);    //設置為MainActivity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 888);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 666);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 222);
        }
        ButterKnife.bind(this);    //用ButterKnife庫綁定視圖，給@blindview標註的視圖賦值
        setSwipeEnabled(false); //禁用滑動
        initData();    //調用initFragment 初始化Fragment
    }

    @Override
    public void onBackPressed() {   //處理使用者的返回按鈕
        finish();     //結束當前Activity
    }

    private void initData() {
        initFragment();
    }    // 初始化Fragment

    /**
     * 初始化 Fragments 並設定初始顯示。
     * 創建一個新的 MapFragment，並進行初始顯示的設定。
     */
    private void initFragment() {        //初始化 mFragment 和 fragment2，並設定初始Fragment顯示
        mFragment = new Fragment();      //創新的 mFragment
        if (fragment2 == null) {           //若fragment2為空,則初始化
            fragment2 = new MainFragment();         //創新的mapfragment賦值給fragment2
        }



        switchContent(mFragment, fragment2);    //切換顯示內容給這兩個Fragment
    }


    /** 切換顯示的 Fragment
     * 從 'from' Fragment 切換到 'to' Fragment。
     * @param from 當前顯示的 Fragment。
     * @param to 將要顯示的 Fragment。
     * */
    public void switchContent(Fragment from, Fragment to) {  //定義方法切換兩個Fragment 當前from和要切換的to
        if (mFragment != to) {        //檢查當前mFragment是否與即將顯示的to相同,不同則切換
            mFragment = to;      //切換後賦值 更新Fragment to
            FragmentManager fragmentManager = getSupportFragmentManager();     //管理Fragment
            FragmentTransaction transaction = fragmentManager.beginTransaction(); //fragment的 添加 移除 替換
            if (!to.isAdded()) {    // 先判斷是否被add過
                transaction.hide(from).add(R.id.fragment_container, to).commitAllowingStateLoss();
            } else {               //隱藏當前的fragment from，add下一個到Activity中
                transaction.hide(from).show(to).commitAllowingStateLoss(); //若to存在activity,隱藏fragment from，顯示to
            }
        }
    }





}
