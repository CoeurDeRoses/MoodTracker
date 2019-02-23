package com.cdrcoeurderoses.moodtracker;


import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MoodManager extends AppCompatActivity {
    //this class handle all the property and method about the mood

    private String mood_name;
    private String mood_sentence;
    // i use the hexadecimal value to choose the right color and i will convert the value with the parse method
    // later in MHistoryMood class cause the setBackground method of a component accept only int value so the get method of color
    // will return an int value
    private String mood_color;
    private String mood_date;


    public MoodManager(){

    }

    public String getMood_name() {
        return mood_name;
    }

    public void setMood_name(String mood_name) {
        this.mood_name = mood_name;
    }

    public String getMood_sentence() {
        return mood_sentence;
    }

    public void setMood_sentence(String mood_sentence) {
        this.mood_sentence = mood_sentence;
    }

    public String getMood_color() {
        return mood_color;
    }

    public void setMood_color(String mood_color) {
        this.mood_color = mood_color;
    }

    public String getMood_date() {
        return mood_date;
    }

    public void setMood_date(String mood_date) {
        this.mood_date = mood_date;
    }

    /**
     * here the method which record many data at same time by calling other method
     * @param mood_name
     * @param mood_sentence
     * @param mood_color
     * @param mood_date
     */
    public void record_ManyData(String mood_name,String mood_sentence, String mood_color, String mood_date )
    {
        setMood_name(mood_name);
        setMood_sentence(mood_sentence);
        setMood_color(mood_color);
        setMood_date(mood_date);
    }

    public Set<String> mood_list_data()
    {
        Set<String> mood_list = new ArraySet<>();

        mood_list.add(mood_name);
        mood_list.add(mood_sentence);
        mood_list.add(mood_color);
        mood_list.add(mood_date);

        return mood_list;
    }

    //Overload method for Json way
    public String mood_list_data_gson_string()
    {
        String mood_list = "{";

        mood_list += " \"mood_name\"     : \""+ mood_name+"\",";
        mood_list += " \"mood_sentence\" : \""+ mood_sentence+"\",";
        mood_list += " \"mood_color\"    : \""+ mood_color+"\",";
        mood_list += " \"mood_date\"     : \""+ mood_date+"\"}";

        return mood_list;
    }


    public List<String> mood_list_data_gson()
    {
        List<String> mood_list =  new ArrayList<>();

        mood_list.add(mood_name);
        mood_list.add(mood_sentence);
        mood_list.add(mood_color);
        mood_list.add(mood_date);

        return mood_list;
    }



}
