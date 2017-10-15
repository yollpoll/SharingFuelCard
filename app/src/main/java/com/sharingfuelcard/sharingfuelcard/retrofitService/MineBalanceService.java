package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.BaseBean;
import com.sharingfuelcard.sharingfuelcard.module.PersonalBalanceBean;
import com.sharingfuelcard.sharingfuelcard.module.PlanChoiceBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/9/25.
 */

public interface MineBalanceService {
    @GET(Url.MY_BALANCE)
    Call<ResponseData<List<PersonalBalanceBean>>> myBalance();

    @GET(Url.ALL_CHOICE)
    Call<ResponseData<List<PlanChoiceBean>>> getAllChoice();

}
