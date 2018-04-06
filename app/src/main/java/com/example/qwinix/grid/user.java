package com.example.qwinix.grid;

/**
 * Created by qwinix on 28/3/18.
 */

public class user {


        String username;
        String userph;
        String email;
        String password;


        public user() {

        }

        public user(String username, String userph,String email,
                String password) {

            this.username = username;
            this.userph = userph;
            this.email = email;
            this.password = password;

        }



        public String getusername() {
            return username;
        }

        public String getuserph() {
            return userph;
        }
    public String getemail() {
        return email;
    }

    public String getpassword() {
        return password;
    }



}




