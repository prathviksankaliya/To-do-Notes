package com.itcraftsolution.to_do_notes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ProxyFileDescriptorCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.itcraftsolution.to_do_notes.Activities.AddNotesActivity;
import com.itcraftsolution.to_do_notes.Fragments.AddNotesFragment;
import com.itcraftsolution.to_do_notes.Models.Notes;
import com.itcraftsolution.to_do_notes.R;
import com.itcraftsolution.to_do_notes.databinding.RvSampleNotesBinding;
import com.itcraftsolution.to_do_notes.spf.PrefConfig;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RvNotesRecyclerAdapter extends RecyclerView.Adapter<RvNotesRecyclerAdapter.viewHolder> {

    private ArrayList<Notes> list;
    private Context context;

    public RvNotesRecyclerAdapter(ArrayList<Notes> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RvNotesRecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_sample_notes, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvNotesRecyclerAdapter.viewHolder holder, int position) {
        Notes notes = list.get(position);
        holder.binding.txNotesTitle.setText(notes.getTitle());
        holder.binding.txNotesDesc.setText(notes.getDesc());
        holder.binding.txNotesDate.setText(notes.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefConfig.updateNotes(context, notes.getId(), holder.getAdapterPosition(), notes.getTitle(), notes.getDesc(), notes.getDate(), true);
                context.startActivity(new Intent(context, AddNotesActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{

        RvSampleNotesBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvSampleNotesBinding.bind(itemView);

        }
    }
}
