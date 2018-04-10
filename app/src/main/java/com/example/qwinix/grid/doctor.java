package com.example.qwinix.grid;



        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.content.SharedPreferences;

        import static android.content.Context.MODE_PRIVATE;
        import static com.example.qwinix.grid.R.layout.doctor;


/**
 * Created by qwinix on 13/2/18.
 */

public class doctor extends Fragment {
    public Button button1;
    public Button button2;
    public Button button3;
    SharedPreferences mPref;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mPref = this.getActivity().getSharedPreferences("navigation", Context.MODE_PRIVATE);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView=inflater.inflate(doctor,container,false);
        button1 = (Button) rootView.findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toy = new Intent(getActivity(), androidgridlayoutactivity.class);
                mPref.edit().putString("categoryval","allopathy").commit();

                startActivity(toy);

            }
        });
        button2 = (Button) rootView.findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(getActivity(), MapsActivity.class);
                toy.putExtra("KEY_BONE","doctor1");
                mPref.edit().putString("categoryval","ayurvedic").commit();


                startActivity(toy);

            }
        });


        button3 = (Button) rootView.findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(getActivity(), MapsActivity.class);
                toy.putExtra("KEY_BONE","doctor2");
                mPref.edit().putString("categoryval","homeopathy").commit();
                startActivity(toy);

            }
        });











        return rootView;
    }


}

