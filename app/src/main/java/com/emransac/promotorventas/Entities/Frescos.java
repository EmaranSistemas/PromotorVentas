package com.emransac.promotorventas.Entities;

public class Frescos {
    String id,name;
    public Frescos() {
    }

    public Frescos(String id, String name){
        this.id = id;
        this.name = name;
    }
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String setId(){
        return this.id;
    }
    public String setNombre(){
        return this.name;
    }
}
