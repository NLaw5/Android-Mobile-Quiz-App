package com.example.quizapplication;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SecondFragment extends Fragment {

    Question question2;
    TextView question2Textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.second_fragment, container, false);
        question2 = getArguments().getParcelable("question2");
        question2Textview = (TextView) v.findViewById(R.id.second_question);

        //Set Question
        question2Textview.setText(question2.question);

        //Set Color
        v.setBackgroundColor(Color.parseColor(question2.color));
        return v;
    }
}

