package com.example.fitnice.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnice.App;
import com.example.fitnice.CustomAdapter;
import com.example.fitnice.R;
import com.example.fitnice.api.model.Routine;
import com.example.fitnice.databinding.FragmentNotificationsBinding;
import com.example.fitnice.repository.Status;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    FragmentNotificationsBinding binding;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(getLayoutInflater());

        app = (App) getActivity().getApplication();

        getActivity().setTitle(/*getResources().getString(*/"Mis Rutinas"/*)*/);

        setHasOptionsMenu(true);

        loadFavs();

        reloadRoutines();

        return binding.getRoot();
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
        app.getRoutinesRepository().getRoutinesSorted(order,direction,page).observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS){
                myRoutines.addAll( r.getData().getContent());
                customAdapter.notifyDataSetChanged();
            }
        });
    }

    public void reloadRoutines() {
        app.getMyRoutinesRepository().getMyRoutinesSorted(order,direction,page).observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS){
                myRoutines = r.getData().getContent();
                customAdapter = new CustomAdapter(myRoutines,this,favs);
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
        inflater.inflate(R.menu.overflow_menu,menu);
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
            case R.id.orderSports:
                item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                changeOrder(item,"date");
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