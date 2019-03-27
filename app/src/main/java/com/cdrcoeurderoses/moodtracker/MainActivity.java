package com.cdrcoeurderoses.moodtracker;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;




public class MainActivity extends AppCompatActivity{

    VerticalViewPager pager;
    //I import the Mediaplayer class to play the music
    //I will initialize the playmusic variable with each relative mp3 value
    // in button code after users press the buttons.
    // With a switch condition i play the relative music
    //object mood created to prepare the record of data


    final Mood mood_manager = new Mood();

    private MediaPlayer playMusic = null;

    //To record the user choice i make a string variable which have to be put in each case
    // to receive the relative color mood_color and other userfull variable
    private String mood_name;
    private String mood_sentence;

    private String mood_Color;
    private SimpleDateFormat mood_date;

    //The following variable will be used to make dialog box exist
    private MainActivity currentActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentActivity = this;
        /*the setContentView () method is used to determine which layout file to use.
          R = Resources is the class that contains all the identifiers of all project resources
        The findViewById() takes as parameter the identifier of the view that interests us,
        and returns the corresponding view
         */
        //setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        //Enable ViewPager to work
        this.configureViewPager();



        //Here i set the actual mood of the day to the viewpager
        ShowActualMood();
        //Prepare all the buttons needed and active the scroll listener of the mood
        InitializeBehavior();



    }

    /**
     * This method construct in a right way the viewpager and set right the all the elements
     */
    private void configureViewPager() {

        //i implement the color for the adapter
        int[] array_colors={Color.parseColor("#EAE108"),
                Color.parseColor("#65D164"),
                        Color.parseColor("#2663EE"),
                                Color.parseColor("#6B6C6F"),
                                        Color.parseColor("#D12A2B")};
        // 1 - Get ViewPager from layout
        pager =  findViewById(R.id.activity_main_viewpager);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new AdapterFrag(getSupportFragmentManager(),array_colors) {
        });
    }




    /**
     * this method is used to handle the case that if the user didnt opened the app since
     * 2 days or more, we must update the data file and switch the mood as much as days gone
     * @return
     */
    public int update_as_much_as_needed()
    {
        Date today = new Date();
        mood_date = new SimpleDateFormat("yyyyMMdd");//the current date
        String string_mood_date= mood_date.format(today);


        //Json way
        Gson gson_manager = new Gson();
        //for the file one
        String last_date_file = getSharedPreferences("mood_file",MODE_PRIVATE).getString("0", "");

        last_date_file = gson_manager.fromJson(last_date_file, String.class);
        //Prepare the data for record
        String[] array_of_mood = mood_manager.mood_ready_read(last_date_file);

        //i convert the both date, current date and last date recorded from the file
        int current_date = Integer.parseInt(string_mood_date), int_last_date_file = Integer.parseInt(array_of_mood[7]);
        //lets make a subtraction, the difference of the calcul mean how many time we have to update the file and switch data
        // in launch_mood_data()

        int diff_date = current_date - int_last_date_file;
        //the loop which determinate how many time

        return diff_date;
    }

    /**
     * This method update the mood data when it needed
     */
    public void launcher_mood_data()
    {
        //and here i create the variable to manage date input in the file by using method of a
        //SharedPreferences object, first of all i put the name of the xml file
        //and put MOVE_PRIVATE to make sure the data can be accessed only by MoodTracker application
        // and not outside.
        //The Editor object will be used to write data in the file
        //first parameter is the key and second is the value

        //Date and SimpleDateFormat implemented to take and prepare the current date to be recorded
        Date today = new Date();
        mood_date = new SimpleDateFormat("yyyyMMdd");//the current date
        String string_mood_date= mood_date.format(today);


        mood_manager.record_ManyData(mood_name,mood_sentence,mood_Color,string_mood_date);


        //Json way
        Gson gson_manager = new Gson();
        //for the file one
        SharedPreferences gson_file_write = getSharedPreferences("mood_file", MODE_PRIVATE);
        SharedPreferences.Editor mood_gson_Editor = gson_file_write.edit();
        String mood_data_gson = gson_manager.toJson(mood_manager.mood_list_data_gson_string());


        // i test if the keys don't exist i don't initialise them
        // and with a set of defaut value except key 1
        String first_start_set = gson_manager.toJson("{\"mood_name\":\"first_launch_application\",\"" +
                "mood_sentence\":\" \",\"mood_color\":\" \",\"mood_date\":\" \"}");


        //i need to create 8 keys even if it' about 7 days data
        //we must record the data of the current day to be ready to switch to the yesterday data
        //the choice selected in the current day must not be showed but we need to record that
        //current day will always be at the key 0
        for(int i=0; i<8;i++) {
            if (i==0 && !gson_file_write.contains(String.valueOf(i))) {
                mood_gson_Editor.putString(String.valueOf(i), mood_data_gson).apply();
            }
            if (i > 0 && !gson_file_write.contains(String.valueOf(i))) {
                mood_gson_Editor.putString(String.valueOf(i), first_start_set).apply();
            }
        }
        //here is the code behavior to handle data about change date if we change the day
         int number_days_gone = update_as_much_as_needed();
        if(number_days_gone>0)
        {
            // we must always start the process from the last-1 to the recent just before its data
            // and we repeat the same until the second i also code the deserealisation process first and
            // the serealisation process after
            //key 1 is always set by the user so i don't need to to update that key
            //so tab size is six

            for(int j = 0; j< number_days_gone; j++) {

//if            the loop has more than one turn i set the code for set default value for the day mood wasn't choose
                //and for each day past i add one unit to be sure to have the right day
                if(j>0)

                {
                    mood_sentence="";
                    int current_date = j+Integer.parseInt(string_mood_date);
                    mood_manager.record_ManyData("Bonne humeur", "", "#65D164", String.valueOf(current_date));
                    String default_mood = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
                    //and record
                    mood_gson_Editor.putString("0", default_mood).apply();

                }

                for (int a = 7; a > 0; a--) {

                    String[] array_of_mood;
                    String current_iteration_data;//to record the current string mood of iteration
                    // I take Take data from the inferior number key  and put them in the superior number key
                    String gson_read_from_inferior_key = getSharedPreferences("mood_file", MODE_PRIVATE).getString(String.valueOf(a - 1), "");

                    String mood_data_gson_superior_key = gson_manager.fromJson(gson_read_from_inferior_key, String.class);
                    //Prepare the data for record
                    array_of_mood = mood_manager.mood_ready_read(mood_data_gson_superior_key);
                    //next step

                    //i need to put condition which set defaut value for the days gone since the time the application
                    // didn't get opened by the user or just opened with set mood, when i = 1
                    //if the app didn't get opened since yesterday i set defaut value

                    mood_manager.record_ManyData(array_of_mood[1], array_of_mood[3], array_of_mood[5], array_of_mood[7]);

                    // next
                    current_iteration_data = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
                    //and record
                    mood_gson_Editor.putString(String.valueOf(a), current_iteration_data).apply();
                }




            }



        }

        //always put the record instruction after the record of other keys the old data of key one will be erased
        //and the key 1 will not take it but gonna take the new data as key

        mood_gson_Editor.putString("0", mood_data_gson).apply();




    }

    public void get_current_mood()
    {
        int current_mood = pager.getCurrentItem();

        switch (current_mood)
        {
            case 0:mood_Color = "#EAE108";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_superhappy);playMusic.start();

                Toast.makeText(getApplicationContext(),"Très bonne humeur",Toast.LENGTH_SHORT).show();
                mood_name ="Super bonne humeur"; break;

            case 1:mood_Color = "#65D164";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_happy); playMusic.start();
                Toast.makeText(getApplicationContext(),"Bonne humeur",Toast.LENGTH_SHORT).show();
                mood_name ="Bonne humeur"; break;

            case 2: mood_Color = "#2663EE";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_normal); playMusic.start();
                Toast.makeText(getApplicationContext(),"Humeur normale",Toast.LENGTH_SHORT).show();
                mood_name ="Humeur normale"; break;

            case 3:  mood_Color = "#6B6C6F";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_disappointed); playMusic.start();
                Toast.makeText(getApplicationContext(),"Mauvaise humeur",Toast.LENGTH_SHORT).show();
                mood_name ="Mauvaise humeur";break;

            case 4:mood_Color = "#D12A2B";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_sad); playMusic.start();
                Toast.makeText(getApplicationContext(),"Très mauvaise  humeur",Toast.LENGTH_SHORT).show();
                mood_name ="Très mauvaise humeur"; break;

            default: break;
        }
        launcher_mood_data();
    }

    public void InitializeBehavior()
    {
        // I create the ImageButton needed to switch to history page
        ImageButton goHistory =  findViewById(R.id.GoHistory);
        // The button to go in percentage history mood
        Button goPiechart = findViewById(R.id.piechart_button);

        // I call the method setOnClickListener to describe what happen when we press Gohistory button
        // Listener object contain a method which will be call when event happen
        goHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onClick method is called each time the user presses the button

                //Here i use the method start activity to launch the activity
                //The intent used to switch to an other activity
                Intent HistoryIntent = new Intent(MainActivity.this,MHistoryMood.class);
                startActivity(HistoryIntent);
            }
        });

        goPiechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PiechartIntent = new Intent(MainActivity.this,PiechartHistory.class);
                startActivity(PiechartIntent);
            }
        });


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(playMusic!=null)
                    playMusic.stop();

                get_current_mood();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        launcher_mood_data();

        //If the user don't scroll
        if(mood_name == null)
        {
            mood_Color="#65D164"; mood_name="Bonne humeur";
            //and we update the key 0
            launcher_mood_data();
        }


        //now i need to handle the button which have to record the possible comment of the user about his mood

        ImageButton btAddComment = findViewById(R.id.AddComment);
        btAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogComment dialogAddComment = new DialogComment(currentActivity);
                dialogAddComment.getBtValidate().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //No need to use the setPositive or Negative method, cause i created my customized method
                        // with my customized buttons

                        Toast.makeText(getApplicationContext(),"Commentaire enregistré",Toast.LENGTH_SHORT).show();
                        //I use cancel() method from dialog class to close the dialog box after a choice


                        mood_sentence = dialogAddComment.getEtComment().getText().toString();
                        if(mood_sentence==null)
                        {
                            mood_sentence="";
                        }
                        //else, If user wrote a comment i record this
                        else
                        {
                            launcher_mood_data();
                        }

                        dialogAddComment.cancel();
                    }
                });
                dialogAddComment.getBtCancel().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Commentaire annulé",Toast.LENGTH_SHORT).show();
                        dialogAddComment.cancel();
                    }
                });

                dialogAddComment.show();
            }
        });
        //Here i put the button to allow users to share their mood to other by sending their mood from the application
        //with the intent way i will use Dialog.builder
        ImageButton btShare = findViewById(R.id.ShareMood);
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder share_dialog = new AlertDialog.Builder(currentActivity);
                share_dialog.setTitle("Partager votre humeur");
                share_dialog.setMessage("Voulez vous partagez vos informations d'humeur avec vos connaissances ?");
                share_dialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Code about share operation
                        Toast.makeText(getApplicationContext(),"Ouverture des applications suggérés pour l'envoie",Toast.LENGTH_SHORT).show();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);

                        String[] array_message = mood_manager.mood_ready_read(mood_manager.mood_list_data_gson_string());
                        String message_send = "Salut ! "+array_message[1]+" aujord'hui. "+array_message[3];
                        sendIntent.putExtra(Intent.EXTRA_TEXT, message_send);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);

                    }
                }).setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                share_dialog.show();
            }
        });
    }

    /**
     * To stop the music launched if activity isn't showed
     */
   @Override
    protected void onPause()
    {
        super.onPause();
        if(playMusic!=null)
            playMusic.stop();
    }

    /**
     * This method set the current mood to be showed of they day while user didn't changed the mood in the same day
     */
    public void ShowActualMood()
    {
        SharedPreferences gson_file = getSharedPreferences("mood_file", MODE_PRIVATE);

        //If key 0 don't exist and it's first launch of app
        if(!gson_file.contains("0"))
        {
            pager.setCurrentItem(1); get_current_mood();
        }
        //if key 0 exist i check about put default value or other values

        else {

            //if we change a day i set the defaut mood
            int day_change = update_as_much_as_needed();
            if(day_change>0)
            {
                pager.setCurrentItem(1);get_current_mood();
            }

            //if day_change=0 so if we didn't changed the day  
            else{
                Gson gson_manager = new Gson();
                String gson_read_from_0 = getSharedPreferences("mood_file", MODE_PRIVATE).getString("0", "");
                String mood_data_gson_superior_key = gson_manager.fromJson(gson_read_from_0, String.class);
                String[] array_moodToShow = mood_manager.mood_ready_read(mood_data_gson_superior_key);
                switch (array_moodToShow[5]) {
                    case "#EAE108":
                        pager.setCurrentItem(0); get_current_mood();
                        break;
                    case "#65D164":
                        pager.setCurrentItem(1);get_current_mood();
                        break;
                    case "#2663EE":
                        pager.setCurrentItem(2);get_current_mood();
                        break;
                    case "#6B6C6F":
                        pager.setCurrentItem(3);get_current_mood();
                        break;
                    case "#D12A2B":
                        pager.setCurrentItem(4);get_current_mood();
                        break;
                    default:
                        pager.setCurrentItem(1);get_current_mood();
                        break;
                }

            }
        }
    }


}
