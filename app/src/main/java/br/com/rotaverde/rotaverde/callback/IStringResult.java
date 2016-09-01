package br.com.rotaverde.rotaverde.callback;

/**
 * Created by iagobelo on 22/06/16.
 */
public interface IStringResult {
    void onResultSuccess(String s);

    void onResultFailed(String errorMsg);
}
