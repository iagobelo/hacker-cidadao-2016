package br.com.rotaverde.rotaverde.callback;

import br.com.rotaverde.rotaverde.model.User;

/**
 * Created by iagobelo on 21/08/2016.
 */
public interface IUserResult {
    void onSuccess(User user);
    void onError(String e);
}
