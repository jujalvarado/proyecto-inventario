package com.example.inventoryapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.Volley;
import com.example.inventoryapp.Constants;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IniciarSesionActivity extends AppCompatActivity {

    String url_iniciar_sesion = Constants.BASE_URL+"iniciar_sesion.php";


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


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_iniciar_sesion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error")){

                        // mensaje
                        Toast.makeText(IniciarSesionActivity.this, "Inicio de sesión exitoso",
                                        Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i <jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            // se guardan los datos con sharedpreferences
                            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS , MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt(Constants.ID_USUARIO,object.getInt("id_usuario"));
                            editor.putInt(Constants.ID_TIENDA,object.getInt("id_tienda"));
                            editor.putString(Constants.NOMBRE_TIENDA, object.getString("nombre_tienda"));
                            editor.apply();

                        }

                        // dirigir a dashboard
                        Intent intent= new Intent(IniciarSesionActivity.this,MainActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    } else{
                        Toast.makeText(IniciarSesionActivity.this, jsonObject.getString("message"),
                                        Toast.LENGTH_LONG).show();
                    }

                }catch(JSONException e){

                    e.printStackTrace();
                    Toast.makeText(IniciarSesionActivity.this,
                                    "Error al iniciar sesion: " + e.getMessage() ,
                                    Toast.LENGTH_SHORT)
                                    .show();
                }
            }
        },
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                Toast.makeText(IniciarSesionActivity.this,
                        "Error al iniciar sesion" + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("correo", correo);
                params.put("contrasena",contrasena);
                return params;
            }



        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

}