package com.example.pkto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pkto.databinding.ActivityMainBinding;

public class mainPage extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
    public void goToTest1(View v){
        Intent intent = new Intent(this, test1_mainPage.class);
        startActivity(intent);
    }

    public void goToTest2(View v){
        Toast.makeText(this, "В разработке...", Toast.LENGTH_SHORT).show();
    }

    public void onClickInfo(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainPage.this);
        builder.setTitle("Информация о приложении").setMessage("Название: \"Программный комплекс - тестирование обучаемых\".\n" +
                        "Разработчик: cтудент группы ИКБО-27-21 Политов А.Е.\n" +
                        "©endrain. Все права защищены.")
                .setCancelable(false)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
    }
}