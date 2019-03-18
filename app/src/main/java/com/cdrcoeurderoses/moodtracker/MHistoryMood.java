package com.cdrcoeurderoses.moodtracker;



import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.lang.reflect.Array;

public class MHistoryMood extends AppCompatActivity {
    double deviceWidth,deviceHeight;

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



        //I take the frame data of each label to set the right width relative to the mood inside
        RelativeLayout frame1 = findViewById(R.id.frame1);
        RelativeLayout frame2 = findViewById(R.id.frame2);
        RelativeLayout frame3 = findViewById(R.id.frame3);
        RelativeLayout frame4 = findViewById(R.id.frame4);
        RelativeLayout frame5 = findViewById(R.id.frame5);
        RelativeLayout frame6 = findViewById(R.id.frame6);
        RelativeLayout frame7 = findViewById(R.id.frame7);

        RelativeLayout[] array_FrameLayout = {frame1, frame2, frame3, frame4,frame5,frame6,frame7};


        // i create as well an array of button about comment

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
        final String[][] array_Many_Gson = new String[7][];
         int i=0;
         // i create the array which show the right string value about how many time passed since the mood
        // recorded in a day

        String[] array_time_textview ={"Hier","Avant hier","Il y'a 3 jour","Il y'a 4 jours"
                    ,"Il y'a 5 jours","Il y'a 6 jours","Il y'a 1 semaine"};
        while(i<7)
        {

            gson_file_read = getSharedPreferences("mood_file",MODE_PRIVATE).getString(String.valueOf(i+1), "");
            mood_data_gson = gson.fromJson(gson_file_read, String.class);
            array_Many_Gson[i] = mood_manager.mood_ready_read(mood_data_gson);
            //if the comment is not void i set the show comment button visible
            // i need to set invisible aswell if the comment variable is null


            if(!array_Many_Gson[i][3].contentEquals("" ) && !array_Many_Gson[i][3].contentEquals(" " ) && !array_Many_Gson[i][3].contentEquals("null")) {
                array_buttons[i].setVisibility(View.VISIBLE);

                //and i allow the user to press the button to show the Toast message about his comment

                final int finalI = i;
                array_buttons[i].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), array_Many_Gson[finalI][3], Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                array_buttons[i].setVisibility(View.INVISIBLE);}


            if(!array_Many_Gson[i][1].contentEquals("first_launch_application")) {
                array_Textview[i].setBackgroundColor(mood_rectangle_color(array_Many_Gson[i][5]));
                array_Textview[i].setText(array_time_textview[i]+" "+array_Many_Gson[i][7]);
                SetDeviceWidth(array_Many_Gson[i][5],array_FrameLayout[i]);

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
        int mood_color;
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
                mood_color = Color.parseColor("#65D164");break;
        }

        return mood_color;
    }

    /**
     * The method which set the right weight to the rectangle
     * no need to modify yellow mood, always at full weight
     */
    private void SetDeviceWidth(String current_color, RelativeLayout Current_Frame){

        //The size is allocated proportionally. An id must be assigned to each item containing the mood. The FrameLayout has the Textview.
        //First you have to get the width and the height of the device having lit the application, dynamically
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;


        // and retrieve the values ​​in width and height variables.
        // Then we set 4 relative percentages for each mood, the yellow mood always taking the
        // total width of the screen needless to do anything when an item falls on yellow. The table will therefore have 4 elements.
        double [] viewSizeMultiplier = {0.2, 0.4, 0.6, 0.8};
        switch (current_color) {

            //And here we set the right dimensions relative to the right color
            case "#65D164": Current_Frame.setLayoutParams
                    ( new LinearLayout.LayoutParams((int)(deviceWidth*viewSizeMultiplier[3]),(int)deviceHeight/7));
                break;

            case "#2663EE": Current_Frame.setLayoutParams
                    ( new LinearLayout.LayoutParams((int)(deviceWidth*viewSizeMultiplier[2]),(int)deviceHeight/7));
                break;

            case "#6B6C6F": Current_Frame.setLayoutParams
                    ( new LinearLayout.LayoutParams((int)(deviceWidth*viewSizeMultiplier[1]),(int)deviceHeight/7));
                break;

            case "#D12A2B": Current_Frame.setLayoutParams
                    ( new LinearLayout.LayoutParams((int)(deviceWidth*viewSizeMultiplier[0]),(int)deviceHeight/7));
                break;

            default:
                break;
        }
    }


}
