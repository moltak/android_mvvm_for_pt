package com.engeng.mvvm_sample1.model;

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
                .setClient(new OkClient())
                .build();
    }
}
