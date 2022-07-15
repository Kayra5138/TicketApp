package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EventList extends AppCompatActivity implements View.OnClickListener{


    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Event> list;
    private TextView yourTickets;
    private FirebaseUser user;
    private DatabaseReference referenceEvents, referenceUsers;
    private String userID;
//    private TextView buyTicket1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView = findViewById(R.id.eventsRecycler);
        referenceEvents = FirebaseDatabase.getInstance().getReference("Events");
        referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        referenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Event event = dataSnapshot.getValue(Event.class);
                    list.add(event);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        yourTickets = (TextView) findViewById(R.id.yourTickets);
        yourTickets.setOnClickListener(this);
    }


//        buyTicket1 = (TextView) findViewById(R.id.buyTicket1);
//        buyTicket1.setOnClickListener(this);

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yourTickets:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
//            case R.id.buyTicket1:
//                buyTicket1();
        }
    }

//    private void buyTicket1() {
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        userID = user.getUid();
//
//
//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//
//                if(userProfile != null){
//                    String ticket1 = userProfile.ticket1;
//                    String ticket2 = userProfile.ticket2;
//                    String ticket3 = userProfile.ticket3;
//                    String ticket4 = userProfile.ticket4;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(EventList.this, "Something wrong happened.", Toast.LENGTH_LONG).show();
//            }
//        });

//        HashMap hashMap1 = new HashMap();
//        hashMap1.put("ticket1","ceza");
//        reference.child(userID).updateChildren(hashMap1);






//    }
}