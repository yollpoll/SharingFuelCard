package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/10/16.
 */

public interface SettingService {
    @GET(Url.GET_ADDRESS)
    Call<ResponseData<String>> getAddress();

    @POST(Url.SET_ADDRESS)
    Call<ResponseData<String>> setAddress(@Query("adress") String adress);

    @GET(Url.SET_PASSWORD)
    Call<ResponseData<String>> setPassword(@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

}
