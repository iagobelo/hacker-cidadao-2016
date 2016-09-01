package br.com.rotaverde.rotaverde.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IagoBelo on 20/05/2016.
 */
public class Utils {

    /**
     * Verifica se existe conexão com a internet.
     *
     * @param context Contexto da classe.
     * @return true = existe conexão, false = sem conexão.
     */
    public static boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Converte uma string para md5.
     *
     * @param string String a ser criptografada.
     * @return String Hash em MD5.
     * @throws NoSuchAlgorithmException
     */
    public static String toMD5(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(string.getBytes()));
        String s = hash.toString(16);
        if (s.length() % 2 != 0)
            s = "0" + s;
        return s;
    }

    /**
     * Exibe uma Toast pelo tempo definido por parametro.
     *
     * @param context Contexto da classe.
     * @param msg     Menssagem a ser exibida na tela.
     * @param time    Tempo que a menssagem deve ser exibida (em milissegundos).
     */
    public static void showMenssage(Context context, String msg, int time) {
        Toast.makeText(context, msg, time).show();
    }

    /**
     * Exibe um Toast por 3 seg na tela.
     *
     * @param context Contexto da classe.
     * @param msg     Menssagem a ser exibida na tela.
     */
    public static void showMenssage(Context context, String msg) {
        showMenssage(context, msg, Toast.LENGTH_SHORT);
    }
}
