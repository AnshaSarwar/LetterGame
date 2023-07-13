package com.lettergame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    Fragment currentFragment;
    FrameLayout fragmentContainer;

    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        welcome = findViewById(R.id.welcome_text_view);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer);
        fragmentContainer = findViewById(R.id.fragment_container);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set the initial fragment
        currentFragment = null;
        clearFragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                try {
                    menuItem.setChecked(true);
                    drawerLayout.closeDrawers();

                    int id = menuItem.getItemId();
                    Fragment fragment = null;

                    if (id == R.id.playgame) {
                        fragment = new MyFragment();
                    } else if (id == R.id.showresults) {
                        fragment = new DisplayFragment();
                    }

                    if (fragment != null && fragment != currentFragment) {
                        currentFragment = fragment; // Update the current fragment
                        loadFragment(fragment);
                    } else if (fragment == null) {
                        clearFragment();
                    }

                    return true;
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            fragmentContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFragment() {
        fragmentContainer.setVisibility(View.GONE);
    }


}

