package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Event> list;

    String ticket1;
    String ticket2;
    String ticket3;
    String ticket4;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
    String userID = user.getUid();

    public MyAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemevent,parent,false);


        referenceUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    ticket1 = userProfile.ticket1;
                    ticket2 = userProfile.ticket2;
                    ticket3 = userProfile.ticket3;
                    ticket4 = userProfile.ticket4;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = list.get(position);
        holder.artistText.setText(event.getArtist());
        holder.dateText.setText(event.getDate());
        holder.priceText.setText(event.getPrice());
        String url = event.getBanner();
//        Glide.with(holder.imageView.getContext()).load(url).centerCrop().fitCenter().into(holder.imageView);
        Picasso.get().load(url).into(holder.imageView);
    }


    @Override
    public int getItemCount() { return list.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView artistText, dateText, priceText;
        ImageView imageView;
        Button buyButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            artistText = itemView.findViewById(R.id.tvArtist);
            dateText = itemView.findViewById(R.id.tvDate);
            priceText = itemView.findViewById(R.id.tvPrice);
            imageView = itemView.findViewById(R.id.eventBanner);
//            Picasso.get().load("https://i.imgur.com/6dIEQbq.jpeg").into(imageView);

            buyButton = itemView.findViewById(R.id.buyButton);
            buyButton.setOnClickListener(this);

        }





        @Override
        public void onClick(View view) {

            String eventTicket = list.get(getAbsoluteAdapterPosition()).getId();
            Log.d("demo",eventTicket);



            //Toast.makeText(context.getApplicationContext(), ticket1 + " " + ticket2 + " " + ticket3 + " " + ticket4, Toast.LENGTH_SHORT).show();

            HashMap blabla = new HashMap();
            if(eventTicket.equals(ticket1) || eventTicket.equals(ticket2) || eventTicket.equals(ticket3) || eventTicket.equals(ticket4)){
                Toast.makeText(context.getApplicationContext(), "You already have a ticket for this event.", Toast.LENGTH_SHORT).show();
            }else{
                if(Objects.equals(ticket1, "empty")){
                    blabla.put("ticket1",eventTicket);
                    referenceUsers.child(userID).updateChildren(blabla);
                    referenceUsers.child(userID).child("ticket1").push();
                    Toast.makeText(context.getApplicationContext(), "You have successfully purchased a ticket for this event.", Toast.LENGTH_SHORT).show();
                }else if(Objects.equals(ticket2, "empty")){
                    blabla.put("ticket2",eventTicket);
                    referenceUsers.child(userID).updateChildren(blabla);
                    referenceUsers.child(userID).child("ticket2").push();
                    Toast.makeText(context.getApplicationContext(), "You have successfully purchased a ticket for this event.", Toast.LENGTH_SHORT).show();
                }else if(Objects.equals(ticket3, "empty")){
                    blabla.put("ticket3",eventTicket);
                    referenceUsers.child(userID).updateChildren(blabla);
                    referenceUsers.child(userID).child("ticket3").push();
                    Toast.makeText(context.getApplicationContext(), "You have successfully purchased a ticket for this event.", Toast.LENGTH_SHORT).show();
                }else if(Objects.equals(ticket4, "empty")){
                    blabla.put("ticket4",eventTicket);
                    referenceUsers.child(userID).updateChildren(blabla);
                    referenceUsers.child(userID).child("ticket4").push();
                    Toast.makeText(context.getApplicationContext(), "You have successfully purchased a ticket for this event.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context.getApplicationContext(), "You don't have an empty ticket slot.", Toast.LENGTH_SHORT).show();
                }
            }

            referenceUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if(userProfile != null){
                        ticket1 = userProfile.ticket1;
                        ticket2 = userProfile.ticket2;
                        ticket3 = userProfile.ticket3;
                        ticket4 = userProfile.ticket4;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


        }
    }
}
