package br.com.rotaverde.rotaverde.communication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import br.com.rotaverde.rotaverde.callback.IImageResult;
import br.com.rotaverde.rotaverde.callback.IStringResult;
import br.com.rotaverde.rotaverde.util.Utils;

/**
 * Created by iagobelo on 17/08/16.
 */

/**
 * Created by iagobelo on 22/06/16.
 */
public class Communication {
    private static final String TAG = "CommunicationLog";
    private static Communication instance;
    private static RequestQueue mRequestQueue;
    private static Context mContext;
    private StringRequest mStringRequest;
    private ImageRequest mImageRequest;

    public Communication(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mContext = context;
    }

    public void stringRequest(String stringRequest, final IStringResult iStringResult) {
        if (Utils.checkConnection(mContext)) {
            mStringRequest = new StringRequest(Request.Method.GET, stringRequest,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, "stringRequest() ok. Resposta = " + response);
                            iStringResult.onResultSuccess(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(TAG, "stringRequest() error!");
                            iStringResult.onResultFailed("Erro ao obter dados do servidor!");
                        }
                    }
            );
            mRequestQueue.add(mStringRequest);
        } else {
            iStringResult.onResultFailed("Não foi possivel se conectar a internet!");
        }
    }

    public void imageRequest(String imgUrl, final IImageResult iImageResult) {
        if (Utils.checkConnection(mContext)) {
            mImageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    Log.i(TAG, "ImageRequest() response: " + response.toString());
                    iImageResult.onSuccess(response);
                }
            }, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "ImageRequest() erro: " + error.getLocalizedMessage());
                }
            });
            mRequestQueue.add(mImageRequest);
        } else {
            iImageResult.onFailed("Não foi possivel se conectar a internet!");
        }
    }
}

