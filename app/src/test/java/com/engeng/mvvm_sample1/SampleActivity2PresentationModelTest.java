package com.engeng.mvvm_sample1;

import com.engeng.mvvm.presentationmodel.Sample2ActivityPresentationModel;

import org.junit.Test;
import org.robobinding.property.PropertyChangeListener;

import java.util.concurrent.CountDownLatch;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by moltak on 15. 3. 21..
 */
public class SampleActivity2PresentationModelTest {

    @Test
    public void testPresentationModelTest() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        // given
        Sample2ActivityPresentationModel presentationModel = new Sample2ActivityPresentationModel();
        presentationModel.enableJunitTesting();
        presentationModel.getPresentationModelChangeSupport().addPropertyChangeListener(
                "text",
                new PropertyChangeListener() {
                    @Override
                    public void propertyChanged() {
                        countDownLatch.countDown();
                    }
                }
        );

        // when
        presentationModel.click();

        // then
        countDownLatch.await();
        assertThat(presentationModel.getText(), not(nullValue()));
    }
}
