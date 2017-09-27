package com.example.dimpy.perkey_dos;

/**
 * Created by dimpy on 27/9/17.
 */

public class List_Item_Location_History {

    private String Insti;
    private String City;
    private String Duration;
    private String Cost;
    private String Exact;  //exact adress

    public List_Item_Location_History() {

    }

    public List_Item_Location_History(String insti, String city, String duration, String cost, String exact) {
        Insti = insti;
        City = city;
        Duration = duration;
        Cost = cost;
        Exact = exact;
    }

    public String getInsti() {
        return Insti;
    }

    public void setInsti(String insti) {
        Insti = insti;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getExact() {
        return Exact;
    }

    public void setExact(String exact) {
        Exact = exact;
    }
}
