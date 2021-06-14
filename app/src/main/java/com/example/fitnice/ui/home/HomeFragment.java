package com.example.fitnice.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.fitnice.App;
import com.example.fitnice.CustomAdapter;
import com.example.fitnice.FilterOptions;
import com.example.fitnice.FilterOptionsAdapter;
import com.example.fitnice.R;
import com.example.fitnice.api.model.Routine;
import com.example.fitnice.databinding.FragmentHomeBinding;
import com.example.fitnice.repository.Status;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private HomeViewModel homeViewModel;

    @NonNull
    FragmentHomeBinding binding;

    List<Routine> dataSet ;
    ArrayList<FilterOptions> filterOptionsArrayList = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());


        App app = (App) getActivity().getApplication();

        app.getRoutinesRepository().getRoutines().observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS){
                dataSet = r.getData().getContent();
                CustomAdapter adapter = new CustomAdapter(dataSet);
                binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                binding.rutinesView.setAdapter(adapter);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        FilterOptionsAdapter filterOptionsAdapter = new FilterOptionsAdapter(filterOptionsArrayList);

        binding.filterView.setAdapter(filterOptionsAdapter);
        binding.filterView.setLayoutManager(layoutManager);

        binding.button.setOnClickListener(view -> {
            if (binding.filterView.getVisibility()==View.GONE){
                TransitionManager.beginDelayedTransition(container,new AutoTransition());
                binding.filterView.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition( container,new AutoTransition());
                binding.filterView.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

//    public void showPopUp(View view) {
//        but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (filterOptItemsView.getVisibility()==View.GONE){
//                    TransitionManager.beginDelayedTransition(parent,new AutoTransition());
//                    filterOptItemsView.setVisibility(View.VISIBLE);
//                    arrowBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
//                } else {
//                    TransitionManager.beginDelayedTransition(parent,new AutoTransition());
//                    filterOptItemsView.setVisibility(View.GONE);
//                    arrowBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
//                }
//            }
//        });
////        PopupMenu popupMenu =  new PopupMenu(this.getContext(),view);
////        popupMenu.setOnMenuItemClickListener(this);
////        popupMenu.inflate(R.menu.filter_popup_menu);
////        popupMenu.show();
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this.getContext(), "item 1",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this.getContext(), "another",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}