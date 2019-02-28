package com.cdrcoeurderoses.moodtracker;



import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.lang.reflect.Array;

public class MHistoryMood extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhistorymood);

        Mood mood_manager = new Mood();

        // i make the link between variable and the component of history layout
        TextView t_yesterday = findViewById(R.id.mood_View),
                t_2daysago = findViewById(R.id.mood_View_2),
                t_3daysago = findViewById(R.id.mood_View_3),
                t_4daysago = findViewById(R.id.mood_View_4),
                t_5daysago = findViewById(R.id.mood_View_5),
                t_6daysago = findViewById(R.id.mood_View_6),
                t_1weekago = findViewById(R.id.mood_View_7);

        //and i put all of them in an array

        TextView[] array_Textview = {t_yesterday, t_2daysago, t_3daysago, t_4daysago,
                t_5daysago, t_6daysago, t_1weekago};
        //getSharedPreferences method is used to read data in the gson file and give their values
        //to the components of the layout history. I put the name of the file i want data and i put the key needed
        //Here the component can now take the value from the file

        // i create aswell an array of button about comment

        Button show_comment= findViewById(R.id.show_comm);
        Button show_comment_2= findViewById(R.id.show_comm_2);
        Button show_comment_3= findViewById(R.id.show_comm_3);
        Button show_comment_4= findViewById(R.id.show_comm_4);
        Button show_comment_5= findViewById(R.id.show_comm_5);
        Button show_comment_6= findViewById(R.id.show_comm_6);
        Button show_comment_7= findViewById(R.id.show_comm_7);

        Button[] array_buttons= {show_comment,show_comment_2,show_comment_3,show_comment_4
        ,show_comment_5, show_comment_6,show_comment_7};


        //I convert an int variable to string in each turn, the first parameter of getString() method
        //The mood_ready_read method will send at each turn the array created in ArrayManyGson

        Gson gson = new Gson();
        String gson_file_read, mood_data_gson;

        //the array of mood data
        String[][] array_Many_Gson = new String[7][];
         int i=0;
        while(i<7)
        {

            gson_file_read = getSharedPreferences("mood_file",MODE_PRIVATE).getString(String.valueOf(i+1), "");
            mood_data_gson = gson.fromJson(gson_file_read, String.class);
            array_Many_Gson[i] = mood_manager.mood_ready_read(mood_data_gson);
            //if the comment is not void i set the show comment button visible
            // i need to set invisible aswell if the comment variable is null
            if(!array_Many_Gson[i][3].contentEquals("" ) && !array_Many_Gson[i][3].contentEquals(" " ) && !array_Many_Gson[i][3].contentEquals("null")) {
                array_buttons[i].setVisibility(View.VISIBLE);
                array_Textview[i].setText(array_Many_Gson[i][3]+" "+array_Many_Gson[i][7]);
                //and i allow the user to press the button to show the Toast message about his comment
                /*
                show_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), array_Many_Gson[i][3]+" first", Toast.LENGTH_LONG).show();
                    }
                });*/
            }
            else{
                array_buttons[i].setVisibility(View.INVISIBLE);}


            if(!array_Many_Gson[i][1].contentEquals("first_launch_application")) {
                array_Textview[i].setText(array_Many_Gson[i][3]);
                array_Textview[i].setBackgroundColor(mood_rectangle_color(array_Many_Gson[i][5]));

            }
            i++;
        }

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
    public int mood_DP(String Hexadecimal_color)
    {

        int mood_dp=0;
        if(Hexadecimal_color=="#65D164")
            mood_dp=200;

        if(Hexadecimal_color=="#2663EE")
            mood_dp=150;

        if(Hexadecimal_color=="#6B6C6F")
            mood_dp=100;

        if(Hexadecimal_color=="#D12A2B")
            mood_dp=50;

        return mood_dp;
    }


}
