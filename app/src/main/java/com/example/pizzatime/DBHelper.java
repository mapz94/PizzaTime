package com.example.pizzatime;

import com.google.firebase.database.FirebaseDatabase;

public class DBHelper {
    // Write a message to the database
    private static FirebaseDatabase instance;

    public DBHelper(){
        if (this.instance == null){
            this.instance = FirebaseDatabase.getInstance("https://pizzatime-2b0b6-default-rtdb.firebaseio.com/");
        }
    }

    public static FirebaseDatabase getInstance() {

        return instance;
    }
}
