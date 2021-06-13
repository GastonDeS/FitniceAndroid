package com.example.fitnice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnice.api.model.Credentials;
import com.example.fitnice.databinding.ActivityLoginBinding;
import com.example.fitnice.databinding.LoginBoxBinding;
import com.example.fitnice.repository.Status;
import com.example.fitnice.viewmodel.DisplayViewModel;

public class Login extends AppCompatActivity {

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
//            app.getUserRepository().login(credentials).observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    app.getPreferences().setAuthToken(r.getData().getToken());
//                    binding.result.setText(R.string.success);
//                    binding.getCurrentUserButton.setEnabled(true);
//                    binding.getAllButton.setEnabled(true);
//                    binding.getButton.setEnabled(true);
//                    binding.addButton.setEnabled(true);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });

        });

    }
}