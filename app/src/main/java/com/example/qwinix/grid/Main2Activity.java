package com.example.qwinix.grid;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Main2Activity extends AppCompatActivity {
    public Button but1;
    public Button button2;
    public Button button3;
    //private FirebaseAnalytics mFirebaseAnalytics;


    public void init() {
        but1 = (Button) findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(Main2Activity.this, ServiceproviderActivity.class);
                startActivity(toy);

            }
        });
    }
    public void init1() {
        button2= (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy2 = new Intent(Main2Activity.this,UsersignupAvtivity.class);
                startActivity(toy2);

            }
        });
    }
    public void init2() {
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy3 = new Intent(Main2Activity.this, ServiceproviderActivity.class);

                startActivity(toy3);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
        init1();
        init2();

    }
}