package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logOut;

    private TextView buyTickets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buyTickets = (TextView) findViewById(R.id.buyTickets);
        buyTickets.setOnClickListener(this);

        logOut = (Button) findViewById(R.id.signOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();



        final TextView welcomeTextView = (TextView) findViewById(R.id.welcome);
        final TextView yourTicket1TextView = (TextView) findViewById(R.id.yourTicket1);
        final TextView yourTicket2TextView = (TextView) findViewById(R.id.yourTicket2);
        final TextView yourTicket3TextView = (TextView) findViewById(R.id.yourTicket3);
        final TextView yourTicket4TextView = (TextView) findViewById(R.id.yourTicket4);



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String ticket1 = userProfile.ticket1;
                    String ticket2 = userProfile.ticket2;
                    String ticket3 = userProfile.ticket3;
                    String ticket4 = userProfile.ticket4;

                    welcomeTextView.setText("Welcome "+fullName+".");

                    // deneme
                    yourTicket1TextView.setText(ticket1);
                    yourTicket2TextView.setText(ticket2);
                    yourTicket3TextView.setText(ticket3);
                    yourTicket4TextView.setText(ticket4);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened.", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buyTickets:
                startActivity(new Intent(this, EventList.class));
                break;
        }
    }
}