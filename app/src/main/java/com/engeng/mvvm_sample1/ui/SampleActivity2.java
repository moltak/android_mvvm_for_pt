package com.engeng.mvvm_sample1.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.engeng.mvvm_sample1.R;
import com.engeng.mvvm_sample1.presentationmodel.Sample2ActivityPresentationModel;

import org.robobinding.binder.Binders;


public class SampleActivity2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sample2ActivityPresentationModel presentationModel = new Sample2ActivityPresentationModel();
        View rootView = Binders.inflateAndBindWithoutPreInitializingViews(
                this,
                R.layout.activity_sample2,
                presentationModel);
        setContentView(rootView);
    }
}
