package com.example.fitnice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FilterOptionsItemAdapter extends RecyclerView.Adapter<FilterOptionsItemAdapter.FilterItemViewHolder> {

    private ArrayList<String> filterOptionsItems;

    public FilterOptionsItemAdapter(ArrayList<String> filterOptionsItems) {
        this.filterOptionsItems = filterOptionsItems;
    }

    @NonNull
    @Override
    public FilterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_options_items,parent,false);
        return new FilterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterItemViewHolder holder, int position) {
        if (filterOptionsItems.get(position)!=null && holder.itemName != null) {
            holder.itemName.setText(filterOptionsItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return filterOptionsItems.size();
    }

    public static class FilterItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        public FilterItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView = itemView.findViewById(R.id.filterItemText);

        }
    }
}
