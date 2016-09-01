package br.com.rotaverde.rotaverde.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.rotaverde.rotaverde.R;
import br.com.rotaverde.rotaverde.adapter.ViewPagerAdapter;
import br.com.rotaverde.rotaverde.extras.SlidingTabLayout;

public class LoginRegisterActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        mToolbar = (Toolbar) findViewById(R.id.tb_login_register);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        //mToolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(mToolbar);

        // Seta o ViewPager e o SlidingtabLayout
        mViewPager = (ViewPager) findViewById(R.id.viewPager_login_register);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), this));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTab_login_register);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.md_white_1000));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);


    }
}
