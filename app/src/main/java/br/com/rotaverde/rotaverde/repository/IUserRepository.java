package br.com.rotaverde.rotaverde.repository;


import android.support.v4.app.FragmentActivity;

import br.com.rotaverde.rotaverde.model.User;

/**
 * Created by iagobelo on 23/06/16.
 */
public interface IUserRepository {
    void insert(User user);

    void update(User user);

    void delete();

    User search();
}
