package com.example.qwinix.grid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ServiceproviderActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_serviceprovider);

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        databaseUsers = FirebaseDatabase.getInstance().getReference("ServiceProviderInformation");

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail1);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword1);
        usrname=(EditText) findViewById(R.id.spname);
        phno=(EditText) findViewById(R.id.spphno);
        addr = (EditText) findViewById(R.id.spaddress);

        but1 = (Button) findViewById(R.id.buttonsp);
        progressDialog = new ProgressDialog(this);


        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                        "\\@" +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                        "(" +

                        "\\." +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                        ")+";


                String email = editTextEmail.getText().toString();

                Matcher matcher= Pattern.compile(validemail).matcher(email);


                if (matcher.matches()){
                    //Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_LONG).show();
                    //return true;
                }
                else {
                    //Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
                    //return false;
                }
                String phn = phno.getText().toString();

                if(isValidMobile(phn)){

                    registerUser();

                }else{
                    Toast.makeText(ServiceproviderActivity.this,"InvalidUser",Toast.LENGTH_LONG).show();
                }


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
                .addOnCompleteListener(ServiceproviderActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(ServiceproviderActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            addUser();
                            Intent toy2 = new Intent(ServiceproviderActivity.this, Main2Activity.class);
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
                Intent toy = new Intent(ServiceproviderActivity.this, Firebase.class);
                startActivity(toy);
            }
        });
    }

    private void addUser() {

        String Username = usrname.getText().toString().trim();
        String Phonenumber = phno.getText().toString().trim();
        String Address = usrname.getText().toString().trim();
        String Spinner =spinner1.getSelectedItem().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(Username) && !TextUtils.isEmpty(Phonenumber) && !TextUtils.isEmpty(Address)) {

            String id = databaseUsers.push().getKey();

            ServiceProviderInformation serviceproviders = new ServiceProviderInformation(Username, Address,Email, Password, Phonenumber, Spinner );

            databaseUsers.child(id).setValue(serviceproviders);
        }
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 11 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }
}