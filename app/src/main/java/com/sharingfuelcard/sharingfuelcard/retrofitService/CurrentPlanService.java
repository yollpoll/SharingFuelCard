package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.CurrentPlanBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 鹏祺 on 2017/10/25.
 */

public interface CurrentPlanService {
    @GET(Url.GET_MY_PLAN)
    Call<ResponseData<List<CurrentPlanBean>>> getPlan();
}
