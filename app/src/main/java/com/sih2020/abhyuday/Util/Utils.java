package com.sih2020.abhyuday.Util;


import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String API_KEY = "AIzaSyCEOGgxcktFGcmMwEaAWjIm-M_PeLbJTsY";

    public List<String> getContacts(){
        List<String> contacts = new ArrayList<>();
        contacts.add("8105798127");
        contacts.add("8277093060");
        contacts.add("9986917728");
        contacts.add("7875226272");

        return contacts;
    }


    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
