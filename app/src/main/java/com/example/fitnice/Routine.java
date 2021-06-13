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

import com.example.fitnice.databinding.FragmentSeeRoutineBinding;

import java.util.ArrayList;

public class Routine extends Fragment {

    FragmentSeeRoutineBinding binding;

    RoutineInfo routineInfo;

    ArrayList<Cycle> dataSet = new ArrayList<>();
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    Dialog routineDialog;
    Dialog exerciseDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSeeRoutineBinding.inflate(getLayoutInflater());
        binding.routineImage.rutineImage.setClipToOutline(true);

        for (int i = 0; i < 1; i++){
            exerciseArrayList.add(new Exercise("tuvi","la mejor rutina","30 s","30 reps"));
        }

        for (int i = 0; i < 1; i++){
            exerciseArrayList.add(new Exercise("wujuuu","lo mejor de todo","15 s","20 reps"));
        }

        for(int i =1; i < 6; i++) {
            dataSet.add(new Cycle("Ciclo " + i,exerciseArrayList));
        }

        routineInfo = new RoutineInfo("The best Routine ever","lo mejor que vas a ver","Piernas","30:00","Novato","Gaston",dataSet);

        CycleAdapter cycleAdapter = new CycleAdapter(routineInfo.cycles,this);

        binding.rutinesView.setAdapter(cycleAdapter);
        binding.rutinesView.setLayoutManager(new LinearLayoutManager(this.getContext()));

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

    public void ShowPopupExercise(Exercise exercise) {
        TextView exerciseName;
        TextView description;

        exerciseDialog.setContentView(R.layout.exercise_info);
        exerciseName = exerciseDialog.findViewById(R.id.exerciseNameDialog);
        exerciseName.setText(exercise.getExerciseName());
        description = exerciseDialog.findViewById(R.id.exerciseDescriptionDialog);
        description.setText(exercise.getDescription());

        exerciseDialog.show();
    }

    public void ShowPopupRoutine(View v) {
        TextView routineName;
        TextView category;
        TextView difficulty;
        TextView duration;
        TextView createdBy;

        routineDialog.setContentView(R.layout.routine_info);
        routineName = routineDialog.findViewById(R.id.routineNameDialog);
        routineName.setText(routineInfo.name);
        category = routineDialog.findViewById(R.id.routineCategoryDialog);
        category.setText(routineInfo.category);
        difficulty = routineDialog.findViewById(R.id.routineDificultyDialog);
        difficulty.setText(routineInfo.difficulty);
        duration = routineDialog.findViewById(R.id.routineDurationDialog);
        duration.setText(routineInfo.duration);
        createdBy = routineDialog.findViewById(R.id.routineCreatedByDialog);
        createdBy.setText(routineInfo.createdBy);
        routineDialog.show();
    }


}