package br.com.rotaverde.rotaverde.repository;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.rotaverde.rotaverde.contant.Constants;
import br.com.rotaverde.rotaverde.model.User;

/**
 * Created by iagobelo on 23/06/16.
 */
public class UserRepository implements IUserRepository {
    private static UserRepository instance;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;

    private UserRepository(Context context) {
        mSharedpreferences = context.getSharedPreferences(Constants.USER_REPOSITORY_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedpreferences.edit();
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    @Override
    public void insert(User user) {
        mEditor.putString(Constants.USER_CODE_KEY, user.getUserCode());
        mEditor.putString(Constants.NAME_KEY, user.getName());
        mEditor.putString(Constants.EMAIL_KEY, user.getEmail());
        mEditor.putString(Constants.PASSWORD_KEY, user.getPassword());
        mEditor.putString(Constants.SCORE_KEY, user.getScore());
        mEditor.commit();
    }

    @Override
    public void update(User user) {
        mEditor.putString(Constants.USER_CODE_KEY, user.getUserCode());
        mEditor.putString(Constants.NAME_KEY, user.getName());
        mEditor.putString(Constants.EMAIL_KEY, user.getEmail());
        mEditor.putString(Constants.PASSWORD_KEY, user.getPassword());
        mEditor.putString(Constants.SCORE_KEY, user.getScore());
        mEditor.commit();
    }

    @Override
    public void delete() {
        mEditor.clear();
        mEditor.commit();
    }

    @Override
    public User search() {
        User user = new User();
        user.setName(mSharedpreferences.getString(Constants.NAME_KEY, Constants.NULL_KEY));
        user.setEmail(mSharedpreferences.getString(Constants.EMAIL_KEY, Constants.NULL_KEY));
        user.setPassword(mSharedpreferences.getString(Constants.PASSWORD_KEY, Constants.NULL_KEY));
        user.setScore(mSharedpreferences.getString(Constants.SCORE_KEY, Constants.NULL_KEY));
        user.setUserCode(mSharedpreferences.getString(Constants.USER_CODE_KEY, Constants.NULL_KEY));
        return user;
    }
}
