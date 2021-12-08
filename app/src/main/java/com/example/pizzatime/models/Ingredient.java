package com.example.pizzatime.models;

import androidx.annotation.NonNull;

@Table(table_name = "ingredients")
public class Ingredient extends Model{
    private String ingredient;
    private String price;

    public Ingredient(String ingredient, String price){
        this.ingredient = ingredient;
        this.price = price;
    }

    public Ingredient(){}

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
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
        return this.getIngredient() + " ($" + this.price + ")";
    }
}
