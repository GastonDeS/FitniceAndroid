package com.example.fitnice;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.ViewHolder> {

    private ArrayList<Cycle> cycles;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private Routine routine;

    public CycleAdapter(ArrayList<Cycle> dataset,Routine routine) {
        this.cycles = dataset;
        this.routine = routine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ciclo, parent, false);

        return new ViewHolder(view,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleAdapter.ViewHolder cycleHolder, int position) {

        Cycle cycle = cycles.get(position);

        cycleHolder.cycleName.setText(cycle.getCycleName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(cycleHolder.exercisesView.getContext(),LinearLayoutManager.VERTICAL,false);

        layoutManager.setInitialPrefetchItemCount(
                cycle.getExerciseList().size()
        );

        ExerciseAdapter exerciseAdapter;
        if (routine!=null) {
            exerciseAdapter = new ExerciseAdapter(
                    cycle.getExerciseList(), routine);
        } else {
            exerciseAdapter = new ExerciseAdapter(
                    cycle.getExerciseList());
        }
        cycleHolder.exercisesView.setAdapter(exerciseAdapter);
        cycleHolder.exercisesView.setLayoutManager(layoutManager);
        cycleHolder.exercisesView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView cycleName;
        RecyclerView exercisesView;
//        Button arrowBtn;

        public ViewHolder(@NonNull View itemView,@NonNull ViewGroup parent) {
            super(itemView);

//            arrowBtn = itemView.findViewById(R.id.arrowBtn);
            exercisesView = itemView.findViewById(R.id.exercisesView);
            cycleName = itemView.findViewById(R.id.cycleNameText);

//            arrowBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (exercisesView.getVisibility()==View.GONE){
//                        Toast.makeText(itemView.getContext(),"SHOW EXERCISES",Toast.LENGTH_LONG).show();
//                        TransitionManager.beginDelayedTransition(parent,new AutoTransition());
//                        exercisesView.setVisibility(View.VISIBLE);
//                        arrowBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
//                    } else {
//                        TransitionManager.beginDelayedTransition(parent,new AutoTransition());
//                        exercisesView.setVisibility(View.GONE);
//                        arrowBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
//                    }
//                }
//            });


        }

    }
}
