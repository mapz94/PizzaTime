package com.example.pizzatime;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzatime.models.Pizza;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
public class ListActivity extends AppCompatActivity {

    private Button deleteButton;
    private ListView pizzaList;
    private Pizza selected = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        deleteButton = findViewById(R.id.deleteButton);
        pizzaList = findViewById(R.id.pizzaList);

        DAO dao = new DAO();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected == null){
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View view1 = View.inflate(view.getContext(), R.layout.empty, null);
                builder.setView(view1)
                        .setTitle("Eliminar " + selected.getName() + "?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try{
                                    dao.delete(selected);
                                    Toast.makeText(view.getContext(), "Pizza eliminada", Toast.LENGTH_LONG).show();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        dao.getAll(Pizza.class, this, "fillPizzaList");
    }

    public void fillPizzaList(DataSnapshot dataSnapshot){
        ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
        for(DataSnapshot op : dataSnapshot.getChildren()){
            Pizza p = op.getValue(Pizza.class);
            p.setId(op.getKey());
            pizzas.add(p);
        }
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, pizzas);
        pizzaList.setAdapter(adapter);
        pizzaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = (Pizza) pizzaList.getItemAtPosition(i);
                deleteButton.setEnabled(true);
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

