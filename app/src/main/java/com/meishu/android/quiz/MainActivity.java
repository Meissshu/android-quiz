package com.meishu.android.quiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton backButton;
    private TextView questionTextView;
    private Button cheatButton;

    private int currentIndex = 0;
    private boolean isCheater;

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Question[] questionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onStart()!");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        questionTextView = (TextView) findViewById(R.id.question_text_view);
        questionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = (currentIndex + 1) % questionBank.length;
                isCheater = false;
                updateQuestion();
            }
        });

        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(true);
            }
        });

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = (currentIndex + 1) % questionBank.length;
                isCheater = false;
                updateQuestion();
            }
        });

        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = Math.abs((currentIndex - 1 + questionBank.length) % questionBank.length);
                updateQuestion();
            }
        });

        updateQuestion();
    }


    private void updateQuestion(){
        int question = questionBank[currentIndex].getTextResId();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean isAnswerTrue = questionBank[currentIndex].isAnswerTrue();
        int messageResId;

        if(isCheater){
            messageResId = R.string.judgment_toast;
        }
        else {
            if (userPressedTrue == isAnswerTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK)
            return;

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            isCheater = CheatActivity.wasAnswerShown(data);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
