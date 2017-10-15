package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.BaseBean;
import com.sharingfuelcard.sharingfuelcard.module.RechargeHistoryBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/9/22.
 */

public interface RechageHistoryService {
    @GET(Url.RECHARGE_HISTORY)
    Call<ResponseData<List<RechargeHistoryBean>>> getHistory();
}
