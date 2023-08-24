package com.example.pkto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pkto.databinding.ActivityTest1FirstPageBinding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class test1_firstPage extends AppCompatActivity {
    private ActivityTest1FirstPageBinding binding;
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
        binding = ActivityTest1FirstPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String [] readLine = getQuestion();
        setMap(readLine);
        Set<String> setKeys = map.keySet();
        String[] keys = setKeys.toArray(new String[0]);

        setPalette();
        setInfo(keys);

        resultValues = getIntent().getStringArrayListExtra("resultValues");

        if(resultValues == null){
            resultValues = new ArrayList<>();
        }

    }
    public void setPalette(){
        question = binding.test1QuestionDescription1;

        radioGroup = binding.test1RadioGroup1;

        radioButton1 = binding.test1RadioButton11;
        radioButton2 = binding.test1RadioButton12;
        radioButton3 = binding.test1RadioButton13;
        radioButton4 = binding.test1RadioButton14;

        buttonContinue = binding.test1ButtonContinue1;
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
    public String[] getQuestion(){
        String line;
        readFile = new ArrayList<>();
        AssetManager assetManager = getAssets();

        try {
            InputStream inputStream = assetManager.open("data.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null){
                line = decrypt(line);
                readFile.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String line1 = readFile.get(0);

        return line1.split("@");
    }
    public static String decrypt(String value) throws Exception {
        String KEY = "mykey12345678910";
        String ALGORITHM = "AES";

        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedValue = Base64.getDecoder().decode(value);
        byte[] decryptedValue = cipher.doFinal(decodedValue);
        return new String(decryptedValue);
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
    private void checkAccuracy(){
        int checkedButtonIndex = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(checkedButtonIndex);
        boolean accuracy = map.get(radioButton.getText());

        resultValues.add(String.valueOf(accuracy));
    }
    public void OnClickContinue(View v){
        checkAccuracy();
        Intent intent = new Intent(this, test1_secondPage.class);

        intent.putExtra("readFile", readFile);
        intent.putExtra("resultValues", resultValues);

        startActivity(intent);

        Log.d(TAG, "OnClickContinue 1: " + resultValues);
    }
    @Override
    public void onBackPressed() {
    }
}