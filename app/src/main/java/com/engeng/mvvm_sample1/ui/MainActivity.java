package com.engeng.mvvm_sample1.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.engeng.mvvm_sample1.R;
import com.engeng.mvvm_sample1.viewmodel.MainActivityViewModel;
import com.engeng.mvvm_sample1.viewmodel.action.ActionCommand;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.textview) TextView mTextview;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        viewModel = new MainActivityViewModel();
        viewModel.setListener(getListener());
        viewModel.putActionCommand(MainActivityViewModel.State.DONE, getDoneActionCommand());
    }

    @OnClick(R.id.button)
    public void onButtonClicked() {
        viewModel.click();
    }

    private MainActivityViewModel.Listener getListener() {
        return new MainActivityViewModel.Listener() {
            @Override
            public void onStateChanged(MainActivityViewModel.State state) {
                if(state != MainActivityViewModel.State.DONE) {
                    mTextview.setText(state.toString());
                } else {
                    viewModel.getAction().execute();
                }
            }
        };
    }

    private ActionCommand getDoneActionCommand() {
        return new ActionCommand() {
            @Override
            public void execute() {
                mTextview.setText(viewModel.getUsers().get(0).getName());
            }
        };
    }
}
