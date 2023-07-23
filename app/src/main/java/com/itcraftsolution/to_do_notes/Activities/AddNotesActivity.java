package com.itcraftsolution.to_do_notes.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.itcraftsolution.to_do_notes.Fragments.AddNotesFragment;
import com.itcraftsolution.to_do_notes.R;
import com.itcraftsolution.to_do_notes.databinding.ActivityAddNotesBinding;

public class AddNotesActivity extends AppCompatActivity {

    private ActivityAddNotesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frCotainer, new AddNotesFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}