package com.example.fitnice;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnice.App;
import com.example.fitnice.CustomAdapter;
import com.example.fitnice.R;
import com.example.fitnice.api.model.Executions;
import com.example.fitnice.api.model.Routine;
import com.example.fitnice.databinding.FragmentHistoryBinding;
import com.example.fitnice.databinding.FragmentNotificationsBinding;
import com.example.fitnice.repository.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class History extends Fragment {

    FragmentHistoryBinding binding;

    List<Routine> myRoutines = new ArrayList<>();
    App app ;
    int myRoutinesPage;
    boolean myRoutinesLastPage;
    CustomAdapter customAdapter;

    ArrayList<Routine> favs = new ArrayList<>();
    int favPage = 0;
    boolean topFavPage =false;

    String order = "date";
    String direction = "asc";
    int page = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(getLayoutInflater());

        app = (App) getActivity().getApplication();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                getActivity().onBackPressed();
            }
        });

        getActivity().setTitle(getString(R.string.historyTitle));

        setHasOptionsMenu(true);

        loadFavs();

        reloadRoutines();

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);

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
            }
        });
    }

    private void loadRoutines() {
        app.getExecutionsRepository().getExecutions(order,direction,page).observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS){
                r.getData().getContent().forEach(new Consumer<Executions>() {
                    @Override
                    public void accept(Executions executions) {
                        myRoutines.add(executions.getRoutine());
                    }
                });
                customAdapter.notifyDataSetChanged();
            }
        });
    }

    public void reloadRoutines() {
        app.getExecutionsRepository().getExecutions(order,direction,page).observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS){
                myRoutines = new ArrayList<>();
                r.getData().getContent().forEach(new Consumer<Executions>() {
                    @Override
                    public void accept(Executions executions) {
                        myRoutines.add(executions.getRoutine());
                    }
                });
                customAdapter = new CustomAdapter(myRoutines,this,favs,getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE);
                if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
                    binding.rutinesView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
                else
                    binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                binding.rutinesView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                            page += 1;
                            loadRoutines();
                        }
                    }});
                binding.rutinesView.setAdapter(customAdapter);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        menu.removeGroup(0);
        inflater.inflate(R.menu.order_filter, menu);
//        inflater.inflate(R.menu.overflow_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orderDate:
                item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                changeOrder(item,"date");
                break;
            case R.id.orderCategory:
                item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                changeOrder(item,"categoryId");
                break;
            case R.id.orderDifficulty:
                item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                changeOrder(item,"difficulty");
                break;
            case R.id.orderStars:
                item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                changeOrder(item,"averageRating");
                break;
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeOrder(@NonNull MenuItem item,String order) {
        if (this.order.equals(order)) {
            if (direction.equals("desc")) {
                direction = "asc";
                item.setIcon(R.drawable.ic_baseline_arrow_upward_24);
            } else {
                direction = "desc";
                item.setIcon(R.drawable.ic_baseline_arrow_downward_24);
            }
        } else {
            this.order = order;
            direction = "desc";
            item.setIcon(R.drawable.ic_baseline_arrow_downward_24);
        }
        reloadRoutines();
    }
}