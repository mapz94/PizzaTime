package com.example.pizzatime;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzatime.models.Model;
import com.example.pizzatime.models.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class DAO {
    public static FirebaseDatabase db;

    public DAO(){
        if(this.db == null){
            db = new DBHelper().getInstance();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insert(Model model){
       Class<?> _class = model.getClass();
       System.out.println(_class.getName());
       String tableName  = "";
       try{
           tableName = _class.getDeclaredAnnotation(Table.class).table_name();
           db.getReference().child(tableName).child(UUID.randomUUID().toString()).setValue(model);
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    /*
    * La mejor forma de pasar los datos obtenidos de forma asincrona devuelta al activity
    * es utilizando la referencia del activity al final de obtener los datos y utilizar
    * un metodo propio del mismo activity que valida que lo tenga y lo renderiza.
    * Sino la renderización del activity siempre pasará en banda y no tomará
    * lo que le estamos obteniendo.
    *
    * Por lo que en cada get o getAll que hagamos, si hay que pasarle el contexto en que estamos.
    * y así nos aseguramoso de que efectivamente renderize al recibir los datos
    * MP.
    * */


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getAll(Class<?> _class, AppCompatActivity app, String methodName){
        try{
            String tableName = _class.getDeclaredAnnotation(Table.class).table_name();
            db.getReference().child(tableName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Aquí validamos si podemos pasarle los datos devuelta al activity:
                    // Aunque no basta con solo pasarle devuelta, hay que ejecutarlo
                    try{
                        if(app != null){
                            try {
                                Method method = app.getClass().getDeclaredMethod(methodName, DataSnapshot.class);
                                // Invocamos el metodo del activity designado para hacer la renderización. MP.
                                method.invoke(app, dataSnapshot);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException noSuchMethodException) {
                                noSuchMethodException.printStackTrace();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void get(Class<?> _class, String id, AppCompatActivity app){
        final Model[] model = {null};
        try{
            String tableName = _class.getAnnotation(Table.class).table_name();
            db.getReference().child(tableName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot op : dataSnapshot.getChildren()){
                        Model m = (Model) op.getValue(_class);
                        if(m.getId().equals(id)) {
                            model[0] = m;
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Model model){
        Class<?> _class = model.getClass();
        try{
            String tableName = _class.getAnnotation(Table.class).table_name();
            db.getReference().child(tableName).orderByKey().equalTo(model.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                        for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                            String key = postsnapshot.getKey();
                            if(key.equals(model.getId())){
                                postsnapshot.getRef().removeValue();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
