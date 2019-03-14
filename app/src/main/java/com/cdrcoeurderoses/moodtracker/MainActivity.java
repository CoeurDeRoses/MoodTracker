package com.cdrcoeurderoses.moodtracker;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
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

        //initialiser une liste 5 mood en faisant leur sélection via le scroll + compteur



    private MediaPlayer playMusic;

    //To record the user choice i make a string variable which have to be put in each case
    // to receive the relative color mood_color and other userfull variable
    private String mood_name;
    private String mood_sentence;

    private String mood_Color;
    private SimpleDateFormat mood_date;

    //The following variable will be used to make dialog box exist
    private MainActivity currentActivity;
    private Context context;

    //a boolean to determinate if the user set mood or not in a day
    // if not the app dont put a default value so boolean get true



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
        setContentView(R.layout.activity_main_2);


        this.configureViewPager();



        //launcher_mood_data();
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
                get_current_mood();
          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });
        launcher_mood_data();

        //If the user don't press the button in a day we must set the defaut value
        if(mood_name == null && mood_Color==null)
        {
            mood_Color="#EAE108"; mood_name="Super bonne humeur";
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
        if(update_as_much_as_needed()>0)
        {
            Toast.makeText(getApplicationContext(), "Comparaison date ---AND "+string_mood_date, Toast.LENGTH_LONG).show();
            // we must always start the process from the last-1 to the recent just before its data
            // and we repeat the same until the second i also code the deserealisation process first and
            // the serealisation process after
            //key 1 is always set by the user so i don't need to to update that key
            //so tab size is six

            for(int j = 0; j< update_as_much_as_needed(); j++) {


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
        mood_sentence="";
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

    public Context getContext() {
        return context;
    }
}
