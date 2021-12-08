package com.example.pizzatime.models;

import androidx.annotation.NonNull;

@Table(table_name = "pizzas")
public class Pizza extends Model {
    private String name;
    private String location;
    private String price;


    public Pizza(String id, String name, String location, String price) {
        super.setId(id);
        this.name=name;
        this.location = location;
        this.price = price;
    }
    public Pizza() {
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        System.out.println(this.price);
        return this.name + " ($" + this.getPrice() + ")";
    }
}
