package com.example.inventoryapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.inventoryapp.Activities.ProveedorActivity;
import com.example.inventoryapp.Adapters.ProveedorAdapter;
import com.example.inventoryapp.Constants;
import com.example.inventoryapp.Models.Proveedor;
import com.example.inventoryapp.R;
import com.example.inventoryapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProveedoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProveedoresFragment extends Fragment {

     private RecyclerView recyclerViewProveedores;
     private ProveedorAdapter proveedorAdapter;
     private List<Proveedor> proveedorList = new ArrayList<>();
     private TextView tvProveedores;
     private Button btnAgregarProveedores;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProveedoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment option1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProveedoresFragment newInstance(String param1, String param2) {
        ProveedoresFragment fragment = new ProveedoresFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proveedores, container, false);


        btnAgregarProveedores = view.findViewById(R.id.ProAgregarRegistro);
        tvProveedores = view.findViewById(R.id.tv_no_proveedores);
        recyclerViewProveedores = view.findViewById(R.id.recyclerProveedores);


        proveedorAdapter = new ProveedorAdapter(getActivity(),proveedorList);

        recyclerViewProveedores.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewProveedores.setAdapter(proveedorAdapter);

        cargarProveedores();

        btnAgregarProveedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProveedorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    String url = Constants.BASE_URL+"proveedor_select.php";

    public void cargarProveedores(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (jsonArray.length() == 0){
                                recyclerViewProveedores.setVisibility(View.GONE);
                                tvProveedores.setVisibility(View.VISIBLE);
                            }else{
                                recyclerViewProveedores.setVisibility(View.VISIBLE);
                                tvProveedores.setVisibility(View.GONE);

                                for (int i = 0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Proveedor proveedor = new Proveedor();

                                    proveedor.setId(object.getInt("id"));
                                    proveedor.setNombre(object.getString("nombre"));
                                    proveedor.setDireccion(object.getString("direccion"));
                                    proveedor.setTelefono(object.getString("telefono"));
                                    proveedor.setCorreo(object.getString("correo"));

                                    proveedorList.add(proveedor);
                                }

                                proveedorAdapter.actualizarDatos(proveedorList);
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error al obtener proveedores", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error al obtener proveedores", Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
                int id_tienda = sharedPreferences.getInt(Constants.ID_TIENDA,-1);
                HashMap<String, String> params = new HashMap();
                params.put("id_tienda",String.valueOf(id_tienda));
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}