package com.example.qwinix.grid;

/**
 * Created by qwinix on 25/3/18.
 */

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by User on 3/14/2018.
 */

public class DataModel {

    int rating;
    String review;
    LatLng mLocation;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getmLocation() {
        return mLocation;
    }

    public void setmLocation(LatLng mLocation) {
        this.mLocation = mLocation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}