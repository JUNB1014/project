package com.app.widgts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.mylibrary.R;
import com.app.interfaces.I_NumChangeListener;

/**
 * Created by seven on 2017/1/5.
 */

public class AddPlusView extends LinearLayout {
    private TextView tv_addplus_num;
    private View view_addplus_plus;
    private View view_addplus_add;
    private int choiceNum;//選擇的數量
    private I_NumChangeListener i_numEditListener;

    public AddPlusView(Context context) {
        this(context, null, 0);
    }

    public AddPlusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddPlusView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_addplus, this);
        view_addplus_plus = view.findViewById(R.id.view_addplus_plus);
        tv_addplus_num = (TextView) view.findViewById(R.id.tv_addplus_num);
        view_addplus_add = view.findViewById(R.id.view_addplus_add);

        view_addplus_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceNum++;
                tv_addplus_num.setText(choiceNum + "");

                if(null!=i_numEditListener){
                    i_numEditListener.onNumAddListener(choiceNum);
                }

            }
        });
        view_addplus_plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choiceNum > 1) {
                    choiceNum--;
                    tv_addplus_num.setText(choiceNum + "");
                }

                if(null!=i_numEditListener){
                    i_numEditListener.onNumSubtractListener(choiceNum);
                }
            }
        });
    }

    /*獲得選擇的數量*/
    public int getChoiceNum() {
        return choiceNum;
    }

    /**
     * 設置默認值
     *
     * @param defultNum
     */
    public void setDefultNum(int defultNum) {
        tv_addplus_num.setText(defultNum + "");
    }


    /*點擊中間數量變化的時候的監聽*/
    public void setOnEditListener(I_NumChangeListener i_numEditListener){
        this.i_numEditListener = i_numEditListener;
    }

}
