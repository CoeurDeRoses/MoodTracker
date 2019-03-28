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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

public class MHistoryMood extends AppCompatActivity {
    double deviceWidth,deviceHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhistorymood);
        //Prepare all the buttons needed and active the scroll listener of the mood
        InitializeBehavior();
    }

    /**
     * The methdd to set the right color to the rectangle
     * @param Hexadecimal_color
     * @return
     */
    public int moodRectangleColor(String Hexadecimal_color)
    {
        int moodColor;
        switch (Hexadecimal_color)
        {
            case "#EAE108": //Yellow color
                moodColor = Color.parseColor(Hexadecimal_color);break;

            case "#65D164": //Green color
                moodColor = Color.parseColor(Hexadecimal_color);break;

            case "#2663EE": //Blue color
                moodColor = Color.parseColor(Hexadecimal_color);break;

            case "#6B6C6F": //Grey color
                moodColor = Color.parseColor(Hexadecimal_color);break;

            case "#D12A2B": //Red color
                moodColor = Color.parseColor(Hexadecimal_color);break;

            default: //Yellow color
                moodColor = Color.parseColor("#65D164");break;
        }

        return moodColor;
    }

    /**
     * The method which set the right weight to the rectangle
     * no need to modify yellow mood, always at full weight
     */
    private void SetDeviceWidth(String currentColor, RelativeLayout Current_Frame){

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
        switch (currentColor) {

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

    public void InitializeBehavior()
    {
        Mood moodManager = new Mood();

        // i make the link between variable and the component of history layout
        TextView tYesterday = findViewById(R.id.mood_View),
                t2Daysago = findViewById(R.id.mood_View_2),
                t3Daysago = findViewById(R.id.mood_View_3),
                t4Daysago = findViewById(R.id.mood_View_4),
                t5Daysago = findViewById(R.id.mood_View_5),
                t6Daysago = findViewById(R.id.mood_View_6),
                t1Weekago = findViewById(R.id.mood_View_7);

        //and i put all of them in an array

        TextView[] arrayTextview = {tYesterday, t2Daysago, t3Daysago, t4Daysago,
                t5Daysago, t6Daysago, t1Weekago};
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

        RelativeLayout[] arrayFrameLayout = {frame1, frame2, frame3, frame4,frame5,frame6,frame7};


        // i create as well an array of button about comment

        Button showComment= findViewById(R.id.show_comm);
        Button showComment2= findViewById(R.id.show_comm_2);
        Button showComment3= findViewById(R.id.show_comm_3);
        Button showComment4= findViewById(R.id.show_comm_4);
        Button showComment5= findViewById(R.id.show_comm_5);
        Button showComment6= findViewById(R.id.show_comm_6);
        Button showComment7= findViewById(R.id.show_comm_7);

        Button[] arrayButtons= {showComment,showComment2,showComment3,showComment4
                ,showComment5, showComment6,showComment7};


        //I convert an int variable to string in each turn, the first parameter of getString() method
        //The moodReadyRead method will send at each turn the array created in ArrayManyGson

        Gson gson = new Gson();
        String gsonFileRead, moodDataGson;

        //the array of mood data
        final String[][] arrayManyGson = new String[7][];
        int i=0;
        // i create the array which show the right string value about how many time passed since the mood
        // recorded in a day

        String[] arrayTimeTextview ={"Hier","Avant hier","Il y'a 3 jour","Il y'a 4 jours"
                ,"Il y'a 5 jours","Il y'a 6 jours","Il y'a 1 semaine"};
        while(i<7)
        {

            gsonFileRead = getSharedPreferences("mood_file",MODE_PRIVATE).getString(String.valueOf(i+1), "");
            moodDataGson = gson.fromJson(gsonFileRead, String.class);
            arrayManyGson[i] = moodManager.moodReadyRead(moodDataGson);
            //if the comment is not void i set the show comment button visible
            // i need to set invisible aswell if the comment variable is null


            if(!arrayManyGson[i][3].contentEquals("" ) && !arrayManyGson[i][3].contentEquals(" " ) && !arrayManyGson[i][3].contentEquals("null")) {
                arrayButtons[i].setVisibility(View.VISIBLE);

                //and i allow the user to press the button to show the Toast message about his comment

                final int finalI = i;
                arrayButtons[i].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), arrayManyGson[finalI][3], Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                arrayButtons[i].setVisibility(View.INVISIBLE);}


            if(!arrayManyGson[i][1].contentEquals("first_launch_application")) {
                arrayTextview[i].setBackgroundColor(moodRectangleColor(arrayManyGson[i][5]));
                arrayTextview[i].setText(arrayTimeTextview[i]);
                SetDeviceWidth(arrayManyGson[i][5],arrayFrameLayout[i]);

            }
            i++;
        }
    }


}
