package com.engeng.mvvm_sample1.model;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by moltak on 15. 3. 21..
 * read from -> http://jsonplaceholder.typicode.com/users
 */
public interface RetrievingUserService {
    @GET("/users")
    Observable<List<User>> get();
}
