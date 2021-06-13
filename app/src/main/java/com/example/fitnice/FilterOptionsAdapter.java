package com.example.fitnice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import java.util.ArrayList;

public class FilterOptionsAdapter extends RecyclerView.Adapter<FilterOptionsAdapter.ViewHolder>{

    private ArrayList<FilterOptions> filterOptionsArray;

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public FilterOptionsAdapter(ArrayList<FilterOptions> filterOptions) {
        this.filterOptionsArray = filterOptions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_options, parent, false);

        return new ViewHolder(view,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilterOptions options = filterOptionsArray.get(position);

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.filterOptItemsView.getContext(),LinearLayoutManager.VERTICAL,false);

        FilterOptionsItemAdapter filterOptionsItemAdapter = new FilterOptionsItemAdapter(options.filterOptionsItemsNames);

        holder.catName.setText(options.filterCatName);

        holder.filterOptItemsView.setAdapter(filterOptionsItemAdapter);
        holder.filterOptItemsView.setLayoutManager(layoutManager);
        holder.filterOptItemsView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return filterOptionsArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView catName;
        RecyclerView filterOptItemsView;
        Button arrowBtn;

        public ViewHolder(@NonNull View itemView,@NonNull ViewGroup  parent) {
            super(itemView);

            catName = itemView.findViewById(R.id.filterCatTitle);
            filterOptItemsView = itemView.findViewById(R.id.filterOptView);
            arrowBtn = itemView.findViewById(R.id.arrowBtn);

            arrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (filterOptItemsView.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(parent,new AutoTransition());
                        filterOptItemsView.setVisibility(View.VISIBLE);
                        arrowBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    } else {
                        TransitionManager.beginDelayedTransition(parent,new AutoTransition());
                        filterOptItemsView.setVisibility(View.GONE);
                        arrowBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }
                }
            });
        }
    }
}
