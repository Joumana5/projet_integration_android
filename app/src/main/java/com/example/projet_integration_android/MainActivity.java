package com.example.projet_integration_android;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projet_integration_android.authentication.Login;
import com.example.projet_integration_android.authentication.ResetPasswordActivity;
import com.example.projet_integration_android.authentication.Signup;
import com.google.android.material.navigation.NavigationView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId() == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.nav_log) {
                    startActivity(new Intent(MainActivity.this, Login.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_sign) {
                    startActivity(new Intent(MainActivity.this, Signup.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    return true;

                } else if (item.getItemId() == R.id.nav_reset) {
                    try {
                        startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
                    } catch (Exception e) {
                        Log.e("navigation", e.getMessage());
                    }
                    return true;
                }

                else {
                    return false;
                }
            }
        });

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(navigationView));
    }
}