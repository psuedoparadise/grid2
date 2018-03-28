package com.example.qwinix.grid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ServiceproviderActivity extends AppCompatActivity {
    /*Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
    }*/
    Spinner spinner1;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText usrname;
    private EditText phno;
    private Button but1;
    private Button but2;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private EditText addr;
    DatabaseReference databaseUsers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner) findViewById(R.id.spinner1);

//
        databaseUsers = FirebaseDatabase.getInstance().getReference("serviceproviders");

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        usrname=(EditText) findViewById(R.id.usrname);
        phno=(EditText) findViewById(R.id.phno);
        addr = (EditText) findViewById(R.id.addr);

        but1 = (Button) findViewById(R.id.buttonsp);
        progressDialog = new ProgressDialog(this);



        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        //attaching listener to button
        init();
    }

    private void registerUser() {


        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = usrname.getText().toString().trim();
        String phn = phno.getText().toString().trim();
        String address = addr.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(name)) {
            //if the value is not given displaying a toast

            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phn)){
            Toast.makeText(this, "Please enter phone no", Toast.LENGTH_LONG).show();
            return;

        }

        if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please enter address", Toast.LENGTH_LONG).show();
            return;

        }



        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new serviceproviders
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(ServiceproviderActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();

                            addUser();
                            Intent toy2 = new Intent(ServiceproviderActivity.this, ServiceproviderActivity.class);

                            startActivity(toy2);

                        } else {
                            //display some message here
                            Toast.makeText(ServiceproviderActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();

                    }
                });

    }


    private void init() {
        but2 = (Button) findViewById(R.id.button);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(ServiceproviderActivity.this, firebase.class);
                startActivity(toy);
            }
        });
    }

    private void addUser() {

        String name = usrname.getText().toString().trim();
        String phn = phno.getText().toString().trim();
        String address = usrname.getText().toString().trim();
        String spinner =spinner1.getSelectedItem().toString().trim();
        String email = editTextEmail.getText().toString().trim();



        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phn) && !TextUtils.isEmpty(address)) {

            String id = databaseUsers.push().getKey();

            serviceproviders serviceproviders = new serviceproviders(name, phn, address,spinner,email);

            databaseUsers.child(id).setValue(serviceproviders);


            //  Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        }

//        else if(TextUtils.isEmpty(name)){
//            //if the value is not given displaying a toast
//
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        else {
//            Toast.makeText(this, "Please enter phone no", Toast.LENGTH_LONG).show();
//            return;
//
//
//        }



    }

}