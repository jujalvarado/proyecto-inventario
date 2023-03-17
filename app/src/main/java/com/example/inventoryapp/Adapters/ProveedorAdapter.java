package com.example.inventoryapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryapp.Models.Proveedor;
import com.example.inventoryapp.R;

import java.util.List;

public class ProveedorAdapter  extends RecyclerView.Adapter<ProveedorAdapter.ProveedorViewHolder> {

    private Context mContext;
    private List<Proveedor> mProveedorList;

    public ProveedorAdapter(Context context, List<Proveedor> proveedorList){
        mContext = context;
        mProveedorList = proveedorList;
    }

    @NonNull
    @Override
    public ProveedorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemproveedor,parent,false);
        return new ProveedorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedorAdapter.ProveedorViewHolder holder, int position) {
        Proveedor proveedor = mProveedorList.get(position);

        holder.tvNombre.setText(proveedor.getNombre());
        holder.tvDireccion.setText((proveedor.getDireccion()));
        holder.tvTelefono.setText(proveedor.getTelefono());
        holder.tvCorreo.setText(proveedor.getCorreo());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProveedorActivity.class);
                intent.putExtra("id_proveedor",proveedor.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProveedorList.size();
    }

    public void actualizarDatos(List<Proveedor> proveedorList ){
        mProveedorList = proveedorList;
        notifyDataSetChanged();
    }

    public static class ProveedorViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombre, tvDireccion, tvTelefono,tvCorreo;

        public ProveedorViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvproveedor);
            tvTelefono = itemView.findViewById(R.id.tvtelefono);
            tvDireccion = itemView.findViewById(R.id.tvdireccion);
            tvCorreo = itemView.findViewById(R.id.tvcorreo);
        }
    }


}
