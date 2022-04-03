package com.example.securednotebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
//import the biometric dependency in to the app
import androidx.biometric.BiometricPrompt;

import android.content.Intent;
//import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    private Executor executor;
    private androidx.biometric.BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;
//create the variable for the fingerprint popup image
    ImageView fingerprintLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 //declare the fingerprint variable
        fingerprintLogin=findViewById(R.id.fingerprint);
//declaring the excutor variable
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }
//what to do when the authentication is success full
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(MainActivity.this,AddRecipe.class));
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }
//what to do when authentication fails
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
//Messages to show when the one clicks on the fingerprint image
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Authenticate your Fingerprint")
                .setNegativeButtonText("Cancel")
                .build();
        fingerprintLogin.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
    }
        );}}