package br.com.rotaverde.rotaverde.controller;

import android.content.Context;

import java.security.NoSuchAlgorithmException;
import java.security.Permission;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.callback.IStringResult;
import br.com.rotaverde.rotaverde.callback.IUserResult;
import br.com.rotaverde.rotaverde.communication.Communication;
import br.com.rotaverde.rotaverde.contant.Constants;
import br.com.rotaverde.rotaverde.exception.UserNotFoundException;
import br.com.rotaverde.rotaverde.model.User;
import br.com.rotaverde.rotaverde.parse.JSONParser;
import br.com.rotaverde.rotaverde.repository.IUserRepository;
import br.com.rotaverde.rotaverde.repository.UserRepository;
import br.com.rotaverde.rotaverde.util.Utils;

/**
 * Created by iagobelo on 20/08/2016.
 */
public class UserController {
    private static UserController instance;
    private Communication mCommunication;
    private JSONParser mJsonParser;
    private IUserRepository mIUserRepository;
    private Context mContext;

    private UserController(Context context) {
        mCommunication = new Communication(context);
        mJsonParser = new JSONParser();
        mIUserRepository = UserRepository.getInstance(context);
        mContext = context;
    }

    public static synchronized UserController getInstance(Context context) {
        if (instance == null) {
            instance = new UserController(context);
        }
        return instance;
    }

    public void insertUser(User user) {

    }

    public void deleteUser(User user) {

    }

    public void loginUser(final User user, final IUserResult iUserResult) {
        // Verifica se existe conexão com o banco de dados.
        if (Utils.checkConnection(mContext)) {
            try {
                // Converte senha para MD5
                String md5Password = Utils.toMD5(user.getPassword());

                // Faz requisição para o servidor.
                mCommunication.stringRequest(Constants.LOGIN_USER_URL + "metodo=login&email="
                        + user.getEmail()
                        + "&" + "senha=" + user.getPassword(), new IStringResult() {
                    @Override
                    public void onResultSuccess(String s) {
                        try {
                            // Faz o parse e salva usuário retornado.
                            User result = mJsonParser.parseUser(s);
                            mIUserRepository.insert(result);
                            iUserResult.onSuccess(result);
                        } catch (UserNotFoundException e) {
                            e.printStackTrace();
                            iUserResult.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onResultFailed(String errorMsg) {
                        iUserResult.onError("Falha ao se conectar ao servidor, tente novamente.");
                    }
                });
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                iUserResult.onError("Falha ao se conectar ao servidor, tente novamente.");
            }
        } else {
            iUserResult.onError(mContext.getResources().getString(R.string.no_connection));
        }
    }

    public void updateUser(User user) {

    }

    public void getCurrentUser(IUserResult iUserResult) {
        User currentUser = mIUserRepository.search();

        // Verifica se os campos do usuário no repositorio não são nulos.
        if (Constants.NULL_KEY.equals(currentUser.getEmail())
                && Constants.NULL_KEY.equals(currentUser.getPassword())) {
            //throw new UserNotFoundException("Usuário não logado");
            iUserResult.onError("Usuário não logado.");
        } else {
            iUserResult.onSuccess(currentUser);
        }
    }

    public void logoutUser(){
        mIUserRepository.delete();
    }
}
