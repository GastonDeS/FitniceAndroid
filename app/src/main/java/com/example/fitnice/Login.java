package com.example.fitnice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnice.api.model.Credentials;
import com.example.fitnice.databinding.ActivityLoginBinding;
import com.example.fitnice.databinding.LoginBoxBinding;
import com.example.fitnice.repository.Resource;
import com.example.fitnice.repository.Status;
import com.example.fitnice.viewmodel.DisplayViewModel;

public class Login extends AppCompatActivity {

    public static final String TAG = "UI";

    private ActivityLoginBinding binding;

    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = ((App) getApplication());

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.box.loginButton.setOnClickListener(view -> {
            Credentials credentials = new Credentials(binding.box.email.getText().toString()
                    ,binding.box.editTextTextPassword.getText().toString());
            app.getUserRepository().login(credentials).observe(this, r -> {
                if (r.getStatus() == Status.SUCCESS) {
                    app.getPreferences().setAuthToken(r.getData().getToken());
                    startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
//                    defaultResourceHandler(r);
//                    Toast.makeText(this,"Not succes",Toast.LENGTH_LONG).show();
                }
            });

        });

    }

//    private void defaultResourceHandler(Resource<?> resource) {
//        switch (resource.getStatus()) {
//            case LOADING:
//                Log.d(TAG, getString(R.string.loading));
//                binding.result.setText(R.string.loading);
//                break;
//            case ERROR:
//                Error error = resource.getError();
//                String message = getString(R.string.error, error.getDescription(), error.getCode());
//                Log.d(TAG, message);
//                binding.result.setText(message);
//                break;
//        }
//    }
}