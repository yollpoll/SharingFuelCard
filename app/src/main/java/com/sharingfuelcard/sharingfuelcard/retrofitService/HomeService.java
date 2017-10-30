package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.HomeDataBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 鹏祺 on 2017/10/13.
 */

public interface HomeService {
    @GET(Url.GET_HOME_DATA)
    Call<ResponseData<HomeDataBean>> getHomeData();


}
