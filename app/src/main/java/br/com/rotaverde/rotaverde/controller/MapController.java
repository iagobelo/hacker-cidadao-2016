package br.com.rotaverde.rotaverde.controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.callback.IStringResult;
import br.com.rotaverde.rotaverde.callback.IUserResult;
import br.com.rotaverde.rotaverde.communication.Communication;
import br.com.rotaverde.rotaverde.contant.Constants;
import br.com.rotaverde.rotaverde.exception.TreeNotFoundException;
import br.com.rotaverde.rotaverde.model.Data;
import br.com.rotaverde.rotaverde.model.Result;
import br.com.rotaverde.rotaverde.model.Tree;
import br.com.rotaverde.rotaverde.model.User;
import br.com.rotaverde.rotaverde.parse.JSONParser;
import br.com.rotaverde.rotaverde.util.Utils;

/**
 * Created by iagobelo on 17/08/16.
 */
public class MapController {

    private static final String TAG = "MapControllerLog";
    private static MapController instance;
    private static Context mContext;
    private Communication mCommunication;
    private JSONParser mJsonParser;
    private UserController mUserController;
    private Marker mMarker;

    private MapController(Context context) {
        mCommunication = new Communication(context);
        mJsonParser = new JSONParser();
        mUserController = UserController.getInstance(mContext);
    }

    public static MapController getInstance(Context context) {
        if (instance == null) {
            instance = new MapController(context);
        }
        return instance;
    }

    public void insertPositionOnMap(GoogleMap googleMap, MarkerOptions marker) {
        Log.i(TAG, "insertPositionOnMap()");
        googleMap.addMarker(marker);
    }

    public void insertTempOnMap(GoogleMap googleMap, MarkerOptions marker) {
        Log.i(TAG, "insertPositionOnMap()");
        //mMarker.remove();
        mMarker = googleMap.addMarker(marker);
    }

    public void insertPositionOnMap(GoogleMap googleMap, List<MarkerOptions> marker) {
        Log.i(TAG, "insertPositionOnMap()");
        for (int i = 0; i < marker.size(); i++) {
            insertPositionOnMap(googleMap, marker.get(i));
            Log.i(TAG, "Adicionando posicao!");
        }
    }

    public void insertNewTreeOnDatabase(final Tree tree, final GoogleMap googleMap, Context context) {
        if (Utils.checkConnection(context)) {
            mUserController.getCurrentUser(new IUserResult() {
                @Override
                public void onSuccess(User user) {
                    Log.i(TAG, Constants.INSERT_TREE_URL
                            + "&title=" + tree.getPopularName()
                            + "&lng=" + tree.getLongitude()
                            + "&lat=" + tree.getLatitude()
                            + "&status=" + "U"
                            + "&cod_usuario=" + user.getUserCode());

                    mCommunication.stringRequest(Constants.INSERT_TREE_URL
                            + "&title=" + tree.getPopularName()
                            + "&lng=" + tree.getLongitude()
                            + "&lat=" + tree.getLatitude()
                            + "&status=" + "U"
                            + "&cod_usuario=" + user.getUserCode(), new IStringResult() {
                        @Override
                        public void onResultSuccess(String s) {
                            Result result = mJsonParser.parseResult(s);

                            if (result.getResult().equals("true")) {
                                Log.i(TAG, "Usuário inserido com sucesso.");
                                clearMap(googleMap);
                                getTree(googleMap);
                            }
                        }

                        @Override
                        public void onResultFailed(String errorMsg) {

                        }
                    });
                }

                @Override
                public void onError(String e) {

                }
            });
        } else {
            Utils.showMenssage(mContext, mContext.getResources().getString(R.string.no_connection));
        }
    }

    private void insertAllTombledTree(final GoogleMap googleMap) {
        Log.i(TAG, "insertAllTombledTree()");
        mCommunication.stringRequest(Constants.TUMBLED_TREES_URL, new IStringResult() {
            @Override
            public void onResultSuccess(String s) {
                try {
                    List<Tree> treeList = mJsonParser.parseTree(s, 1);
                    List<MarkerOptions> markerList = new ArrayList<MarkerOptions>();

                    for (int i = 0; i < treeList.size(); i++) {
                        markerList.add(treeList.get(i).toMarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.tombada)));
                    }
                    insertPositionOnMap(googleMap, markerList);
                } catch (TreeNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResultFailed(String errorMsg) {

            }
        });
    }

    public void insertTemperature(final GoogleMap googleMap) {
        mCommunication.stringRequest("http://things.ubidots.com/api/v1.6/datasources/57b90d7576254278ccb65839/variables/?format=json&token=UNr1IAnVsrFk0z43weuoISjSxeoDbH", new IStringResult() {
            @Override
            public void onResultSuccess(String s) {
                Data data = mJsonParser.parseData(s);
                LatLng temperaturePosition = new LatLng(-8.03211048, -34.87061238);

                insertPositionOnMap(googleMap, new MarkerOptions()
                        .title(Long.toString(data.getTemperature() + 20))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.temperatura))
                        .position(temperaturePosition));
            }

            @Override
            public void onResultFailed(String errorMsg) {

            }
        });
    }

    private void insertAllTreeOfDatabase(final GoogleMap googleMap) {
        mCommunication.stringRequest(Constants.ALL_TREE_URL, new IStringResult() {
            @Override
            public void onResultSuccess(String s) {
                try {
                    List<Tree> treeList = mJsonParser.parseTree(s, 2);
                    List<MarkerOptions> markerList = new ArrayList<MarkerOptions>();

                    for (int i = 0; i < treeList.size(); i++) {
                        markerList.add(treeList.get(i).toMarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.usuario)));
                    }

                    insertPositionOnMap(googleMap, markerList);
                } catch (TreeNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResultFailed(String errorMsg) {

            }
        });
    }

    private void clearMap(GoogleMap googleMap) {
        googleMap.clear();
    }

    public void getTree(GoogleMap googleMap) {
        insertAllTreeOfDatabase(googleMap);
        insertAllTombledTree(googleMap);
    }
}
