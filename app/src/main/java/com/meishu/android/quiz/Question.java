package com.meishu.android.quiz;

public class Question {

    private int textResId;
    private boolean isAnswerTrue;

    public Question(int textResId, boolean isAnswerTrue){
        this.textResId = textResId;
        this.isAnswerTrue = isAnswerTrue;

    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public int getTextResId() {

        return textResId;
    }
}
