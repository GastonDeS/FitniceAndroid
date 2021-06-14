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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnice.api.model.User;
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

    String[] profileElements = {"Nombre", "Apellido", "Email", "Usuario", "Genero"};
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

        super.onCreate(savedInstanceState);

        App app = ((App) getActivity().getApplication());
        app.getUserRepository().getCurrentUser().observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                currentUser = r.getData();
                Log.d("String", currentUser.toString());
                Activity current = getActivity();
                new DownloadProfileImageTask(((ImageView)current.findViewById(R.id.profile_image))).execute(r.getData().getAvatarUrl());
                ((TextView)current.findViewById(R.id.name_content)).setText(currentUser.getFirstName());
                ((TextView)current.findViewById(R.id.surname_content)).setText(currentUser.getFLastName());
                ((TextView)current.findViewById(R.id.email_content)).setText(currentUser.getEmail());
                ((TextView)current.findViewById(R.id.user_content)).setText(currentUser.getUsername());
                ((TextView)current.findViewById(R.id.gender_content)).setText(genders.get(currentUser.getGender()));
            }
        });





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
}


