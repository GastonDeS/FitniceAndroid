package com.example.fitnice;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.FragmentSeeRoutineBinding;
import com.example.fitnice.repository.Status;

import java.io.Serializable;
import java.util.ArrayList;

public class Routine extends Fragment {

    FragmentSeeRoutineBinding binding;

    com.example.fitnice.api.model.Routine routine;

    ArrayList<ExerciseContent> playerList = new ArrayList<>();

    Dialog routineDialog;
    Dialog exerciseDialog;
    App app;

    int faved;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSeeRoutineBinding.inflate(getLayoutInflater());
        binding.routineImage.rutineImage.setClipToOutline(true);

        app = (App) getActivity().getApplication();

        faved = getArguments().getInt("isFaved");

        setHasOptionsMenu(true);

        app.getRoutinesRepository().getRoutine(getArguments().getInt("id"))
                .observe(getActivity(),r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        routine = r.getData();
                        getActivity().setTitle(routine.getName());
                        binding.routineImage.textStartsFav.rating.setText(routine.getAverageRating().toString());
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

        binding.playRoutineBtn.setOnClickListener(view -> {
            NavController nav = Navigation.findNavController(this.binding.view);
            Bundle args = new Bundle();
            args.putInt("id",routine.getId());
            args.putSerializable("exList",(Serializable) playerList);
            nav.navigate(R.id.doRoutine2,args);
//            getActivity().overridePendingTransition(R.anim.slide_up,0);
        });

        routineDialog = new Dialog(this.getContext());
        exerciseDialog = new Dialog(this.getContext());
        routineDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exerciseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.routineImage.imageView3.setOnClickListener(view -> {
            Intent intent =new Intent();
            intent.setType("text/plain");
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"https://www.fitnice.com/"+getArguments().getInt("id"));
            startActivity(intent);
        });

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        menu.removeGroup(0);
        inflater.inflate(R.menu.fav, menu);
        inflater.inflate(R.menu.overflow_menu,menu);
        if (getArguments().getInt("isFaved")==1) {
            menu.getItem(0).setIcon(R.drawable.ic_baseline_favorite_24);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.like:
                if (faved==0) {
                    faved = 1;
                    app.getFavouritesRepository().postFav(routine.getId()).observe(this, r-> {
                        if (r.getStatus() == Status.SUCCESS) {
                            item.setIcon(R.drawable.ic_baseline_favorite_24);
                        }
                    });
                } else {
                    faved = 0;
                    app.getFavouritesRepository().removeFav(routine.getId()).observe(this,r -> {
                        if (r.getStatus() == Status.SUCCESS) {
                            item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                        }
                    });

                }
                break;
            case R.id.infobtn:
                ShowPopupRoutine(getView());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ShowPopupExercise(ExerciseContent exercise) {
        TextView exerciseName;
        TextView description;
        ImageView close;

        exerciseDialog.setContentView(R.layout.exercise_info);
        exerciseName = exerciseDialog.findViewById(R.id.exerciseNameDialog);
        exerciseName.setText(exercise.getExercise().getName());
        description = exerciseDialog.findViewById(R.id.exerciseDescriptionDialog);
        description.setText(exercise.getExercise().getDetail());
        close = exerciseDialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {exerciseDialog.cancel();});


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