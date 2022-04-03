package com.example.securednotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class AddRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//redirecting to the add note activity
        MaterialButton addnotebtn = findViewById(R.id.addnewbttn);
        addnotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddRecipe.this,AddNewRecipeActivity.class));

            }
        });

//initializing realm results
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
//sorting displayed data in descending order
        RealmResults<Note> notesList = realm.where(Note.class).findAll().sort("createdTime", Sort.DESCENDING);
//passing notes from the adapter
        RecyclerView recyclerView= findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter((getApplicationContext()),notesList);
        recyclerView.setAdapter(myAdapter);
//notifing the adapter to add a created note
        notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });

    }
}