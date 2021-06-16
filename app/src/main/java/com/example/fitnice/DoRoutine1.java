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

        app.getRoutinesRepository().getRoutine(getArguments().getInt("id"))
                .observe(getActivity(), r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        routine = r.getData();
                        app.getCyclesRepository().getCycles(r.getData().getId())
                                .observe(getActivity(), rc -> {
                                    if (rc.getStatus() == Status.SUCCESS) {
                                        app.getExerciseRepository().getExercises(r.getData().getId()).observe(getActivity(), re ->{
                                            if (re.getStatus() == Status.SUCCESS) {
                                                binding.ExerciseName.setText(re.getData().getContent().get(0).getExercise().getName());
                                                binding.ExerciseDescription.setText(re.getData().getContent().get(0).getExercise().getDetail());
                                                binding.Player.playerExName.setText(re.getData().getContent().get(0).getExercise().getName());
                                            }
                                        });

//                                        //TODO implement player
                                    }
                                });

                    }
                });

        binding.Player.playerExName.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),DoRoutine2.class));
        });

        return binding.getRoot();
    }
}