package com.mobileapp.localnews;


//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.support.v4.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //Starts a new or restarts an existing Loader in this manager
//        getLoaderManager().restartLoader(0, null, this);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showFragment(R.id.id_top);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.id_top ){
                    showFragment(R.id.id_top);
                }else if(id == R.id.id_business ){
                    showFragment(R.id.id_business );
                }else if(id == R.id.id_entertainment ){
                    showFragment(R.id.id_entertainment);
                }else if(id == R.id.id_lifestyle ){
                    showFragment(R.id.id_lifestyle);
                }else if(id == R.id.id_sports ){
                    showFragment(R.id.id_sports);
                }else if(id == R.id.id_tech ){
                    showFragment(R.id.id_tech);
                }


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void showFragment(int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("key",id);
        mainFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, mainFragment, "MainFragment");
        fragmentTransaction.commit();
    }


}

