package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.BuyCardBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/10/25.
 */

public interface ForgetPasswordService {
    @POST(Url.FORGET_PASSWORD)
    Call<ResponseData<String>> forget(@Query("tel") String phone, @Query("password") String password);
}
