package com.example.fitnice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

    private ArrayList<Exercise> exercises;
    private Routine routine;

    public ExerciseAdapter(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public ExerciseAdapter(ArrayList<Exercise> exercises, Routine routine) {
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
        Exercise exercise = exercises.get(position);

        exerciseViewHolder.exerciseName.setText(exercise.getExerciseName());
        exerciseViewHolder.time.setText(exercise.getDurationS());
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

        }

    }
}
