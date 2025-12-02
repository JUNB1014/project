package com.app.demo.shibie.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口地址管理
 *
 * @author llw
 */
public class ServiceGenerator {

    /**
     * 默認地址
     */
    public static String BASE_URL = "https://aip.baidubce.com";

    /**
     * 創建服務  參數就是API服務
     *
     * @param serviceClass 服務接口
     * @param <T>          泛型規範
     * @return api接口服務
     */
    public static <T> T createService(Class<T> serviceClass) {

        //創建OkHttpClient構建器對象
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        //設置請求超時的時間，這李是10秒
        okHttpClientBuilder.connectTimeout(20000, TimeUnit.MILLISECONDS);

        //消息攔截器  因為有時候接口不同在排錯的時候 需要先從接口的響應中做分析。利用了消息攔截器可以清楚的看到接口返回的所有內容
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        //setlevel用來設置日志打印的級別，共包括了四個級別：NONE,BASIC,HEADER,BODY
        //BASEIC:請求/響應行
        //HEADER:請求/響應行 + 頭
        //BODY:請求/響應航 + 頭 + 體
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //為OkHttp添加消息攔截器
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

        //在Retrofit中設置httpclient
        //設置地址  就是上面的固定地址,如果你是本地訪問的話，可以拼接上端口號  例如 +":8080"
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                //用Gson把服務端返回的json數據解析成實體
                .addConverterFactory(GsonConverterFactory.create())
                //放入OKHttp，之前說過retrofit是對OkHttp的進一步封裝
                .client(okHttpClientBuilder.build())
                .build();
        //返回這個創建好的API服務
        return retrofit.create(serviceClass);
    }

}
