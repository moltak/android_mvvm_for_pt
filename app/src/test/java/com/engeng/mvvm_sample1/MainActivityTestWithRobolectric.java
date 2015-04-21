package com.engeng.mvvm_sample1;

import android.app.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by moltak on 15. 4. 21..
 */
@Config(manifest = "app/src/main/AndroidManifest.xml", emulateSdk = 18, resourceDir = "res")
@RunWith(RobolectricTestRunner.class)
public class MainActivityTestWithRobolectric {

    @Test
    public void shouldGetApplicationObject() {
        Application application = Robolectric.application;
        assertThat(application, notNullValue());

        String app_name = application.getResources().getString(R.string.app_name);
        assertThat(app_name, is("mvvm_sample1"));
    }
}
