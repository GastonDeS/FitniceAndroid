package com.example.fitnice.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import com.example.fitnice.CustomAdapter;
import com.example.fitnice.R;
import com.example.fitnice.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private HomeViewModel homeViewModel;

    @NonNull
    FragmentHomeBinding binding;

    ArrayList<String> dataSet = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());


        for(int i =1; i < 10; i++) {
            dataSet.add("Rutina diamante " + i);
        }

        CustomAdapter adapter = new CustomAdapter(dataSet);
        binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.rutinesView.setAdapter(adapter);

        binding.button.setOnClickListener(this::showPopUp);

        return binding.getRoot();
    }

    public void showPopUp(View view) {
        PopupMenu popupMenu =  new PopupMenu(this.getContext(),view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.filter_popup_menu);
        popupMenu.show();
    }

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