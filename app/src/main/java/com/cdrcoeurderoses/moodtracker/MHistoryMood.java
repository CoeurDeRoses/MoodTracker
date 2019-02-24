package com.cdrcoeurderoses.moodtracker;



import android.graphics.Color;
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


        //Json way and deserealisation
        Gson gson = new Gson();
        String gson_file_read = getSharedPreferences("mood_file",MODE_PRIVATE).getString("1", "");
        String gson_file_read_2 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("2", "");
        String gson_file_read_3 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("3", "");
        String gson_file_read_4 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("4", "");
        String gson_file_read_5 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("5", "");
        String gson_file_read_6 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("6", "");
        String gson_file_read_7 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("7", "");


        //i put all in a string
        String mood_data_json = gson.fromJson(gson_file_read, String.class);
        String mood_data_json_2 = gson.fromJson(gson_file_read_2, String.class);
        String mood_data_json_3 = gson.fromJson(gson_file_read_3, String.class);
        String mood_data_json_4 = gson.fromJson(gson_file_read_4, String.class);
        String mood_data_json_5 = gson.fromJson(gson_file_read_5, String.class);
        String mood_data_json_6 = gson.fromJson(gson_file_read_6, String.class);
        String mood_data_json_7 = gson.fromJson(gson_file_read_7, String.class);

        // i make the string workable to manage data with method of MoodManager
        String[] many_gson_array = mood_manager.mood_ready_read(mood_data_json);
        String[] many_gson_array_2 = mood_manager.mood_ready_read(mood_data_json_2);
        String[] many_gson_array_3 = mood_manager.mood_ready_read(mood_data_json_3);
        String[] many_gson_array_4 = mood_manager.mood_ready_read(mood_data_json_4);
        String[] many_gson_array_5 = mood_manager.mood_ready_read(mood_data_json_5);
        String[] many_gson_array_6 = mood_manager.mood_ready_read(mood_data_json_6);
        String[] many_gson_array_7 = mood_manager.mood_ready_read(mood_data_json_7);

        //and i show them
        t_yesterday.setText(many_gson_array[3]+" "+many_gson_array[1]+" "+many_gson_array[7]);
        t_yesterday.setBackgroundColor(mood_rectangle_color(many_gson_array[5]));

        t_2daysago.setText(many_gson_array_2[3]+" "+many_gson_array_2[1]+" "+many_gson_array_2[7]);
        t_2daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_2[5]));

        t_3daysago.setText(many_gson_array_3[3]+" "+many_gson_array_3[1]+" "+many_gson_array_3[7]);
        t_3daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_3[5]));

        t_4daysago.setText(many_gson_array_4[3]+" "+many_gson_array_4[1]+" "+many_gson_array_4[7]);
        t_4daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_4[5]));

        t_5daysago.setText(many_gson_array_5[3]+" "+many_gson_array_5[1]+" "+many_gson_array_5[7]);
        t_5daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_5[5]));

        t_6daysago.setText(many_gson_array_6[3]+" "+many_gson_array_6[1]+" "+many_gson_array_6[7]);
        t_6daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_6[5]));

        t_1weekago.setText(many_gson_array_7[3]+" "+many_gson_array_7[1]+" "+many_gson_array_7[7]);
        t_1weekago.setBackgroundColor(mood_rectangle_color(many_gson_array_7[5]));


    }

    /**
     * The methdd to set the right color to the rectangle
     * @param Hexadecimal_color
     * @return
     */
    public int mood_rectangle_color(String Hexadecimal_color)
    {
        int mood_color = 0;
        switch (Hexadecimal_color)
        {
            case "#EAE108": //Yellow color
                mood_color = Color.parseColor(Hexadecimal_color);break;

            case "#65D164": //Green color
                mood_color = Color.parseColor(Hexadecimal_color);break;

            case "#2663EE": //Blue color
                mood_color = Color.parseColor(Hexadecimal_color);break;

            case "#6B6C6F": //Grey color
                mood_color = Color.parseColor(Hexadecimal_color);break;

            case "#D12A2B": //Red color
                mood_color = Color.parseColor(Hexadecimal_color);break;

            default: //Yellow color
                mood_color = Color.parseColor("#EAE108");break;
        }

        return mood_color;
    }

    /**
     * The method which set the right weight to the rectangle
     * no need to modify yellow mood, always at full weight
     * @param Hexadecimal_color
     * @return
     */
    public String mood_DP(String Hexadecimal_color)
    {

        String mood_dp="";
        if(Hexadecimal_color=="#65D164")
            mood_dp="200dp";

        if(Hexadecimal_color=="#2663EE")
            mood_dp="150dp";

        if(Hexadecimal_color=="#6B6C6F")
            mood_dp="100dp";

        if(Hexadecimal_color=="#D12A2B")
            mood_dp="50dp";

        return mood_dp;
    }
}
