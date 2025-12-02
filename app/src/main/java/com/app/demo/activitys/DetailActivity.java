package com.app.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.MyWebActivity;
import com.app.base.BaseActivity;
import com.app.demo.MyApplication;
import com.app.demo.R;
import com.app.demo.shibie.ShibieActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class    DetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.imv_pic)
    ImageView imv_pic;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;

    @BindView(R.id.tv_url)
    TextView tv_url;

    ImageView imgvReturn;

    int posi;

    public void updateLanguage(){
        switch(SwitchActivity.CurrentLanguage){
            case SwitchActivity.Chinese:
                tv_title.setText(" 圖片辨識詳細資訊 ");
                tv_url.setText("詳細資訊");
                break;
            case SwitchActivity.English:
                tv_title.setText("Image Recognition Details");
                tv_url.setText("Detailed Consultation");
                break;
            case SwitchActivity.Japan:
                tv_title.setText("画像認識の詳細情報");
                tv_url.setText("詳細な相談");
                break;
            case SwitchActivity.Korean:
                tv_title.setText("이미지 인식 상세 정보");
                tv_url.setText("상세한 상담");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        updateLanguage();
        tv_title_right.setVisibility(View.GONE);

        posi = (int) getIntent().getIntExtra("posi",0);

        imgvReturn = (ImageView)findViewById(R.id.imgv_return);
        Glide.with(DetailActivity.this).load(MyApplication.picUrls[posi]).into(imv_pic);

        switch(SwitchActivity.CurrentLanguage){
            case SwitchActivity.Chinese:
                tv_name.setText(MyApplication.names[posi]);
                tv_content.setText(MyApplication.conts[posi]);
                break;
            case SwitchActivity.English:
                tv_name.setText(MyApplication.namesEn[posi]);
                tv_content.setText(MyApplication.contsEn[posi]);
                break;
            case SwitchActivity.Japan:
                tv_name.setText(MyApplication.namesJa[posi]);
                tv_content.setText(MyApplication.contsJa[posi]);
                break;
            case SwitchActivity.Korean:
                tv_name.setText(MyApplication.namesKo[posi]);
                tv_content.setText(MyApplication.contsKo[posi]);
                break;
        }


//        tv_addr.setText(MyApplication.address[posi]);
        tv_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWebActivity.start(DetailActivity.this,MyApplication.url[posi],MyApplication.names[posi]);
            }
        });

        imgvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


//    @OnClick({R.id.imgv_return,R.id.tv_addr ,R.id.tv_url})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.imgv_return:
//                onBackPressed();
//                break;
////            case R.id.tv_addr:
////                Intent intent = new Intent(DetailActivity.this, MapActivity.class);
////                intent.putExtra("posi", posi);
////                startActivity(intent);
////                break;
//            case R.id.tv_url:
//                MyWebActivity.start(DetailActivity.this,MyApplication.url[posi],MyApplication.names[posi]);
//
//                break;
//        }
//    }


}
