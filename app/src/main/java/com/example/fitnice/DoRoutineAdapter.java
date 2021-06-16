package com.example.fitnice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnice.api.model.ExerciseContent;

import java.util.List;

public class DoRoutineAdapter extends RecyclerView.Adapter<DoRoutineAdapter.DoRoutineViewHolder> {

    private List<ExerciseContent> exercises;
    private DoRoutine2 routine;

    public DoRoutineAdapter(List<ExerciseContent> exercises, DoRoutine2 routine) {
        this.exercises = exercises;
        this.routine = routine;
    }

    @NonNull
    @Override
    public DoRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise,parent,false);
        return new DoRoutineAdapter.DoRoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoRoutineViewHolder holder, int position) {
        ExerciseContent exerciseContent = exercises.get(position);

        holder.exerciseName.setText(exerciseContent.getExercise().getName());
        holder.time.setText(routine.getResources().getString(R.string.seconds,exerciseContent.getDuration().toString()));
        if (exerciseContent.getExercise().getType().equals("exercise"))
            holder.reps.setText(routine.getResources().getString(R.string.reps,exerciseContent.getRepetitions().toString()));

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class DoRoutineViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        TextView time;
        TextView reps;

        public DoRoutineViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseName);
            time = itemView.findViewById(R.id.timer);
            reps = itemView.findViewById(R.id.timesE);

        }

    }
}
