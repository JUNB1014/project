package com.app.demo.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseFragment;
import com.app.demo.R;
import com.app.demo.activitys.SwitchActivity;
import com.app.demo.shibie.ShibieActivity;
import com.app.utils.GlideUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    TextView tvDaohang;
    TextView tvSwitch;
    TextView tvTitle;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        initBanner();
        tvDaohang = view.findViewById(R.id.tv_daohang);
        tvSwitch  = view.findViewById(R.id.tv_switch);
        tvTitle   = view.findViewById(R.id.tv_map_title);
        tvSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SwitchActivity.class));

            }
        });
        switch(SwitchActivity.CurrentLanguage){
            case SwitchActivity.Chinese:
                tvDaohang.setText("景點圖片辨識");
                tvSwitch.setText("切換語言");
                tvTitle.setText("首頁");
                break;
            case SwitchActivity.English:
                tvDaohang.setText("Scenic Spot Image Recognition");
                tvSwitch.setText("Switch Language");
                tvTitle.setText("Home");
                break;
            case SwitchActivity.Japan:
                tvDaohang.setText("景勝地画像認識");
                tvSwitch.setText("言語を切り替える");
                tvTitle.setText("ホーム");
                break;
            case SwitchActivity.Korean:
                tvDaohang.setText("관광지 이미지 인식");
                tvSwitch.setText("언어 전환");
                tvTitle.setText("홈");
                break;
        }
        return view;
    }


    @OnClick({ R.id.tv_daohang})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_daohang:
                startActivity(new Intent(getActivity(), ShibieActivity.class));
                break;

//            case R.id.tv_switch:
//                startActivity(new Intent(getActivity(), SwitchActivity.class));
//                break;
        }
    }

    /**
     * 輪播圖
     */
    private void initBanner() {
        //設置banner樣式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //設置圖片加載器
        banner.setImageLoader(new FresscoImageLoader());
        //設置banner動畫效果
//        banner.setBannerAnimation(Transformer.Default);
        //設置自動輪播，默認為true
        banner.isAutoPlay(true);
        //設置輪播時間
        banner.setDelayTime(3000);
        //設置允許手勢滑動
        banner.setViewPagerIsScroll(true);
        //設置指示器位置（當banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        List list_banner = new ArrayList();

        list_banner.add("https://i.imgur.com/ExTdcp9.jpeg");
        list_banner.add("https://i.imgur.com/U04e7eL.jpeg");

        banner.setImages(list_banner);

        //banner設置方法全部調用完畢時最後調用
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        switch(SwitchActivity.CurrentLanguage){
            case SwitchActivity.Chinese:
                tvDaohang.setText("景點圖片辨識");
                tvSwitch.setText("切換語言");
                tvTitle.setText("首頁");
                break;
            case SwitchActivity.English:
                tvDaohang.setText("Scenic Spot Image Recognition");
                tvSwitch.setText("Switch Language");
                tvTitle.setText("Home");
                break;
            case SwitchActivity.Japan:
                tvDaohang.setText("景勝地画像認識");
                tvSwitch.setText("言語を切り替える");
                tvTitle.setText("ホーム");
                break;
            case SwitchActivity.Korean:
                tvDaohang.setText("관광지 이미지 인식");
                tvSwitch.setText("언어 전환");
                tvTitle.setText("홈");
                break;
        }
    }

    public class FresscoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            String url = (String) path;
            GlideUtils.getInstance().loadImage(getActivity(), url, imageView);
        }

        //提供createImageView 方法，方便fresco自定義ImageView
        @Override
        public ImageView createImageView(Context context) {

            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) getLayoutInflater().inflate(R.layout.layout_banner_imageview, null);
//            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }
}
