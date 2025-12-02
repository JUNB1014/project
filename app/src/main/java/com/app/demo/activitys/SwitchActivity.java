package com.app.demo.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.demo.R;

import butterknife.ButterKnife;

// 切换语言
public class SwitchActivity extends Activity {

    public final static String Chinese = "Chinese";
    public final static String English = "English";
    public final static String Japan   = "Japan";
    public final static String Korean  = "Korean";

    // 當前語言
    public static String CurrentLanguage = Chinese;



    public Button BtnChinese;
    public Button BtnEnglish;
    public Button BtnJapan;
    public Button BtnKorean;
    public TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        initView();
        initOnClickListener();
        updateLanguage();
    }

    public void updateLanguage(){
        switch(SwitchActivity.CurrentLanguage){
            case SwitchActivity.Chinese:
                tvTitle.setText("切換語言");
                BtnChinese.setText("中文");
                BtnEnglish.setText("英文");
                BtnJapan.setText("日文");
                BtnKorean.setText("韓文");
                break;
            case SwitchActivity.English:
                tvTitle.setText("Switch Language");
                BtnChinese.setText("Chinese");
                BtnEnglish.setText("English");
                BtnJapan.setText("Japanese");
                BtnKorean.setText("Korean");
                break;
            case SwitchActivity.Japan:
                tvTitle.setText("言語を切り替える");
                BtnChinese.setText("中国語");
                BtnEnglish.setText("英語");
                BtnJapan.setText("日本語");
                BtnKorean.setText("韓国語");
                break;
            case SwitchActivity.Korean:
                tvTitle.setText("언어 전환");
                BtnChinese.setText("중국어");
                BtnEnglish.setText("영어");
                BtnJapan.setText("일본어");
                BtnKorean.setText("한국어");
                break;
        }

    }

    private void initOnClickListener() {
        BtnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentLanguage = Chinese;
                updateLanguage();
            }
        });

        BtnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentLanguage = English;
                updateLanguage();
            }
        });

        BtnJapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentLanguage = Japan;
                updateLanguage();
            }
        });

        BtnKorean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentLanguage = Korean;
                updateLanguage();
            }
        });
    }

    private void initView() {
        BtnChinese = findViewById(R.id.btn_chinese);
        BtnEnglish = findViewById(R.id.btn_english);
        BtnJapan   = findViewById(R.id.btn_japanese);
        BtnKorean  = findViewById(R.id.btn_korean);
        tvTitle    = findViewById(R.id.tv_title);
    }
}
