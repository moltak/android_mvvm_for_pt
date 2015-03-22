package com.engeng.mvvm_sample1;

import com.engeng.mvvm_sample1.model.User;
import com.engeng.mvvm_sample1.viewmodel.MainActivityViewModel;
import com.engeng.mvvm_sample1.viewmodel.MainActivityViewModel.State;
import com.engeng.mvvm_sample1.viewmodel.action.DoneAction;
import com.engeng.mvvm_sample1.viewmodel.action.NothingAnyAction;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by moltak on 15. 3. 22..
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainViewModelTest {

    MainActivityViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new MainActivityViewModel();
    }

    @Test
    public void test01ShouldHasState() {
        assertThat(viewModel.getState(), is(State.WAITING));
    }

    @Test
    public void test02ShouldChangeStateWhenButtnClick() {
        assertThat(viewModel.getState(), is(State.WAITING));

        viewModel.click();

        assertThat(viewModel.getState(), is(State.RETRIEVING));
    }

    @Test
    public void test03ShouldNotifyToThisInstance() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        viewModel.setListener(new MainActivityViewModel.Listener() {
            @Override
            public void onStateChanged(State state) {
                countDownLatch.countDown();
            }
        });

        viewModel.click();

        assertThat(viewModel.getListener(), not(nullValue()));
        countDownLatch.await();
        assertThat(viewModel.getState(), is(State.RETRIEVING));
    }

    @Test
    public void test04ShouldRetrieveData() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        viewModel.setListener(new MainActivityViewModel.Listener() {
            @Override
            public void onStateChanged(State state) {
                countDownLatch.countDown(); // 2 call, RETRIEVING, DONE
            }
        });

        viewModel.click();

        // waiting
        countDownLatch.await();
        // -> data verify
        List<User> users = viewModel.getUsers();
        assertThat(users, not(nullValue()));
        assertThat(users.size(), not(0));
        assertThat(users.get(0).getName(), is("Leanne Graham"));
        assertThat(viewModel.getState(), is(State.DONE));
    }

    @Test
    public void test05ShouldReturnStateWhenChangeState() throws InterruptedException {
        assertThat(viewModel.getAction(), instanceOf(NothingAnyAction.class));
        viewModel.putActionCommand(State.DONE, new DoneAction());

        final CountDownLatch countDownLatch = new CountDownLatch(2);
        viewModel.setListener(new MainActivityViewModel.Listener() {
            @Override
            public void onStateChanged(State state) {
                countDownLatch.countDown();
            }
        });

        // when
        viewModel.click();

        // then
        countDownLatch.await();
        assertThat(viewModel.getState(), is(State.DONE));
        assertThat(viewModel.getAction(), instanceOf(DoneAction.class));
    }
}
