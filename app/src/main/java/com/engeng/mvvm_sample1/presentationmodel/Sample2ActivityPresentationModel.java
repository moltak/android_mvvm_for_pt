package com.engeng.mvvm_sample1.presentationmodel;


import com.engeng.mvvm_sample1.model.UserRestAdapter;
import com.engeng.mvvm_sample1.model.User;
import com.engeng.mvvm_sample1.model.UserService;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by moltak on 15. 3. 21..
 */
@PresentationModel
public class Sample2ActivityPresentationModel implements HasPresentationModelChangeSupport {
    private PresentationModelChangeSupport changeSupport;
    private List<User> userList;
    private boolean isJunitTesting = false;

    public Sample2ActivityPresentationModel() {
        changeSupport = new PresentationModelChangeSupport(this);
    }

    public String getText() {
        return userList.get(0).getUsername();
    }

    public void click() {
        Observable<List<User>> observable = UserRestAdapter.getAdapter()
                .create(UserService.class)
                .get()
                .subscribeOn(Schedulers.io());
        if(!isJunitTesting) {
            observable = observable.observeOn(AndroidSchedulers.mainThread());
        }
        observable.subscribe(getUserResteivingListener());
    }

    private Action1<List<User>> getUserResteivingListener() {
        return new Action1<List<User>>() {
            @Override
            public void call(List<User> users) {
                userList = users;
                changeSupport.firePropertyChange("text");
            }
        };
    }

    public void enableJunitTesting() {
        isJunitTesting = true;
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
