package com.engeng.mvvm_sample1;


import com.engeng.mvvm_sample1.viewmodel.command.NoBehavierActionCommand;
import com.engeng.mvvm_sample1.viewmodel.command.PrintActionCommand;
import com.engeng.mvvm_sample1.viewmodel.Sample1ActivityViewModel;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by moltak on 15. 3. 21..
 */
public class SampleActivity1ViewModelTest {

    @Test
    public void testShouldThereAreDatasWhenDataRetrieved() throws ExecutionException, InterruptedException {
        Sample1ActivityViewModel viewModel = new Sample1ActivityViewModel();
        viewModel.enableJunitTesting();
        viewModel.putAction(Sample1ActivityViewModel.State.DONE, new PrintActionCommand());
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        // given
        assertThat(viewModel.getState(), is(Sample1ActivityViewModel.State.WAITING));
        assertThat(viewModel.getAction(), instanceOf(NoBehavierActionCommand.class));
        viewModel.setListener(new Sample1ActivityViewModel.Listener() {
            @Override
            public void onStateChanged(Sample1ActivityViewModel.State state) {
                countDownLatch.countDown();
            }
        });

        // when
        viewModel.click();

        // then
        countDownLatch.await();
        assertThat(viewModel.getState(), is(Sample1ActivityViewModel.State.DONE));
        assertThat(viewModel.getAction(), instanceOf(PrintActionCommand.class));
        assertThat(viewModel.getUserList(), not(nullValue()));
    }
}
