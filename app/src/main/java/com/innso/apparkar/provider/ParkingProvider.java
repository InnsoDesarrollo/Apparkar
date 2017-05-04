package com.innso.apparkar.provider;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innso.apparkar.R;
import com.innso.apparkar.api.models.Parking;

import java.util.ArrayList;

import io.reactivex.subjects.BehaviorSubject;


public class ParkingProvider implements ValueEventListener {

    private BehaviorSubject<ArrayList<Parking>> parkingSlots = BehaviorSubject.create();

    private Context context;

    public ParkingProvider(Context context, FirebaseDatabase firebaseDatabase) {
        this.context = context;
        DatabaseReference ref = firebaseDatabase.getReference(context.getString(R.string.channel_firebase_parking));
        ref.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<Parking> parkingSlots = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            parkingSlots.add(child.getValue(Parking.class));
        }
        this.parkingSlots.onNext(parkingSlots);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
