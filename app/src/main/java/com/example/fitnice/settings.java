package com.example.fitnice;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnice.databinding.FragmentSettingsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class settings extends Fragment {

    private static final String SPANISH_CODE = "es";

    FragmentSettingsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());

        setHasOptionsMenu(true);

        binding.englishButton.setOnClickListener(v -> {
            Locale locale = Locale.US;
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
            getActivity().recreate();
        });

        binding.spanishButton.setOnClickListener(v -> {
            Locale locale = new Locale(SPANISH_CODE);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
            getActivity().recreate();
        });


        return binding.getRoot();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeGroup(0);
//        inflater.inflate(R.menu.order_filter, menu);
//        inflater.inflate(R.menu.overflow_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    }
}