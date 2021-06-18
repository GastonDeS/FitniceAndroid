package com.example.fitnice;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnice.databinding.FragmentSettingsBinding;

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
}