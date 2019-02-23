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


    final MoodManager mood_recorder = new MoodManager();


    private MediaPlayer playMusic;

    //To record the user choice i make a string variable which have to be put in each case
    // to receive the relative color mood_color and other userfull variable
    private String mood_name;
    private String mood_setence="a comment";
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
        // I create the ImageButton needed to switch to history page
        ImageButton goHistory =  findViewById(R.id.GoHistory);

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
                        mood_setence = dialogAddComment.getEtComment().getText().toString();
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
                        sendIntent.putExtra(Intent.EXTRA_TEXT,mood_recorder.mood_list_data_gson_string());
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
                        mood_Color = "2663EE"; playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_normal); playMusic.start();
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

                        //and here i create the variable to manage date input in the file by using method of a
                        //SharedPreferences object, first of all i put the name of the xml file
                        //and put MOVE_PRIVATE to make sure the data can be accessed only by MoodTracker application
                        // and not outside.
                        //The Editor object will be used to write data in the file
                        //first parameter is the key and second is the value


                        //Date and SimpleDateFormat implemented to take and prepare the current date to be recorded
                        Date today = new Date();
                        mood_date = new SimpleDateFormat("ddMMyy");//the current date


                        mood_recorder.record_ManyData(mood_name,mood_setence,mood_Color,mood_date.format(today));
                        /* Xml file way
                        SharedPreferences mood_file = getSharedPreferences("mood_data_file", MODE_PRIVATE);
                        SharedPreferences.Editor mood_Editor = mood_file.edit();
                        mood_Editor.putStringSet("many_values",mood_recorder.mood_list_data()).apply();
                        */

                        //Json way
                        SharedPreferences  mood_gson = getSharedPreferences("mood_data_file_gson", MODE_PRIVATE);
                        SharedPreferences.Editor mood_gson_Editor = mood_gson.edit();
                        Gson gson_manager = new Gson();
                        String mood_data_gson = gson_manager.toJson(mood_recorder.mood_list_data_gson_string());
                        mood_gson_Editor.putString("mood_data_gson", mood_data_gson).apply();


                        //Message of confirmation
                        Toast.makeText(getApplicationContext(), "Donnée enregistré "+ mood_date.format(today)+" "+mood_Color+" "+mood_name, Toast.LENGTH_SHORT).show();
                    }
                });
                alertMood.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Message of cancellation
                        Toast.makeText(getApplicationContext(), "Donnée non enregistré", Toast.LENGTH_SHORT).show();
                    }
                });
                alertMood.show();
            }
        });
    }
}
