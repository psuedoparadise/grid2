package com.example.qwinix.grid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;


public class androidgridlayoutactivity extends Activity {
    SharedPreferences mPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        mPref=getSharedPreferences("navigation",MODE_PRIVATE);
        //LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linear);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
              /*.d("---i","pos"+position);
                Intent intent;
                intent =  new Intent(androidgridlayoutactivity.this,MapsActivity.class);
                Log.d("---i","pos");
                intent.putExtra("KEY_BONE","bone");
                startActivity(intent);*/
        // Sending image id to FullScreenActivity


                 Intent intent=null ;

                // passing array index

                switch(position)
                {
                    case 0:
                        intent= new Intent(v.getContext(),MapsActivity.class);
                        Log.d("---i","pos");
                        intent.putExtra("KEY_BONE","bone");
                        mPref.edit().putString("gridval","orthopaedic").commit();
                        startActivity(intent);

                        break;

                    case 1:
                        intent =  new Intent(v.getContext(), MapsActivity.class);
                        Log.d("---i","pos1");
                        intent.putExtra("KEY_BONE","DBC");
                        mPref.edit().putString("gridval","diabetician").commit();
                        startActivity(intent);

                        break;
                    case 2:
                        intent =  new Intent(v.getContext(), MapsActivity.class);
                        Log.d("---i","pos2");
                        intent.putExtra("KEY_BONE","gyno");
                        mPref.edit().putString("gridval","gynocologist").commit();
                        startActivity(intent);
                        break;
                    case 3:
                        intent =  new Intent(v.getContext(), MapsActivity.class);
                        Log.d("---i","pos3");
                        intent.putExtra("KEY_BONE","general");
                        mPref.edit().putString("gridval","general").commit();
                        startActivity(intent);
                        break;
                    case 4:
                        intent =  new Intent(v.getContext(), MapsActivity.class);
                        Log.d("---i","pos4");
                        intent.putExtra("KEY_BONE","neuro");
                        mPref.edit().putString("gridval","neurology").commit();
                        startActivity(intent);
                        break;
                    case 5:
                        intent =  new Intent(v.getContext(), MapsActivity.class);
                        Log.d("---i","pos5");
                        intent.putExtra("KEY_BONE","derma");
                        mPref.edit().putString("gridval","dermatalogy").commit();
                        startActivity(intent);

                        break;
                    case 6:
                        intent =  new Intent(v.getContext(), MapsActivity.class);
                        Log.d("---i","pos6");
                        intent.putExtra("KEY_BONE","pedia");
                        mPref.edit().putString("gridval","pediatrician").commit();
                        startActivity(intent);

                        break;
                    case 7:
                        intent =  new Intent(v.getContext(), MapsActivity.class);
                        Log.d("---i","pos7");
                        intent.putExtra("KEY_BONE","cardio");
                        mPref.edit().putString("gridval","cardiology").commit();
                        startActivity(intent);

                        break;







                }


            }
        });


    }
}














