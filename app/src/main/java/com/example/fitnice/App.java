package com.example.fitnice;

import android.app.Application;

import com.example.fitnice.repository.CycleRepository;
import com.example.fitnice.repository.ExerciseRepository;
import com.example.fitnice.repository.FavouritesRepository;
import com.example.fitnice.repository.MyRoutinesRepository;
import com.example.fitnice.repository.RoutineRepository;
import com.example.fitnice.repository.UserRepository;

public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private RoutineRepository routineRepository;
    private CycleRepository cycleRepository;
    private ExerciseRepository exerciseRepository;
    private FavouritesRepository favouritesRepository;
    private MyRoutinesRepository myRoutinesRepository;

    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoutineRepository getRoutinesRepository() {
        return routineRepository;
    }

    public CycleRepository getCyclesRepository() { return cycleRepository; }

    public ExerciseRepository getExerciseRepository() { return exerciseRepository; }

    public FavouritesRepository getFavouritesRepository() {
        return favouritesRepository;
    }

    public MyRoutinesRepository getMyRoutinesRepository() {
        return myRoutinesRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        userRepository = new UserRepository(this);

        routineRepository = new RoutineRepository(this);

        cycleRepository = new CycleRepository(this);

        exerciseRepository = new ExerciseRepository(this);

        favouritesRepository = new FavouritesRepository(this);

        myRoutinesRepository = new MyRoutinesRepository(this);
    }
}
