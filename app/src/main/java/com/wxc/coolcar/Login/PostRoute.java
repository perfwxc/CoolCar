package com.wxc.coolcar.Login;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Chenge666 on 2018/11/19.
 */

public interface PostRoute {
    //@Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("sendsms")
    //@POST("send")
    Call<Tl> postFlyRoute(@Query("sdkappid") String sdkappid, @Query("random") String random, @Body RequestBody route);//传入的参数为RequestBody
    //Call<Gh> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody
}
