package com.example.qwinix.grid;

/**
 * Created by qwinix on 28/3/18.
 */

public class serviceproviders {

    String username;
    String userph;
    String address;
    String spinner;
    String email;


    public serviceproviders() {

    }

    public serviceproviders(String username, String userph, String address, String spinner,String email) {

        this.username = username;
        this.userph = userph;
        this.address = address;
        this.spinner = spinner;
        this.email = email;
    }



    public String getusername() {
        return username;
    }

    public String getuserph() {
        return userph;
    }

    public String getaddress() {return address; }

    public  String getSpinner() {return spinner;}
    public String getEmail() {return email;}

}

