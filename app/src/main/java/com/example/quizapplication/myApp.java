package com.example.quizapplication;

import android.app.Application;

public class myApp extends Application {
    private QuestionStorage questionManager = new QuestionStorage();

    public QuestionStorage getQuestionManager() {return questionManager;}
}
