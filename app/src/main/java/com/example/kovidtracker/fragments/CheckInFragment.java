package com.example.kovidtracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kovidtracker.History;
import com.example.kovidtracker.HistoryListViewHolder;
import com.example.kovidtracker.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CheckInFragment extends Fragment {

    ImageButton btn_checkIn;
    private RecyclerView recyclerView;
    // Initialize firebase
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    // Get current user instance
    FirebaseUser currentUser = fAuth.getCurrentUser();
    private String userId = currentUser.getUid();
    CollectionReference collectionReference = fStore.collection("users").document(userId).collection("CheckIn");
    FirestoreRecyclerOptions<History> historyOptions;
    FirestoreRecyclerAdapter<History, HistoryListViewHolder> historyAdapter;

    Query query = fStore.collection("users").document(userId).collection("CheckIn").orderBy("date", Query.Direction.DESCENDING).limit(5);

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkin, container, false);

        collectionReference.get();

        recyclerView = view.findViewById(R.id.recyclerview);
        // Linear Vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        RetrieveData();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_checkIn = view.findViewById(R.id.btn_qr);

        btn_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trigger QRCode Scanner Using ZXing
                // ZXing library codes
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(CheckInFragment.this);
                //set the properties of the scan
                //integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setOrientationLocked(true);

                Log.e("Scan*******", "Clicked");

                // initiate teh scan action
                integrator.initiateScan();

            }
        });
    }

    private void RetrieveData() {
        historyOptions = new FirestoreRecyclerOptions.Builder<History>().setQuery(query, History.class).build();
        historyAdapter = new FirestoreRecyclerAdapter<History, HistoryListViewHolder>(historyOptions){
            @Override
            public void onBindViewHolder(HistoryListViewHolder holder, int position, History model) {

                holder.location.setText(model.getLocation());
                holder.date.setText(model.getDate());
                Log.e("ScanResult*******", model.getLocation());
                Log.e("ScanResult*******", model.getDate());
            }

            @Override
            public HistoryListViewHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.recycler_row_history, group, false);

                return new HistoryListViewHolder(view);
            }
        };
        historyAdapter.startListening();

        recyclerView.setAdapter(historyAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // ZXing
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) { //not successful
                Log.e("Scan*******", "Cancelled scan");
                Log.d("Scan*******", "ScanFailed");
                Toast.makeText(this.getContext(), "Cancelled scan", Toast.LENGTH_LONG).show();
            }
            else {
                // Scan is successful and the QR code is decoded into a location name
                Log.d("Scan*******", "ScanSuccess");
                String scan_location = result.getContents();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String scan_date = now.format(format);
                Log.e("Tag", scan_date);

                Map<String, Object> thisUserCheckIn = new HashMap<>();
                thisUserCheckIn.put("location", scan_location);
                thisUserCheckIn.put("date", scan_date);


                DocumentReference documentReference = fStore.collection("users").document(userId).collection("CheckIn").document(scan_date);

                collectionReference.add(thisUserCheckIn);
            }
        }
    }
}


