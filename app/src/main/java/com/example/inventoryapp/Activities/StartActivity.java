package com.example.inventoryapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.inventoryapp.Constants;
import com.example.inventoryapp.R;

public class StartActivity extends AppCompatActivity {

    private Button btnIniciarSesion, btnRegistrarse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        int user_id = sharedPreferences.getInt(Constants.ID_USUARIO,-1);

        if (user_id != -1 ){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        btnIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        btnRegistrarse = findViewById(R.id.buttonRegistrarse);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this,IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this,RegistrarseActivity.class);
                startActivity(intent);

            }
        });

    }
}