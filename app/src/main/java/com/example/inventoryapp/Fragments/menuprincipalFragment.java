package com.example.inventoryapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.SharedPreferences;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link menuprincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menuprincipalFragment extends Fragment {

    private CardView cardViewProveedores,cardViewCategorias,cardViewProductos,
                     cardViewClientes, cardViewProductoEntrante, cardViewProductoSaliente;

    private TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public menuprincipalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menuprincipalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static menuprincipalFragment newInstance(String param1, String param2) {
        menuprincipalFragment fragment = new menuprincipalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_menuprincipal, container, false);

        cardViewProveedores = view.findViewById(R.id.cardViewProveedores);
        cardViewCategorias = view.findViewById(R.id.cardViewCategorias);
        cardViewProductos = view.findViewById(R.id.cardViewProductos);
        cardViewClientes = view.findViewById(R.id.cardViewClientes);
        cardViewProductoEntrante = view.findViewById(R.id.cardViewProductoEntrante);
        cardViewProductoSaliente = view.findViewById(R.id.cardViewProductoSaliente);


        textView = view.findViewById(R.id.mbienvenida);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        String nombre_usuario = sharedPreferences.getString(Constants.NOMBRE_USUARIO,"") ;
        String nombre_tienda = sharedPreferences.getString (Constants.NOMBRE_TIENDA,"");

        textView.setText("Bienvenido de vuelta "+nombre_usuario+" a tu tienda '"+nombre_tienda+"'");

        cardViewProveedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProveedoresFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardViewCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CategoriasProductosFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardViewProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProductosFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardViewClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ClientesFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardViewProductoEntrante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EntradaProductoFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardViewProductoSaliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EntradaProductoFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;


    }
}