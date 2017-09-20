package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.LoginBean;
import com.sharingfuelcard.sharingfuelcard.module.RegisterBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/9/20.
 */

public interface LoginService {
    @GET(Url.LOGIN)
    Call<ResponseData<LoginBean>> login(@Query("tel") long tel, @Query("password") String password);
}
