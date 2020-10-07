package com.carlosveloper.state.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carlosveloper.state.Model.Stickers;
import com.carlosveloper.state.R;


import java.util.List;

public class VistaStickers extends RecyclerView.Adapter<VistaStickers.MultiHolder> {


    List<Stickers> lstvistas_tienda;
    Context contex;
    FragmentManager fragmentManager;
    OnItemClick itemClick;

    public VistaStickers(List<Stickers> lstvistas_tienda) {
        this.lstvistas_tienda = lstvistas_tienda;
    }

    public VistaStickers(List<Stickers> lstvistas_tienda, OnItemClick itemClick) {
        this.lstvistas_tienda = lstvistas_tienda;
        this.itemClick = itemClick;
    }

    public VistaStickers(List<Stickers> lstvistas_tienda, FragmentManager fragmentManager) {
        this.lstvistas_tienda = lstvistas_tienda;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MultiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stickers,parent,false);


        //RecyclerView.LayoutParams layoutParams= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // view.setLayoutParams(layoutParams);
        MultiHolder th= new MultiHolder(view);
        return th;    }

    @Override
    public void onBindViewHolder(@NonNull MultiHolder holder, int position) {
        holder.nombre.setText(""+lstvistas_tienda.get(position).getTituloImage());
        Glide
                .with(holder.imagen.getContext())
                .load(""+lstvistas_tienda.get(position).getUrlImage())
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(holder.imagen);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.e("click","Tienda");

        itemClick.onItemClickImagen(position);
    }
});

    }

    @Override
    public int getItemCount() {
        return lstvistas_tienda.size();
    }

    public class MultiHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView nombre;
        public MultiHolder(@NonNull View itemView) {
            super(itemView);
            imagen=itemView.findViewById(R.id.multi_foto);
            nombre=itemView.findViewById(R.id.multi_nombre);
        }
    }


    public interface  OnItemClick{
        void onItemClickImagen(int position);
    }
}
