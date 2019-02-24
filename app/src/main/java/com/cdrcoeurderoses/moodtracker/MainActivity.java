package com.cdrcoeurderoses.moodtracker;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    //I import the Mediaplayer class to play the music
    //I will initialize the playmusic variable with each relative mp3 value
    // in button code after users press the buttons.
    // With a switch condition i play the relative music
    //object mood created to prepare the record of data


    final MoodManager mood_manager = new MoodManager();




    private MediaPlayer playMusic;

    //To record the user choice i make a string variable which have to be put in each case
    // to receive the relative color mood_color and other userfull variable
    private String mood_name;
    private String mood_sentence;
    private String mood_Color;
    private SimpleDateFormat mood_date;

    //The following variable will be used to make dialog box exist
    private MainActivity currentActivity;
    //The onCreate method is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentActivity = this;
        /*the setContentView () method is used to determine which layout file to use.
          R = Resources is the class that contains all the identifiers of all project resources
        The findViewById() takes as parameter the identifier of the view that interests us,
        and returns the corresponding view
         */
        setContentView(R.layout.activity_main);
        //launcher_mood_data();
        // I create the ImageButton needed to switch to history page
        ImageButton goHistory =  findViewById(R.id.GoHistory);

        launcher_mood_data();

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

        //here i Create the 5 buttons which allows to users to record their mood
        //and to confirm with a little dialog message if they are really sure
        ImageButton btSuperHappy = findViewById(R.id.buttonSuperHappy); ImageButton btHappy = findViewById(R.id.buttonHappy);
        ImageButton btNormal = findViewById(R.id.buttonNormal); ImageButton btDisappointed = findViewById(R.id.buttonDisappointed);
        ImageButton btSad = findViewById(R.id.buttonSad);



        //For very happy mood
        alertDialogMood(btSuperHappy,"Etes vous vraiment de très bonne humeur ?");
        //For happy mood
        alertDialogMood(btHappy,"Etes vous vraiment de bonne humeur ?");
        //For normal mood
        alertDialogMood(btNormal,"Etes vous vraiment d'une humeur normale ?");
        //For disappointed mood
        alertDialogMood(btDisappointed,"Etes vous vraiment de mauvaise humeur ?");
        //for sad mood
        alertDialogMood(btSad,"Etes vous vraiment de très mauvaise humeur ?");


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
                        mood_sentence = dialogAddComment.getEtComment().getText().toString();
                        Toast.makeText(getApplicationContext(),"Commentaire enregistré",Toast.LENGTH_SHORT).show();
                        //I use cancel() method from dialog class to close the dialog box after a choice

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
                        sendIntent.putExtra(Intent.EXTRA_TEXT, mood_manager.mood_list_data_gson_string());
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

    //The method which will be used for every button and show the relative question on the select mood.
    // and the relative button
    public void alertDialogMood ( ImageButton theButton, final String moodQuestion){



        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //in the switch condition i will use the moodQuestion to determinate which music
                // i have to launch with start() method from the MediaPlayer object
                switch (moodQuestion)
                {
                    //hexadecimal of yellow, green, blue, grey and red and that following order are used.
                    case "Etes vous vraiment de très bonne humeur ?":
                        mood_Color = "#EAE108"; playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_superhappy);playMusic.start();
                        mood_name ="Super bonne humeur";
                        break;

                    case "Etes vous vraiment de bonne humeur ?":
                        mood_Color = "#65D164"; playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_happy); playMusic.start();
                        mood_name ="Bonne humeur";
                        break;

                    case "Etes vous vraiment d'une humeur normale ?":
                        mood_Color = "#2663EE"; playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_normal); playMusic.start();
                        mood_name ="Humeur normale";
                        break;

                    case "Etes vous vraiment de mauvaise humeur ?":
                        mood_Color = "#6B6C6F"; playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_disappointed); playMusic.start();
                        mood_name ="Mauvaise humeur";
                        break;

                    case "Etes vous vraiment de très mauvaise humeur ?":
                        mood_Color = "#D12A2B";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_sad); playMusic.start();
                        mood_name ="Très mauvaise humeur";
                        break;

                    default: mood_Color="#EAE108"; mood_name="Super bonne humeur"; break;
                }

                /*I create a Builder Object which ask if the user want to confirm his mood and record that*/

                AlertDialog.Builder alertMood = new AlertDialog.Builder(MainActivity.this);
                //The name of the Dialogbox will be the mood selected
                alertMood.setTitle(mood_name);
                //The question of that dialogbox
                alertMood.setMessage(moodQuestion);
                alertMood.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        launcher_mood_data();
                        //Message of confirmation
                        Toast.makeText(getApplicationContext(), "Données enregistré ", Toast.LENGTH_SHORT).show();
                    }
                });
                alertMood.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Message of cancellation
                        Toast.makeText(getApplicationContext(), "Données non enregistré", Toast.LENGTH_SHORT).show();
                    }
                });
                alertMood.show();
            }
        });
    }

    /**
     * this method return the most recent date of data recorded in order to check if we need to update if we change the date
     * compare the most recent data and the date provide by the android system is enough for that
     * @return
     */
    public String most_recent_date_recorded()
    {
        Gson gson = new Gson();
        String gson_file_read = getSharedPreferences("mood_file",MODE_PRIVATE).getString("1", "");
        //i put all in a strng
        String mood_data_json = gson.fromJson(gson_file_read, String.class);
        // i make the string workable to manage data with method of MoodManager
        String[] many_gson_array = mood_manager.mood_ready_read(mood_data_json);
       // Toast.makeText(getApplicationContext(), "Donnée non enregistré " + many_gson_array[7], Toast.LENGTH_LONG).show();
        return  many_gson_array[7];
    }

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
        mood_date = new SimpleDateFormat("ddMMyy");//the current date
        String string_mood_date= mood_date.format(today);



        //Here i determinate if it's the first time some start app and we have no value
        //i make the condition in the MHistoryActivity code wrong to not execute setbackground and setText
        // and let the backgroung of each rectangle normal
        //to find if it's the first time i check if we have the key 1 recorder
        //if not the rectange stay normal but if
        if(!getSharedPreferences("mood_file", MODE_PRIVATE).contains("1")) {
            mood_name = "first_launch_application";
        }
        mood_manager.record_ManyData(mood_name, mood_sentence,mood_Color,string_mood_date);

                        /* Xml file way
                        SharedPreferences mood_file = getSharedPreferences("mood_data_file", MODE_PRIVATE);
                        SharedPreferences.Editor mood_Editor = mood_file.edit();
                        mood_Editor.putStringSet("many_values",mood_manager.mood_list_data()).apply();
                        */

        //Json way
        Gson gson_manager = new Gson();
        //for the file one
        SharedPreferences gson_file_write = getSharedPreferences("mood_file", MODE_PRIVATE);
        SharedPreferences.Editor mood_gson_Editor = gson_file_write.edit();
        String mood_data_gson = gson_manager.toJson(mood_manager.mood_list_data_gson_string());


        // i test if the keys don't exist i don't initialise them
        if (!gson_file_write.contains("1")) {
            mood_gson_Editor.putString("1", mood_data_gson).apply();
        }
        if (!gson_file_write.contains("2")) {
            mood_gson_Editor.putString("2", mood_data_gson).apply();
        }
        if (!gson_file_write.contains("3")) {
            mood_gson_Editor.putString("3", mood_data_gson).apply();
        }

        if (!gson_file_write.contains("4")) {
            mood_gson_Editor.putString("4", mood_data_gson).apply();
        }
        if (!gson_file_write.contains("5")) {
            mood_gson_Editor.putString("5", mood_data_gson).apply();
        }
        if (!gson_file_write.contains("5")) {
            mood_gson_Editor.putString("5", mood_data_gson).apply();
        }
        if (!gson_file_write.contains("6")) {
            mood_gson_Editor.putString("6", mood_data_gson).apply();
        }

        if (!gson_file_write.contains("7")) {
            mood_gson_Editor.putString("7", mood_data_gson).apply();
        }
        //here is the code behavior to handle data about change date if we change the day
        // if we change the day the day i must switch the data contained to key 1 to the 2 and the
        // data of key 2 to the 3 and the same way for the rest
        if(!string_mood_date.contentEquals(most_recent_date_recorded()))
        {
            Toast.makeText(getApplicationContext(), "Comparaison date " + most_recent_date_recorded()+" AND "+string_mood_date, Toast.LENGTH_LONG).show();
            // we must always start the process from the last-1 to the recent just before its data
            // and we repeat the same until the second i also code the derealisation process first and
            // the serealisation process after

            // Take data from key 6 and put them and key 7 ----------------------------
            String gson_read_from_key_6 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("6", "");
            //i put all in a strng

            String mood_data_gson_7 = gson_manager.fromJson(gson_read_from_key_6, String.class);
            //Prepare the data for record
            String[] many_gson_array_7 = mood_manager.mood_ready_read(mood_data_gson_7);
            //next step
            mood_manager.record_ManyData(many_gson_array_7[1],many_gson_array_7[3],many_gson_array_7[5],many_gson_array_7[7]);
            // next
            mood_data_gson_7 = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
            //and record
            mood_gson_Editor.putString("7",mood_data_gson_7).apply();

            // Take data from key 5 and put them and key 6 ----------------------------

            String gson_read_from_key_5 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("5", "");
            //i put all in a strng
            String mood_data_gson_6 = gson_manager.fromJson(gson_read_from_key_5, String.class);
            //Prepare the data for record
            String[] many_gson_array_6 = mood_manager.mood_ready_read(mood_data_gson_6);
            //next step
            mood_manager.record_ManyData(many_gson_array_6[1],many_gson_array_6[3],many_gson_array_6[5],many_gson_array_6[7]);
            // next
            mood_data_gson_6 = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
            //and record
            mood_gson_Editor.putString("6",mood_data_gson_6).apply();

            // Take data from key 4 and put them and key 5 ----------------------------
            String gson_read_from_key_4 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("4", "");

            String mood_data_gson_5 = gson_manager.fromJson(gson_read_from_key_4, String.class);
            //Prepare the data for record
            String[] many_gson_array_5 = mood_manager.mood_ready_read(mood_data_gson_5);
            //next step
            mood_manager.record_ManyData(many_gson_array_5[1],many_gson_array_5[3],many_gson_array_5[5],many_gson_array_5[7]);
            // next
            mood_data_gson_5 = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
            //and record
            mood_gson_Editor.putString("5",mood_data_gson_5).apply();


            // Take data from key 3 and put them and key 4 ----------------------------

            String gson_read_from_key_3 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("3", "");
            //i put all in a strng

            String mood_data_gson_4 = gson_manager.fromJson(gson_read_from_key_3, String.class);
            //Prepare the data for record
            String[] many_gson_array_4 = mood_manager.mood_ready_read(mood_data_gson_4);
            //next step
            mood_manager.record_ManyData(many_gson_array_4[1],many_gson_array_4[3],many_gson_array_4[5],many_gson_array_4[7]);
            // next
            mood_data_gson_4 = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
            //and record
            mood_gson_Editor.putString("4",mood_data_gson_4).apply();


            // Take data from key 2 and put them and key 3 ----------------------------

            String gson_read_from_key_2 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("2", "");
            //i put all in a strng

            String mood_data_gson_3 = gson_manager.fromJson(gson_read_from_key_2, String.class);
            //Prepare the data for record
            String[] many_gson_array_3 = mood_manager.mood_ready_read(mood_data_gson_3);
            //next step
            mood_manager.record_ManyData(many_gson_array_3[1],many_gson_array_3[3],many_gson_array_3[5],many_gson_array_3[7]);
            // next
            mood_data_gson_3 = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
            //and record
            mood_gson_Editor.putString("3",mood_data_gson_3).apply();


            // Take data from key 1 and put them and key 2 ----------------------------

            String gson_read_from_key_1 = getSharedPreferences("mood_file",MODE_PRIVATE).getString("1", "");
            //i put all in a strng

            String mood_data_gson_2 = gson_manager.fromJson(gson_read_from_key_1, String.class);
            //Prepare the data for record
            String[] many_gson_array_2 = mood_manager.mood_ready_read(mood_data_gson_2);
            //next step
            mood_manager.record_ManyData(many_gson_array_2[1],many_gson_array_2[3],many_gson_array_2[5],many_gson_array_2[7]);
            // next
            mood_data_gson_2 = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
            //and record
            mood_gson_Editor.putString("2",mood_data_gson_2).apply();


        }
        //always put the record instruction after the test or the old data of key one will be erased
        //and the key 2 will not take it but gonna take the new data as key
        mood_gson_Editor.putString("1", mood_data_gson).apply();
    }
}
