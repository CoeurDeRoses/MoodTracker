package com.cdrcoeurderoses.moodtracker;


import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MoodManager extends AppCompatActivity {
    //this class handle all the property and method about the mood

    private String mood_name="";
    private String mood_sentence="";
    // i use the hexadecimal value to choose the right color and i will convert the value with the parse method
    // later in MHistoryMood class cause the setBackground method of a component accept only int value so the get method of color
    // will return an int value
    private String mood_color="";
    private String mood_date="";


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

    /**
     * Method to prepare the strings for JSON format way
     * when i extract data
     * @return
     */
    public String mood_list_data_gson_string()
    {
        String mood_list = "{";

        mood_list += "\"mood_name\":\""+ mood_name+"\",";
        mood_list += "\"mood_sentence\":\""+ mood_sentence+"\",";
        mood_list += "\"mood_color\":\""+ mood_color+"\",";
        mood_list += "\"mood_date\":\""+ mood_date+"\"}";

        return mood_list;
    }

    /**
     * This method format the string to make him able to be managed and for handle data
     * to show them well in the mood history i delete many kind of char useless to show
     * @param the_string
     * @return
     */
    public String[] mood_ready_read(String the_string){

        //Curly bracket deleted
        the_string = the_string.replaceAll("\\{|\\}","");
        //quote
        the_string = the_string.replaceAll("\"","");
        // i replace all the comma by ":" to make this character the split parameter to create an array
        the_string = the_string.replaceAll(",",":");
        //I split all the data and put them in an array
        String[] the_array_string = the_string.split(":");
        // Data are recorded in the array in this way
        // array[0] mood_name array[1] mood_name value. the exact same order of the data send by user after user select mood data
        // and recorded in JSON file

        return the_array_string;
    }




}
