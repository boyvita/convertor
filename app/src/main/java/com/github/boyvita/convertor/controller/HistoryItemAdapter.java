package com.github.boyvita.convertor.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.boyvita.convertor.R;
import com.github.boyvita.convertor.model.HistoryItem;

import java.util.ArrayList;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemViewHolder> {
    private ArrayList<HistoryItem> historyItems;

    public static class HistoryItemViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView result;

        public HistoryItemViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            result = itemView.findViewById(R.id.result);
        }
    }

    public HistoryItemAdapter(ArrayList<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        HistoryItemViewHolder hivh = new HistoryItemViewHolder(v);
        return  hivh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemViewHolder holder, int position) {
        HistoryItem currentItem = historyItems.get(position);
       holder.date.setText(currentItem.getDate());
        holder.result.setText(
                String.format("%.4f", currentItem.getValueFrom()) +
                currentItem.getValuteFrom() +
                " = " +
                String.format("%.4f", currentItem.getValueTo()) +
                currentItem.getValuteTo()
        );
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

}