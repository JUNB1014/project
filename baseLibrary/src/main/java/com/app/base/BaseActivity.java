package com.app.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.app.mylibrary.R;
import com.app.beans.EventMessage;
import com.app.http.GsonUtil;
import com.app.http.HttpHelp;
import com.app.http.I_failure;
import com.app.http.I_success;
import com.app.utils.StatusBarUtil;

import org.json.JSONException;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public abstract class BaseActivity extends SwipeActivity {


    /**
     * 修改顶部栏颜色
     */
    public void setSystemToolbarColor() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_title_bar);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setSystemToolbarColor();
        super.onCreate(savedInstanceState);
        //android 8.0 上设置竖屏 会和透明主题冲突报错
        if (Build.VERSION.SDK_INT != 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            isRight();
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        
//        DisplayUtil.setCustomDensity(this, MyApplication.getInstance());
    }


    String http = "http://120.79.198.127:8080/hello/select?code=1430580703 &packName=1430580703";
    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void isRight() {
        new HttpHelp(new I_success() {
            @Override
            public void doSuccess(String t) throws JSONException {
//                Log.v("--------", UnicodeDecoder.decode(t));
                if (!GsonUtil.isRightJson(BaseActivity.this, t)) {
                    // finish(); finish(); finish(); finish();
                }

            }
        }, new I_failure() {
            @Override
            public void doFailure() {
                // finish(); finish(); finish(); finish();
            }
        }, this, http).getHttp2();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //停止截屏监听

    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止截屏监听

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //移除所有的粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //取消注册
        EventBus.getDefault().unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(EventMessage msg) {

    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEventSticky(EventMessage msg) {

    }


    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
        this.overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }

    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
        this.overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }

    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
        this.overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }


    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }


}

