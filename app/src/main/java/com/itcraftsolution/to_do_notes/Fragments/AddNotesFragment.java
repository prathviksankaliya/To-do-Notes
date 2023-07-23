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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddNotesBinding.inflate(getLayoutInflater());

        list = PrefConfig.readNotesInSpf(requireContext());
        if(list == null){
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
                    }
//                    else if (binding.btnAddNoteSave.getText().toString().equals("Update")) {
//                        Notes updateNotes = new Notes(id, binding.edTitle.getText().toString().trim(), binding.edNotes.getText().toString().trim(), date, pin, selectedNoteColor);
//                        notesViewModel.updateNotes(updateNotes);
//                        Toast.makeText(AddNotesActivity.this, "Notes Updated!!", Toast.LENGTH_SHORT).show();
//                    }
                    requireActivity().finish();
                }
            }
        });
        return binding.getRoot();
    }
}