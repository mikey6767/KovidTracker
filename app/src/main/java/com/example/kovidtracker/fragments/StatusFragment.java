package com.example.kovidtracker.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kovidtracker.History;
import com.example.kovidtracker.HistoryListViewHolder;
import com.example.kovidtracker.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class StatusFragment extends Fragment {

    // Initialize firebase
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    FirebaseUser currentUser = fAuth.getCurrentUser();
    private String userId = currentUser.getUid();

    TextView tv_name,tv_ic,tv_risk;
    TextView tv_fBrand, tv_fDate;
    TextView tv_sBrand, tv_sDate;

    String name, ic;
    int status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_status, container, false);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        tv_ic = view.findViewById(R.id.tv_ic);
        tv_name = view.findViewById(R.id.tv_name);
        tv_risk = view.findViewById(R.id.tv_risk);
        tv_fBrand = view.findViewById(R.id.tv_fbrand);
        tv_fDate = view.findViewById(R.id.tv_fdate);
        tv_sBrand = view.findViewById(R.id.tv_sbrand);
        tv_sDate = view.findViewById(R.id.tv_sdate);

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    name = value.getString("fName");
                    ic = value.getString("IC");
                    status = value.getLong("healthStatus").intValue();

                    tv_ic.setText(String.valueOf(ic));
                    tv_name.setText(String.valueOf(name));
                    String highRisk = "High Risk";
                    String lowRisk = "Low Risk";
                    if(status > 10){
                        tv_risk.setText(highRisk);
                    }else{
                        tv_risk.setText(lowRisk);
                    }

                }else{
                    Log.e("Value not exists", userId);
                }
            }
        });



        DocumentReference documentReferenceDose = fStore.collection("users").document(userId).collection("Dose").document("FirstDose");
        documentReferenceDose.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {

                    tv_fBrand.setText(value.getString("DoseBrand"));
                    tv_fDate.setText(value.getString("DoseDate"));
                }else{
                    tv_fBrand.setText("Null");
                    tv_fDate.setText("Null");
                }
            }
        });

        DocumentReference documentReferenceDose2 = fStore.collection("users").document(userId).collection("Dose").document("SecondDose");
        documentReferenceDose2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {

                    tv_sBrand.setText(value.getString("DoseBrand"));
                    tv_sDate.setText(value.getString("DoseDate"));

                }else{
                    tv_sBrand.setText("Null");
                    tv_sDate.setText("Null");
                }
            }
        });

    }
}