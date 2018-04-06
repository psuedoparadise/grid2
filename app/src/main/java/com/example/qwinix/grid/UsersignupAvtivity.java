package com.example.qwinix.grid;


import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UsersignupAvtivity extends AppCompatActivity {
    // public Button but1;
    private EditText Email;
    private EditText Password;
    private EditText usrname;
    private EditText phno;
    private Button but1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersignup);
        //init();
        databaseUsers = FirebaseDatabase.getInstance().getReference("UserInformation");
        firebaseAuth = FirebaseAuth.getInstance();


        Email = (EditText) findViewById(R.id.editTextEmail);
        Password = (EditText) findViewById(R.id.editTextPassword);
        usrname=(EditText) findViewById(R.id.usrname);
        phno=(EditText) findViewById(R.id.phno);

        but1 = (Button) findViewById(R.id.but1);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        // but1.setOnClickListener(this);
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


                String email = Email.getText().toString();

                Matcher matcher= Pattern.compile(validemail).matcher(email);


                if (matcher.matches()){
                    //Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_LONG).show();
                    //return true;
                }
                else {
                    //Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
                    //return false;
                }

                String phn = phno.getText().toString().trim();

                //   Matcher matcher1= Pattern.compile(validnum).matcher(phn);
                if(PhoneNumberUtils.isGlobalPhoneNumber(phn)){

                    registerUser();

                }else{
                    Toast.makeText(UsersignupAvtivity.this,"InvalidUser",Toast.LENGTH_LONG).show();
                }
//                addUser();
//                Intent toy2 = new Intent(MainActivity.this, Second.class);
//
//                startActivity(toy2);
            }
        });

    }

    private void registerUser() {

        //getting email and password from edit texts
       /* String username = usrname.getText().toString().trim();
        String phoneno = phno.getText().toString().trim();*/
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
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
        if(TextUtils.isEmpty(name)) {
            //if the value is not given displaying a toast

            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(phn)){
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
                            Toast.makeText(UsersignupAvtivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            addUser();
                            Intent toy2 = new Intent(UsersignupAvtivity.this, MainActivity.class);

                            startActivity(toy2);
                        } else {
                            //display some message here
                            Toast.makeText(UsersignupAvtivity.this, "Registration Error", Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();

                    }
                });

    }

    private void addUser() {

        String name = usrname.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String phn = phno.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phn)) {

            String id = databaseUsers.push().getKey();

            UserInformation user = new UserInformation(name, email, password, phn);

            databaseUsers.child(id).setValue(user);

        }

    }

    public boolean getValidMobile(String mobileNo,String validPattern){

        Matcher matcher1= Pattern.compile(validPattern).matcher(mobileNo);
        if(matcher1.matches()){
            return true;
        }else {
            return false;
        }
    }

    public boolean getValidLandline(String mobileNo,String validPattern){

        Matcher matcher1= Pattern.compile(validPattern).matcher(mobileNo);
        if(matcher1.matches()){
            return true;
        }else {
            return false;
        }
    }



    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 9 || phone.length() > 13) {
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