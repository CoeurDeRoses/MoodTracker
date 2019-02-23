package com.cdrcoeurderoses.moodtracker;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MHistoryMood extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhistorymood);

        // i make the link between variable and the component of history layout
        TextView t_yesterday = findViewById(R.id.mood_View),
                t_2daysago = findViewById(R.id.mood_View2),
                t_3daysago = findViewById(R.id.mood_View3),
                t_4daysago = findViewById(R.id.mood_View4),
                t_5daysago = findViewById(R.id.mood_View5),
                t_6daysago = findViewById(R.id.mood_View6),
                t_1weekago = findViewById(R.id.mood_View7);
        //getSharedPreferences method is used to read data in the xml file and give their values
        //to the components of the layout history. I put the name of the file i want data and i put the key needed
        //Here the component can now take the value from the file

        //i create a SetString defaut value in order to respect the method getStringSet
        Set<String> mood_list_defaut = new ArraySet<>();

        mood_list_defaut.add("SanGoku");
        mood_list_defaut.add("Vegeta");
        mood_list_defaut.add("Broly");
        mood_list_defaut.add("Jiren");

        //Xml way
        //Set<String> set_mood_list = getSharedPreferences("mood_data_file",MODE_PRIVATE).getStringSet("many_values",mood_list_defaut);

        //Here is the way to how i handle the mood data to allow me to manage them in the layout component
        //I convert all
        //String many = set_mood_list.toString().replaceAll(","," ").replaceAll("\\[|\\]","");

        //Json way
        Gson gson = new Gson();
        String json_file = getSharedPreferences("mood_data_file_gson",MODE_PRIVATE).getString("mood_data_gson", "");
        String many_gson = gson.fromJson(json_file, String.class);

        // order of the method take order like this way: name - sentence - color - date
        // after i take them from the file, order is reversed

        // the rectangle of component must change and be relative to the moods
        // More the mood is good more bigger the rectangle he is. And of course the background color
        // must be associated to the mood i will use switch condition
        //I  modify the property of the textView object with their method
        //About the size,the yellow rectangle can stay as he is already but the other must change


        t_4daysago.setText(many_gson);


    }
}
