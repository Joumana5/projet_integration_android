package com.example.projet_integration_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.EmployeeSignupDto;
import com.example.projet_integration_android.services.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    private TextInputEditText fn, ln, age, rib, adr, mail, pwd;
    private Button signupButton;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainS), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> startActivity(new Intent(Signup.this, MainActivity.class)));

        fn = findViewById(R.id.Fn);
        ln = findViewById(R.id.Ln);
        age = findViewById(R.id.age);
        rib = findViewById(R.id.rib);
        adr = findViewById(R.id.adr);
        mail = findViewById(R.id.mail);
        pwd = findViewById(R.id.pwd);
        signupButton = findViewById(R.id.signup_button);
        loginText = findViewById(R.id.log);

        loginText.setOnClickListener(view -> startActivity(new Intent(Signup.this, Login.class)));

        signupButton.setOnClickListener(view -> {
            String firstName = fn.getText().toString();
            String lastName = ln.getText().toString();
            String userAge = age.getText().toString();
            String ribNumber = rib.getText().toString();
            String address = adr.getText().toString();
            String email = mail.getText().toString();
            String password = pwd.getText().toString();

            if (firstName.isEmpty() || lastName.isEmpty() || userAge.isEmpty() ||
                    ribNumber.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Log.e("signup", "fields empty");
                Toast.makeText(Signup.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

            } else if (!isEmailValid(email)) {
                Toast.makeText(Signup.this, "Email is not valid", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 8) {
                Toast.makeText(Signup.this, "Password must at least have 8 characters", Toast.LENGTH_SHORT).show();
            }else {

                try {
                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

                    EmployeeSignupDto employeeSignup = new EmployeeSignupDto(
                            firstName, lastName, Integer.parseInt(userAge), address, Integer.parseInt(ribNumber), email, password
                    );

                    apiService.signup(employeeSignup).enqueue(new Callback<ApiResponseDto<String>>() {
                        @Override
                        public void onResponse(Call<ApiResponseDto<String>> call, Response<ApiResponseDto<String>> response) {
                            try {
                                ApiResponseDto<String> apiResponse = response.body();
                                if (response.isSuccessful() && apiResponse != null) {

                                    Log.v("signup","email saved");

                                    Toast.makeText(Signup.this, "Signup successful", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", email);
                                    editor.apply();

                                    Log.v("signup", "Email has been put");

                                    Intent intent = new Intent(Signup.this, VerificationActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Signup.this, "Email address already in use!", Toast.LENGTH_SHORT).show();
                                    Log.e("signup","HTTP Error: " + response.body());
                                }
                            } catch (Exception e) {
                                Log.e("signup", e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponseDto<String>> call, Throwable t) {
                            if (t instanceof IOException) {
                                Log.e("SignupActivity", "Network error: " + t.getMessage());
                            } else {
                                Log.e("SignupActivity", "Unexpected error: " + t.getMessage(), t);
                            }
                        }
                    });

                } catch (Exception e) {
                    Log.e("signup", e.toString());
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (email == null) {
            return false;
        }

        return email.matches(emailRegex);
    }

}