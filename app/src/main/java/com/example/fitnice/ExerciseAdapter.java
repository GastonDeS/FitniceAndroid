package com.example.fitnice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.api.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

    private List<ExerciseContent> exercises;
    private Routine routine;

    public ExerciseAdapter(List<ExerciseContent> exercises) {
        this.exercises = exercises;
    }

    public ExerciseAdapter(List<ExerciseContent> exercises, Routine routine) {
        this.exercises = exercises;
        this.routine = routine;
    }


    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise,parent,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder exerciseViewHolder, int position) {
        ExerciseContent exercise = exercises.get(position);

        exerciseViewHolder.exerciseName.setText(exercise.getExercise().getName());
        exerciseViewHolder.time.setText(routine.getResources().getString(R.string.seconds,exercise.getDuration().toString()));
        if (exercise.getExercise().getType().equals("exercise"))
            exerciseViewHolder.reps.setText(routine.getResources().getString(R.string.reps,exercise.getRepetitions().toString()));
        if (routine!=null) {
            exerciseViewHolder.itemView.setOnClickListener(view -> {
                routine.ShowPopupExercise(exercise);
            });
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        TextView time;
        TextView reps;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseName);
            time = itemView.findViewById(R.id.timer);
            reps = itemView.findViewById(R.id.timesE);

        }

    }
}
