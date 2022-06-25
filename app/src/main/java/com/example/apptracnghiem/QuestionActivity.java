package com.example.apptracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptracnghiem.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textTextViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewCountDown;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> questionList;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;

    private int Score;
    private boolean answered;
    private Question currentQuestion;

    private int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        AnhXa();

        //Nhận dữ liệu chủ đề
        Intent intent = getIntent();
        int categoryID = intent.getIntExtra("IdCategories",0);
        String categoryName = intent.getStringExtra("CategoriesName");
        //HIển thị chủ đề
        textViewCategory.setText("Chủ đề :"+categoryName);

        Database database = new Database(this);

        //Danh sach list chua cau hoi
        questionList = database.getQuestions(categoryID);
        //lay kich co danh sach = tong so cau hoi
        questionSize = questionList.size();
        //dao vi tri cac phan tu cau hoi
        Collections.shuffle(questionList);
        //show cau hoi va dap an
        showNextQuestion();
        //button xac nhan,cau tiep, hoan thanh
        buttonConfirmNext.setOnClickListener(view -> {
            if(!answered){
                if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                    checkAnswer();
                }
                else {
                    Toast.makeText(QuestionActivity.this,"Hãy chọn đáp án",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                showNextQuestion();
            }
        });

    }

    //hien thi cau hoi
    private void showNextQuestion() {
        //set lai mau den cho dap an
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);

        //xoa chon
        rbGroup.clearCheck();

        //neu con cau hoi
        if (questionCounter < questionSize){
            //lay du lieu o vi tri counter
            currentQuestion = questionList.get(questionCounter);
            //hien thi cau hoi
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            //tang sau moi cau hoi
            questionCounter++;
            //set vi tri cau hoi hien tai
            textTextViewQuestionCount.setText("Câu hỏi : "+questionCounter+ " / "+questionSize);

            //gia tri false chua tra loi, dang show
            answered = false;
            //gan ten cho button
            buttonConfirmNext.setText("Xác nhận");
            //thoi gian chay 30s
            timeLeftInMillis = 30000;
            //dem nguoc thoi gian tra loi
            startCountDown();

        }
        else {
            finishQuestion();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;

                //update
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                //het gio
                timeLeftInMillis = 0;
                updateCountDownText();
                //kiem tra dap an
                checkAnswer();
            }
        }.start();
    }

    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());

        int answer = rbGroup.indexOfChild(rbSelected) + 1;

        if(answer == currentQuestion.getAnswer()){
            Score = Score +10;

            textViewScore.setText("Điểm :"+Score);
        }
        //hien thi dap an
        showSolution();
    }
    //dap an
    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswer()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là A");
                break;
                case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là B");
                break;
                case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là C");
                break;
                case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là D");
                break;
        }
        if (questionCounter < questionSize){
            buttonConfirmNext.setText("Câu tiếp");
        }
        else {
            buttonConfirmNext.setText("Hoàn thành");
        }
        countDownTimer.cancel();
    }

    private void updateCountDownText() {
        int minutes = (int) ((timeLeftInMillis/1000)/60);
        int seconds = (int) ((timeLeftInMillis/1000)%60);
        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeFormatted);
        if (timeLeftInMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);
        }
        else{
            textViewCountDown.setTextColor(Color.BLACK);
        }
    }

    //thoat qua giao dien chinh
    private void finishQuestion() {
        //chua du lieu gui qua activity main
        Intent resultIntent = new Intent();
        resultIntent.putExtra("score",Score);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count>=1){
            finishQuestion();
        }
        count=0;
    }

    //Ánh Xạ id
    private void AnhXa(){
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textTextViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCategory = findViewById(R.id.text_view_category);

        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        buttonConfirmNext = findViewById(R.id.button_confim_next);

    }
}