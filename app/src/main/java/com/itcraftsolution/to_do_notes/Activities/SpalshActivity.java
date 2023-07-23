package com.itcraftsolution.to_do_notes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.itcraftsolution.to_do_notes.R;
import com.itcraftsolution.to_do_notes.databinding.ActivitySpalshBinding;

public class SpalshActivity extends AppCompatActivity {

    private ActivitySpalshBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpalshBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SpalshActivity.this, MainActivity.class));
                finish();
            }
        }, 1200);

    }
}