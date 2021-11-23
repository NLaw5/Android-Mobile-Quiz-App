package com.example.quizapplication;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    Question question1;
    TextView question1Textview;
    int counter = 0;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_fragment, container, false);

        question1 = getArguments().getParcelable("question1");
        question1Textview = (TextView) v.findViewById(R.id.first_question);

        //Set Question
        question1Textview.setText(question1.question);

        //Set Color
        v.setBackgroundColor(Color.parseColor(question1.color));

        counter = counter + 1;
        return v;
    }
}