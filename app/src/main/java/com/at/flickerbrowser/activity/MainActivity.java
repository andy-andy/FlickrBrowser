package com.at.flickerbrowser.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.at.flickerbrowser.R;
import com.at.flickerbrowser.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
