package com.itcraftsolution.to_do_notes.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itcraftsolution.to_do_notes.Adapters.RvNotesRecyclerAdapter;
import com.itcraftsolution.to_do_notes.Models.Notes;
import com.itcraftsolution.to_do_notes.R;
import com.itcraftsolution.to_do_notes.databinding.ActivityMainBinding;
import com.itcraftsolution.to_do_notes.databinding.ActivitySpalshBinding;
import com.itcraftsolution.to_do_notes.spf.PrefConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RvNotesRecyclerAdapter adapter;
    private ActivityMainBinding binding;
    private ArrayList<Notes> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = PrefConfig.readNotesInSpf(MainActivity.this);
        if(list == null){
            list = new ArrayList<>();
        }
        adapter = new RvNotesRecyclerAdapter(list, this);
        binding.rvAllNotes.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvAllNotes.setHasFixedSize(false);
        binding.rvAllNotes.setAdapter(adapter);

        binding.btnFabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNotesActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = PrefConfig.readNotesInSpf(MainActivity.this);
        if(list == null){
            list = new ArrayList<>();
        }
        adapter = new RvNotesRecyclerAdapter(list, this);
        binding.rvAllNotes.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvAllNotes.setHasFixedSize(false);
        binding.rvAllNotes.setAdapter(adapter);
    }
}