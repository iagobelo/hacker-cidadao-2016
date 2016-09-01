package br.com.rotaverde.rotaverde.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.fragment.LoginFragment;
import br.com.rotaverde.rotaverde.fragment.RegisterFragment;

/**
 * Created by iagobelo on 18/07/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        // Returns the number of tabs
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        // Returns a new instance of the fragment
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                //return mContext.getString(R.string.title_section1).toUpperCase(l);
                return mContext.getString(R.string.fragment_login_name).toUpperCase(l);
            case 1:
                //return mContext.getString(R.string.title_section2).toUpperCase(l);
                return mContext.getString(R.string.fragment_register_name).toUpperCase(l);
        }
        return null;
    }
}
