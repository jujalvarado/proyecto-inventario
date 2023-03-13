package com.example.inventoryapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.inventoryapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class IniciarSesionActivity extends AppCompatActivity {

    String url_iniciar_sesion = "https://inventoryappweb.000webhostapp.com/iniciar_sesion.php";


    private EditText editTextemail, editTextcontrasena;
    private Button btnIniciarSesion;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        btnIniciarSesion = findViewById(R.id.ibuttonIniciarSesion);

        editTextemail = findViewById(R.id.ieditTextEmail);
        editTextcontrasena = findViewById(R.id.ieditTextContrasena);


        // barra de proceso
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando Sesion...");
        progressDialog.setCancelable(false);


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniciarsesion();

                Toast.makeText(IniciarSesionActivity.this, "Sesión Iniciada con éxito", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(IniciarSesionActivity.this,MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });
    }

    private void iniciarsesion(){

        String correo = editTextemail.getText().toString().trim();
        String contrasena = editTextcontrasena.getText().toString().trim();


        // valida el campo email
        if(TextUtils.isEmpty(correo)){
            editTextemail.setError("Ingrese su correo");
            editTextemail.requestFocus();
            return;
        }

        // valida el campo email
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            editTextemail.setError("Ingrese un correo válido");
            editTextemail.requestFocus();
            return;
        }

        // valida el campo email
        if(TextUtils.isEmpty(contrasena)){
            editTextcontrasena.setError("Ingrese su contraseña");
            editTextcontrasena.requestFocus();
            return;
        }

        // se muestra la barra de progreso
        progressDialog.show();


        StringRequest stringRequet = new StringRequest(Request.Method.POST, url_iniciar_sesion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error")){

                        Toast.makeText(IniciarSesionActivity.this, "Inicio de sesión inválido", Toast.LENGTH_SHORT).show();


//                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS , MODE_PRIVATE)

                    }

                }catch(JSONException e){


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        ){


        };


    }

}