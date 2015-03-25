package com.engeng.mvvm_sample1.viewmodel;

import com.engeng.mvvm_sample1.model.JsonAdapter;
import com.engeng.mvvm_sample1.model.UserService;
import com.engeng.mvvm_sample1.model.User;
import com.engeng.mvvm_sample1.viewmodel.action.ActionCommand;
import com.engeng.mvvm_sample1.viewmodel.action.NothingAnyAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by moltak on 15. 3. 22..
 * It has 3 states.[WAITING, RETRIEVING, DONE]
 */
public class MainActivityViewModel {
    private State state;
    private Listener listener;
    private List<User> userList;
    private Map<State, ActionCommand> actionCommandMap;

    public MainActivityViewModel() {
        state = State.WAITING;
        actionCommandMap = new HashMap<>();
    }

    public State getState() {
        return state;
    }

    public void click() {
        state = State.RETRIEVING;
        if(listener != null) listener.onStateChanged(state);

        JsonAdapter.getAdapter()
                .create(UserService.class)
                .get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }

    private Subscriber<List<User>> getSubscriber() {
        return new Subscriber<List<User>>() {
            @Override
            public void onNext(List<User> users) {
                state = State.DONE;
                userList = users;
                if (listener != null) listener.onStateChanged(state);
            }

            @Override
            public void onError(Throwable e) {
                state = State.ERROR;
                if (listener != null) listener.onStateChanged(state);
            }

            @Override
            public void onCompleted() {}
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Listener getListener() {
        return listener;
    }

    public List<User> getUsers() {
        return userList;
    }

    public void putActionCommand(State state, ActionCommand actionCommand) {
        actionCommandMap.put(state, actionCommand);
    }

    public ActionCommand getAction() {
        ActionCommand actionCommand = actionCommandMap.get(state);
        if(actionCommand == null) {
            return new NothingAnyAction();
        }
        return actionCommand;
    }

    public static enum State {
        WAITING, RETRIEVING, DONE, ERROR
    }

    public interface Listener {
        public void onStateChanged(State state);
    }
}
