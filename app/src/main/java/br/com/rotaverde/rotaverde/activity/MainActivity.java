package br.com.rotaverde.rotaverde.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.callback.IUserResult;
import br.com.rotaverde.rotaverde.controller.UserController;
import br.com.rotaverde.rotaverde.fragment.MapFragment;
import br.com.rotaverde.rotaverde.fragment.SettingsFragment;
import br.com.rotaverde.rotaverde.model.User;
import br.com.rotaverde.rotaverde.util.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityLog";

    // Views
    private Toolbar mToolbar;
    private AccountHeader mAccountHeader;
    private Drawer mNavigationDrawerLeft;
    private AlertDialog.Builder mInsertTreeDialog;

    // Fragment
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmenttransaction;
    private SettingsFragment mSettingsFragment;
    private MapFragment mMapFragment;

    private UserController mUserController;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserController = UserController.getInstance(this);

        // Fragments
        mSettingsFragment = new SettingsFragment();
        mMapFragment = new MapFragment();
        mMapFragment.setMainActivity(this);

        mFragmentManager = getFragmentManager();
        mFragmenttransaction = mFragmentManager.beginTransaction();
        mFragmenttransaction.add(R.id.fragment_container, mMapFragment);
        mFragmenttransaction.commit();

        // Seta o toolbar
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Rota Verde");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        setSupportActionBar(mToolbar);

        mUserController.getCurrentUser(new IUserResult() {
            @Override
            public void onSuccess(User user) {
                mUser = user;
            }

            @Override
            public void onError(String e) {
                Utils.showMenssage(MainActivity.this, "Usuário não encontrado, porfavor logue novamente!");
                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
            }
        });

        // Instacia o Account Header e seta os atributos
        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withCompactStyle(false)
                .withHeaderBackground(R.color.colorPrimary)
                .withAlternativeProfileHeaderSwitching(true)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(mUser.getName())
                                .withEmail(mUser.getEmail())
                ).withAlternativeProfileHeaderSwitching(true)
                .build();

        // Instacia o Navigation Drawer e seta os atributos
        mNavigationDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withToolbar(mToolbar)
                .withDisplayBelowStatusBar(true)
                .withSelectedItem(2)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(mAccountHeader)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        replaceFragment(drawerItem);
                        return false;
                    }
                })
                .build();

        mNavigationDrawerLeft.addItem(new SectionDrawerItem().withName("MENU"));
        mNavigationDrawerLeft.addItem(new DividerDrawerItem());
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Mapa").withIdentifier(1));
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Usuário").withIdentifier(2));
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Configurações").withIdentifier(3));
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIdentifier(4));
    }

    private void replaceFragment(IDrawerItem drawerItem) {
        switch (Long.toString(drawerItem.getIdentifier())) {
            case "1":
                mFragmentManager = getFragmentManager();
                mFragmenttransaction = mFragmentManager.beginTransaction();
                mFragmenttransaction.replace(R.id.fragment_container, mMapFragment);
                mFragmenttransaction.addToBackStack(null);
                mFragmenttransaction.commit();
                break;

            case "2":
                break;

            case "3":
                mFragmentManager = getFragmentManager();
                mFragmenttransaction = mFragmentManager.beginTransaction();
                mFragmenttransaction.replace(R.id.fragment_container, mSettingsFragment);
                mFragmenttransaction.addToBackStack(null);
                mFragmenttransaction.commit();
                break;

            case "4":
                mUserController.logoutUser();
                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
                break;
        }
    }
}
