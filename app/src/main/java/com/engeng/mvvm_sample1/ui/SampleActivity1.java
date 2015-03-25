package com.engeng.mvvm_sample1.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.engeng.mvvm_sample1.R;
import com.engeng.mvvm_sample1.viewmodel.Sample1ActivityViewModel;
import com.engeng.mvvm_sample1.viewmodel.command.ActionCommand;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SampleActivity1 extends ActionBarActivity {

    @InjectView(R.id.textview) TextView mTextview;
    private Sample1ActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample1);
        ButterKnife.inject(this);
        viewModel = new Sample1ActivityViewModel();
        viewModel.setListener(getViewModelListener());
        viewModel.putAction(Sample1ActivityViewModel.State.DONE, getActionCommand());
    }

    @OnClick(R.id.button)
    public void onClick(View view) {
        viewModel.click();
    }

    private Sample1ActivityViewModel.Listener getViewModelListener() {
        return new Sample1ActivityViewModel.Listener() {
            @Override
            public void onStateChanged(Sample1ActivityViewModel.State state) {
                viewModel.getAction().execute();
            }
        };
    }

    private ActionCommand getActionCommand() {
        return new ActionCommand() {
            @Override
            public void execute() {
                mTextview.setText(viewModel.getUserList().get(0).getUsername());
            }
        };
    }
}
