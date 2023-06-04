package com.iquad.internship.mobiletask1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.iquad.internship.mobiletask1.Fragments.GetStudentsFragment;
import com.iquad.internship.mobiletask1.Fragments.ModulesFragment;

public class MainActivity extends AppCompatActivity {

    private Button getModulesButton;
    private Button getStudentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getModulesButton = findViewById(R.id.getModulesButton);
        getStudentsButton = findViewById(R.id.getStudentsButton);

        getModulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModulesFragment modulesFragment = new ModulesFragment();
                replaceFragment(modulesFragment);
                //Intent intent = new Intent(MainActivity.this, ModuleListActivity.class);
                //startActivity(intent);
            }
        });

        getStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStudentsFragment getStudentsFragment = new GetStudentsFragment();
                replaceFragment(getStudentsFragment);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}