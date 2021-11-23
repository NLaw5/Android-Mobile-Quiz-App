package com.example.quizapplication;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionBank {
    ArrayList<Question> questionBank = new ArrayList<Question>();
    ArrayList<String> colors = new ArrayList<String>();

    QuestionBank()
    {

        //Our initialization of the questions
        Question question1 =
                new Question(0, "", "");
        Question question2 =
                new Question(0, "", "");
        Question question3 =
                new Question(1, "", "");
        Question question4 =
                new Question(0, "", "");

        colors.add("#FF03DAC5");
        colors.add("#CD5C5C");
        colors.add("#9370DB");
        colors.add("#1E90ff");

        Collections.shuffle(colors);

        questionBank.add(question1);
        questionBank.add(question2);
        questionBank.add(question3);
        questionBank.add(question4);

        //How to randomize our colors
        for(int i = 0; i < questionBank.size(); i++)
        {
            questionBank.get(i).setColors(colors.get(i));
        }
    }

    public void addQuestion(List<String> input_Questions)
    {
        for(int i = 0; i < questionBank.size(); i++)
        {
            questionBank.get(i).setQuestion(input_Questions.get(i));
        }
    }

    public void questionShuffle() {
        Collections.shuffle(questionBank);
        Collections.shuffle(colors);

        //How to randomize our colors
        for(int i = 0; i < questionBank.size(); i++)
        {
            questionBank.get(i).setColors(colors.get(i));
        }
    }

    public Question returnQuestion(int question_index) {
        return questionBank.get(question_index - 1);
    }

    public void addQuestionBank(ArrayList<Question>  input_questions)
    {
        this.questionBank.clear();
        this.questionBank = input_questions;
    }
}
