package com.example.qwinix.grid;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.qwinix.grid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    SharedPreferences mPref;
    String spinnerval,categoryval,gridval;
    private DatabaseReference mDatabase;
    // public static Object ServiceProviderInformation;
    private int Default_radius=8000;
    ArrayList<ServiceProviderInformation> mProvider=new ArrayList<ServiceProviderInformation>();
    private ArrayList<String> mkeys= new ArrayList<>();
    private LatLng newlocation;
    private LatLng latLng1;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPref = getSharedPreferences("navigation", MODE_PRIVATE);
        spinnerval = mPref.getString("spinnerval", "");
        categoryval = mPref.getString("categoryval", "");
        gridval = mPref.getString("gridval", "");
        Log.d("----logcat", "spinner=" + spinnerval);
        Log.d("----logcat", "category=" + categoryval);
        Log.d("----logcat", "gridval=" + gridval);


    }




//    private void getsortedlist() {
//        for (ServiceProviderInformation prov : mProvider) {
//
//
//
//    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;



        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("ServiceProviderInformation");
        mDatabase.keepSynced(true);

        mDatabase.addChildEventListener(new ChildEventListener() {

            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                double distance=0;


                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    //ServiceProviderInformation user=Snapshot.getValue(ServiceProviderInformation.class);
                    //String user1=dataSnapshot.child("latitude").getValue().toString();
                    //String user2=dataSnapshot.child("latitude").getValue().toString();
                    if (spinnerval.equalsIgnoreCase("doctor") && dataSnapshot.child("Spinner").getValue().toString().equalsIgnoreCase(spinnerval)) {

                        if (categoryval.equalsIgnoreCase("allopathy") && dataSnapshot.child("category").getValue().toString().equalsIgnoreCase(categoryval)) {
                            Log.d("---maps","come");

                            if (gridval.equalsIgnoreCase("orthopaedic") && dataSnapshot.child("subcategory").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getPhonenumber());
                               Log.d("mathew", "there");
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);
                                double check = markerreturn(distance);
                                Log.d("---plss", "come");
                                if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                                }
                            }
                            else if (gridval.equalsIgnoreCase("diabetician") && dataSnapshot.child("subcategory").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber());
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);

                                double check = markerreturn(distance);
                                Log.d("radii",""+check);
                                if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                                }


                            }
                            else if (gridval.equalsIgnoreCase("gynocologist") && dataSnapshot.child("subcategory").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber());
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);



                                double check = markerreturn(distance);
                                if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                                }
                            }
                            else if (gridval.equalsIgnoreCase("general") && dataSnapshot.child("subcategory").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber());
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);



                                double check = markerreturn(distance);
                                if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                                }
                            }
                            else if (gridval.equalsIgnoreCase("neurology") && dataSnapshot.child("subcategory").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber())
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                double check = markerreturn(distance);
                                if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                                }
                            }
                        }
                        else if (categoryval.equalsIgnoreCase("ayurvedic") && dataSnapshot.child("category").getValue().toString().equalsIgnoreCase(categoryval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                            Log.d("----sony", "" + newlocation);


                            double check = markerreturn(distance);
                            if (check < Default_radius) {


                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                            }
                        }
                        else if (categoryval.equalsIgnoreCase("homeopathy") && dataSnapshot.child("category").getValue().toString().equalsIgnoreCase(categoryval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));


                            Log.d("----sony", "" + newlocation);


                            double check = markerreturn( distance);
                            if (check < Default_radius) {


                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                            }
                        }
                    }
                    else if (spinnerval.equalsIgnoreCase("pharmacy") && dataSnapshot.child("Spinner").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                        Log.d("----sony", "" + newlocation);



                        double check = markerreturn(distance);
                        if (check < Default_radius) {


                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                        }
                    }
                    else if (spinnerval.equalsIgnoreCase("pathology") && dataSnapshot.child("Spinner").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                        Log.d("----sony", "" + newlocation);



                        double check = markerreturn(distance);
                        if (check < Default_radius) {


                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                        }
                    }
                    else if (spinnerval.equalsIgnoreCase("ambulance") && dataSnapshot.child("Spinner").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                        Log.d("----sony", "" + newlocation);



                        double check = markerreturn(distance);
                        Log.d("-ambu","lance"+check);
                        if (check < Default_radius) {
                            Log.d("-----ambu","lance");




                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                        }
                    }
                    else if (spinnerval.equalsIgnoreCase("bloodbank") && dataSnapshot.child("Spinner").getValue().toString().equalsIgnoreCase(spinnerval)) {
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                        Log.d("----sony", "" + newlocation);



                        double check = markerreturn(distance);
                        if (check < Default_radius) {


                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                        }
                    }
                }
                String key = dataSnapshot.getKey();
                mkeys.add(key);
                String value=dataSnapshot.child("Username").getValue().toString();
                Log.d("user",""+value);


            }

            //LatLng newlocation= new LatLng(dataSnapshot.child("latitude").getValue(double.class),dataSnapshot.child("longitude").getValue(double.class) );


            //Log.d("----sony",""+user2);


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                int index = mkeys.indexOf(key);
                Log.d("child","childdd"+dataSnapshot.child("latitude").getValue().toString());
                ;
                //mProvider.set(index,value);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                int index = mkeys.indexOf(key);
                Log.d("rem","remove"+dataSnapshot.getValue().toString());
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    public double markerreturn(double distance) {
        Location locationA = new Location("POINT A");
        locationA.setLatitude(latLng1.latitude);
        locationA.setLongitude(latLng1.longitude);
        Location locationB = new Location("POINT B");
        locationB.setLatitude(newlocation.latitude);
        locationB.setLongitude(newlocation.longitude);
        distance = locationA.distanceTo(locationB);
        Log.d("--dayavittu","baa"+distance);

        return distance;
    }

    protected synchronized void buildGoogleApiClient() {
        Log.d("--map","common");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        Geocoder geocoder = new Geocoder(getApplicationContext());
        markerOptions.position(latLng1);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));



        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 100000;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

}