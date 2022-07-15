package com.example.myapplication;

public class User {

    public String fullName;
    public String email;

    public String getTicket1() {
        return ticket1;
    }

    public String getTicket2() {
        return ticket2;
    }

    public String getTicket3() {
        return ticket3;
    }

    public String getTicket4() {
        return ticket4;
    }

    public String ticket1;
    public String ticket2;
    public String ticket3;
    public String ticket4;

    public User(){}

    public User(String fullName, String email, String ticket1, String ticket2, String ticket3, String ticket4){
        this.fullName = fullName;
        this.email = email;
        this.ticket1 = ticket1;
        this.ticket2 = ticket2;
        this.ticket3 = ticket3;
        this.ticket4 = ticket4;
    }
}
