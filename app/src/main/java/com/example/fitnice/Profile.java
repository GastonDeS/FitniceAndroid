package com.example.fitnice;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnice.api.model.User;
import com.example.fitnice.databinding.FragmentProfileBinding;
import com.example.fitnice.repository.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    User currentUser;
    FragmentProfileBinding binding;
    App app;

    HashMap<String, Integer> genders = new HashMap<String, Integer>() {{
        put("male", R.string.male); put("female", R.string.female);
    }};

    public Profile() {
        // Required empty public constructor
    }
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(getLayoutInflater());

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        app = ((App) requireActivity().getApplication());

        setHasOptionsMenu(true);

        getActivity().setTitle(getString(R.string.profile));

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                getActivity().onBackPressed();
            }
        });

        app.getUserRepository().getCurrentUser().observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                currentUser = r.getData();
                Log.d("String", currentUser.toString());
                Activity current = getActivity();
                new DownloadProfileImageTask(((ImageView)current.findViewById(R.id.profile_image))).execute(r.getData().getAvatarUrl());
                ((EditText)current.findViewById(R.id.name_content)).setText(currentUser.getFirstName());
                ((EditText)current.findViewById(R.id.surname_content)).setText(currentUser.getFLastName());
                ((EditText)current.findViewById(R.id.email_content)).setText(currentUser.getEmail());
                ((TextView)current.findViewById(R.id.user_content)).setText(currentUser.getUsername());
                ((EditText)current.findViewById(R.id.gender_content)).setText(getString(genders.get(currentUser.getGender())));
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.removeGroup(0);
        inflater.inflate(R.menu.save_edit_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            binding.nameContent.clearFocus();
            binding.surnameContent.clearFocus();
            binding.emailContent.clearFocus();
            HideKeyboard.hideKeyboardFrom(this.requireContext(), this.requireView());
            updateUserOnAPI();
        }
        return super.onOptionsItemSelected(item);
    }

    private class DownloadProfileImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadProfileImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void updateUserOnAPI() {
        Activity current = getActivity();
        if (currentUser != null) {
            currentUser.setFirstName(((EditText) current.findViewById(R.id.name_content)).getText().toString());
            currentUser.setLastName(((EditText) current.findViewById(R.id.surname_content)).getText().toString());
            currentUser.setEmail(((EditText) current.findViewById(R.id.email_content)).getText().toString());
            App app = ((App) requireActivity().getApplication());
            app.getUserRepository().updateUser(currentUser).observe(this, r -> {
                if (r.getStatus() == Status.ERROR) {
                    Toast.makeText(getContext(), getString(R.string.generic_error), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}


