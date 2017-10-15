package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.BaseBean;
import com.sharingfuelcard.sharingfuelcard.module.MyCardMsgBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 鹏祺 on 2017/9/22.
 */

public interface MyFuelCardService {
    @GET(Url.MY_CARD_MSG)
    Call<ResponseData<List<MyCardMsgBean>>> getMyCardMsg();
}
