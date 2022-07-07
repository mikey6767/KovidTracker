package com.example.kovidtracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kovidtracker.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mName,mEmail,mPassword,mPhone, mIC;
    Button mRegisterBtn;
    TextView mBackBtn;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mName   = findViewById(R.id.et_registerFullName);
        mIC   = findViewById(R.id.et_registerIC);
        mPhone   = findViewById(R.id.et_registerPhoneNumber);
        mEmail   = findViewById(R.id.et_registerEmail);
        mPassword   = findViewById(R.id.et_registerPassword);
        mRegisterBtn = findViewById(R.id.btn_register);
        mBackBtn = findViewById(R.id.btn_backlogin);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        auth.getInstance().signOut();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mName.getText().toString();
                final String phone    = mPhone.getText().toString();
                final String IC    = mIC.getText().toString();


                if(TextUtils.isEmpty(fullName)){
                    mName.setError("Name is Required!");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone is Required!");
                    return;
                }

                if(TextUtils.isEmpty(IC)){
                    mIC.setError("IC is Required!");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required!");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be More Than Characters");
                    return;
                }


                // register the user in firebase

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = auth.getCurrentUser().getUid();
                            DocumentReference documentReferenceUser = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("IC",IC);
                            user.put("healthStatus", 0);
                            documentReferenceUser.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            String fDose= "1Dose";
                            DocumentReference documentReferenceDose1 = fStore.collection("users").document(userID).collection("Dose").document(fDose);
                            Map<String, Object> thisUserDose1 = new HashMap<>();
                            thisUserDose1.put("brand", "Pfizer");
                            thisUserDose1.put("batch", "B001202010");
                            thisUserDose1.put("facility", "CLINIC NULL");
                            thisUserDose1.put("date", "2020-10-01");
                            documentReferenceDose1.set(thisUserDose1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "on Success: 1dose created for" + userID );
                                }
                            });
                            String sDose= "2Dose";
                            DocumentReference documentReferenceDose2 = fStore.collection("users").document(userID).collection("Dose").document(sDose);
                            Map<String, Object> thisUserDose2 = new HashMap<>();
                            thisUserDose2.put("brand", "Pfizer");
                            thisUserDose2.put("batch", "B001202010");
                            thisUserDose2.put("facility", "CLINIC NULL");
                            thisUserDose2.put("date", "2020-10-01");
                            documentReferenceDose2.set(thisUserDose2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "on Success: 2dose is created for" + userID );
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();

                        }else {
                            Toast.makeText(SignupActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }
}
