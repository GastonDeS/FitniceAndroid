package com.example.fitnice;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.FragmentSeeRoutineBinding;
import com.example.fitnice.repository.Status;

public class Routine extends Fragment {

    FragmentSeeRoutineBinding binding;

    com.example.fitnice.api.model.Routine routine;

    Dialog routineDialog;
    Dialog exerciseDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSeeRoutineBinding.inflate(getLayoutInflater());
        binding.routineImage.rutineImage.setClipToOutline(true);

        App app = (App) getActivity().getApplication();

        app.getRoutinesRepository().getRoutine(getArguments().getInt("id"))
                .observe(getActivity(),r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        routine = r.getData();
                        binding.routineImage.textStartsFav.name.setText(routine.getName());
                        binding.routineImage.textStartsFav.ratingBar.setRating(routine.getAverageRating());
                        app.getCyclesRepository().getCycles(r.getData().getId())
                                .observe(getActivity(),rc -> {
                                    if (rc.getStatus() == Status.SUCCESS) {
                                        CycleAdapter cycleAdapter = new CycleAdapter(rc.getData().getContent(),this);
                                        binding.rutinesView.setAdapter(cycleAdapter);
                                        binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                                    }
                        });

                    }
                });

        binding.startRoutineBtn.button2.setOnClickListener(view -> {
            NavController nav = Navigation.findNavController(this.binding.view);
            nav.navigate(R.id.action_routine_to_doRoutine1);
        });

        routineDialog = new Dialog(this.getContext());
        exerciseDialog = new Dialog(this.getContext());
        routineDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exerciseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.routineImage.RoutineInfo.setOnClickListener(this::ShowPopupRoutine);


        return binding.getRoot();
    }

    public void ShowPopupExercise(ExerciseContent exercise) {
        TextView exerciseName;
        TextView description;
        TextView times;
        TextView durationS;

        exerciseDialog.setContentView(R.layout.exercise_info);
        exerciseName = exerciseDialog.findViewById(R.id.exerciseNameDialog);
        exerciseName.setText(exercise.getExercise().getName());
        description = exerciseDialog.findViewById(R.id.exerciseDescriptionDialog);
        description.setText(exercise.getExercise().getDetail());
        times = exerciseDialog.findViewById(R.id.times);
        times.setText(getResources().getString(R.string.reps,exercise.getRepetitions().toString()));
        durationS = exerciseDialog.findViewById(R.id.durationS);
        durationS.setText(getResources().getString(R.string.seconds,exercise.getDuration().toString()));


        exerciseDialog.show();
    }

    public void ShowPopupRoutine(View v) {
        TextView routineName;
        TextView category;
        TextView difficulty;
        TextView createdBy;

        routineDialog.setContentView(R.layout.routine_info);
        routineName = routineDialog.findViewById(R.id.routineNameDialog);
        routineName.setText(routine.getName());
        category = routineDialog.findViewById(R.id.routineCategoryDialog);
        category.setText(routine.getCategory().getName());
        difficulty = routineDialog.findViewById(R.id.routineDificultyDialog);
        difficulty.setText(routine.getDifficulty());
        createdBy = routineDialog.findViewById(R.id.routineCreatedByDialog);
        createdBy.setText(routine.getUser().getUsername());
        routineDialog.show();
    }


}