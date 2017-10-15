package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.RegisterBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/9/20.
 */

public interface RegisterService {
    @Multipart
    @POST(Url.REGISTER)
    Call<ResponseData<RegisterBean>> register(@Query("tel") String tel, @Query("password") String password, @Query("name") String name,
                                              @Query("sex") String gender, @Query("code") String code, @Query("license_plate") String license_plate,
                                              @Query("car_models") String car_model, @Part("uploadFile\"; filename=\"file\"") RequestBody imgs);

    @GET(Url.GET_SMS_CODE)
    Call<ResponseData<String>> getSmsCode(@Query("tel") String tel);
}
