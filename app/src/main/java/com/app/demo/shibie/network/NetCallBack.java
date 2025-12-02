package com.app.demo.shibie.network;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 網絡請求回調
 *
 * @param <T>
 */
public abstract class NetCallBack<T> implements Callback<T> {//這里實現了retrofit2.Callback

    //訪問成功回調
    @Override
    public void onResponse(Call<T> call, Response<T> response) {//數據返回
        if (response != null && response.body() != null && response.isSuccessful()) {
            onSuccess(call, response);
        } else {
            onFailed(response.raw().toString());
        }
    }

    //訪問失敗回調
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("data str", t.toString());
        onFailed(t.toString());
    }

    //數據返回
    public abstract void onSuccess(Call<T> call, Response<T> response);

    //失敗異常
    public abstract void onFailed(String errorStr);


}
