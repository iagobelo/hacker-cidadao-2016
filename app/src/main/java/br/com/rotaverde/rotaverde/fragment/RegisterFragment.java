package br.com.rotaverde.rotaverde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.rotaverde.rotaverde.R;

/**
 * Created by iagobelo on 18/07/16.
 */
public class RegisterFragment extends Fragment {
    // Views
    private EditText mEdtEmail, mEdtPassword, mEdtConfirmPassowod;
    private Button mBtnRegister;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Pega as views.
        mEdtEmail = (EditText) view.findViewById(R.id.edt_email);
        mEdtPassword = (EditText) view.findViewById(R.id.edt_password);
        mEdtConfirmPassowod = (EditText) view.findViewById(R.id.edt_confirm_password);
        mBtnRegister = (Button) view.findViewById(R.id.btn_register);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // 'Botão' de Registrar
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEdtEmail.getText().toString();
                String password = mEdtPassword.getText().toString();

                mProgressBar.setVisibility(View.VISIBLE);

            }
        });
        return view;
    }
}
