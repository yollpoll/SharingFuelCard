package com.sharingfuelcard.sharingfuelcard.retrofitService;

import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.BuyCardBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by 鹏祺 on 2017/10/15.
 */

public interface BuyCardService {
    //    @Multipart
//    @POST(Url.BUY_CARD)
//    Call<ResponseData<String>> buyCard(@Query("id_card") String uid_card, @Query("actual_name") String actual_name,
//                                       @Query("shipping_address") String shipping_address, @Query("tel") String tel,
//                                       @Query("type") String type, @Part("uploadFile\"; filename=\"IDCard_A\"") RequestBody IDCard_A,
//                                       @Part("uploadFile\"; filename=\"IDCard_B\"") RequestBody IDCard_B);
    @Multipart
    @POST(Url.BUY_CARD)
    Call<ResponseData<BuyCardBean>> buyCard(@Query("id_card") String uid_card, @Query("actual_name") String actual_name,
                                            @Query("shipping_address") String shipping_address, @Query("tel") String tel,
                                            @Query("type") String type, @Part MultipartBody.Part IDCard_A,
                                            @Part MultipartBody.Part IDCard_B);
}
