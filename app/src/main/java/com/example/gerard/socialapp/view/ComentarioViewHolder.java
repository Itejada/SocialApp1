package com.example.gerard.socialapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gerard.socialapp.R;

public class ComentarioViewHolder extends RecyclerView.ViewHolder {


    public TextView userComentarioData,userComentarioName;

    public ComentarioViewHolder(@NonNull View itemView) {
        super(itemView);
        userComentarioData = itemView.findViewById(R.id.userComentarioData);

        userComentarioName = itemView.findViewById(R.id.userComentarioName);


    }
}