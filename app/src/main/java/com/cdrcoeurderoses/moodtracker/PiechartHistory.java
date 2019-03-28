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
        //Prepare all the buttons needed and active the scroll listener of the mood
        IntializeBehavior();


    }

    public void IntializeBehavior()
    {
        PieChart piechartHistory = findViewById(R.id.piechart_drawing);
        //i use the method which set the percentage format value
        piechartHistory.setUsePercentValues(true);

        //A description of the piechart to show
        Description moodInfo = new Description();
        moodInfo.setText("Niveau d'intentisé de chacune de vos humeurs sur les 7 derniers jours");
        moodInfo.setTextSize(11f);

        //i add the description in the layout
        piechartHistory.setDescription(moodInfo);

        // i need take mood date from data file and put them all in an array

        Mood mood_manager = new Mood();
        Gson gson = new Gson();
        String gsonFileRead;
        String moodDataGson;

        String[][] arrayManyGson = new String[7][];

        int i=0;
        while(i<7) {

            gsonFileRead = getSharedPreferences("mood_file", MODE_PRIVATE).getString(String.valueOf(i+1), "");
            moodDataGson = gson.fromJson(gsonFileRead, String.class);
            arrayManyGson[i] = mood_manager.moodReadyRead(moodDataGson); i++;
        }

        //need to make a calcul, any time i find a relative color in a tab i add the value to relative variable
        // in a loop
        float yellow=0, green=0, blue=0, grey=0, red=0;
        i=0;
        while (i<7){

            if(arrayManyGson[i][5].contentEquals("#EAE108"))
            {
                yellow+=1;

            }
            if(arrayManyGson[i][5].contentEquals("#65D164"))
            {
                green+=1;
            }
            if(arrayManyGson[i][5].contentEquals("#2663EE"))
            {
                blue+=1;
            }
            if(arrayManyGson[i][5].contentEquals("#6B6C6F"))
            {
                grey+=1;
            }
            if(arrayManyGson[i][5].contentEquals("#D12A2B"))
            {
                red +=1;
            }
            i++;
        }

        //I select the number of total mood which exist in purpose to have have clear data, if a variable is superior than 0 that mean we can use its
        float totalMood=0;

        if(yellow>0)
        {
            totalMood+=1;
        }

        if(green>0)
        {
            totalMood+=1;
        }
        if(blue>0)
        {
            totalMood+=1;
        }
        if(grey>0)
        {
            totalMood+=1;
        }
        if(red>0)
        {
            totalMood+=1;
        }

        //after that i need to divide by the total of mood and multiply by 100 to get to percentage
        float percentYellow = (yellow*100f)/totalMood , percentGreen= (green*100f)/totalMood,
                percentBlue=(blue*100f)/totalMood, percentGrey=(grey*100f)/totalMood, percentRed= (red*100f)/totalMood;


        // i create a List of PieEntry which set the data in the piechart.
        List<PieEntry> list_percent = new ArrayList<>();
        list_percent.add(new PieEntry(percentYellow,"Super bonne"));
        list_percent.add(new PieEntry(percentGreen,"Bonne"));
        list_percent.add(new PieEntry(percentBlue,"Normale"));
        list_percent.add(new PieEntry(percentGrey,"Mauvaise"));
        list_percent.add(new PieEntry(percentRed,"Très mauvaise"));



        PieDataSet moodDataSet = new PieDataSet(list_percent,"");

        PieData moodPieData = new PieData(moodDataSet);

        //We put colors to make the view of each percentage data clear
        moodDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        //We put the list in the view
        piechartHistory.setData(moodPieData);
    }
}
