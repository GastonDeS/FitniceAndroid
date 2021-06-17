package com.example.fitnice.ui.dashboard;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnice.App;
import com.example.fitnice.CustomAdapter;
import com.example.fitnice.R;
import com.example.fitnice.api.model.Routine;
import com.example.fitnice.databinding.FragmentDashboardBinding;
import com.example.fitnice.repository.Status;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FragmentDashboardBinding binding;

    ArrayList<Routine> favs = new ArrayList<>();
    App app ;
    int favPage;
    boolean topFavPage;
    CustomAdapter customAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(getLayoutInflater());

        app = (App) getActivity().getApplication();

        getActivity().setTitle(getString(R.string.favs));

        loadFavs();

        customAdapter = new CustomAdapter(favs,this,favs);
        loadFavs();

        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        Activity a = getActivity();
        if (a!=null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    private void loadFavs() {
        app.getFavouritesRepository().getFavourites(favPage).observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                favs.addAll(r.getData().getContent());
                topFavPage = r.getData().getIsLastPage();
                favPage++;
                if (!topFavPage) loadFavs();
                else reloadFavs();
            }
        });
    }

    public void reloadFavs() {

        customAdapter = new CustomAdapter(favs,this,favs,getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            binding.rutinesView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        else
            binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.rutinesView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }});
        binding.rutinesView.setAdapter(customAdapter);
    }
}