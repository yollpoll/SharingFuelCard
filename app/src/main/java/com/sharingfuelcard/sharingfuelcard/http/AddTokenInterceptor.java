package com.sharingfuelcard.sharingfuelcard.http;

import android.util.Log;

import com.sharingfuelcard.sharingfuelcard.base.MyApplication;
import com.sharingfuelcard.sharingfuelcard.utils.SPUtiles;
import com.sharingfuelcard.sharingfuelcard.view.activity.LoginActivity;
import com.sharingfuelcard.sharingfuelcard.view.activity.LuncherActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 鹏祺 on 2017/9/21.
 */

public class AddTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        Request oldRequest = chain.request();
        Request newRequest = addParam(oldRequest);
        response = chain.proceed(newRequest);

        ResponseBody value = response.body();
        byte[] resp = value.bytes();
        String json = new String(resp, "UTF-8");
        Log.d("response", json);
//         判断stateCode值
        try {
            JSONObject jsonObject = new JSONObject(json);
            int stateCode = jsonObject.optInt("code");
            if (stateCode == 2) {
                SPUtiles.saveToken("");
                LuncherActivity.gotoLuncherActivity(MyApplication.getInstance().getApplicationContext());
//                LoginActivity.gotoLoginActivity(MyApplication.getInstance().getApplicationContext());
            } else {

            }
        } catch (Exception e) {
            Log.d("spq", "intercept error>>>>>>>" + e);
        } finally {
            // 这里值得注意。由于前面value.bytes()把响应流读完并关闭了，所以这里需要重新生成一个response，否则数据就无法正常解析了
            response = response.newBuilder()
                    .body(ResponseBody.create(null, resp))
                    .build();
        }
        return response;
    }

    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest) {
        HttpUrl.Builder builder = oldRequest.url()
                .newBuilder()
                .setEncodedQueryParameter("token", SPUtiles.getToken());

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();

        return newRequest;
    }
}
