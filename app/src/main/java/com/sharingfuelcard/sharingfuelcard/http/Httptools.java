package com.sharingfuelcard.sharingfuelcard.http;


import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by xlm on 2015/11/24.
 */
public class Httptools {
    private static Httptools instance = null;
    private static Retrofit retrofit = null;

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.HEAD_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    public Retrofit getNoHeadRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Httptools getInstance() {
        if (instance == null) {
            instance = new Httptools();
        }
        return instance;
    }

    public static OkHttpClient getOkHttpClient() {
        Interceptor interceptor = new AddTokenInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();
        return client;
    }

    /**
     * 根据文件创建requestbody
     *
     * @param file
     * @return
     */
    public static RequestBody getRequestBody(File file) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);
        return requestFile;
    }

    /**
     * 根据文件创建requestbody
     *
     * @param content
     * @return
     */
    public static RequestBody getRequestBody(String content) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("text/plain"), content);
        return requestBody;
    }
}
