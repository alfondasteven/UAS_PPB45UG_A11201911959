package com.fonda.uascrudjavafb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fonda.uascrudjavafb.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //configure actionbar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Admin");

        //firebaseuser
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //logout
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        final EditText etTitle = findViewById(R.id.etTitle);
        final EditText etGenre = findViewById(R.id.etGenre);
        final EditText etDuration = findViewById(R.id.etDuration);
        Button btn = findViewById(R.id.btnSubmit);
        Button btnShow = findViewById(R.id.btnShow);
        Button btnLogout = findViewById(R.id.btnLogout);
        btnShow.setOnClickListener(v->
        {
            Intent intent = new Intent(MainActivity.this,ShowListActivity.class);
            startActivity(intent);
        });
        DAOMovie dao = new DAOMovie();
        Movie emp_edit = (Movie)getIntent().getSerializableExtra("EDIT");
        if(emp_edit != null)
        {
            btn.setText("UPDATE");
            etTitle.setText(emp_edit.getTitle());
            etGenre.setText(emp_edit.getGenre());
            etDuration.setText(emp_edit.getDuration());
            btnShow.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            actionBar = getSupportActionBar();
            actionBar.setTitle("Edit");

        }
        else {
            btn.setText("SUBMIT");
            btnShow.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(v->
        {
            Movie emp = new Movie(etTitle.getText().toString(),etGenre.getText().toString(),etDuration.getText().toString());
            if(emp_edit==null) {


                dao.add(emp).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Success Inserted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else{
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("title",etTitle.getText().toString());
                hashMap.put("genre",etGenre.getText().toString());
                hashMap.put("duration",etDuration.getText().toString());
                dao.update(emp_edit.getKey(),hashMap).addOnSuccessListener(suc->
                {
                    Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er->
                {
                    Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                });}
        
        });


    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }

    }
}