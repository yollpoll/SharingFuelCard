package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.BaseBean;
import com.sharingfuelcard.sharingfuelcard.module.LoginBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/9/21.
 */

public interface BindCardService {
    @POST(Url.BIND_CARD)
    Call<ResponseData<BaseBean>> bindCard(@Query("oilcard_id") String oilcard_id, @Query("oilard_password") String oilard_password,
                                          @Query("oilcard_type") int oilcard_type, @Query("oilcard_name") String oilcard_name);

}
