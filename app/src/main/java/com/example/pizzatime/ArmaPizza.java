package com.example.pizzatime;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzatime.models.Ingredient;
import com.example.pizzatime.models.Pizza;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ArmaPizza extends AppCompatActivity {

    private Spinner pizzaTypeSpinner, ingredientSpinner;
    private TextView resultText;
    private Button calculateButton;

    private Pizza pizza;
    private Ingredient ingredient;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arma_pizza);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        calculateButton = findViewById(R.id.calculateButton);
        pizzaTypeSpinner = findViewById(R.id.pizzaTypesSpinner);
        ingredientSpinner = findViewById(R.id.ingredientsSpinner);

        resultText = findViewById(R.id.resultText);

        setCalulateButton();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass
                int result = 0;
                if(pizza != null && ingredient != null){
                    int pizzaPrice = Integer.valueOf(pizza.getPrice());
                    int ingredientPrice = Integer.valueOf(ingredient.getPrice());
                    result = pizzaPrice + ingredientPrice;
                    resultText.setText("$" + result);

                }else {
                    Toast.makeText(view.getContext(), "Favor seleccionar", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        // Ejecutamos los get y sus renders
        DAO dao = new DAO();
        dao.getAll(Pizza.class, this, "fillPizzaSpinner");
        dao.getAll(Ingredient.class, this, "fillIngredientSpinner");
    }

    private void setCalulateButton(){
        if(pizza != null && ingredient != null){
            calculateButton.setEnabled(true);
        }else{
            calculateButton.setEnabled(false);
        }
    }


    public void fillPizzaSpinner(DataSnapshot dataSnapshot){
        ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
        for(DataSnapshot op : dataSnapshot.getChildren()){
            Pizza p = op.getValue(Pizza.class);
            p.setId(op.getKey());
            pizzas.add(p);
        }
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, pizzas);
        pizzaTypeSpinner.setAdapter(adapter);
        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pizza = (Pizza) pizzaTypeSpinner.getItemAtPosition(i);
                setCalulateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void fillIngredientSpinner(DataSnapshot dataSnapshot){
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        for(DataSnapshot op : dataSnapshot.getChildren()){
            Ingredient p = op.getValue(Ingredient.class);
            p.setId(op.getKey());
            ingredients.add(p);
        }
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, ingredients);
        ingredientSpinner.setAdapter(adapter);
        ingredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ingredient = (Ingredient) ingredientSpinner.getItemAtPosition(i);
                setCalulateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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