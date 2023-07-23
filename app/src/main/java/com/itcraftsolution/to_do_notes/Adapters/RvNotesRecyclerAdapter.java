package com.itcraftsolution.to_do_notes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ProxyFileDescriptorCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                BottomSheetDialog menuBottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);
                View menuSheetView = LayoutInflater.from(context).inflate(R.layout.note_option, view.findViewById(R.id.bottomSheetMenu));
                menuBottomSheetDialog.setContentView(menuSheetView);
                menuBottomSheetDialog.show();
                ImageView igEdit, igDelete, igShare;

                igEdit = menuSheetView.findViewById(R.id.btnEdit);
                igDelete = menuSheetView.findViewById(R.id.btnMenuDelete);
                igShare = menuSheetView.findViewById(R.id.btnShare);

                igEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PrefConfig.updateNotes(context, notes.getId(), holder.getAdapterPosition(), notes.getTitle(), notes.getDesc(), notes.getDate(), true);
                        context.startActivity(new Intent(context, AddNotesActivity.class));
                        menuBottomSheetDialog.dismiss();
                    }
                });

                igDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);
                        View sheetView = LayoutInflater.from(context).inflate(R.layout.delete_box, view.findViewById(R.id.bottomSheetDelete));
                        bottomSheetDialog.setContentView(sheetView);
                        bottomSheetDialog.show();
                        Button canclebtn, deletebtn;
                        canclebtn = sheetView.findViewById(R.id.btnCancle);
                        deletebtn = sheetView.findViewById(R.id.btnDelete);

                        deletebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                list.remove(holder.getAdapterPosition());
                                PrefConfig.writeNotesInSpf(context, list);
                                notifyDataSetChanged();
                                bottomSheetDialog.dismiss();
                                Snackbar.make(holder.binding.getRoot(), "Deleted Successfully!!", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(context.getResources().getColor(R.color.red))
                                        .setTextColor(context.getResources().getColor(R.color.white))
                                        .show();
                            }
                        });
                        canclebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheetDialog.dismiss();
                            }
                        });
                        menuBottomSheetDialog.dismiss();
                    }
                });

                igShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, notes.getTitle());
                        intent.putExtra(Intent.EXTRA_TEXT, notes.getDesc());
                        context.startActivity(Intent.createChooser(intent, "Share Notes!!"));
                        menuBottomSheetDialog.dismiss();
                    }
                });

                return true;
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
