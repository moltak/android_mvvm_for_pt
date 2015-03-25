package com.engeng.mvvm_sample1;

import com.engeng.mvvm.model.JsonSampleAdapter;
import com.engeng.mvvm.model.User;
import com.engeng.mvvm.model.UserService;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;
import rx.observables.BlockingObservable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by moltak on 15. 3. 21..
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestModelTest {

    private final String BODY = "[{\"id\":1,\"name\":\"Leanne Graham\",\"username\":\"Bret\"},{\"id\":2,\"name\":\"Ervi Howell\"}]";
    private MockWebServer server;

    @Before
    public void setup() throws IOException {
        server = new MockWebServer();
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(BODY);
        server.enqueue(mockResponse);
        server.start();
    }

    @Test
    public void testShouldHasResultsUsingMockServer() throws InterruptedException, ExecutionException {
        Observable<List<User>> getUserService = JsonSampleAdapter.getAdapter(server.getUrl("").toString())
                .create(UserService.class)
                .get();
        BlockingObservable<List<User>> blockingObservable = BlockingObservable.from(getUserService);
        Future<List<User>> future = blockingObservable.toFuture();
        List<User> result = future.get();
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("Leanne Graham"));
    }
}
