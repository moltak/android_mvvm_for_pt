package com.engeng.mvvm_sample1.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.engeng.mvvm_sample1.R;
import com.engeng.mvvm_sample1.presentationmodel.MainPresentationModel;

import org.robobinding.binder.Binders;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPresentationModel presentationModel = new MainPresentationModel(this);
        View rootView = Binders.inflateAndBindWithoutPreInitializingViews(
                this,
                R.layout.activity_main,
                presentationModel);
        setContentView(rootView);
    }
}
