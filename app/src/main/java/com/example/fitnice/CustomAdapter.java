package com.example.fitnice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnice.repository.Status;
import com.example.fitnice.ui.home.HomeFragment;
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
    private Fragment homeFragment;
    private ArrayList<Routine> favs ;

    public CustomAdapter(List<Routine> dataset, Fragment homeFragment, ArrayList<Routine> favs) {
        this.dataSet = dataset;
        this.homeFragment = homeFragment;
        this.favs = favs;
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

        int isLiked = like(holder,position);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id",dataSet.get(position).getId());
            bundle.putInt("isFaved",like(holder,position));
            NavController nav = Navigation.findNavController(holder.itemView);
            nav.navigate(R.id.routine,bundle);
//                Snackbar.make(view,"Element " + getAdapterPosition(), Snackbar.LENGTH_LONG).show();
        });
        App app = (App) homeFragment.getActivity()
                .getApplication();

        holder.itemView.findViewById(R.id.favIcon).setOnClickListener(view -> {
            if (isLiked==0) {
                app.getFavouritesRepository().postFav(dataSet.get(position).getId()).observe(homeFragment, r-> {
                    if (r.getStatus() == Status.SUCCESS) {
                        holder.getFav().setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                        favs.add(dataSet.get(position));
                    }
                });

            } else {
                app.getFavouritesRepository().removeFav(dataSet.get(position).getId()).observe(homeFragment,r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        holder.getFav().setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                        favs.remove(dataSet.get(position));
                    }
                });

            }
        });

    }

    private int like(@NonNull ViewHolder holder, int position) {
        if (favs.stream().anyMatch(routine -> routine.getId().equals(dataSet.get(position).getId()))) {
            holder.getFav().setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final RatingBar ratingBar;
        private final ImageView fav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            fav = itemView.findViewById(R.id.favIcon);
        }

        public ImageView getFav() {
            return fav;
        }

        public TextView getTextView() {
            return textView;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }
    }
}
