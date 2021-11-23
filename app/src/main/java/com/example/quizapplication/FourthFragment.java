package com.example.quizapplication;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FourthFragment extends Fragment {
    Question question4;
    TextView question4Textview;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_fragment, container, false);
        question4 = getArguments().getParcelable("question4");
        question4Textview = (TextView) v.findViewById(R.id.second_question);

        //Set Question
        question4Textview.setText(question4.question);

        //Set Color
        v.setBackgroundColor(Color.parseColor(question4.color));
        return v;
    }
}
