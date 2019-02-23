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

        MoodManager mood_manager = new MoodManager();

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


        //Json way
        Gson gson = new Gson();
        String gson_file_read = getSharedPreferences("mood_file",MODE_PRIVATE).getString("1", "");
        String gson_file_read_2 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("2", "");
        String gson_file_read_3 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("3", "");
        //i put all in a strng
        String mood_data_json = gson.fromJson(gson_file_read, String.class);
        String mood_data_json_2 = gson.fromJson(gson_file_read_2, String.class);
        String mood_data_json_3 = gson.fromJson(gson_file_read_3, String.class);
        // i make the string workable to manage data with method of MoodManager
        String[] many_gson_array = mood_manager.mood_ready_read(mood_data_json);
        String[] many_gson_array_2 = mood_manager.mood_ready_read(mood_data_json_2);
        String[] many_gson_array_3 = mood_manager.mood_ready_read(mood_data_json_3);

        t_yesterday.setText(many_gson_array[0]+" "+many_gson_array[1]+" "+many_gson_array[7]);
        t_2daysago.setText(many_gson_array_2[0]+" "+many_gson_array_2[1]+" "+many_gson_array_2[7]);
        t_3daysago.setText(many_gson_array_3[0]+" "+many_gson_array_3[1]+" "+many_gson_array_3[7]);


    }
}
