package com.app.demo.shibie.network;


import com.app.demo.shibie.model.GetDiscernResultResponse;
import com.app.demo.shibie.model.GetTokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * API服務
 *
 * @author llw
 * @date 2021/4/1 17:48
 */
public interface ApiService {

    /**
     * 獲取鑒權認證Token
     * @param grant_type 類型
     * @param client_id API Key
     * @param client_secret Secret Key
     * @return GetTokenResponse
     */
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Call<GetTokenResponse> getToken(@Field("grant_type") String grant_type,
                                    @Field("client_id") String client_id,
                                    @Field("client_secret") String client_secret);

    /**
     * 獲取圖像識別結果
     * @param accessToken 獲取鑒權認證Token
     * @param image 圖片base64
     * @param url 網絡圖片Url
     * @return JsonObject
     */
    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v2/advanced_general")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<GetDiscernResultResponse> getDiscernResult(@Field("access_token") String accessToken,
                                                    @Field("image") String image,
                                                    @Field("url") String url);
}
