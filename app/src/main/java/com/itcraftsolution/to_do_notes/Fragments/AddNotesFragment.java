package com.itcraftsolution.to_do_notes.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.itcraftsolution.to_do_notes.Activities.AddNotesActivity;
import com.itcraftsolution.to_do_notes.Models.Notes;
import com.itcraftsolution.to_do_notes.R;
import com.itcraftsolution.to_do_notes.databinding.FragmentAddNotesBinding;
import com.itcraftsolution.to_do_notes.spf.PrefConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddNotesFragment extends Fragment {

    private FragmentAddNotesBinding binding;
    private boolean pin = false;
    private ArrayList<Notes> list;
    private int id, position;
    private String title, desc;
    private SharedPreferences spf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddNotesBinding.inflate(getLayoutInflater());
        spf = requireContext().getSharedPreferences("UpdateNotes", Context.MODE_PRIVATE);
        loadData();

        list = PrefConfig.readNotesInSpf(requireContext());
        if (list == null) {
            list = new ArrayList<>();
        }
        String date = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).format(new Date());
        binding.txEditDate.setText(date);

        binding.igBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().finish();
            }
        });

        binding.btnAddNoteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edTitle.getText().toString().isEmpty()) {
                    Snackbar.make(binding.mainLayout, "Please Set Notes Title!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setTextColor(getResources().getColor(R.color.white))
                            .show();
                    binding.edTitle.requestFocus();
                } else if (binding.edNotes.getText().toString().isEmpty()) {
                    Snackbar.make(binding.mainLayout, "Please Set Notes Text!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setTextColor(getResources().getColor(R.color.white))
                            .show();
                    binding.edNotes.requestFocus();
                } else {
                    Notes notes = new Notes(binding.edTitle.getText().toString().trim(), binding.edNotes.getText().toString().trim(), date, pin);
                    if (binding.btnAddNoteSave.getText().toString().equals("Save")) {
                        list.add(notes);
                        PrefConfig.writeNotesInSpf(requireContext(), list);
                        Toast.makeText(requireContext(), "Notes Saved!!", Toast.LENGTH_SHORT).show();
                    } else if (binding.btnAddNoteSave.getText().toString().equals("Update")) {
                        Notes updateNotes = new Notes(id, binding.edTitle.getText().toString().trim(), binding.edNotes.getText().toString().trim(), date, pin);
                        list.set(position, updateNotes);
                        PrefConfig.writeNotesInSpf(requireContext(), list);
                        SharedPreferences.Editor edit =  spf.edit();
                        edit.putBoolean("insert", false);
                        edit.apply();
                        Toast.makeText(requireContext(), "Notes Updated!!", Toast.LENGTH_SHORT).show();

                    }
                    requireActivity().finish();
                }
            }
        });
        return binding.getRoot();
    }

    private void loadData() {

        boolean update = spf.getBoolean("insert", false);
        if(update) {
            id = spf.getInt("id", 0);
            position = spf.getInt("position", 0);
            String title = spf.getString("title", "");
            String desc = spf.getString("desc", "");

            binding.edTitle.setText(title);
            binding.edNotes.setText(desc);
            binding.btnAddNoteSave.setText("Update");
        }else{
            binding.btnAddNoteSave.setText("Save");
        }
    }
}