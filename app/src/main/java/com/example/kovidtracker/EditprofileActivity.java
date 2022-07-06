package com.example.kovidtracker;

import static android.text.InputType.TYPE_NULL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class EditprofileActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText name,email,IC,phone, password, confirmpassword;
    ImageView profileImageView;
    Button updateprofileBtn, updatePasswordBtn, backFragmentbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Intent data = getIntent();
        final String Aname = data.getStringExtra("name");
        String Aemail = data.getStringExtra("email");
        String Aphone = data.getStringExtra("phone");
        String AIC = data.getStringExtra("IC");





        name =(EditText) findViewById(R.id.et_profileName);
        IC =(EditText) findViewById(R.id.et_profileIC);
        email =(EditText) findViewById(R.id.et_profileemail);
        phone =(EditText) findViewById(R.id.et_profilephonenumber);
        profileImageView = (ImageView) findViewById(R.id.img_profilePictureChange);
        updateprofileBtn =(Button)findViewById(R.id.btn_updateprofile);
        updatePasswordBtn =(Button)findViewById(R.id.btn_updateprofilepassword);
        backFragmentbtn =(Button)findViewById(R.id.btn_backprofilefrag);
        password =(EditText) findViewById(R.id.et_profilepassword);
        confirmpassword =(EditText) findViewById(R.id.et_profileconfirmpassword);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();


        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    phone.setText(documentSnapshot.getString("phone"));
                    name.setText(documentSnapshot.getString("name"));
                    email.setText(documentSnapshot.getString("email"));
                    IC.setText(documentSnapshot.getString("IC"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        updateprofileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cname = name.getText().toString().trim();
                final String cemail = email.getText().toString().trim();
                final String cic = IC.getText().toString().trim();
                final String cphone = phone.getText().toString().trim();

                if(TextUtils.isEmpty(cname)){
                    name.setError("Name is Required.");
                    return;
                }

                if(TextUtils.isEmpty(cemail)){
                    email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(cphone)){
                    phone.setError("Phone number is Required.");
                    return;
                }

                if(TextUtils.isEmpty(cic)){
                    IC.setError("Identify Card Number is Required.");
                    return;
                }

                final String femail = email.getText().toString();
                user.updateEmail(femail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",cemail);
                        edited.put("name",cname);
                        edited.put("phone",cphone);
                        edited.put("IC",cic);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditprofileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditprofileActivity.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        backFragmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetPassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Change New Password?");
                passwordResetDialog.setMessage("Enter New Password. Min. 6 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditprofileActivity.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditprofileActivity.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();

            }
        });

        email.setText(Aemail);
        name.setText(Aname);
        IC.setText(AIC);
        phone.setText(Aphone);

        Log.d(TAG, "onCreate: " + Aname + " " + Aemail + " " + Aphone + " " + AIC);





//        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String cpassword = password.getText().toString().trim();
//                String cconfirmpassword = confirmpassword.getText().toString().trim();
//
//                if(TextUtils.isEmpty(cpassword)){
//                    password.setError("Password is Required.");
//                    return;
//                }
//
//                if(TextUtils.isEmpty(cconfirmpassword)){
//                    confirmpassword.setError("Password is Required.");
//                    return;
//                }
//
//                if(password.length() < 6){
//                    password.setError("Password Must be >= 6 Characters");
//                    return;
//                }
//
//                if (!cpassword.equals(cconfirmpassword)) {
//                    Toast.makeText(EditprofileActivity.this, "Password Do Not Match!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                    user.updatePassword(cpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(EditprofileActivity.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(EditprofileActivity.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}