package com.example.pkto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pkto.databinding.ActivityMainBinding;
import com.example.pkto.databinding.ActivityTest1SecondPageBinding;
import com.example.pkto.databinding.ActivityTest1ThirdPageBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class test1_thirdPage extends AppCompatActivity {
    private ActivityTest1ThirdPageBinding binding;
    ArrayList<String> readFile;
    Map<String, Boolean> map = new LinkedHashMap<>();
    ArrayList<String> resultValues;
    TextView question;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    Button buttonContinue;
    final String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTest1ThirdPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        readFile = getIntent().getStringArrayListExtra("readFile");
        String [] readLine = readFile.get(2).split("@");
        setMap(readLine);
        Set<String> setKeys = map.keySet();
        String[] keys = setKeys.toArray(new String[0]);

        setPalette();
        setInfo(keys);

        resultValues = getIntent().getStringArrayListExtra("resultValues");
    }
    public void setMap(String[] words){
        for (String word : words) {
            if (word.charAt(0) == '*') {
                map.put(word.substring(1), true);
            } else {
                map.put(word.substring(1), false);
            }
        }
    }
    public void setPalette(){
        question = binding.test1QuestionDescription3;

        radioGroup = binding.test1RadioGroup3;

        radioButton1 = binding.test1RadioButton31;
        radioButton2 = binding.test1RadioButton32;
        radioButton3 = binding.test1RadioButton33;
        radioButton4 = binding.test1RadioButton34;

        buttonContinue = binding.test1ButtonContinue3;
    }
    public void setInfo(String[] keys){
        RadioButton[] radioButtons = {radioButton1, radioButton2, radioButton3, radioButton4};

        for (int i = 0; i < radioButtons.length; i++){
            radioButtons[i].setText(keys[i+1]);
        }

        question.setText(keys[0]);

        buttonContinue.setEnabled(false);
        buttonContinue.setBackgroundColor(Color.GRAY);

        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            buttonContinue.setEnabled(true);
            buttonContinue.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blueLight));
        });
    }
    private void checkAccuracy() {
        int checkedButtonIndex = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(checkedButtonIndex);
        boolean accuracy = map.get(radioButton.getText());
        resultValues.add(String.valueOf(accuracy));
    }
    public void OnClickContinue(View v){
        checkAccuracy();
        Intent intent = new Intent(this, test1_resultPage.class);
        intent.putExtra("resultValues", resultValues);
        startActivity(intent);
        finish();

        Log.d(TAG, "OnClickContinue 3: " + resultValues);
    }
    private void Back(){
        Intent intent = new Intent(this, test1_secondPage.class);
        intent.putExtra("readFile", readFile);
        resultValues.remove(1);
        intent.putExtra("resultValues", resultValues);
        startActivity(intent);
    }
    public void OnClickBack(View v){
        Back();
    }

    @Override
    public void onBackPressed() {
        Back();
    }
}