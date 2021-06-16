package com.example.fitnice;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnice.databinding.FragmentDoRoutine1Binding;
import com.example.fitnice.repository.Status;

import java.util.ArrayList;


public class DoRoutine1 extends Fragment {

    FragmentDoRoutine1Binding binding;

    com.example.fitnice.api.model.Routine routine;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoRoutine1Binding.inflate(getLayoutInflater());

        App app = (App) getActivity().getApplication();



        return binding.getRoot();
    }
}