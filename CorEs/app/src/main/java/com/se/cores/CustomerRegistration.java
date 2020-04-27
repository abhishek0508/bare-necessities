package com.se.cores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;


import java.io.IOException;
import java.util.List;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.squareup.picasso.Picasso;


public class CustomerRegistration extends AppCompatActivity {
    ImageButton imageCustomerReg;

    EditText nameCustomerReg,emailCustomerReg,passwordCustomerReg,mobileNumCustomerReg;
    Button saveButtonCR;
    CustomerBuilder customerBuilderObject;
    Customer customerObject;

    ImageButton customerImage;
    Bitmap uploadedCustomerImage;

    String imageDbURL;

    private static final int PICK_IMAGE = 1000;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        nameCustomerReg = (EditText)findViewById(R.id.nameCustomerReg);
        emailCustomerReg = (EditText)findViewById(R.id.emailCustomerReg);
        passwordCustomerReg = (EditText)findViewById(R.id.passwordCustomerReg);
        mobileNumCustomerReg = (EditText)findViewById(R.id.mobileNumCustomerReg);


        saveButtonCR = (Button)findViewById(R.id.saveButtonCR);
        customerImage = findViewById(R.id.imageCustomerReg);
        storageReference = FirebaseStorage.getInstance().getReference();

        customerBuilderObject = new CustomerBuilder();

        requestMultiplePermissions();
        customerImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        saveButtonCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameCustomerReg.getText().toString().trim();
                String email = emailCustomerReg.getText().toString().trim();
                String password = passwordCustomerReg.getText().toString().trim();
                String phoneNumber = mobileNumCustomerReg.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty())
                {
                    TextView view = (TextView) findViewById(R.id.errorMessage);
                    view.setVisibility(View.VISIBLE);
                }
                else
                {
                    customerBuilderObject.setName(name);
                    customerBuilderObject.setEmail(email);
                    customerBuilderObject.setPassword(password);
                    customerBuilderObject.setPhoneNumber(phoneNumber);
                    customerBuilderObject.setImage_url(imageDbURL);
                    Log.d("URL"," image firstore URL "+imageDbURL);

                    customerObject = customerBuilderObject.build();

                    DatabaseAdapter db = new DatabaseAdapter();
                    db.addNewCustomer(customerObject);

                    // Go to app home screen
                    Intent home = new Intent(CustomerRegistration.this, CustomerRegistration.class);  // go to screen 4 + 5 (home, customer logged in)
                    startActivity(home);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                Uri contentURI = data.getData();
                customerImage.setImageURI(contentURI);
                uploadImageToFirebase(contentURI);
            }
        }
    }



    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        report.areAllPermissionsGranted();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        //  Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void uploadImageToFirebase(Uri contentURI)
    {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(); //storage.getReference();
        StorageReference fileRef = storageRef.child("profile1.jpg");
        UploadTask uploadTask = fileRef.putFile(contentURI);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful())
                {
                    throw task.getException();
                }
                return  fileRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful())
                {
                    Uri downloadUri = task.getResult();
                    imageDbURL=downloadUri.toString();
                }
            }
        });

    }
}
