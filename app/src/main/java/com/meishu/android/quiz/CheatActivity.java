package com.meishu.android.quiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.meishu.android.quiz.answer_is_true";
    private boolean isAnswerTrue;
    public static final String EXTRA_ANSWER_SHOWN = "com.meishu.android.quiz.answer_shown";


    private TextView answerTextView;
    private Button showAnswer;
    private TextView apiTextView;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent  = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        isAnswerTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        answerTextView = (TextView) findViewById(R.id.answer_text_view);
        showAnswer = (Button) findViewById(R.id.show_answer_button);
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnswerTrue)
                    answerTextView.setText(R.string.true_button);
                else
                    answerTextView.setText(R.string.false_button);

                setAnswerShownResult(true);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = showAnswer.getWidth() / 2;
                    int cy = showAnswer.getHeight() / 2;
                    float radius = showAnswer.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(showAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            answerTextView.setVisibility(View.VISIBLE);
                            showAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }
                else{
                    answerTextView.setVisibility(View.VISIBLE);
                    showAnswer.setVisibility(View.INVISIBLE);
                }
            }

        });

        apiTextView = (TextView) findViewById(R.id.api_text_view);
        apiTextView.setText("API version " + Build.VERSION.SDK_INT);

    }


}
