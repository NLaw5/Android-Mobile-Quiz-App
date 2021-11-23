package com.example.quizapplication;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ThirdFragment extends Fragment {

    Question question3;
    TextView question3Textview;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_fragment, container, false);
        question3 = getArguments().getParcelable("question3");
        question3Textview = (TextView) v.findViewById(R.id.second_question);

        //Set Question
        question3Textview.setText(question3.question);

        //Set Color
        v.setBackgroundColor(Color.parseColor(question3.color));
        return v;
    }
}
