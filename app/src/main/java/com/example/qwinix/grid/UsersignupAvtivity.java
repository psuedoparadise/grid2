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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



    public class UsersignupAvtivity extends AppCompatActivity {
        // public Button but1;
        private EditText editTextEmail;
        private EditText editTextPassword;
        private EditText usrname;
        private EditText phno;
        private Button but1;
        private FirebaseAuth firebaseAuth;
        private ProgressDialog progressDialog;

        DatabaseReference databaseUsers;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //init();
            databaseUsers = FirebaseDatabase.getInstance().getReference("user");
            firebaseAuth = FirebaseAuth.getInstance();


            editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            usrname = (EditText) findViewById(R.id.usrname);
            phno = (EditText) findViewById(R.id.phno);

            but1 = (Button) findViewById(R.id.but1);

            progressDialog = new ProgressDialog(this);

            //attaching listener to button
            // but1.setOnClickListener(this);
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerUser();
//                addUser();
//                Intent toy2 = new Intent(ServiceproviderActivity.this, Second.class);
//
//                startActivity(toy2);


                }
            });

        }

        private void registerUser() {

            //getting email and password from edit texts
       /* String username = usrname.getText().toString().trim();
        String phoneno = phno.getText().toString().trim();*/
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String name = usrname.getText().toString().trim();
            String phn = phno.getText().toString().trim();

            //checking if email and passwords are empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                //if the value is not given displaying a toast

                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(phn)) {
                Toast.makeText(this, "Please enter phone no", Toast.LENGTH_LONG).show();
                return;

            }

            //if the email and password are not empty
            //displaying a progress dialog

            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                //display some message here
                                Toast.makeText(getApplication(), "Successfully registered", Toast.LENGTH_LONG).show();
                            } else {
                                //display some message here
                                Toast.makeText(getApplication(), "Registration Error", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                            addUser();
                            Intent toy2 = new Intent(getApplication(), MainActivity.class);

                            startActivity(toy2);
                        }
                    });

        }

        private void addUser() {

            String name = usrname.getText().toString().trim();
            String phn = phno.getText().toString().trim();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phn)) {

                String id = databaseUsers.push().getKey();

                user user = new user(name, phn);

                databaseUsers.child(id).setValue(user);


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
