package com.example.pizzatime;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzatime.models.Pizza;

import java.util.UUID;

public class Gestion extends AppCompatActivity {


    private EditText nameEdit, priceEdit, localEdit;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        nameEdit = findViewById(R.id.nameEdit);
        priceEdit = findViewById(R.id.priceEdit);
        localEdit = findViewById(R.id.localEdit);

        // Por defecto
        localEdit.setText("Santiago");

        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String price = priceEdit.getText().toString();
                String local = localEdit.getText().toString();

                if(name.isEmpty() || price.isEmpty() || local.isEmpty()){
                    Toast.makeText(view.getContext(), "Favor llenar los campos!", Toast.LENGTH_LONG).show();
                    return;
                }

                Pizza pizza = new Pizza();
                pizza.setName(name);
                pizza.setPrice(price);
                pizza.setLocation(local);

                DAO dao = new DAO();
                try{
                    dao.insert(pizza);
                    Toast.makeText(view.getContext(), "Pizza creada correctamente",Toast.LENGTH_LONG).show();
                    // Devolvemos
                    Intent i = new Intent(view.getContext(), MainActivity.class);
                    startActivity(i);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}