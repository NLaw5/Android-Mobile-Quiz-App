package com.example.quizapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    int answer; //1 for true, 0 for false
    String question;
    String color; //Will change the color, will be Hexcode

    Question(int answer_input, String question_input, String color_input)
    {
        answer = answer_input;
        question = question_input;
        color = color_input;
    }

    public void setColors(String colors_input)
    {
        this.color = colors_input;
    }

    public void setQuestion(String question_input)
    {
        this.question = question_input;
    }

    protected Question(Parcel in) {
        answer = in.readInt();
        question = in.readString();
        color = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(answer);
        parcel.writeString(question);
        parcel.writeString(color);
    }
}
