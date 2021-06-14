package com.example.fitnice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnice.ui.home.HomeFragmentDirections;
import com.google.android.material.snackbar.Snackbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.fitnice.api.model.Routine;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Routine> dataSet;

    public CustomAdapter(List<Routine> dataset) {
        this.dataSet = dataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rutina, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getTextView().setText(dataSet.get(position).getName());
        holder.getRatingBar().setRating(dataSet.get(position).getAverageRating());

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id",dataSet.get(position).getId());
            NavController nav = Navigation.findNavController(holder.itemView);
            nav.navigate(R.id.action_navigation_home_to_routine,bundle);
//                Snackbar.make(view,"Element " + getAdapterPosition(), Snackbar.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        public TextView getTextView() {
            return textView;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }
    }
}
