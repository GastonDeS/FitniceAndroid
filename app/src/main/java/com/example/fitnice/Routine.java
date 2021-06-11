package com.example.fitnice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnice.databinding.FragmentRoutineBinding;

public class Routine extends Fragment {

    FragmentRoutineBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoutineBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


}