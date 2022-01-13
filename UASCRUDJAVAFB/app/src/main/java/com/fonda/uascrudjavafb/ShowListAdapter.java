package com.fonda.uascrudjavafb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    ArrayList<Movie> list = new ArrayList<>();
    public ShowListAdapter(Context ctx){
        this.context = ctx;
    }
    public void setItems(ArrayList<Movie> emp){
        list.addAll(emp);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new MovieVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieVH vh = (MovieVH) holder;
        Movie emp = list.get(position);
        vh.txt_title.setText(emp.getTitle());
        vh.txt_genre.setText(emp.getGenre());
        vh.txt_duration.setText(emp.getDuration());
        vh.txt_option.setOnClickListener(v->
        {
            PopupMenu popupMenu = new PopupMenu(context,vh.txt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                switch (item.getItemId()){
                    case R.id.menu_edit:
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.putExtra("EDIT",emp);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAOMovie dao = new DAOMovie();
                        dao.remove(emp.getKey()).addOnSuccessListener(suc->
                        {
                            Toast.makeText(context, "Remove Success", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                        }).addOnFailureListener(er->
                        {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                        break;

                }
                return false;
            });
            popupMenu.show();
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
