package br.com.rotaverde.rotaverde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.activity.MainActivity;
import br.com.rotaverde.rotaverde.callback.IUserResult;
import br.com.rotaverde.rotaverde.controller.UserController;
import br.com.rotaverde.rotaverde.model.User;
import br.com.rotaverde.rotaverde.util.Utils;

/**
 * Created by iagobelo on 18/07/16.
 */
public class LoginFragment extends Fragment {
    // Views
    private EditText mEdtEmail, mEdtPassword;
    private Button mBtnLogin;
    private ProgressBar mProgressBar;

    private UserController mUserController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mUserController = UserController.getInstance(getActivity());

        // Inicia Views
        mEdtEmail = (EditText) view.findViewById(R.id.edt_email);
        mEdtPassword = (EditText) view.findViewById(R.id.edt_password);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // 'Botão' de logar
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);

                User user = new User();
                user.setEmail(mEdtEmail.getText().toString());
                user.setPassword(mEdtPassword.getText().toString());

                mUserController.loginUser(user, new IUserResult() {
                    @Override
                    public void onSuccess(User user) {
                        mUserController.insertUser(user);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String e) {
                        Utils.showMenssage(getActivity(), e);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        return view;
    }
}
