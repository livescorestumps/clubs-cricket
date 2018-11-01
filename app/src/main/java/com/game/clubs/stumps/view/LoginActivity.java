package com.game.clubs.stumps.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.game.clubs.stumps.BaseActivity;
import com.game.clubs.stumps.R;
import com.game.clubs.stumps.databinding.ActivityLoginBinding;
import com.game.clubs.stumps.landing.view.LandingActivity;
import com.game.clubs.stumps.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Praneeth on 10/10/2018.
 */

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        setObservers();
        viewModel.getCurrentUser();
        setListeners();
    }

    private void setListeners() {
        binding.emailSignInButton.setOnClickListener(view -> clickedonSignButton());
        binding.registerButton.setOnClickListener(view -> clickedonRegister());
    }

    private void clickedonRegister() {
        if (binding.email.getText().toString().isEmpty() ||
                binding.password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        viewModel.signup(binding.email.getText().toString(), binding.password.getText().toString());
    }

    private void clickedonSignButton() {
        if (binding.email.getText().toString().isEmpty() ||
                binding.password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        viewModel.authenticate(binding.email.getText().toString(), binding.password.getText().toString());
    }

    private void setObservers() {
        viewModel.getUserlivedata().observe(this, this::handleLogin);
        viewModel.getRegisterlivedata().observe(this, this::handleRegistration);
        viewModel.getCheckIfUserLiveData().observe(this, this::handleUserRes);
    }

    private void handleUserRes(Boolean value) {
        if (value) {
            Intent intent = new Intent(this, LandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, PlayerProfileActivity.class);
            startActivity(intent);
        }
    }

    private void handleRegistration(FirebaseUser firebaseUser) {
        if (firebaseUser == null) {
            showToast("Registration Failed");
            return;
        }
        showToast("Registration successfull: " + firebaseUser.getEmail());
        validate(firebaseUser.getEmail());
    }

    private void validate(String email) {
        viewModel.checkIfUserExits(email);
    }

    //Handle Login
    private void handleLogin(FirebaseUser firebaseUser) {
        if (firebaseUser == null) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            return;
        }
        showToast("Login successfull: " + firebaseUser.getEmail());
        validate(firebaseUser.getEmail());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
