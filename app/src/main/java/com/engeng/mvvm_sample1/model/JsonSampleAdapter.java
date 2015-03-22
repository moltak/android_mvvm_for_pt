package com.engeng.mvvm_sample1.model;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by moltak on 15. 3. 21..
 */
public class JsonSampleAdapter {
    public static RestAdapter getAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint("http://jsonplaceholder.typicode.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(getOkHttpClient())
                .build();
    }

    private static OkClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(3000, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(3000, TimeUnit.SECONDS);
        return new OkClient(okHttpClient);
    }
}
