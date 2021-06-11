package com.example.fitnice;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fitnice.databinding.ActivityMainBinding;
import com.example.fitnice.ui.home.HomeFragment;
import com.example.fitnice.ui.home.HomeFragmentDirections;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        if (savedInstanceState == null) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.navigation_home);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu,menu);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search)
//                .getActionView();
//        if (null != searchView) {
//            searchView.setSearchableInfo(searchManager
//                    .getSearchableInfo(getComponentName()));
//            searchView.setIconifiedByDefault(false);
//        }
//
//        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//            public boolean onQueryTextChange(String newText) {
//                // This is your adapter that will be filtered
//                return true;
//            }
//
//            public boolean onQueryTextSubmit(String query) {
//                //TODO make the function search and find the rooutine
//                Toast.makeText(getBaseContext(),query ,Toast.LENGTH_LONG).show();
//                return true;
//            }
//        };
//        assert searchView != null;
//        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                //TODO LOGOUT
                return true;
            case R.id.profile:
                //TODO GO TO PROFILE
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}