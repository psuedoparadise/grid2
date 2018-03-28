package com.example.qwinix.grid;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


        private static final int PICK_IMAGE_REQUEST = 234;
        private Button buttonChoose, buttonUpload;
        private ImageView imageView;
        private Uri filePath;

        private StorageReference storageReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.firebasestorage);

            storageReference = FirebaseStorage.getInstance().getReference();

            imageView = (ImageView) findViewById(R.id.imageView);
            buttonChoose = (Button) findViewById(R.id.buttonChoose);
            buttonUpload = (Button) findViewById(R.id.buttonUpload);

            buttonChoose.setOnClickListener(this);
            buttonUpload.setOnClickListener(this);
        }
        private  void showFileChooser(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select an Image"), PICK_IMAGE_REQUEST);
        }

        private void uploadFile() {
            if (filePath != null) {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                StorageReference riversRef = storageReference.child("images/profile.jpg");

                riversRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"File Uploaded", Toast.LENGTH_LONG).show();
                                // Get a URL to the uploaded content
                                // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),exception.getMessage(), Toast.LENGTH_LONG).show();
                                // Handle unsuccessful uploads
                                // ...
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage(((int) progress) + "% Uploaded..");
                            }
                        });
                ;
            } else {

            }
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onClick(View view) {
            //if the clicked button is choose
            if (view == buttonChoose) {
                showFileChooser();
            }
            //if the clicked button is upload
            else if (view == buttonUpload) {
                uploadFile();
            }
        }
    }

