package com.example.qwinix.grid;

import  android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by qwinix on 5/3/18.
 */

public class ambulance extends Fragment {
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.ambulance, container, false);
        Intent toy2 = new Intent(getActivity(), MapsActivity.class);
        toy2.putExtra("KEY_BONE","ambulance");

        startActivity(toy2);

        return rootView;
    }



}
