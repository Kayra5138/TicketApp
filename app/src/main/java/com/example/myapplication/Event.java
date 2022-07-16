package com.example.myapplication;

import android.media.Image;

public class Event {

    public Event(){}

    public String artist, date, price, banner, id;

    public String getArtist() {
        return artist;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getBanner() {
        return banner;
    }

    public String getId() {
        return id;
    }

    public Event(String artist, String date, String price, String banner, String id){
        this.artist = artist;
        this.date = date;
        this.price = price;
        this.banner = banner;
        this.id = id;
    }
}
