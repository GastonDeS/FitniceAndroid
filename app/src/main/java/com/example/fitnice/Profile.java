package com.example.fitnice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnice.api.model.User;
import com.example.fitnice.databinding.FragmentProfileBinding;
import com.example.fitnice.repository.Status;

import java.io.InputStream;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    User currentUser;
    FragmentProfileBinding binding;

    HashMap<String, String> genders = new HashMap<String, String>() {{
        put("male", "Masculino"); put("female", "Femenino");
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
    public void onCreate(Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        getActivity().setContentView(R.layout.fragment_profile);
        super.onCreate(savedInstanceState);

        App app = ((App) requireActivity().getApplication());
        app.getUserRepository().getCurrentUser().observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                currentUser = r.getData();
                Log.d("String", currentUser.toString());
                Activity current = getActivity();
                new DownloadProfileImageTask(((ImageView)current.findViewById(R.id.profile_image))).execute(r.getData().getAvatarUrl());
                ((EditText)current.findViewById(R.id.name_content)).setText(currentUser.getFirstName());
                ((EditText)current.findViewById(R.id.surname_content)).setText(currentUser.getFLastName());
                ((EditText)current.findViewById(R.id.email_content)).setText(currentUser.getEmail());
                ((TextView)current.findViewById(R.id.user_content)).setText(currentUser.getUsername());
                ((EditText)current.findViewById(R.id.gender_content)).setText(genders.get(currentUser.getGender()));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Profile profile = this;
        Button saveBtn = getActivity().findViewById(R.id.SaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserOnAPI();
            }
        });


        return inflater.inflate(R.layout.fragment_profile, container, false);
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
            currentUser.setFirstName(((EditText)current.findViewById(R.id.name_content)).getText().toString());
            currentUser.setLastName(((EditText)current.findViewById(R.id.surname_content)).getText().toString());
            currentUser.setEmail(((EditText)current.findViewById(R.id.email_content)).getText().toString());
            App app = ((App) requireActivity().getApplication());
            app.getUserRepository().updateUser(currentUser).observe(this, r -> {
                if (r.getStatus() == Status.SUCCESS) {
                    Log.d("Saving Profile", "Se guardo!!!!!!");
                }
            });
        }
    }
}


