package com.cdrcoeurderoses.moodtracker;



import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
                t_2daysago = findViewById(R.id.mood_View_2),
                t_3daysago = findViewById(R.id.mood_View_3),
                t_4daysago = findViewById(R.id.mood_View_4),
                t_5daysago = findViewById(R.id.mood_View_5),
                t_6daysago = findViewById(R.id.mood_View_6),
                t_1weekago = findViewById(R.id.mood_View_7);
        //getSharedPreferences method is used to read data in the xml file and give their values
        //to the components of the layout history. I put the name of the file i want data and i put the key needed
        //Here the component can now take the value from the file


        Gson gson = new Gson();
        String gson_file_read = getSharedPreferences("mood_file",MODE_PRIVATE).getString("1", "");
        String mood_data_json = gson.fromJson(gson_file_read, String.class);
        String[] many_gson_array = mood_manager.mood_ready_read(mood_data_json);

        //if the comment is not void i set the show comment button visible
        // i need to set invisible aswell if the comment variable is null
        Button show_comment= findViewById(R.id.show_comm);
        if(!many_gson_array[3].contentEquals(""))
            show_comment.setVisibility(View.VISIBLE);
        else
            show_comment.setVisibility(View.INVISIBLE);

        if(!many_gson_array[1].contentEquals("first_launch_application")) {
            t_yesterday.setText("Hier "+many_gson_array[3]);

            t_yesterday.setBackgroundColor(mood_rectangle_color(many_gson_array[5]));
        }




        String gson_file_read_2 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("2", "");
        String mood_data_json_2 = gson.fromJson(gson_file_read_2, String.class);
        final String[] many_gson_array_2 = mood_manager.mood_ready_read(mood_data_json_2);

        Button show_comment_2= findViewById(R.id.show_comm_2);
        if(!many_gson_array_2[3].contentEquals("")) {
            show_comment_2.setVisibility(View.VISIBLE);
            //and we allow the user to press the button and set the event which show the toast message
            //about the comment
            show_comment_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),many_gson_array_2[3],Toast.LENGTH_LONG).show();
                }
            });
        }

        else
            show_comment_2.setVisibility(View.INVISIBLE);

        if(!many_gson_array_2[1].contentEquals("first_launch_application")) {
            t_2daysago.setText("Avant hier "+many_gson_array_2[3]);
            t_2daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_2[5]));
        }




        String gson_file_read_3 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("3", "");
        String mood_data_json_3 = gson.fromJson(gson_file_read_3, String.class);
        String[] many_gson_array_3 = mood_manager.mood_ready_read(mood_data_json_3);

        Button show_comment_3= findViewById(R.id.show_comm_3);
        if(!many_gson_array_3[3].contentEquals(""))
            show_comment_3.setVisibility(View.VISIBLE);
        else
            show_comment_3.setVisibility(View.INVISIBLE);

        if(!many_gson_array_3[1].contentEquals("first_launch_application")) {
            t_3daysago.setText("Il y'a 3 jours "+many_gson_array_3[3]);
            t_3daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_3[5]));
        }





        String gson_file_read_4 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("4", "");
        String mood_data_json_4 = gson.fromJson(gson_file_read_4, String.class);
        String[] many_gson_array_4 = mood_manager.mood_ready_read(mood_data_json_4);

        Button show_comment_4= findViewById(R.id.show_comm_4);
        if(!many_gson_array_4[3].contentEquals(""))
            show_comment_4.setVisibility(View.VISIBLE);
        else
            show_comment_4.setVisibility(View.INVISIBLE);

        if(!many_gson_array_4[1].contentEquals("first_launch_application")) {
            t_4daysago.setText("Il y'a 4 jours "+many_gson_array_4[3]);
            t_4daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_4[5]));
        }






        String gson_file_read_5 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("5", "");
        String mood_data_json_5 = gson.fromJson(gson_file_read_5, String.class);
        String[] many_gson_array_5 = mood_manager.mood_ready_read(mood_data_json_5);

        Button show_comment_5= findViewById(R.id.show_comm_5);
        if(!many_gson_array_5[3].contentEquals(""))
            show_comment_5.setVisibility(View.VISIBLE);
        else
            show_comment_5.setVisibility(View.INVISIBLE);

        if(!many_gson_array_5[1].contentEquals("first_launch_application")) {
            t_5daysago.setText("Il y'a 5 jours "+many_gson_array_5[3]);
            t_5daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_5[5]));
        }





        String gson_file_read_6 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("6", "");
        String mood_data_json_6 = gson.fromJson(gson_file_read_6, String.class);
        String[] many_gson_array_6 = mood_manager.mood_ready_read(mood_data_json_6);

        Button show_comment_6= findViewById(R.id.show_comm_6);
        if(!many_gson_array_6[3].contentEquals(""))
            show_comment_6.setVisibility(View.VISIBLE);
        else
            show_comment_6.setVisibility(View.INVISIBLE);

        if(!many_gson_array_6[1].contentEquals("first_launch_application")) {
            t_6daysago.setText("Il y'a 6 jours "+many_gson_array_6[3]);
            t_6daysago.setBackgroundColor(mood_rectangle_color(many_gson_array_6[5]));
        }


        String gson_file_read_7 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("7", "");
        String mood_data_json_7 = gson.fromJson(gson_file_read_7, String.class);
        String[] many_gson_array_7 = mood_manager.mood_ready_read(mood_data_json_7);



        if(!many_gson_array_7[1].contentEquals("first_launch_application")) {
            t_1weekago.setText("Il y'a une semaine "+many_gson_array_7[3]);
            t_1weekago.setBackgroundColor(mood_rectangle_color(many_gson_array_7[5]));
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
