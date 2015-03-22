package com.engeng.mvvm_sample1;

import com.engeng.mvvm_sample1.model.JsonSampleAdapter;
import com.engeng.mvvm_sample1.model.RetrievingUserService;
import com.engeng.mvvm_sample1.model.User;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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

    @Test
    public void testShouldHas10Results() throws InterruptedException, ExecutionException {
        Observable<List<User>> getUserService = JsonSampleAdapter.getAdapter()
                .create(RetrievingUserService.class)
                .get();
        BlockingObservable<List<User>> blockingObservable = BlockingObservable.from(getUserService);
        Future<List<User>> future = blockingObservable.toFuture();
        List<User> result = future.get();
        assertThat(result.size(), is(10));
        assertThat(result.get(0).getName(), is("Leanne Graham"));
    }
}
