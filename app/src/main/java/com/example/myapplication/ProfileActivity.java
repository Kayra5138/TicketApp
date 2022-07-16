package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logOut;

    private TextView buyTickets;
    private Button cancelButton1, cancelButton2, cancelButton3, cancelButton4;

    ImageView qrcodeiv;

    DatabaseReference referenceEvents = FirebaseDatabase.getInstance().getReference("Events");

    String ticket1 = "empty";
    String ticket2;
    String ticket3;
    String ticket4;
    String slot = "xd";
    TextView yourTicket1TextView, yourTicket2TextView, yourTicket3TextView, yourTicket4TextView;
    String qrString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        qrcodeiv = (ImageView) findViewById(R.id.qrcode);
        qrcodeiv.setOnClickListener(this);

        buyTickets = (TextView) findViewById(R.id.buyTickets);
        buyTickets.setOnClickListener(this);

        yourTicket1TextView = (TextView) findViewById(R.id.yourTicket1);
        yourTicket1TextView.setOnClickListener(this);
        yourTicket2TextView = (TextView) findViewById(R.id.yourTicket2);
        yourTicket2TextView.setOnClickListener(this);
        yourTicket3TextView = (TextView) findViewById(R.id.yourTicket3);
        yourTicket3TextView.setOnClickListener(this);
        yourTicket4TextView = (TextView) findViewById(R.id.yourTicket4);
        yourTicket4TextView.setOnClickListener(this);

        cancelButton1 = (Button) findViewById(R.id.cancelButton1);
        cancelButton1.setOnClickListener(this);
        cancelButton2 = (Button) findViewById(R.id.cancelButton2);
        cancelButton2.setOnClickListener(this);
        cancelButton3 = (Button) findViewById(R.id.cancelButton3);
        cancelButton3.setOnClickListener(this);
        cancelButton4 = (Button) findViewById(R.id.cancelButton4);
        cancelButton4.setOnClickListener(this);



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
        yourTicket1TextView = (TextView) findViewById(R.id.yourTicket1);
        yourTicket2TextView = (TextView) findViewById(R.id.yourTicket2);
        yourTicket3TextView = (TextView) findViewById(R.id.yourTicket3);
        yourTicket4TextView = (TextView) findViewById(R.id.yourTicket4);



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    ticket1 = userProfile.ticket1;
                    ticket2 = userProfile.ticket2;
                    ticket3 = userProfile.ticket3;
                    ticket4 = userProfile.ticket4;
                    welcomeTextView.setText("Welcome "+fullName+".");
                }


                if(!Objects.equals(ticket1, "empty")){
                    referenceEvents.child(ticket1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Event event = snapshot.getValue(Event.class);
                            if(event != null){
                                slot = event.getArtist() + " - " + event.getDate();
                                yourTicket1TextView.setText(slot);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    cancelButton1.setVisibility(View.VISIBLE);
                }else{
                    yourTicket1TextView.setText("");
                    cancelButton1.setVisibility(View.GONE);
                }

                if(!Objects.equals(ticket2, "empty")){
                    referenceEvents.child(ticket2).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Event event = snapshot.getValue(Event.class);
                            if(event != null){
                                slot = event.getArtist() + " - " + event.getDate();
                                yourTicket2TextView.setText(slot);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    cancelButton2.setVisibility(View.VISIBLE);
                }else{
                    yourTicket2TextView.setText("");
                    cancelButton2.setVisibility(View.GONE);
                }

                if(!Objects.equals(ticket3, "empty")){
                    referenceEvents.child(ticket3).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Event event = snapshot.getValue(Event.class);
                            if(event != null){
                                slot = event.getArtist() + " - " + event.getDate();
                                yourTicket3TextView.setText(slot);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    cancelButton3.setVisibility(View.VISIBLE);
                }else{
                    yourTicket3TextView.setText("");
                    cancelButton3.setVisibility(View.GONE);
                }

                if(!Objects.equals(ticket4, "empty")){
                    referenceEvents.child(ticket4).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Event event = snapshot.getValue(Event.class);
                            if(event != null){
                                slot = event.getArtist() + " - " + event.getDate();
                                yourTicket4TextView.setText(slot);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    cancelButton4.setVisibility(View.VISIBLE);
                }else{
                    yourTicket4TextView.setText("");
                    cancelButton4.setVisibility(View.GONE);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened.", Toast.LENGTH_LONG).show();
            }
        });


    }
    HashMap blabla = new HashMap();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelButton1:
                blabla.put("ticket1","empty");
                reference.child(userID).updateChildren(blabla);
                reference.child(userID).child("ticket1").push();
                Toast.makeText(this, "You successfully canceled your ticket.", Toast.LENGTH_SHORT).show();
                yourTicket1TextView.setText("");
                cancelButton1.setVisibility(View.GONE);
                break;
            case R.id.cancelButton2:
                blabla.put("ticket2","empty");
                reference.child(userID).updateChildren(blabla);
                reference.child(userID).child("ticket2").push();
                Toast.makeText(this, "You successfully canceled your ticket.", Toast.LENGTH_SHORT).show();
                yourTicket2TextView.setText("");
                cancelButton2.setVisibility(View.GONE);
                break;
            case R.id.cancelButton3:
                blabla.put("ticket3","empty");
                reference.child(userID).updateChildren(blabla);
                reference.child(userID).child("ticket3").push();
                Toast.makeText(this, "You successfully canceled your ticket.", Toast.LENGTH_SHORT).show();
                yourTicket3TextView.setText("");
                cancelButton3.setVisibility(View.GONE);
                break;
            case R.id.cancelButton4:
                blabla.put("ticket4","empty");
                reference.child(userID).updateChildren(blabla);
                reference.child(userID).child("ticket4").push();
                Toast.makeText(this, "You successfully canceled your ticket.", Toast.LENGTH_SHORT).show();
                yourTicket4TextView.setText("");
                cancelButton4.setVisibility(View.GONE);
                break;
            case R.id.yourTicket1:
                qrString = userID + ticket1;
                MultiFormatWriter writer1 = new MultiFormatWriter();
                try {
                    BitMatrix matrix1 = writer1.encode(qrString, BarcodeFormat.QR_CODE,1300,1300);
                    BarcodeEncoder encoder1 = new BarcodeEncoder();
                    Bitmap bitmap1 = encoder1.createBitmap(matrix1);
                    qrcodeiv.setImageBitmap(bitmap1);
                    qrcodeiv.setVisibility(View.VISIBLE);
                    qrcodeiv.setZ(500);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.yourTicket2:
                qrString = userID + ticket2;
                MultiFormatWriter writer2 = new MultiFormatWriter();
                try {
                    BitMatrix matrix2 = writer2.encode(qrString, BarcodeFormat.QR_CODE,1300,1300);
                    BarcodeEncoder encoder2 = new BarcodeEncoder();
                    Bitmap bitmap2 = encoder2.createBitmap(matrix2);
                    qrcodeiv.setImageBitmap(bitmap2);
                    qrcodeiv.setVisibility(View.VISIBLE);
                    qrcodeiv.setZ(500);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.yourTicket3:
                qrString = userID + ticket3;
                MultiFormatWriter writer3 = new MultiFormatWriter();
                try {
                    BitMatrix matrix3 = writer3.encode(qrString, BarcodeFormat.QR_CODE,1300,1300);
                    BarcodeEncoder encoder3 = new BarcodeEncoder();
                    Bitmap bitmap3 = encoder3.createBitmap(matrix3);
                    qrcodeiv.setImageBitmap(bitmap3);
                    qrcodeiv.setVisibility(View.VISIBLE);
                    qrcodeiv.setZ(500);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.yourTicket4:
                qrString = userID + ticket4;
                MultiFormatWriter writer4 = new MultiFormatWriter();
                try {
                    BitMatrix matrix4 = writer4.encode(qrString, BarcodeFormat.QR_CODE,1300,1300);
                    BarcodeEncoder encoder4 = new BarcodeEncoder();
                    Bitmap bitmap4 = encoder4.createBitmap(matrix4);
                    qrcodeiv.setImageBitmap(bitmap4);
                    qrcodeiv.setVisibility(View.VISIBLE);
                    qrcodeiv.setZ(500);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.qrcode:
                qrcodeiv.setVisibility(View.GONE);
                break;
            case R.id.buyTickets:
                startActivity(new Intent(this, EventList.class));
                break;
        }
    }
}