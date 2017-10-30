package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.BuyCardBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/10/17.
 */

public interface BuyPlanService {
    @POST(Url.BUY_PLAN)
    Call<ResponseData<BuyCardBean>> buyPlan(@Query("oilcard_id") String oilcard_id, @Query("combo_type") String combo_type,
                                            @Query("monthly_sharing") double monthly_sharing, @Query("period") int period,
                                            @Query("combo_price") String combo_price, @Query("familyOilcard_id") String familyOilcard_id,
                                            @Query("original_price") String original_price);

}
