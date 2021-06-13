package com.example.fitnice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnice.databinding.FragmentDoRoutine1Binding;

import java.util.ArrayList;


public class DoRoutine1 extends Fragment {

    FragmentDoRoutine1Binding binding;

    ArrayList<Cycle> dataSet = new ArrayList<>();
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoRoutine1Binding.inflate(getLayoutInflater());

        for (int i = 0; i < 2; i++){
            exerciseArrayList.add(new Exercise("tuvi","la mejor rutina","30 s","30 reps"));
        }

        for(int i =1; i < 6; i++) {
            dataSet.add(new Cycle("Ciclo " + i,exerciseArrayList));
        }

        binding.ExerciseDescription.setText(dataSet.get(0).getExerciseList().get(0).getDescription());
        binding.ExerciseName.setText(dataSet.get(0).getExerciseList().get(0).getExerciseName());
        binding.Timer.setText("10:24");

        CycleAdapter cycleAdapter = new CycleAdapter(dataSet,null);

        binding.rutinesView.setAdapter(cycleAdapter);
        binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return binding.getRoot();
    }
}