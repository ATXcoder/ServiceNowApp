package com.groundupcoding.servicenow.models;

/**
 * Created by Thomas on 1/23/2015.
 */
public class Instance {
    private String name;
    private String address;

    public Instance(){}

    public Instance(String name, String address)
    {
        this.name = name;
        this.address = address;
    }

    public void setName(String name){this.name = name;}
    public String getName(){return name;}

    public void setAddress(String address){this.address = address;}
    public String getAddress(){return address;}
}
