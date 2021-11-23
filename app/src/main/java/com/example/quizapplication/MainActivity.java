package com.example.quizapplication;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Integer counter = 1;
    Integer answer = 0;
    Integer numOfCorrect = 0;
    ProgressBar pb;

    QuestionBank questionBankforUser = new QuestionBank(); //creates our question
    QuestionStorage questionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prog(counter);
        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);

        questionManager = ((myApp)getApplication()).getQuestionManager();

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);


        //-------------------------------------------------------
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //--------------------------------------------------------


        //SAVED INSTANCE STATE
        if(savedInstanceState == null)
        {
            List<String> list_of_questions = Arrays.asList(
                    getResources().getString(R.string.Question1),
                    getResources().getString(R.string.Question2),
                    getResources().getString(R.string.Question3),
                    getResources().getString(R.string.Question4)
            );

            questionBankforUser.addQuestion(list_of_questions);
            questionBankforUser.questionShuffle();
        }
        else
        {
            questionBankforUser.addQuestionBank(
                    savedInstanceState.getParcelableArrayList("listOfQuestions"));
            counter = savedInstanceState.getInt("counter");
            numOfCorrect = savedInstanceState.getInt("correct");

            setSavedFragments(counter);
            prog(counter);
        }


        // For first initialization check
        if (counter == 1) {
            Fragment firstQuestion = new FirstFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question1", questionBankforUser.returnQuestion(counter));

            firstQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, firstQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
        //-------------------------------------------------------
    }

    //Button clicked
    @Override
    public void onClick(View view) {
        //-------------------------------------------------------
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //-------------------------------------------------------

        //-------------------------------------------------------
        if (((Button) view).getText().toString().equals("True")) {
            answer = 1;
        } else {
            answer = 0;
        }

        int correct_answer = questionBankforUser.questionBank.get(counter - 1).answer;
        //Check answer:
        if (answer == correct_answer) {
            Toast.makeText(this, getResources().getString(R.string.Correct), Toast.LENGTH_SHORT).show();
            numOfCorrect++;
        } else {
            Toast.makeText(this, getResources().getString(R.string.Incorrect), Toast.LENGTH_SHORT).show();
        }

        //--------------------------------------------------------
        counter = counter + 1;

        if (counter == 2) {

            FirstFragment firstFragmentObject = (FirstFragment)fm.findFragmentById(R.id.frame_layout);
            transaction.remove(firstFragmentObject);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

            Fragment secondQuestion = new SecondFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question2", questionBankforUser.returnQuestion(counter));

            secondQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, secondQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            prog(counter);
        } else if (counter == 3) {
            SecondFragment secondFragmentObject = (SecondFragment) fm.findFragmentById(R.id.frame_layout);
            transaction.remove(secondFragmentObject);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

            Fragment thirdQuestion = new ThirdFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question3", questionBankforUser.returnQuestion(counter));

            thirdQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, thirdQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            prog(counter);
        } else if (counter == 4) {
            ThirdFragment thirdFragmentObject = (ThirdFragment) fm.findFragmentById(R.id.frame_layout);
            transaction.remove(thirdFragmentObject);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);


            Fragment fourthQuestion = new FourthFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question4", questionBankforUser.returnQuestion(counter));

            fourthQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, fourthQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            prog(counter);

        } else {
            //Shuffle Questions
            questionBankforUser.questionShuffle();

            //This is at the end of our questions for now
            counter = 1;

            FourthFragment fourthFragmentObject = (FourthFragment) fm.findFragmentById(R.id.frame_layout);
            transaction.remove(fourthFragmentObject);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);


            Fragment firstQuestion = new FirstFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question1", questionBankforUser.returnQuestion(counter));

            firstQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, firstQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            prog(1);

            //Alert dialog
            AlertDialog result = new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.Result))
                    .setMessage(getResources().getString(R.string.total1) + " " + numOfCorrect
                            + " " + getResources().getString(R.string.total2))
                    .setPositiveButton(getResources().getString(R.string.Save), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            questionManager.saveQuestionsInInternalPrivateFile(MainActivity.this,
                                    numOfCorrect);
                            numOfCorrect = 0; //restart of questions
                        }
                    })
                    .setNeutralButton(getResources().getString(R.string.Ignore), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            numOfCorrect = 0; //restart of questions
                            dialogInterface.dismiss();
                        }
                    })
                    .create();

            result.show();
        }
        transaction.commit();
    }

    //Set Fragments:
    //NOTE: we can't use the full logic above, therefore this logic must be used which is simliar
    //to the logic for OnClick except certain key features are taken out (remove fragment)
    public void setSavedFragments(int counterInput)
    {
        //-------------------------------------------------------
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //-------------------------------------------------------

        if (counterInput == 2) {

            Fragment secondQuestion = new SecondFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question2", questionBankforUser.returnQuestion(counterInput));

            secondQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, secondQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            prog(counter);
        } else if (counterInput == 3) {


            Fragment thirdQuestion = new ThirdFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question3", questionBankforUser.returnQuestion(counterInput));

            thirdQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, thirdQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            prog(counter);
        } else if (counterInput == 4) {


            Fragment fourthQuestion = new FourthFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question4", questionBankforUser.returnQuestion(counterInput));

            fourthQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, fourthQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            prog(counter);
        } else {

            Fragment firstQuestion = new FirstFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("question1", questionBankforUser.returnQuestion(counterInput));

            firstQuestion.setArguments(bundle); //send Bundle to fragment
            transaction.add(R.id.frame_layout, firstQuestion);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        transaction.commit();
    }



    public void prog(int progress) {
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setMax(4);
        pb.setProgress(progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.averageMenu:{
                double average = 0.0;
                average = questionManager.getAverageFromInternalPrivateFile(MainActivity.this);
                if(Double.isNaN(average))
                {
                    average = 0.0;
                }
                //Alert dialog
                AlertDialog result = new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.Average))
                        .setMessage(getResources().getString(R.string.toastAverage) + " " +
                                String.format("%.2f", average)).create();
                result.show();
                break;
            }
            case R.id.deleteMenu:{
                questionManager.resetTheStorage(MainActivity.this);
                AlertDialog result = new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.Delete))
                        .setMessage(getResources().getString(R.string.toastDelete)).create();
                result.show();
                break;
            }
        }
        return true;
    }

    //Save our work:
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);

        //Need to save:
        outState.putInt("counter", counter);
        outState.putInt("correct", numOfCorrect);
        outState.putParcelableArrayList("listOfQuestions", questionBankforUser.questionBank);
    }
}