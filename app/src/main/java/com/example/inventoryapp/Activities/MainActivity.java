package com.example.inventoryapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventoryapp.Constants;
import com.example.inventoryapp.Fragments.menuprincipalFragment;
import com.example.inventoryapp.Fragments.ProveedoresFragment;
import com.example.inventoryapp.Fragments.CategoriasProductosFragment;
import com.example.inventoryapp.Fragments.ProductosFragment;
import com.example.inventoryapp.Fragments.ClientesFragment;
import com.example.inventoryapp.Fragments.EntradaProductoFragment;
import com.example.inventoryapp.Fragments.SalidaProductoFragment;
import com.example.inventoryapp.Fragments.acercadeFragment;
import com.example.inventoryapp.Fragments.contactanosFragment;

import com.example.inventoryapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout =  findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.navigation_view);


        setToolbar();


        //Fragment por default

        setFragmentByDefault();

        // Metodos para las opciones

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentsTransaccion = false;
                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.menuprincipal:
                        fragment= new menuprincipalFragment();
                        fragmentsTransaccion = true;
                        break;

                    case R.id.proveedores:
                        fragment= new ProveedoresFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.categoriasproductos:
                        fragment= new CategoriasProductosFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.productos:
                        fragment= new ProductosFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.clientes:
                        fragment= new ClientesFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.entradaproductos:
                        fragment= new EntradaProductoFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.salidaproductos:
                        fragment = new SalidaProductoFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.contactanos:
                        fragment = new contactanosFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.acercade:
                        fragment = new acercadeFragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.cerrarsesion:
                        cerrarSesion();
                        break;
                }

                // Renderizar

                if (fragmentsTransaccion){

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,fragment)
                            .commit();


                    // opcion seleccionada
                    item.setChecked(true);

                    //Titulo opcion seleccionada en toolbar
                    getSupportActionBar().setTitle(item.getTitle());

                    //Cerrar opciones
                    drawerLayout.closeDrawers();

                }

                return true;
            }
        });


    }


    private void setToolbar(){

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setFragmentByDefault(){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,new menuprincipalFragment())
                .commit();

        // variable item
        MenuItem item = navigationView
                        .getMenu()
                        .getItem(0);


        // opcion seleccionada
        item.setChecked(true);

        //Titulo
        getSupportActionBar().setTitle("Inventory App");


    }


    private void cerrarSesion(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        int id_usuario = sharedPreferences.getInt(Constants.ID_USUARIO,-1);

        String url_cerrar_sesion = Constants.BASE_URL+"cerrar_sesion.php" ;

        if(id_usuario == -1){
            Toast.makeText(MainActivity.this, "Error al cerrar sesion", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_cerrar_sesion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(MainActivity.this, "Cierre de sesión exitoso",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al cerrar sesion: "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override

            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id_usuario",String.valueOf(id_usuario));
                return params;

            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
