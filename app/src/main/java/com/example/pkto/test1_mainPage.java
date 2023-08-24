package com.example.pkto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pkto.databinding.ActivityTest1MainPageBinding;

public class test1_mainPage extends AppCompatActivity {
    private ActivityTest1MainPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTest1MainPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
    public void onClickContinue(View v){
        Intent intent = new Intent(this, test1_firstPage.class);
        startActivity(intent);
        finish();
    }
    public void onClickBack(View v){
        Intent intent = new Intent(this, mainPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}