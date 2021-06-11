package com.example.fitnice;

import android.app.Activity;
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

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<String> dataSet;

    public CustomAdapter(ArrayList<String> dataset) {
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

        holder.getTextView().setText(dataSet.get(position));
        holder.getRatingBar().setRating((position+1)%5+1);
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



            itemView.setOnClickListener(view -> {
                NavController nav = Navigation.findNavController(itemView);
                nav.navigate(R.id.action_navigation_home_to_routine);
                Snackbar.make(view,"Element " + getAdapterPosition(), Snackbar.LENGTH_LONG).show();
            });

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
