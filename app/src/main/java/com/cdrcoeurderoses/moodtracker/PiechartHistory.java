package com.cdrcoeurderoses.moodtracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

        // i need take mood date from data file and put them all in an array

        Mood mood_manager = new Mood();
        Gson gson = new Gson();
        String gson_file_read;
        String mood_data_gson;

        String[][] array_many_gson = new String[7][];

        int i=0;
        while(i<7) {

            gson_file_read = getSharedPreferences("mood_file", MODE_PRIVATE).getString(String.valueOf(i+1), "");
            mood_data_gson = gson.fromJson(gson_file_read, String.class);
            array_many_gson[i] = mood_manager.mood_ready_read(mood_data_gson); i++;
        }

        //need to make a calcul, any time i find a relative color in a tab i add the value to relative variable
        // in a loop
        float yellow=0, green=0, blue=0, grey=0, red=0;
        i=0;
        while (i<7){

            if(array_many_gson[i][5].contentEquals("#EAE108"))
            {
                yellow+=1;

            }
            if(array_many_gson[i][5].contentEquals("#65D164"))
            {
                green+=1;
            }
            if(array_many_gson[i][5].contentEquals("#2663EE"))
            {
                blue+=1;
            }
            if(array_many_gson[i][5].contentEquals("#6B6C6F"))
            {
                grey+=1;
            }
            if(array_many_gson[i][5].contentEquals("#D12A2B"))
            {
                red +=1;
            }
            i++;
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
