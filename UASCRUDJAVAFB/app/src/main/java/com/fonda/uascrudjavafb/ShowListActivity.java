package com.fonda.uascrudjavafb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ShowListAdapter adapter;
    DAOMovie dao;
    boolean isLoading = false;
    String key = null;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        swipeRefreshLayout = findViewById(R.id.swip);
        recyclerView = findViewById(R.id.xmlrecylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new ShowListAdapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAOMovie();
        loadData();
        actionBar = getSupportActionBar();
        actionBar.setTitle("List Film");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem < lastVisible+3)
                {
                    if(!isLoading){
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Movie> emps = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren())
                {
                    Movie emp = data.getValue(Movie.class);
                    emp.setKey(data.getKey());
                    emps.add(emp);
                    key = data.getKey();

                }
                adapter.setItems(emps);
                adapter.notifyDataSetChanged();
                isLoading = false;
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}