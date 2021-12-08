package com.example.pizzatime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton fbButton;
    private ImageButton inButton;
    private ImageButton twButton;
    private ImageButton ytButton;

    private Button addPizzaButton;
    private Button listPizzaButton;
    private Button selectPizzaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbButton = findViewById(R.id.fbButton);
        inButton = findViewById(R.id.inButton);
        twButton = findViewById(R.id.twButton);
        ytButton = findViewById(R.id.ytButton);

        addPizzaButton = findViewById(R.id.addPizza);
        listPizzaButton = findViewById(R.id.listPizza);
        selectPizzaButton = findViewById(R.id.selectPizza);

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                try {
                    getPackageManager().getPackageInfo("com.facebook.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://TorosPizza.PA"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/TorosPizza.PA"));
                }
                startActivity(intent);
            }
        });

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                try {
                    // get the Twitter app if possible
                    getPackageManager().getPackageInfo("com.instagram.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("instagram://pizzatoros"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/pizzatoros"));
                }
                startActivity(intent);
            }
        });

        twButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                try {
                    // get the Twitter app if possible
                    getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=pizzeriatoro"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/pizzeriatoro"));
                }
                startActivity(intent);
            }
        });

        ytButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                try {
                    // get the Twitter app if possible
                    getPackageManager().getPackageInfo("com.youtube.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("youtube://dQw4w9WgXcQ"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dQw4w9WgXcQ"));
                }
                startActivity(intent);
            }
        });

        addPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Gestion.class);
                startActivity(i);
            }
        });
        listPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ListActivity.class);
                startActivity(i);
            }
        });
        selectPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ArmaPizza.class);
                startActivity(i);
            }
        });

    }
}