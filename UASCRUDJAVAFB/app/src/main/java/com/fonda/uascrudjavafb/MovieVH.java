package com.fonda.uascrudjavafb;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieVH extends RecyclerView.ViewHolder {

    public TextView txt_title,txt_genre,txt_duration,txt_option;


    public MovieVH(@NonNull View itemView) {
        super(itemView);
        txt_title = itemView.findViewById(R.id.txt_title);
        txt_genre = itemView.findViewById(R.id.txt_genre);
        txt_duration = itemView.findViewById(R.id.txt_duration);
        txt_option = itemView.findViewById(R.id.txt_option);
    }
}
