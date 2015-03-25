package com.engeng.mvvm_sample1.viewmodel;

import com.engeng.mvvm_sample1.model.UserRestAdapter;
import com.engeng.mvvm_sample1.model.User;
import com.engeng.mvvm_sample1.model.UserService;
import com.engeng.mvvm_sample1.viewmodel.command.ActionCommand;
import com.engeng.mvvm_sample1.viewmodel.command.NoBehavierActionCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by moltak on 15. 3. 21..
 */
public class Sample1ActivityViewModel {
    private State state;
    private Listener listener;
    private List<User> userList;
    private Map<State, ActionCommand> actionCommandMap;
    private boolean isJunitRunning = false;

    public Sample1ActivityViewModel() {
        actionCommandMap = new HashMap<>();
        state = State.WAITING;
    }

    public void click() {
        this.state = State.RETRIEVING;
        listener.onStateChanged(this.state);
        Observable<List<User>> observable = UserRestAdapter.getAdapter()
                .create(UserService.class)
                .get()
                .subscribeOn(Schedulers.io());
        if(!isJunitRunning) {
            observable = observable.observeOn(AndroidSchedulers.mainThread());
        }
        observable.subscribe(getUserResteivingListener());
    }

    private Action1<List<User>> getUserResteivingListener() {
        return users -> {
            state = State.DONE;
            userList = users;
            listener.onStateChanged(state);
        };
    }

    public void enableJunitTesting() {
        isJunitRunning = true;
    }

    public State getState() {
        return state;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void putAction(State state, ActionCommand actionCommand) {
        actionCommandMap.put(state, actionCommand);
    }

    public ActionCommand getAction() {
        ActionCommand action = actionCommandMap.get(state);
        if(action == null) {
            return new NoBehavierActionCommand();
        }
        return action;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static enum State {
        WAITING, RETRIEVING, DONE
    }

    public static interface Listener {
        public void onStateChanged(State state);
    }
}
