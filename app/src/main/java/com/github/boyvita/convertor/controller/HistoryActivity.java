package com.github.boyvita.convertor.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.github.boyvita.convertor.R;
import com.github.boyvita.convertor.model.HistoryItem;
import com.github.boyvita.convertor.util.SharedPreferncesMaster;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        SharedPreferncesMaster master = new SharedPreferncesMaster(this);
        ArrayList<HistoryItem> items = master.getItems();
        Log.e("MYAPP", items.toString());
        buildRecyclerView(items);
    }
    public void buildRecyclerView(ArrayList<HistoryItem> items) {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HistoryItemAdapter(items);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuMaster.createMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuMaster.redirect(getApplicationContext(), item.getTitle());
        return super.onOptionsItemSelected(item);
    }

}