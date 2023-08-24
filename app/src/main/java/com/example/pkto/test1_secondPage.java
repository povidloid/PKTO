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

import com.example.pkto.databinding.ActivityTest1SecondPageBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class test1_secondPage extends AppCompatActivity {
    private ActivityTest1SecondPageBinding binding;
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
        binding = ActivityTest1SecondPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        readFile = getIntent().getStringArrayListExtra("readFile");
        String [] readLine = readFile.get(1).split("@");
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
        question = binding.test1QuestionDescription2;

        radioGroup = binding.test1RadioGroup2;

        radioButton1 = binding.test1RadioButton21;
        radioButton2 = binding.test1RadioButton22;
        radioButton3 = binding.test1RadioButton23;
        radioButton4 = binding.test1RadioButton24;

        buttonContinue = binding.test1ButtonContinue2;
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
        Intent intent = new Intent(this, test1_thirdPage.class);

        intent.putExtra("readFile", readFile);
        intent.putExtra("resultValues", resultValues);

        startActivity(intent);

        Log.d(TAG, "OnClickContinue 2: " + resultValues);
    }
    private void Back(){
        resultValues.remove(0);

        Intent intent = new Intent(this, test1_firstPage.class);
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