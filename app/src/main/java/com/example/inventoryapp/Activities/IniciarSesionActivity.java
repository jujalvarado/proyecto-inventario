package com.example.inventoryapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.inventoryapp.R;

public class IniciarSesionActivity extends AppCompatActivity {

    private Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        btnIniciarSesion = findViewById(R.id.ibuttonIniciarSesion);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IniciarSesionActivity.this, "Sesión Iniciada con éxito", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(IniciarSesionActivity.this,MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });


    }

}