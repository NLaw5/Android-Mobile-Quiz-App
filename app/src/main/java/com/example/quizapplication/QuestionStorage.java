package com.example.quizapplication;

import android.app.Activity;
import android.content.Context; //our memory

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionStorage {
    String filename = "questionsRecord.txt";

    public void resetTheStorage(Activity activity){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_PRIVATE); // reset
            fileOutputStream.write("".getBytes());  //only write empty file beside it

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    //Saving our questions
    public void saveQuestionsInInternalPrivateFile(Activity activity, int result){
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_APPEND); // continue writing, basically Mode_append vs. Mode_private
            fileOutputStream.write((Integer.toString(result)+"$").getBytes()); //$ or plus indicates the end of a new task
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                fileOutputStream.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        // internal Stream
    }

    public Double getAverageFromInternalPrivateFile(Activity activity){
        FileInputStream fileInputStream = null;
        int read;
        ArrayList<Double> list = new ArrayList<>();
        Double average = 0.0;
        StringBuffer buffer = new StringBuffer();
        try
        {
            fileInputStream = activity.openFileInput(filename);
            while(( read = fileInputStream.read() )!= -1 ){ //-1 is EOF
                buffer.append((char)read); //convert the integer to the character
            }
            //Buffer is all the data from our file, need to send to function so that format
            //Fits in ArrayList<ToDo> list
            list =  fromStringToList( buffer.toString());

            for(int i = 0; i < list.size(); i++)
            {
                average += list.get(i);
            }
            average = average/list.size();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally{
            try{
                if(fileInputStream != null)
                {
                    fileInputStream.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return average;
    }

    private ArrayList<Double> fromStringToList(String str){ // str come from the file
        // there is a $ between tasks
        ArrayList<Double> list = new ArrayList(0);
        int index = 0;
        for (int i = 0 ; i < str.toCharArray().length ; i++){
            if (str.toCharArray()[i] == '$'){
                String task = str.substring(index, i); //will tell when a task ends
                list.add(Double.parseDouble(task));
                index = i+1;
            }
        }
        return list;
    }
}
