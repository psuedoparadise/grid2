package com.example.qwinix.grid;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mPref=getSharedPreferences("navigation",MODE_PRIVATE);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void displaySelectedScreen(int id) {
        Fragment fragment=null;


        //creating fragment object

        //initializing the fragment object which is selected
        switch (id) {
            case R.id.nav_doctor:
                fragment = new doctor();
                mPref.edit().putString("spinnerval","doctor").commit();
                break;
            case R.id.nav_pharmacy:
              Intent fromPharmacy = new Intent(MainActivity.this,MapsActivity.class);
              fromPharmacy.putExtra("KEY_BONE","pharmacy");
                mPref.edit().putString("spinnerval","pharmacy").commit();
              startActivity(fromPharmacy);


                //fragment = new pharmacy();
                break;
            case R.id.nav_pathology:
                //fragment=new pathology();
                Intent toy2=new Intent(MainActivity.this,MapsActivity.class);
                toy2.putExtra("KEY_BONE","pathology");
                mPref.edit().putString("spinnerval","pathology").commit();
                startActivity(toy2);
                break;
            case R.id.nav_ambulance:
                //fragment= new ambulance();
                Intent fromAmbulance = new Intent(MainActivity.this,MapsActivity.class);
                fromAmbulance.putExtra("KEY_BONE","ambulance");
                mPref.edit().putString("spinnerval","ambulance").commit();
                startActivity(fromAmbulance);

                break;
            case R.id.nav_blood:
                //fragment= new blood();
                mPref.edit().putString("spinnerval","bloodbank").commit();
                break;


        }

        if (fragment != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        displaySelectedScreen(id);
        return true;
    }
}