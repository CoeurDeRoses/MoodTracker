package com.cdrcoeurderoses.moodtracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PiechartHistory extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_piechart);
       PieChart piechart_history = findViewById(R.id.piechart_drawing);
        //i use the method which set the percentage format value
        piechart_history.setUsePercentValues(true);

        //A description of the piechart to show
        Description mood_info = new Description();
        mood_info.setText("Niveau d'intentisé de chacune de vos humeurs sur les 7 derniers jours");
        mood_info.setTextSize(13f);

        //i add the description in the layout
        piechart_history.setDescription(mood_info);

        // i need take mood date from data file
        MoodManager mood_manager = new MoodManager();
        Gson gson = new Gson();
        String gson_file_read = getSharedPreferences("mood_file",MODE_PRIVATE).getString("1", "");
        String mood_data_json = gson.fromJson(gson_file_read, String.class);
        String[] many_gson_array = mood_manager.mood_ready_read(mood_data_json);

        String gson_file_read_2 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("2", "");
        String mood_data_json_2 = gson.fromJson(gson_file_read_2, String.class);
        String[] many_gson_array_2 = mood_manager.mood_ready_read(mood_data_json_2);


        String gson_file_read_3 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("3", "");
        String mood_data_json_3 = gson.fromJson(gson_file_read_3, String.class);
        String[] many_gson_array_3 = mood_manager.mood_ready_read(mood_data_json_3);

        String gson_file_read_4 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("4", "");
        String mood_data_json_4 = gson.fromJson(gson_file_read_4, String.class);
        String[] many_gson_array_4 = mood_manager.mood_ready_read(mood_data_json_4);

        String gson_file_read_5 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("5", "");
        String mood_data_json_5 = gson.fromJson(gson_file_read_5, String.class);
        String[] many_gson_array_5 = mood_manager.mood_ready_read(mood_data_json_5);

        String gson_file_read_6 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("6", "");
        String mood_data_json_6 = gson.fromJson(gson_file_read_6, String.class);
        String[] many_gson_array_6 = mood_manager.mood_ready_read(mood_data_json_6);

        String gson_file_read_7 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("7", "");
        String mood_data_json_7 = gson.fromJson(gson_file_read_7, String.class);
        String[] many_gson_array_7 = mood_manager.mood_ready_read(mood_data_json_7);

        //need to make a calcul, any time i find a relative color in a tab i add the value to relative variable
        // in a loop
        float yellow=0, green=0, blue=0, grey=0, red=0;
        String[][] array_many_gson_array = {many_gson_array, many_gson_array_2,many_gson_array_3, many_gson_array_4
        , many_gson_array_5, many_gson_array_6, many_gson_array_7};

        for(int i=0; i<7;i++) {

            if(array_many_gson_array[i][5].contentEquals("#EAE108"))
            {
                yellow+=1;

            }
            if(array_many_gson_array[i][5].contentEquals("#65D164"))
            {
                green+=1;
            }
            if(array_many_gson_array[i][5].contentEquals("#2663EE"))
            {
                blue+=1;
            }
            if(array_many_gson_array[i][5].contentEquals("#6B6C6F"))
            {
                grey+=1;
            }
            if(array_many_gson_array[i][5].contentEquals("#D12A2B"))
            {
                red +=1;
            }

        }

        //I select the number of total mood which exist in purpose to have have clear data, if a variable is superior than 0 that mean we can use its
        float total_mood=0;

        if(yellow>0)
        {
            total_mood+=1;
        }

        if(green>0)
        {
            total_mood+=1;
        }
        if(blue>0)
        {
            total_mood+=1;
        }
        if(grey>0)
        {
            total_mood+=1;
        }
        if(red>0)
        {
            total_mood+=1;
        }

        //after that i need to divide by the total of mood and multiply by 100 to get to percentage
        float percent_yellow = (yellow*100f)/total_mood , percent_green= (green*100f)/total_mood,
                percent_blue=(blue*100f)/total_mood, percent_grey=(grey*100f)/total_mood, percent_red= (red*100f)/total_mood;


        // i create a List of PieEntry which set the data in the piechart.
        List<PieEntry> list_percent = new ArrayList<>();
        list_percent.add(new PieEntry(percent_yellow,"Super bonne"));
        list_percent.add(new PieEntry(percent_green,"Bonne"));
        list_percent.add(new PieEntry(percent_blue,"Normale"));
        list_percent.add(new PieEntry(percent_grey,"Mauvaise"));
        list_percent.add(new PieEntry(percent_red,"Très mauvaise"));



        PieDataSet mood_data_set = new PieDataSet(list_percent,"");

        PieData mood_pie_data = new PieData(mood_data_set);

        //We put colors to make the view of each percentage data clear
        mood_data_set.setColors(ColorTemplate.COLORFUL_COLORS);


        //We put the list in the view
        piechart_history.setData(mood_pie_data);


    }
}
