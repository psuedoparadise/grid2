package com.example.qwinix.grid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by qwinix on 13/2/18.
 */

public class pathology extends Fragment {
    @Nullable
    /*private GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pathology,container,false);
        map= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync((OnMapReadyCallback)this);
        return view;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.pathology, container, false);
           Intent toy2 = new Intent(getActivity(), MapsActivity.class);

        startActivity(toy2);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("pathology");
    }
}


