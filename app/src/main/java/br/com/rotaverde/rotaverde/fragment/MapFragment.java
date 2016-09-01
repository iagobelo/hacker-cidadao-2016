package br.com.rotaverde.rotaverde.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.activity.MainActivity;
import br.com.rotaverde.rotaverde.controller.MapController;
import br.com.rotaverde.rotaverde.controller.UserController;
import br.com.rotaverde.rotaverde.model.Tree;
import br.com.rotaverde.rotaverde.util.Utils;

/**
 * Created by iagobelo on 21/08/2016.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapFragment";
    private AlertDialog.Builder mInsertTreeDialog;
    private MainActivity mMainActivity;

    public MapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) mMainActivity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.i(TAG, "onMapReady()");

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            Utils.showMenssage(getActivity(), "Erro");
        }

        // Seta o tips do mapa.
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Insere todas as árvores tombadas pela prefeitura e da Database.
        MapController.getInstance(getActivity()).getTree(googleMap);

        final View insertTreeLayout = mMainActivity.getLayoutInflater().inflate(R.layout.register_tree, null);
        final EditText edtTreeName = (EditText) insertTreeLayout.findViewById(R.id.edt_tree_name);

        // Quando segurar em algum ponto do mapa.
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                mInsertTreeDialog = new AlertDialog.Builder(getActivity());
                mInsertTreeDialog.setView(insertTreeLayout);
                mInsertTreeDialog.setMessage("Inserir Árvore:");
                mInsertTreeDialog.setCancelable(true);

                mInsertTreeDialog.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Tree tree = new Tree();
                        tree.setLatitude(Double.toString(latLng.latitude));
                        tree.setLongitude(Double.toString(latLng.longitude));
                        tree.setPopularName(edtTreeName.getText().toString());

                        // Insere a nova arvore no banco.
                        MapController.getInstance(getActivity()).insertNewTreeOnDatabase(tree, googleMap, getActivity());
                    }
                });

                mInsertTreeDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mInsertTreeDialog.show();
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MapController.getInstance(getActivity()).insertTemperature(googleMap);
            }
        }, 2000, 2000);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }
}
