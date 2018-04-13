package com.example.qwinix.grid;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {
    public Button login;
    public Button user;
    public Button spsignup;
    public EditText Email;
    public EditText Password;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.but1);
        user= (Button) findViewById(R.id.button2);
        spsignup = (Button) findViewById(R.id.button3);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                        "\\@" +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                        "(" +

                        "\\." +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                        ")+";


                String email = Email.getText().toString();

                Matcher matcher= Pattern.compile(validemail).matcher(email);


                if (matcher.matches()){
                    //Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_LONG).show();


                }
                else {
                    //Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
                }


                Validate(Email.getText().toString(), Password.getText().toString());


                //Intent toy = new Intent(MainActivity.this, NavigateActivity.class);
                //startActivity(toy);

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy1 = new Intent(Main2Activity.this, UsersignupAvtivity.class);
                startActivity(toy1);

            }
        });


        spsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy1 = new Intent(Main2Activity.this, ServiceproviderActivity.class);
                startActivity(toy1);

            }
        });

    }
    private void Validate(String Email, String Password) {
        if (TextUtils.isEmpty(Email)) {

            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("verifing");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Main2Activity.this, "LoginSuccessful", Toast.LENGTH_SHORT).show();
                    Intent toy = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(toy);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(Main2Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}