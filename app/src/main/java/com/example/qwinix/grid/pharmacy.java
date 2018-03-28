package com.example.qwinix.grid;

/**
 * Created by qwinix on 16/2/18.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by qwinix on 13/2/18.
 */

public class pharmacy extends Fragment {
    public Button button1;
    public Button button2;
    public Button button3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView= inflater.inflate(R.layout.pharmacy, container, false);
        Intent toy2 = new Intent(getActivity(), MapsActivity.class);
        toy2.putExtra("KEY_BONE","pharmacy");
        startActivity(toy2);

        return rootView;
    }



}
