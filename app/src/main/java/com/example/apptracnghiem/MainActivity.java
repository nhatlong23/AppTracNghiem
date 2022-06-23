package com.example.apptracnghiem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apptracnghiem.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHighScore;
    private Spinner spinnerCategory;
    private Button buttonStartQuestion;

    private int HighScore;
    private static final int REQUEST_CODE_QUESTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        //load chu de
        loadCategories();
        //load diem
        loadHighScore();
        //click bat dau
        buttonStartQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuestion();
            }
        });
    }
    //load diemr hien thi
    private void loadHighScore() {
        SharedPreferences preferences = getSharedPreferences("share",MODE_PRIVATE);
        HighScore = preferences.getInt("highscore",0);
        textViewHighScore.setText("Điểm cao :"+HighScore);
    }

    //ham bat dau cau hoi qua activity question
    private void startQuestion(){
        //lay id, chu de da chon
        Category category = (Category) spinnerCategory.getSelectedItem();
        int categoryID = category.getId();
        String categoryName = category.getName();

        //chuyen qua ActivityQuestion
        Intent intent = new Intent(MainActivity.this,QuestionActivity.class);

        //gui du lieu name.id
        intent.putExtra("IdCategories",categoryID);
        intent.putExtra("CategoriesName",categoryName);


        startActivityForResult(intent,REQUEST_CODE_QUESTION);

    }

    //phuong thuc anh xa id
    private void AnhXa(){
        textViewHighScore = findViewById(R.id.textview_high_score);
        buttonStartQuestion = findViewById(R.id.button_start_question);
        spinnerCategory = findViewById(R.id.spinner_category);
    }

    //load chu de
    private void loadCategories(){
        Database database = new Database(this);
        //lay du lieu danh sach chu de
        List<Category> categories = database.getDataCategories();

        //tao Adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        //bo cuc hien thi chu de
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gan chu den len cho spinner hien thi
        spinnerCategory.setAdapter(categoryArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_QUESTION){
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra("score",0);

                if(score > HighScore){
                    updateHighScore(score);
                }
            }
        }
    }

    private void updateHighScore(int score) {
        //gan diem cao moi
        HighScore = score;
        //hien thi
        textViewHighScore.setText("Điểm cao : "+HighScore);
        //luu tru diem
        SharedPreferences preferences = getSharedPreferences("share",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("highscore",HighScore);

        editor.apply();
    }
}