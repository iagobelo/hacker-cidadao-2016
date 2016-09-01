package br.com.rotaverde.rotaverde.callback;

import android.graphics.Bitmap;

/**
 * Created by iagobelo on 24/06/16.
 */
public interface IImageResult {

    void onSuccess(Bitmap bitmap);

    void onFailed(String errorMsg);
}
