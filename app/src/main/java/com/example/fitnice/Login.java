package com.example.fitnice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnice.api.ApiError;
import com.example.fitnice.api.model.Credentials;
import com.example.fitnice.databinding.ActivityLoginBinding;
import com.example.fitnice.databinding.LoginBoxBinding;
import com.example.fitnice.repository.Resource;
import com.example.fitnice.repository.Status;
import com.example.fitnice.viewmodel.DisplayViewModel;

import org.intellij.lang.annotations.Language;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Login extends AppCompatActivity {

    public static final String TAG = "UI";

    private ActivityLoginBinding binding;

    private App app;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = ((App) getApplication());

        if (app.getPreferences().getAuthToken()!=null) {
            goToMain();
        }

        if (!getResources().getBoolean(R.bool.tablet_player_land) ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int passwordType = binding.box.editTextTextPassword.getInputType();

        binding.box.seeNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.box.editTextTextPassword.getInputType() != InputType.TYPE_CLASS_TEXT) {
                    binding.box.editTextTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    binding.box.seeNot.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_24));
                } else {
                    binding.box.editTextTextPassword.setInputType(passwordType);
                    binding.box.seeNot.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24));
                }
            }
        });

        binding.box.loginButton.setOnClickListener(view -> {
            Credentials credentials = new Credentials(binding.box.email.getText().toString()
                    ,binding.box.editTextTextPassword.getText().toString());
            app.getUserRepository().login(credentials).observe(this, r -> {
                if (r.getStatus() == Status.SUCCESS) {
                    app.getPreferences().setAuthToken(r.getData().getToken());
                    goToMain();
                } else if (r.getStatus() == Status.ERROR){
                    if (r.getError().getCode().equals(ApiError.INVALID_USER_PWS));
                    TextView errorMsg = (TextView)this.findViewById(R.id.login_error_msg);
                    errorMsg.setVisibility(View.VISIBLE);
                    errorMsg.setText(getString(R.string.loginErrorMsg));
                }
            });

        });

    }

    private void goToMain() {
        Intent intent =new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getIntent().getData();
        if (uri!=null) {
            intent.putExtra("id",Integer.parseInt(uri.getLastPathSegment()));
//            bundle.putInt("isFaved",0);
//            navController.navigate(R.id.action_navigation_home_to_routine,bundle);
        }
        startActivity(intent);
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!getResources().getBoolean(R.bool.tablet_player_land) ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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