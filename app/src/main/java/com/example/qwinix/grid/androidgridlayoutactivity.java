package com.example.qwinix.grid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;


public class androidgridlayoutactivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        //LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linear);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity

                 Intent intent=null ;
                // passing array index

                switch(position)
                {
                    case 0:
                        intent =  new Intent(getApplicationContext(),MapsActivity.class);
                        break;

                    case 1:
                        intent =  new Intent(getApplicationContext(), MapsActivity.class);
                        break;
                    case 2:
                        intent =  new Intent(getApplicationContext(), MapsActivity.class);
                        break;
                    case 3:
                        intent =  new Intent(getApplicationContext(), MapsActivity.class);
                        break;
                    case 4:
                        intent =  new Intent(getApplicationContext(), MapsActivity.class);
                        break;
                    case 5:
                        intent =  new Intent(getApplicationContext(), MapsActivity.class);
                        break;
                    case 6:
                        intent =  new Intent(getApplicationContext(), MapsActivity.class);
                        break;
                    case 7:
                        intent =  new Intent(getApplicationContext(), MapsActivity.class);
                        break;







                }
                startActivity(intent);
            }
        });


    }
}














