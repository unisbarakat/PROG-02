package com.unisbarakat.represent;

/**
 * Created by unisbarakat on 3/11/16.
 */
public class Person implements  java.io.Serializable{

    public String name;
    public String party;


    public Person(String name, String party){
        this.name = name;
        this.party = party;

    }
}
