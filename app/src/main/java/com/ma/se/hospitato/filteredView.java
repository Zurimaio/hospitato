package com.ma.se.hospitato;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class filteredView extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_view);

        databaseReference = FirebaseDatabase.getInstance().getReference("Hospitals");

        recyclerView = (RecyclerView) findViewById(R.id.request_HospitalsList);
        //Avoid unnecessary layout passes by setting setHasFixedSize to true
        recyclerView.setHasFixedSize(true);
        //Select the type of layout manager you would use for your recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    Hospital h = adSnapshot.getValue(Hospital.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    @Override
    protected void onStart(){
        super.onStart();


        FirebaseRecyclerAdapter<Hospital, RequestViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Hospital, RequestViewHolder>(
                Hospital.class,
                R.layout.first_element_filtered_list,
                RequestViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(RequestViewHolder viewHolder, Hospital model, int position) {
                final String filter= getIntent().getStringExtra("Department");
                final boolean t=model.getDepartments().get(filter);

                if(model.getDepartments().get(filter)) {
                    viewHolder.setDetails(model.getName(), model.getAddress());
                }
                else{
                    viewHolder.noDetails();
                }
            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public RequestViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(String Nameh, String Addressh ) {
            TextView name = (TextView) mView.findViewById(R.id.request_name);
            TextView address = (TextView) mView.findViewById(R.id.request_address);
            name.setText(Nameh);
            address.setText(Addressh);
        }

        public void noDetails() {

            TextView name = (TextView) mView.findViewById(R.id.request_name);
            TextView address = (TextView) mView.findViewById(R.id.request_address);
            TextView greenWaitingTime = (TextView) mView.findViewById(R.id.greenWaitingTime);
            TextView whiteWaitingTime = (TextView) mView.findViewById(R.id.WhiteWaitingTime);
            TextView estimatedTravelTime = (TextView) mView.findViewById(R.id.estimatedTravelTime);
            name.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            greenWaitingTime.setVisibility(View.GONE);
            whiteWaitingTime.setVisibility(View.GONE);
            estimatedTravelTime.setVisibility(View.GONE);
        }
    }

}