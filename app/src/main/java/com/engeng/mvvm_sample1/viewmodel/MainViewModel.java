package com.engeng.mvvm_sample1.viewmodel;

import android.content.Context;
import android.content.Intent;

import com.engeng.mvvm_sample1.ui.SampleActivity1;
import com.engeng.mvvm_sample1.ui.SampleActivity2;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * Created by moltak on 15. 3. 21..
 */
@PresentationModel
public class MainViewModel implements HasPresentationModelChangeSupport {
    private final PresentationModelChangeSupport changeSupport;
    private final Context context;

    public MainViewModel(Context context) {
        changeSupport = new PresentationModelChangeSupport(this);
        this.context = context;
    }

    public void sample1() {
        context.startActivity(new Intent(context, SampleActivity1.class));
    }

    public void sample2() {
        context.startActivity(new Intent(context, SampleActivity2.class));
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
