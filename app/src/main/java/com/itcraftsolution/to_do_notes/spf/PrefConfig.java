package com.itcraftsolution.to_do_notes.spf;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itcraftsolution.to_do_notes.Models.Notes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfig {
    public static void writeNotesInSpf(Context context, ArrayList<Notes> list){
        Gson gson = new Gson();
        String notes = gson.toJson(list);

        SharedPreferences spf = context.getSharedPreferences("TODONOTES", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putString("list_key", notes);
        edit.apply();
    }

    public static ArrayList<Notes> readNotesInSpf(Context context){
        SharedPreferences spf = context.getSharedPreferences("TODONOTES", Context.MODE_PRIVATE);
        String notes = spf.getString("list_key", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Notes>>() {}.getType();
        ArrayList<Notes> list = gson.fromJson(notes, type);
        return list;
    }
}
