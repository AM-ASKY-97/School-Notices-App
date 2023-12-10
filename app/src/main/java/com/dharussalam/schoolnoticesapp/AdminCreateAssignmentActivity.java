package com.dharussalam.schoolnoticesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

public class AdminCreateAssignmentActivity extends AppCompatActivity {

    ImageView imagebrowser, filecancel, imageupload, filelogo;

    Uri filepath;

    Button btnSave, btnBack;
    EditText edTittle, edQuiz, edDue;

    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_assignment);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("assigments");

        edTittle = (EditText) findViewById(R.id.edTittle);
        edQuiz = (EditText) findViewById(R.id.edQuiz);
        edDue = (EditText) findViewById(R.id.edDue);

        imagebrowser = (ImageView) findViewById(R.id.imagebrowse);
        filelogo = (ImageView) findViewById(R.id.filelogo);
        filecancel = (ImageView) findViewById(R.id.filecancel);

        filecancel.setVisibility(View.INVISIBLE);
        filelogo.setVisibility(View.INVISIBLE);

        filecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filecancel.setVisibility(View.INVISIBLE);
                filelogo.setVisibility(View.INVISIBLE);
                imagebrowser.setVisibility(View.VISIBLE);
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eTittle = edTittle.getText().toString().trim();
                String eDue= edDue.getText().toString().trim();
                String eQuiz = edQuiz.getText().toString().trim();

                if(TextUtils.isEmpty(eTittle) || TextUtils.isEmpty(eDue) || TextUtils.isEmpty(eQuiz))
                {
                    AlertUtils.showAlert(AdminCreateAssignmentActivity.this, "Alert", "All filed is required.");
                }
                else {
                    processupload(filepath);
                }
            }
        });

        imagebrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "select pdf files"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK)
        {
            filepath = data.getData();
            filecancel.setVisibility(View.VISIBLE);
            filelogo.setVisibility(View.VISIBLE);
            imagebrowser.setVisibility(View.INVISIBLE);
        }
    }

    public void processupload(Uri filepath)
    {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploading....");
        pd.show();

        final  StorageReference reference = storageReference.child("uploads/" + System.currentTimeMillis()+".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fileinfomodel obj =new fileinfomodel(edTittle.getText().toString(), edQuiz.getText().toString(), edDue.getText().toString(), uri.toString());
                                databaseReference.child(databaseReference.push().getKey()).setValue(obj);

                                pd.dismiss();
                                AlertUtils.showSuccessAlert(AdminCreateAssignmentActivity.this, findViewById(android.R.id.content), "Success! Data saved successfully.");
                                filecancel.setVisibility(View.INVISIBLE);
                                filelogo.setVisibility(View.INVISIBLE);
                                imagebrowser.setVisibility(View.VISIBLE);
                                clearAll();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("uploaded : "+(int)percent+"%");
                    }
                });
    }

    private void clearAll()
    {
        edTittle.setText("");
        edDue.setText("");
        edQuiz.setText("");
    }
}