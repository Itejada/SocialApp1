package com.example.gerard.socialapp.view.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.gerard.socialapp.R;

public class PerfilViewHolder extends RecyclerView.ViewHolder {
    public ImageView fodnoPerfil,imagenPerfil;

    public PerfilViewHolder(@NonNull View itemView) {
        super(itemView);

        imagenPerfil = itemView.findViewById(R.id.profileImage);
        fodnoPerfil = itemView.findViewById(R.id.profileImageBackground);
    }
}
