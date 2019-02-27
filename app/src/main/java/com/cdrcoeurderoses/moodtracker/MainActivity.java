package com.cdrcoeurderoses.moodtracker;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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


public class MainActivity extends AppCompatActivity {

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
        // The button to go in percentage history mood
        Button goPiechart = findViewById(R.id.piechart_button);

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

        goPiechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PiechartIntent = new Intent(MainActivity.this,PiechartHistory.class);
                startActivity(PiechartIntent);
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
        //i put all in a string
        String mood_data_json = gson.fromJson(gson_file_read, String.class);
        // i make the string workable to manage data with method of Mood
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


        for(int i=1; i<8;i++) {
            if (i==1 && !gson_file_write.contains(String.valueOf(i))) {
                mood_gson_Editor.putString(String.valueOf(i), mood_data_gson).apply();
            }
            if (i > 1 && !gson_file_write.contains(String.valueOf(i))) {
                mood_gson_Editor.putString(String.valueOf(i), first_start_set).apply();
            }
        }
        //here is the code behavior to handle data about change date if we change the day
        // if we change the day the day i must switch the data contained to key 1 to the 2 and the
        // data of key 2 to the 3 and the same way for the rest
        if(!string_mood_date.contentEquals(most_recent_date_recorded()))
        {
            Toast.makeText(getApplicationContext(), "Comparaison date --- " + most_recent_date_recorded()+" AND "+string_mood_date, Toast.LENGTH_LONG).show();
            // we must always start the process from the last-1 to the recent just before its data
            // and we repeat the same until the second i also code the deserealisation process first and
            // the serealisation process after
            //key 1 is always set by the user so i don't need to to update that key
            //so tab size is six

            String[] array_of_mood;
            String current_iteration_data;//to recort the current string mood of iteration
            for(int i=7; i>1;i--)
            {
                // I take Take data from the inferior number key  and put them in the superior number key

                String gson_read_from_inferior_key = getSharedPreferences("mood_file",MODE_PRIVATE).getString(String.valueOf(i-1), "");

                String mood_data_gson_superior_key = gson_manager.fromJson(gson_read_from_inferior_key, String.class);
                //Prepare the data for record
                array_of_mood = mood_manager.mood_ready_read(mood_data_gson_superior_key);
                //next step
                mood_manager.record_ManyData(array_of_mood[1],array_of_mood[3],array_of_mood[5],array_of_mood[7]);
                //mood_manager.record_ManyData(many_gson_array_7[1],many_gson_array_7[5],many_gson_array_7[7]);
                // next
                current_iteration_data = gson_manager.toJson(mood_manager.mood_list_data_gson_string());
                //and record
                mood_gson_Editor.putString(String.valueOf(i),current_iteration_data).apply();
            }

        }
        //always put the record instruction after the record of other keys the old data of key one will be erased
        //and the key 2 will not take it but gonna take the new data as key
        mood_gson_Editor.putString("1", mood_data_gson).apply();
        mood_sentence="";
    }

}
