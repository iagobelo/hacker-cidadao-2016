package br.com.rotaverde.rotaverde;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by iagobelo on 21/08/2016.
 */
public class MapService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }


    private class doInBackground extends Thread {

    }
}
