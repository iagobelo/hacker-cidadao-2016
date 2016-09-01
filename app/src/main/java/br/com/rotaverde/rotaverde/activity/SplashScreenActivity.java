package br.com.rotaverde.rotaverde.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.callback.IUserResult;
import br.com.rotaverde.rotaverde.controller.UserController;
import br.com.rotaverde.rotaverde.model.User;

public class SplashScreenActivity extends Activity {

    private UserController mUserController;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Views
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mUserController = UserController.getInstance(this);
        mUserController.getCurrentUser(new IUserResult() {
            @Override
            public void onSuccess(User user) {

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                    }
                }, 4000);
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String e) {
                startActivity(new Intent(SplashScreenActivity.this, LoginRegisterActivity.class));
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
