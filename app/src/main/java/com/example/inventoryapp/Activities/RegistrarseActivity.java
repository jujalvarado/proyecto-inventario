package com.example.inventoryapp.Activities;

import com.example.inventoryapp.Constants;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.inventoryapp.R;
import com.example.inventoryapp.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {

    String url_registrar = Constants.BASE_URL+"usuario_insertar.php";

    private Button btnRegistrarse;
    private EditText editTextnombreusuario, editTextemailusuario, editTextcontrasena,
                     editTextnombretienda, editTextdirecciontienda,editTexttelefonotienda ;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        editTextnombreusuario = findViewById(R.id.reditTextNombres);
        editTextemailusuario = findViewById(R.id.reditTextEmail);
        editTextcontrasena = findViewById(R.id.reditTextContrasena);

        editTextnombretienda = findViewById(R.id.reditTextNombret);
        editTextdirecciontienda = findViewById(R.id.reditTextdireccion);
        editTexttelefonotienda = findViewById(R.id.reditTexttelefono);

        btnRegistrarse = findViewById(R.id.rbuttonRegistrarse);

        // barra de proceso
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando Usuario...");
        progressDialog.setCancelable(false);


        // boton registarse llama al metodo para registrar usuario,
        // muestra un mensaje y cierra la activity y retorna al inicio

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

    }

    // metodo para registrar el usuario y asu vez la tienda ingresada
    private void registrarUsuario(){

        String nombre = editTextnombreusuario.getText().toString();
        String correo = editTextemailusuario.getText().toString().trim();
        String contrasena = editTextcontrasena.getText().toString().trim();

        String nombretienda = editTextnombretienda.getText().toString();
        String direcciontienda = editTextdirecciontienda.getText().toString();
        String telefono = editTexttelefonotienda.getText().toString().trim();


        // valida el campo nombre
        if(TextUtils.isEmpty(nombre)){
            editTextnombreusuario.setError("Ingrese sus nombres y apellidos");
            editTextnombreusuario.requestFocus();
            return;
        }

        // valida el campo email
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            editTextemailusuario.setError("Ingrese un correo válido");
            editTextemailusuario.requestFocus();
            return;
        }

        // valida el campo contrasena y que tenga mas de 6 caracteres
        if(TextUtils.isEmpty(contrasena)){
            editTextcontrasena.setError("Ingrese su contraseña");
            editTextcontrasena.requestFocus();
            return;
        }

        if(contrasena.length() < 6){
            editTextcontrasena.setError("Su contraseña debe tener mas 6 caracteres");
            editTextcontrasena.requestFocus();
            return;
        }

        //valida el campo nombre de la tienda
        if(TextUtils.isEmpty(nombretienda)){
            editTextnombretienda.setError("Ingrese el nombre de su tienda/negocio");
            editTextnombretienda.requestFocus();
            return;
        }

        //valida el campo direccion de la tienda
        if(TextUtils.isEmpty(direcciontienda)){
            editTextdirecciontienda.setError("Ingrese la dirección de su tienda/negocio");
            editTextdirecciontienda.requestFocus();
            return;
        }

        // valida el campo telefono de la tienda y que tenga 10 digitos numericos
        if(TextUtils.isEmpty(telefono)){
            editTexttelefonotienda.setError("Ingrese el numero de telefono/celular");
            editTexttelefonotienda.requestFocus();
            return;
        }

        String regex = "\\d{9}" ;

        if(telefono.matches(regex)){
            editTexttelefonotienda.setError("Debe tener al menos 10 digitos numericos");
            editTexttelefonotienda.requestFocus();
            return;
        }

        // se muestra la barra de progreso
        progressDialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST,url_registrar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    //
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(RegistrarseActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_SHORT)
                                .show();

                        Intent intent = new Intent(RegistrarseActivity.this, StartActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(RegistrarseActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                    Toast.makeText(RegistrarseActivity.this,
                            "Error al registrar al usuario: " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();

                        Toast.makeText(RegistrarseActivity.this,
                                "Error al registrar al usuario: "+ error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }

        ) {
            @Override

            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("nombre_usuario",nombre);
                params.put("correo",correo);
                params.put("contrasena",contrasena);

                params.put("nombre_tienda",nombretienda);
                params.put("direccion",direcciontienda);
                params.put("telefono",telefono);

                return params;

            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}