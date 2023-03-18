package com.example.inventoryapp.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventoryapp.Constants;
import com.example.inventoryapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProveedorActivity extends AppCompatActivity {

    private TextView title;
    private EditText editTextnombre, editTextdireccion,editTexttelefono, editTextcorreo;
    private Button btnagregar, btncancelar, btneditar, btnactualizar,btncancelact, btneliminar;

    private int idProveedorEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor);

        title = findViewById(R.id.provtextView);

        editTextnombre = findViewById(R.id.proveditTextNombre);
        editTextdireccion = findViewById(R.id.proveditTextDireccion);
        editTexttelefono = findViewById(R.id.proveditTextTelefono);
        editTextcorreo = findViewById(R.id.proveditTextCorreo);

        btnagregar = findViewById(R.id.ProvAgregarRegistro);
        btncancelar = findViewById(R.id.ProvCancelarAgregar);
        btneditar = findViewById(R.id.ProvEditarRegistro);
        btneliminar = findViewById(R.id.ProvEliminarRegistro);
        btnactualizar = findViewById(R.id.ProvActualizarProveedor);
        btncancelact = findViewById(R.id.ProvCancelarActualizar);

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar();
            }
        });

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        btncancelact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelaract();
            }
        });


        Intent intent = getIntent();
        idProveedorEditar = intent.getIntExtra("id_proveedor",-1);



        if (idProveedorEditar != -1){
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS , MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Constants.ID_PROVEEDOR,idProveedorEditar);

            title.setText("Editar Proveedor");
            btnseditarproveedor();
            cargarproveedor(idProveedorEditar);

        }else{
            title.setText("Agregar Proveedor");
            btnsnuevoproveedor();
            setEnabledEditTexts(true);
        }

    }


    private boolean validareditText(){

        String nombre = editTextnombre.getText().toString();
        String direccion = editTextdireccion.getText().toString();
        String telefono = editTexttelefono.getText().toString().trim();
        String correo = editTextcorreo.getText().toString().trim();

        // valida el campo nombre
        if(TextUtils.isEmpty(nombre)){
            editTextnombre.setError("Ingrese el nombre");
            editTextnombre.requestFocus();
            return false;
        }
        // valida el campo direccion
        if(TextUtils.isEmpty(direccion)){
            editTextdireccion.setError("Ingrese la direccion");
            editTextdireccion.requestFocus();
            return false;
        }

        // valida el campo telefono
        if(TextUtils.isEmpty(telefono)){
            editTexttelefono.setError("Ingrese un numero e telefono");
            editTexttelefono.requestFocus();
            return false;
        }
        // valida el campo correo
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            editTextcorreo.setError("Ingrese un correo válido");
            editTextcorreo.requestFocus();
            return false;
        }
        return true;
    }



    private void cargarproveedor(int idProveedorEditar){

        btnseditarproveedor();

        String url = Constants.BASE_URL+"proveedor_selectbyid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            setEnabledEditTexts(true);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                editTextnombre.setText(object.getString("nombre"));                                editTextnombre.setText(object.getString("nombre"));
                                editTextdireccion.setText(object.getString("direccion"));
                                editTexttelefono.setText(object.getString("telefono"));
                                editTextcorreo.setText(object.getString("correo"));
                            }
                            setEnabledEditTexts(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProveedorActivity.this, "Error al cargar proveedr", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ProveedorActivity.this, "Error al cargar proveedor", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
                int id_proveedor= sharedPreferences.getInt(Constants.ID_PROVEEDOR,-1);
                HashMap<String, String> params = new HashMap();
                params.put("id_proveedor",String.valueOf(id_proveedor));
                return params;
            }

        };
        Volley.newRequestQueue(this).add(stringRequest);

    }


    private void agregar(){

        if(validareditText()){
            String nombre = editTextnombre.getText().toString();
            String direccion = editTextdireccion.getText().toString();
            String telefono = editTexttelefono.getText().toString().trim();
            String correo = editTextcorreo.getText().toString().trim();

            String url = Constants.BASE_URL+"proveedor_insertar.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                if(!jsonObject.getBoolean("error")) {
                                    Toast.makeText(ProveedorActivity.this, "Proveedor agregado", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(ProveedorActivity.this, "Error al agregar proveedor: " + e.getMessage() , Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ProveedorActivity.this, "Error al agregar proveedor", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
                    int id_tienda = sharedPreferences.getInt(Constants.ID_TIENDA,-1);
                    HashMap<String, String> params = new HashMap();
                    params.put("nombre",nombre);
                    params.put("direccion",direccion);
                    params.put("telefono",telefono);
                    params.put("correo",correo);
                    params.put("id_tienda",String.valueOf(id_tienda));
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }

    }


    private void cancelar(){
        finish();
    }

    private void editar(){
        setEnabledEditTexts(true);
        btnsactualizar();
    }

    private void eliminar(){

        String url = Constants.BASE_URL+"proveedor_delete.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(ProveedorActivity.this, "Proveedor eliminado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProveedorActivity.this, "Error al eliminar proveedor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ProveedorActivity.this, "Error al eliminar el proveedor", Toast.LENGTH_SHORT).show();
                }
        })
            {
                @Override

                protected Map<String, String> getParams () throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("id_proveedor", String.valueOf(idProveedorEditar));
                return params;

                }
            };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void actualizar(){
        if(validareditText()){
            String nombre = editTextnombre.getText().toString();
            String direccion = editTextdireccion.getText().toString();
            String telefono = editTexttelefono.getText().toString().trim();
            String correo = editTextcorreo.getText().toString().trim();

            String url = Constants.BASE_URL+"proveedor_update.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                if(!jsonObject.getBoolean("error")) {
                                    Toast.makeText(ProveedorActivity.this, "Proveedor actualizado", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(ProveedorActivity.this, "Error al actualizar proveedor: " + e.getMessage() , Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ProveedorActivity.this, "Error al actualizar el proveedor", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
                    int id_tienda = sharedPreferences.getInt(Constants.ID_TIENDA,-1);
                    HashMap<String, String> params = new HashMap();
                    params.put("nombre",nombre);
                    params.put("direccion",direccion);
                    params.put("telefono",telefono);
                    params.put("correo",correo);
                    params.put("id_proveedor",String.valueOf(idProveedorEditar));
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }

    }

    private void cancelaract(){
        cargarproveedor(idProveedorEditar);
        btnseditarproveedor();
    }



    private void setEnabledEditTexts(boolean b) {
        editTextnombre.setEnabled(b);
        editTextdireccion.setEnabled(b);
        editTexttelefono.setEnabled(b);
        editTextcorreo.setEnabled(b);
    }



    private void btnsnuevoproveedor(){

        btnagregar.setVisibility(View.VISIBLE);
        btncancelar.setVisibility(View.VISIBLE);

        btneditar.setVisibility(View.GONE);
        btneliminar.setVisibility(View.GONE);
        btnactualizar.setVisibility(View.GONE);
        btncancelact.setVisibility(View.GONE);
    }

    private void btnseditarproveedor(){

        btneditar.setVisibility(View.VISIBLE);
        btneliminar.setVisibility(View.VISIBLE);

        btnagregar.setVisibility(View.GONE);
        btncancelar.setVisibility(View.GONE);
        btnactualizar.setVisibility(View.GONE);
        btncancelact.setVisibility(View.GONE);

    }

    private void btnsactualizar(){

        btnactualizar.setVisibility(View.VISIBLE);
        btncancelact.setVisibility(View.VISIBLE);

        btneditar.setVisibility(View.GONE);
        btneliminar.setVisibility(View.GONE);
        btnagregar.setVisibility(View.GONE);
        btncancelar.setVisibility(View.GONE);

    }




}