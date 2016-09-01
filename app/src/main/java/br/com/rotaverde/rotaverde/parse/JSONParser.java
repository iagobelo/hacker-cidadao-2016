package br.com.rotaverde.rotaverde.parse;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.rotaverde.rotaverde.contant.Constants;
import br.com.rotaverde.rotaverde.exception.TreeNotFoundException;
import br.com.rotaverde.rotaverde.exception.UserNotFoundException;
import br.com.rotaverde.rotaverde.model.Data;
import br.com.rotaverde.rotaverde.model.Result;
import br.com.rotaverde.rotaverde.model.Tree;
import br.com.rotaverde.rotaverde.model.User;

/**
 * Created by iagobelo on 22/06/16.
 */
public class JSONParser {
    private static final String TAG = "JSONParserLog";
    private JSONObject mJsonObject;
    private JSONArray mJsonArray;

    public JSONParser() {

    }


    public List<Tree> parseTree(String jsonString, int type) throws TreeNotFoundException {
        switch (type) {
            case 1:
                try {
                    mJsonObject = new JSONObject(jsonString);
                    JSONObject object = mJsonObject.getJSONObject("result");
                    mJsonArray = object.getJSONArray("records");

                    List<Tree> trees = new ArrayList<>();

                    for (int i = 0; i < mJsonArray.length(); i++) {
                        Tree tree = new Tree();
                        tree.setMicroRegion(mJsonArray.getJSONObject(i).getString(Constants.MICROREGION_KEY));
                        tree.setObservation(mJsonArray.getJSONObject(i).getString(Constants.OBSERVATION_KEY));
                        tree.setNumber(mJsonArray.getJSONObject(i).getString(Constants.NUMBER_KEY));
                        tree.setRpa(mJsonArray.getJSONObject(i).getString(Constants.RPA_KEY));
                        tree.setLatitude(mJsonArray.getJSONObject(i).getString(Constants.LATITUDE_KEY));
                        tree.setLongitude(mJsonArray.getJSONObject(i).getString(Constants.LONGITUDE_KEY));
                        tree.setFamily(mJsonArray.getJSONObject(i).getString(Constants.FAMILY_KEY));
                        tree.setDecree(mJsonArray.getJSONObject(i).getString(Constants.DECREE_KEY));
                        tree.setScientificName(mJsonArray.getJSONObject(i).getString(Constants.SCIENTIFIC_NAME_KEY));
                        tree.setAdress(mJsonArray.getJSONObject(i).getString(Constants.ADRESS_KEY));
                        tree.setId(mJsonArray.getJSONObject(i).getString(Constants.ID_KEY));
                        tree.setPopularName(mJsonArray.getJSONObject(i).getString(Constants.POPULAR_NAME_KEY));
                        tree.setIdentifies(mJsonArray.getJSONObject(i).getString(Constants.IDENTIFIES_KEY));
                        trees.add(tree);
                    }
                    Log.i(TAG, "parseTree() 1" + trees.get(0).toString());
                    return trees;
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new TreeNotFoundException("Usuário não encontrado!");
                }

            case 2:
                try {
                    mJsonObject = new JSONObject(jsonString);
                    mJsonArray = mJsonObject.getJSONArray("arvores");

                    List<Tree> trees = new ArrayList<>();

                    for (int i = 0; i < mJsonArray.length(); i++) {
                        Tree tree = new Tree();
                        tree.setId(mJsonArray.getJSONObject(i).getString(Constants.TREE_CODE_KEY));
                        tree.setPopularName(mJsonArray.getJSONObject(i).getString(Constants.TITLE_KEY));
                        tree.setLatitude(mJsonArray.getJSONObject(i).getString("lat"));
                        tree.setLongitude(mJsonArray.getJSONObject(i).getString("lng"));
                        tree.setStatus(mJsonArray.getJSONObject(i).getString(Constants.STATUS_KEY));
                        tree.setUserCode(mJsonArray.getJSONObject(i).getString(Constants.USER_CODE_KEY));
                        trees.add(tree);
                    }
                    Log.i(TAG, "parseTree() 2" + trees.get(0).toString());
                    return trees;
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new TreeNotFoundException("Usuário não encontrado!");
                }
        }
        return null;
    }

    public User parseUser(String jsonString) throws UserNotFoundException {
        User user = new User();
        try {
            mJsonObject = new JSONObject(jsonString);

            user.setUserCode(mJsonObject.getString(Constants.USER_CODE_KEY));
            user.setName(mJsonObject.getString(Constants.NAME_KEY));
            user.setEmail(mJsonObject.getString(Constants.EMAIL_KEY));
            user.setPassword(mJsonObject.getString(Constants.PASSWORD_KEY));
            user.setScore(mJsonObject.getString(Constants.SCORE_KEY));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new UserNotFoundException("Usuário não encontrado!");
        }
        Log.i(TAG, "parseUser()\n" + user.toString());
        return user;
    }

    public Result parseResult(String jsonString) {
        Result result = new Result();
        try {
            mJsonArray = new JSONArray(jsonString);
            result.setResult(mJsonArray.getJSONObject(0).getString("processo"));
            Log.i(TAG, "parseResult()");
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            result.setResult("false");
            return result;
        }
    }

    public Data parseData(String jsonString) {
        try {
            Data data = new Data();

            mJsonObject = new JSONObject(jsonString);
            mJsonArray = mJsonObject.getJSONArray("results");

            JSONObject jsonObject = mJsonArray.getJSONObject(0);
            data.setTemperature(jsonObject.getJSONObject("last_value").getLong("value"));

            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
