package com.example.inventoryapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.inventoryapp.Fragments.menuprincipalFragment;
import com.example.inventoryapp.Fragments.opcion1Fragment;
import com.example.inventoryapp.Fragments.opcion2Fragment;
import com.example.inventoryapp.Fragments.opcion3Fragment;
import com.example.inventoryapp.Fragments.opcion4Fragment;
import com.example.inventoryapp.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);


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

                    case R.id.opcion1:
                        fragment= new opcion1Fragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.opcion2:
                        fragment= new opcion2Fragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.opcion3:
                        fragment= new opcion3Fragment();
                        fragmentsTransaccion = true;
                        break;
                    case R.id.opcion4:
                        fragment= new opcion4Fragment();
                        fragmentsTransaccion = true;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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
        };

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


}