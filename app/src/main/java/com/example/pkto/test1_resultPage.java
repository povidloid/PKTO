package com.example.pkto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pkto.databinding.ActivityTest1ResultPageBinding;

import java.util.ArrayList;
import java.util.Objects;

public class test1_resultPage extends AppCompatActivity {
    ArrayList<String> resultValues;
    int score = 0;
    ImageView resultImage, correct1, correct2, correct3;
    TextView resultText;

    private ActivityTest1ResultPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTest1ResultPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        resultValues = getIntent().getStringArrayListExtra("resultValues");
        getScore();

        setPalette();
        setCorrectImages();

        switch (score){
            case 0:
                setBadWindow();
                break;
            case 1:
            case 2:
                setGoodWindow();
                break;
            case 3:
                setPerfectWindow();
                break;
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            resultImage.setVisibility(View.GONE);
        }
    }
    private void setPalette(){
        resultImage = binding.test1ResultPicture;
        correct1 = binding.test1CorrectImage1;
        correct2 = binding.test1CorrectImage2;
        correct3 = binding.test1CorrectImage3;

        resultText = binding.test1ResultTextView;
    }
    public void setBadWindow(){
        resultText.setText(R.string.resultBad);
        resultImage.setImageResource(R.drawable.bad);
    }
    public void setGoodWindow(){
        resultText.setText(R.string.resultGood);
        resultImage.setImageResource(R.drawable.good);
    }
    public void setPerfectWindow(){
        resultText.setText(R.string.resultPerfect);
        resultImage.setImageResource(R.drawable.perfect);
    }
    public void setCorrectImages(){
        ImageView [] correctImages = {correct1, correct2, correct3};
        for (int i = 0; i < correctImages.length; i++) {
            if (resultValues.get(i).equals("true")){
                correctImages[i].setImageResource(R.drawable.correct);
            }
            else
                correctImages[i].setImageResource(R.drawable.incorrect);
        }
    }
    private void getScore(){
        for (String value :
                resultValues) {
            if (Objects.equals(value, "true")){
                score++;
            }
        }
    }
    public void toMainPage(View v){
        Intent intent = new Intent(this, mainPage.class);
        startActivity(intent);
        finish();
    }
    public void restartTest1(View v){
        Intent intent = new Intent(this, test1_mainPage.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
    }
}