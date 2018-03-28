package com.example.qwinix.grid;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private static final float DEFAULT_ZOOM = 25;
    private GoogleMap mMap;
    private TextView mpathology;
    private Button btnambulance;
    private LinearLayout mDetail;
    private TextView mText;
    private EditText mSearchText;
    private ImageView mGps;
    String tabName;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    ArrayList<DataModel> mModel= new ArrayList<>();
    ArrayList<Integer>  ratings = new ArrayList<>();
    ArrayList<String>  revies = new ArrayList<>();
    ArrayList<LatLng>  mLoca = new ArrayList<>();
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //GridView gridView = (GridView) findViewById(R.id.grid_view);
        mSearchText = (EditText) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        getDeviceLocation();
        Intent intent=getIntent();
        tabName=   intent.getStringExtra("KEY_BONE");
        Log.d("---name",""+tabName);



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("mathew", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("mathew","Google Play Services available.");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mpathology=findViewById(R.id.btnpathology);

        //mRestaurants = findViewById(R.id.restaurant);
        mDetail = findViewById(R.id.bottomcontainer);
        mText = findViewById(R.id.position);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLoca.add(new LatLng(12.348477,76.618211));//0
        mLoca.add(new LatLng(12330451,76.629726));//1
        mLoca.add(new LatLng(12.329947,76.626920));//2
        mLoca.add(new LatLng(12.323323,76.631357));//3
        mLoca.add(new LatLng(12.324513,76.673160));//4
        mLoca.add(new LatLng(12.330802,76.675134));//5
        mLoca.add(new LatLng(12.334156, 76.676336));//6
        mLoca.add(new LatLng(12.327868, 76.677365));//7
        mLoca.add(new LatLng(12.323843, 76.690927));//8
        mLoca.add(new LatLng(12.375089, 76.665328));//9
        mLoca.add(new LatLng(12.346672, 76.673225));//10
        mLoca.add(new LatLng(12.335023, 76.674855));//11
        mLoca.add(new LatLng(12.316073, 76.651509));//12
        mLoca.add(new LatLng(12.325547, 76.674684));//13
        mLoca.add(new LatLng(12.318000, 76.650737));//14
        mLoca.add(new LatLng(12.308944, 76.677087));//15
        mLoca.add(new LatLng(12.332170, 76.680692));//16
        mLoca.add(new LatLng(12.310947, 76.648907));//17
        mLoca.add(new LatLng(12.312960, 76.647277));//18
        mLoca.add(new LatLng(12.299483, 76.652255));//19
        mLoca.add(new LatLng(12.298906, 76.626293));//20
        mLoca.add(new LatLng(12.297409, 76.631529));//21
        mLoca.add(new LatLng(12.302021, 76.626808));//22
        mLoca.add(new LatLng(12.301267, 76.616594));//23
        mLoca.add(new LatLng(12.288436, 76.623032));//24
        mLoca.add(new LatLng(12.285477, 76.625263));//25
        mLoca.add(new LatLng(12.291790, 76.649296));//26
        mLoca.add(new LatLng(12.298540, 76.653199));//27
        mLoca.add(new LatLng(12.299379, 76.658349));//28


        DataModel model = new DataModel();
        model.setmLocation(new LatLng(12.348477,76.618211));
        model.setReview("good");
        model.setTitle("PERFECT PATHOLOGY");
        model.setRating(3);

        DataModel model1 = new DataModel();
        model1.setmLocation(new LatLng(12330451,76.629726));
        model1.setReview("average");

        model1.setTitle("ST. labs");
        model1.setRating(3);

        DataModel model2 = new DataModel();
        model2.setmLocation(new LatLng(12.329947,76.626920));
        model2.setReview("average");
        model2.setTitle("DRM labs");
        model2.setRating(2);

        DataModel model3 = new DataModel();
        model3.setmLocation(new LatLng(12.323323,76.631357));
        model3.setReview("excellent");
        model3.setTitle("Vinayaka diagnostic center");
        model3.setRating(3);

        DataModel model4 = new DataModel();
        model4.setmLocation(new LatLng(12.324513,76.673160));
        model4.setReview("excellent");
        model4.setTitle("hemanth diagnostic center");
        model4.setRating(3);

        DataModel model5 = new DataModel();
        model5.setmLocation(new LatLng(12.330802,76.675134));
        model5.setReview("excellent");
        model5.setTitle("quba diagnostic center");
        model5.setRating(3);

        DataModel model6 = new DataModel();
        model6.setmLocation(new LatLng(12.334156, 76.676336));
        model6.setReview("excellent");
        model6.setTitle("mysore diagnostic center");
        model6.setRating(3);

        DataModel model7 = new DataModel();
        model7.setmLocation(new LatLng(12.327868, 76.677365));
        model7.setReview("excellent");
        model7.setTitle("universal diagnostic center");
        model7.setRating(3);

        DataModel model8 = new DataModel();
        model8.setmLocation(new LatLng(12.323843, 76.690927));
        model8.setReview("excellent");
        model8.setTitle("baba diagnostic center");
        model8.setRating(3);

        DataModel model9 = new DataModel();
        model9.setmLocation(new LatLng(12.375089, 76.665328));
        model9.setReview("excellent");
        model9.setTitle("columbia asia hospital");
        model9.setRating(3);

        DataModel model10 = new DataModel();
        model10.setmLocation(new LatLng(12.346672, 76.673225));
        model10.setReview("excellent");
        model10.setTitle("narayana multispeciality hospital");
        model10.setRating(3);

        DataModel model11 = new DataModel();
        model11.setmLocation(new LatLng(12.335023, 76.674855));
        model11.setReview("excellent");
        model11.setTitle("bio tech diagnostic center");
        model11.setRating(3);

        DataModel model12 = new DataModel();
        model12.setmLocation(new LatLng(12.316073, 76.651509));
        model12.setReview("excellent");
        model12.setTitle("ravi diagnostic center");
        model12.setRating(3);

        DataModel model13 = new DataModel();
        model13.setmLocation(new LatLng(12.325547, 76.674684));
        model13.setReview("good");
        model13.setTitle("asian diagnostic center");
        model13.setRating(3);

        DataModel model14 = new DataModel();
        model14.setmLocation(new LatLng(12.318000, 76.650737));
        model14.setReview("excellent");
        model14.setTitle("Kannan hospital");
        model14.setRating(3);

        DataModel model15 = new DataModel();
        model15.setmLocation(new LatLng(12.308944, 76.677087));
        model15.setReview("excellent");
        model15.setTitle("sanjevini diagnostic center");
        model15.setRating(3);

        DataModel model16 = new DataModel();
        model16.setmLocation(new LatLng(12.332170, 76.680692));
        model16.setReview("excellent");
        model16.setTitle("hi tech");
        model16.setRating(3);

        DataModel model17 = new DataModel();
        model17.setmLocation(new LatLng(12.310947, 76.648907));
        model17.setReview("excellent");
        model17.setTitle("bhagvan diagnostic center");
        model17.setRating(3);

        DataModel model18 = new DataModel();
        model18.setmLocation(new LatLng(12.312960, 76.647277));
        model18.setReview("excellent");
        model18.setTitle("rama diagnostic center");
        model18.setRating(3);

        DataModel model19 = new DataModel();
        model19.setmLocation(new LatLng(12.299483, 76.652255));
        model19.setReview("excellent");
        model19.setTitle("jss hospital");
        model19.setRating(3);

        DataModel model20 = new DataModel();
        model20.setmLocation(new LatLng(12.298906, 76.626293));
        model20.setReview("excellent");
        model20.setTitle("accura diagnostic center");
        model20.setRating(3);

        DataModel model21 = new DataModel();
        model21.setmLocation(new LatLng(12.297409, 76.631529));
        model21.setReview("excellent");
        model21.setTitle("dr lal path labs");
        model21.setRating(3);

        DataModel model22 = new DataModel();
        model22.setmLocation(new LatLng(12.302021, 76.626808));
        model22.setReview("excellent");
        model22.setTitle("bhagvan diagnostic center");
        model22.setRating(3);

        DataModel model23 = new DataModel();
        model23.setmLocation(new LatLng(12.301267, 76.616594));
        model23.setReview("excellent");
        model23.setTitle("vachana diagnostic center");
        model23.setRating(3);

        DataModel model24 = new DataModel();
        model24.setmLocation(new LatLng(12.288436, 76.623032));
        model24.setReview("excellent");
        model24.setTitle("yashashwini diagnostic  center");
        model24.setRating(3);

        DataModel model25 = new DataModel();
        model25.setmLocation(new LatLng(12.285477, 76.625263));
        model25.setReview("excellent");
        model25.setTitle("mallige diagnostic center");
        model25.setRating(3);

        DataModel model26 = new DataModel();
        model26.setmLocation(new LatLng(12.291790, 76.649296));
        model26.setReview("excellent");
        model26.setTitle("prakash diagnostic  center");
        model26.setRating(3);

        DataModel model27 = new DataModel();
        model27.setmLocation(new LatLng(12.298540, 76.653199));
        model27.setReview("excellent");
        model27.setTitle("apollo hospital");
        model27.setRating(3);

        DataModel model28 = new DataModel();
        model28.setmLocation(new LatLng(12.299379, 76.658349));
        model28.setReview("excellent");
        model28.setTitle("anand diagnostic center");
        model28.setRating(3);

        mModel.add(model);
        mModel.add(model1);
        mModel.add(model2);
        mModel.add(model3);
        mModel.add(model4);
        mModel.add(model5);
        mModel.add(model6);
        mModel.add(model7);
        mModel.add(model8);
        mModel.add(model9);
        mModel.add(model10);
        mModel.add(model11);
        mModel.add(model12);
        mModel.add(model13);
        mModel.add(model14);
        mModel.add(model15);
        mModel.add(model16);
        mModel.add(model17);
        mModel.add(model18);
        mModel.add(model19);
        mModel.add(model20);
        mModel.add(model21);
        mModel.add(model22);
        mModel.add(model23);
        mModel.add(model24);
        mModel.add(model25);
        mModel.add(model26);
        mModel.add(model27);
        mModel.add(model28);

        //  setTab(tabName);

    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


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
        mMap = googleMap;


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        Button btnHospital = (Button) findViewById(R.id.btnHospital);
        btnHospital.setOnClickListener(new View.OnClickListener() {
            String Hospital = "hospital";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, Hospital);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby Hospitals", Toast.LENGTH_LONG).show();
            }
        });
        Button btnpharmacy = (Button) findViewById(R.id.btnpHARMACY);
        btnpharmacy.setOnClickListener(new View.OnClickListener() {
            String pharmacy = "pharmacy";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, pharmacy);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby pharmacy", Toast.LENGTH_LONG).show();
            }
        });

        btnambulance = (Button) findViewById(R.id.btnambulance);
        btnambulance.setOnClickListener(new View.OnClickListener() {
            String Hospital = "hospital";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(latitude, longitude, Hospital);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby ambulance", Toast.LENGTH_LONG).show();
            }
        });



        mpathology.setOnClickListener(new View.OnClickListener() {
            String pathology = "pathology";

            @Override
            public void onClick(View v) {
                Log.d("mathew", "Button is Clicked" + latitude);
                mMap.clear();
              /*  String url = getUrl(latitude, longitude, Restaurant);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("mathew", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby Restaurants", Toast.LENGTH_LONG).show();*/

                for (int i = 0; i < 28; i++) {
                    DataModel md = new DataModel();
                    Log.d("mathew", "Entered into showing locations");
                    MarkerOptions markerOptions = new MarkerOptions();
                    //   HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
                    //  double lat = 12.359664;//Double.parseDouble(googlePlace.get("lat"));
                    //  double lng = 76.600152;//Double.parseDouble(googlePlace.get("lng"));
                    //   String placeName = googlePlace.get("place_name");
                    //   String vicinity = googlePlace.get("vicinity");
                    //      LatLng latLng = mModel.get(i).getmLocation();


                    markerOptions.position(mLoca.get(i));
                    markerOptions.title(mModel.get(i).getTitle());
                    //     markerOptions.title(placeName + " : " + vicinity);
                    mMap.addMarker(markerOptions);

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            for (int j = 0; j < mModel.size(); j++) {
                                if (marker.getTitle().equalsIgnoreCase(mModel.get(j).getTitle())) {
                                    mText.setText(mModel.get(j).getReview());
                                    mDetail.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }

                            Toast.makeText(MapsActivity.this, "position" + marker.getTitle(), Toast.LENGTH_LONG).show();
                            return false;
                        }
                    });

                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoca.get(i)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(50));
                }
            }
        });

       // setTab(tabName,latitude,longitude);
      //  init();
    }

    private void init() {
        Log.d("i", "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });


        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("i", "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

    }

    private void geoLocate(){
        Log.d("i", "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e("ei", "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d("I", "geoLocate: found a location: " + address.toString());
            Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void getDeviceLocation(){
        Log.d("i", "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(checkLocationPermission()){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d("i", "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        }else{
                            Log.d("i", "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e("i", "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d("i", "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }


    }







    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    mLastLocation = location;
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }

                    //Place current location marker
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    setTab(tabName,latitude,longitude);

                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str=addressList.get(0).getSubLocality()+":";
                        str+=addressList.get(0).getLocality()+":";
                        str+=addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    //markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mCurrLocationMarker = mMap.addMarker(markerOptions);


                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    Toast.makeText(MapsActivity.this,"Your Current Location", Toast.LENGTH_LONG).show();

                    Log.d("mathew", String.format("latitude:%.3f longitude:%.3f",latitude,longitude));

                    //stop location updates
                    if (mGoogleApiClient != null) {
                        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                        }
                        Log.d("mathew", "Removing Location Updates");
                    }
                }
            }

            ;
        };
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates( mLocationRequest, mLocationCallback,null);
        }


    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyDxc6yW914sE3mCRgjmdl1QXQzjUwVaQK8");
        Log.d("mathew", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("mathew", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("---lat",""+latitude+" long"+longitude);
        setTab(tabName,latitude,longitude);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String str=addressList.get(0).getSubLocality()+":";
            str+=addressList.get(0).getLocality()+":";
            str+=addressList.get(0).getCountryName();
            mMap.addMarker(new MarkerOptions().position(latLng).title(str));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        Toast.makeText(MapsActivity.this,"Your Current Location", Toast.LENGTH_LONG).show();

        Log.d("mathew", String.format("latitude:%.3f longitude:%.3f",latitude,longitude));

        //stop location updates
        if (mGoogleApiClient != null) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.requestLocationUpdates( mLocationRequest, mLocationCallback,null);
            }
            Log.d("mathew", "Removing Location Updates");
        }
        Log.d("mathew", "Exit");


    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
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

    public void setTab(String tab,double lat,double longtidue1){



        switch (tab){
            case "bone":
                //   double lat = Double.parseDouble(googlePlace.get("lat"));

                mMap.clear();
                String url = getUrl(lat,longtidue1,"hospital");
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);

                break;
            case "DBC":
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;
            case "gyno":
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;

            case "general":
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;
            case "neuro" :
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;
            case "derma" :
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;
            case "pedia" :
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;
            case "cardio" :
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;



            case "pathology":
                mMap.clear();

                for (int i = 0; i < 28; i++) {
                    DataModel md = new DataModel();
                    Log.d("mathew", "Entered into showing locations");
                    MarkerOptions markerOptions = new MarkerOptions();
                    //   HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
                    //  double lat = 12.359664;//Double.parseDouble(googlePlace.get("lat"));
                    //  double lng = 76.600152;//Double.parseDouble(googlePlace.get("lng"));
                    //   String placeName = googlePlace.get("place_name");
                    //   String vicinity = googlePlace.get("vicinity");
                    //      LatLng latLng = mModel.get(i).getmLocation();


                    markerOptions.position(mLoca.get(i));
                    markerOptions.title(mModel.get(i).getTitle());
                    //     markerOptions.title(placeName + " : " + vicinity);
                    mMap.addMarker(markerOptions);

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            for (int j = 0; j < mModel.size(); j++) {
                                if (marker.getTitle().equalsIgnoreCase(mModel.get(j).getTitle())) {
                                    mText.setText(mModel.get(j).getReview());
                                    mDetail.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }

                            Toast.makeText(MapsActivity.this, "position" + marker.getTitle(), Toast.LENGTH_LONG).show();
                            return false;
                        }
                    });

                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoca.get(i)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
                }
                break;
            case "pharmacy" :
                mMap.clear();
                url = getUrl(lat, longtidue1, "pharmacy");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(MapsActivity.this,"Nearby pharmacy", Toast.LENGTH_LONG).show();
break;

            case "ambulance" :
                mMap.clear();
                url = getUrl(lat, longtidue1, "hospital");
                DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("---onClick", url);
                getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                break;

        }
    }
}