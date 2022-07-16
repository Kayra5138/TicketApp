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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class EventList extends AppCompatActivity implements View.OnClickListener{


    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Event> list;
    private TextView yourTickets, artistTv, dateTv, priceTv;
    private FirebaseUser user;
    private DatabaseReference referenceEvents, referenceUsers;
    private String userID;
//    private TextView buyTicket1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        artistTv = (TextView) findViewById(R.id.artist);
        artistTv.setOnClickListener(this);
        dateTv = (TextView) findViewById(R.id.date);
        dateTv.setOnClickListener(this);
        priceTv = (TextView) findViewById(R.id.price);
        priceTv.setOnClickListener(this);

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
        artistTv.setText("Artist >");
        yourTickets = (TextView) findViewById(R.id.yourTickets);
        yourTickets.setOnClickListener(this);
    }


    boolean sortByArtist = true;
    boolean sortByDate = false;
    boolean sortByPrice = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.artist:
                sortByDate = false;
                sortByPrice = false;
                if(sortByArtist){
                    sortByArtist = false;
                    Collections.reverse(list);
                    artistTv.setText("Artist <");
                    dateTv.setText("Date");
                    priceTv.setText("Price");
                }else{
                    sortByArtist = true;
                    Collections.sort(list, new Comparator<Event>() {
                        @Override
                        public int compare(Event t1, Event t2) {
                            return t1.artist.compareToIgnoreCase(t2.artist);
                        }
                    });
                    artistTv.setText("Artist >");
                    dateTv.setText("Date");
                    priceTv.setText("Price");
                }
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.date:
                sortByArtist = false;
                sortByPrice = false;
                if(sortByDate){
                    sortByDate = false;
                    Collections.reverse(list);
                    dateTv.setText("Date <");
                    artistTv.setText("Artist");
                    priceTv.setText("Price");
                }else{
                    sortByDate = true;
                    Collections.sort(list, new Comparator<Event>() {
                        @Override
                        public int compare(Event t1, Event t2) {
                            return t1.date.compareToIgnoreCase(t2.date);
                        }
                    });
                    dateTv.setText("Date >");
                    artistTv.setText("Artist");
                    priceTv.setText("Price");
                }
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.price:
                sortByDate = false;
                sortByArtist = false;
                if(sortByPrice){
                    sortByPrice = false;
                    Collections.reverse(list);
                    priceTv.setText("Price <");
                    artistTv.setText("Artist");
                    dateTv.setText("Date");
                }else{
                    sortByPrice = true;
                    Collections.sort(list, new Comparator<Event>() {
                        @Override
                        public int compare(Event t1, Event t2) {
                            return t1.price.compareToIgnoreCase(t2.price);
                        }
                    });
                    priceTv.setText("Price >");
                    artistTv.setText("Artist");
                    dateTv.setText("Date");
                }
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.yourTickets:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }

}