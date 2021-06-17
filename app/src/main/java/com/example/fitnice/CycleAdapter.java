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
import com.example.fitnice.api.model.Cycle;

import com.example.fitnice.repository.Status;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.ViewHolder> {

    private List<Cycle> cycles;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private int cycleType = -1;

    private Routine routine;

    public CycleAdapter(List<Cycle> dataset,Routine routine) {
        this.cycles = dataset.stream().sorted(new Comparator<Cycle>() {
            @Override
            public int compare(Cycle o1, Cycle o2) {
                if ( (types(o1.getType()) - types(o2.getType()))==0) {
                    return o1.getOrder() - o2.getOrder();
                };
                return types(o1.getType()) - types(o2.getType());
            }
        }).collect(Collectors.toList());
        this.routine = routine;
    }

    private String translate(String type) {
        if (type.equals("warmup")) {
            return "calentamiento";
        } else if (type.equals("exercise")) {
            return "ejercicio";
        } else return "Enfriamiento";
    }

    private int types(String type) {
        if (type.equals("warmup")) {
            return 0;
        } else if (type.equals("exercise")) {
            return 1;
        } else return 2;
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



        cycleHolder.cycleName.setText(cycle.getName());
        cycleHolder.times.setText(routine.getResources().getString(R.string.cycleReps,cycle.getRepetitions().toString()));
        if (cycleType!=types(cycle.getType())) {
            cycleType = types(cycle.getType());
            String cycleTypeName = cycle.getType();
            if (Locale.getDefault().getLanguage().contains("es")) {
                cycleTypeName = translate(cycleTypeName);
            }
            cycleTypeName = Character.toUpperCase(cycleTypeName.charAt(0))+ cycleTypeName.substring(1,cycleTypeName.length());
            TextView cycletypename = cycleHolder.cycleType.findViewById(R.id.cycleNameTitle);
//            cycletypename.setText(routine.getResources().getString(R.string.cycles,cycleTypeName));
            cycletypename.setText(cycleTypeName);
            cycleHolder.cycleType.setVisibility(View.VISIBLE);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(cycleHolder.exercisesView.getContext(),LinearLayoutManager.VERTICAL,false);


        App app = (App) routine.getActivity().getApplication();

        app.getExerciseRepository().getExercises(cycle.getId()).observe(routine.getActivity(),r -> {
            if (r.getStatus() == Status.SUCCESS) {
                layoutManager.setInitialPrefetchItemCount(
                        r.getData().getContent().size()
                );
                ExerciseAdapter exerciseAdapter;
                exerciseAdapter = new ExerciseAdapter(
                            r.getData().getContent(), routine);
                cycleHolder.exercisesView.setAdapter(exerciseAdapter);
                cycleHolder.exercisesView.setLayoutManager(layoutManager);
                cycleHolder.exercisesView.setRecycledViewPool(viewPool);
                for (int i =0 ; i < cycle.getRepetitions(); i++){
                    routine.playerList.addAll(r.getData().getContent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView cycleName;
        final TextView times;
        RecyclerView exercisesView;
        View cycleType;

        public ViewHolder(@NonNull View itemView,@NonNull ViewGroup parent) {
            super(itemView);

            exercisesView = itemView.findViewById(R.id.exercisesView);
            cycleName = itemView.findViewById(R.id.cycleNameText);
            times = itemView.findViewById(R.id.CycleCount);
            cycleType = itemView.findViewById(R.id.cycleType);



        }

    }
}
