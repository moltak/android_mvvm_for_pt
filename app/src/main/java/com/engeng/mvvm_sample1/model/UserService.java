package com.engeng.mvvm_sample1.model;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by moltak on 15. 3. 21..
 */
public interface UserService {
    @GET("/users")
    Observable<List<User>> get();
}
