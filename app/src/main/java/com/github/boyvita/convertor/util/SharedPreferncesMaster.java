package com.github.boyvita.convertor.util;

import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.github.boyvita.convertor.model.HistoryItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferncesMaster {
    ContextWrapper context;

    public SharedPreferncesMaster(ContextWrapper context) {
        this.context = context;
    }

    //saveJson pull a JSON file from mySQl server then saves that file in its JSON type eg .json
    public void addItem(HistoryItem historyItem){
        ArrayList<HistoryItem> items = getItems();
        if (items.size() == 10) {
            items.remove(9);
        }
        items.add(0, historyItem);
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("history", json);
        editor.apply();
    }

    public ArrayList<HistoryItem> getItems() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("history", null);
        Type type = new TypeToken<ArrayList<HistoryItem>>() {}.getType();
        ArrayList<HistoryItem> items = gson.fromJson(json, type);
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
