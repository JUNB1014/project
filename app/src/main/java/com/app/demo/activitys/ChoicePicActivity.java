package com.app.demo.activitys;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.app.base.CommonAdapter;
import com.app.base.ViewHolder;
import com.app.beans.EventMessage;
import com.app.demo.MyApplication;
import com.app.demo.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ChoicePicActivity extends BaseActivity {
// 主界面的標題視圖，顯示 "選擇圖片"
    @BindView(R.id.tv_title) //定義視圖
    TextView tvTitle; // 引用文字介面，"顯示標題 選擇圖片"
    @BindView(R.id.gridview)//定義視圖
    GridView gridview; // 引用介面格子，顯示圖片列表

    GridAdapter adapter;//變量adapter 用於處理GridVew的數據
    List<String> list = new ArrayList<>(); // 初始化一個字串List 用於儲存圖片URL

    public void updateLanguage(){
        switch(SwitchActivity.CurrentLanguage){
            case SwitchActivity.Chinese:
                tvTitle.setText(" 選擇圖片 ");
                break;
            case SwitchActivity.English:
                tvTitle.setText("Select Image");
                break;
            case SwitchActivity.Japan:
                tvTitle.setText("画像を選択");
                break;
            case SwitchActivity.Korean:
                tvTitle.setText("이미지 선택");
                break;
        }
    }

    @Override //Activity初始化 使用initData加載數據
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_pic); //設定此活動為ChoicePicActivity
        ButterKnife.bind(this); //用ButterKnife庫 處理通過 @BindView綁定的視圖
        updateLanguage();
        // tvTitle.setText(" 選擇圖片 ");  //設定tvTitle的文字為 選擇圖片
        initData(); // 調用 initData 來初始化數據 設置GridView的數據
    }

    private void initData() {//宣告initData 初始化數據
        list.clear();
        for(String pic: MyApplication.picUrls){ //  從MyApplication.picUrls 中獲取每一個圖片的 URL
            list.add(pic); // 將每個圖片URL加到List中

        }

        adapter = new GridAdapter(this, (ArrayList) list, R.layout.item_gridview);
        gridview.setAdapter(adapter);
        //連接this ArrayList和item_gridview
        //讓adapter適配Gridview->顯示數據

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //讓Gridview有一個點擊按鈕
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new EventMessage(EventMessage.CHOICE_PIC, list.get(position)));
                finish(); // view view是被點擊的視圖 int position是點擊項目的位置 id是行ID
            }
        });

    }

    @OnClick(R.id.imgv_return)// 當此視圖被點擊時，執行以下方法
    public void onViewClicked() {
        onBackPressed();
    }//調用onBackPressed 處理返回操作

    class GridAdapter extends CommonAdapter { //處理集合視圖的數據綁定和視圖生成

        public GridAdapter(Context context, ArrayList datas, int layoutId) {// 上下文Context 數據列表datas 網格布局ID
            super(context, datas, layoutId); // 傳遞參數和初始化
        }

        @Override //設定更新GridVew每個項目的視圖
        public void setView(ViewHolder holder, Object o, int position) {
         // holder訪問或重用視圖 , o ->圖片URL , position ->項目位置索引
            ImageView imgv_item = holder.getView(R.id.imgv_item); // 從holder中獲取imageview,ID=imgv_item
            String url = (String) o; //將o轉為字串
            Glide.with(ChoicePicActivity.this).load(url).into(imgv_item);
           //Glide加載圖片,使用目前活動THIS串接, load加載圖片URL, into將圖片設定到imageview
        }
    }

}
