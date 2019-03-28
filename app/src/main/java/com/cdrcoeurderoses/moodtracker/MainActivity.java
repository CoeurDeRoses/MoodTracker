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


    final Mood moodManager = new Mood();

    private MediaPlayer playMusic = null;

    //To record the user choice i make a string variable which have to be put in each case
    // to receive the relative color mood_color and other userfull variable
    private String moodName;
    private String moodSentence;

    private String moodColor;
    private SimpleDateFormat moodDate;

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
        int[] arrayColors={Color.parseColor("#EAE108"),
                Color.parseColor("#65D164"),
                        Color.parseColor("#2663EE"),
                                Color.parseColor("#6B6C6F"),
                                        Color.parseColor("#D12A2B")};
        // 1 - Get ViewPager from layout
        pager =  findViewById(R.id.activity_main_viewpager);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new AdapterFrag(getSupportFragmentManager(),arrayColors) {
        });
    }




    /**
     * this method is used to handle the case that if the user didnt opened the app since
     * 2 days or more, we must update the data file and switch the mood as much as days gone
     * @return
     */
    public int updateAsMuchAsNeeded()
    {
        Date today = new Date();
        moodDate = new SimpleDateFormat("yyyyMMdd");//the current date
        String stringMoodDate= moodDate.format(today);


        //Json way
        Gson gsonManager = new Gson();
        //for the file one
        String lastDateFile = getSharedPreferences("mood_file",MODE_PRIVATE).getString("0", "");

        lastDateFile = gsonManager.fromJson(lastDateFile, String.class);
        //Prepare the data for record
        String[] arrayOfMood = moodManager.moodReadyRead(lastDateFile);

        //i convert the both date, current date and last date recorded from the file
        int currentDate = Integer.parseInt(stringMoodDate), intLastDateFile = Integer.parseInt(arrayOfMood[7]);
        //lets make a subtraction, the difference of the calcul mean how many time we have to update the file and switch data
        // in launch_mood_data()

        int diffDate = currentDate - intLastDateFile;
        //the loop which determinate how many time

        return diffDate;
    }

    /**
     * This method update the mood data when it needed
     */
    public void launcherMoodData()
    {
        //and here i create the variable to manage date input in the file by using method of a
        //SharedPreferences object, first of all i put the name of the xml file
        //and put MOVE_PRIVATE to make sure the data can be accessed only by MoodTracker application
        // and not outside.
        //The Editor object will be used to write data in the file
        //first parameter is the key and second is the value

        //Date and SimpleDateFormat implemented to take and prepare the current date to be recorded
        Date today = new Date();
        moodDate = new SimpleDateFormat("yyyyMMdd");//the current date
        String stringMoodDate= moodDate.format(today);


        moodManager.recordManyData(moodName, moodSentence, moodColor,stringMoodDate);


        //Json way
        Gson gsonManager = new Gson();
        //for the file one
        SharedPreferences gsonFileWrite = getSharedPreferences("mood_file", MODE_PRIVATE);
        SharedPreferences.Editor moodGsonEditor = gsonFileWrite.edit();
        String moodDataGson = gsonManager.toJson(moodManager.moodListDataGsonString());


        // i test if the keys don't exist i don't initialise them
        // and with a set of defaut value except key 1
        String firstStartSet = gsonManager.toJson("{\"moodName\":\"first_launch_application\",\"" +
                "moodSentence\":\" \",\"mood_color\":\" \",\"moodDate\":\" \"}");


        //i need to create 8 keys even if it' about 7 days data
        //we must record the data of the current day to be ready to switch to the yesterday data
        //the choice selected in the current day must not be showed but we need to record that
        //current day will always be at the key 0
        for(int i=0; i<8;i++) {
            if (i==0 && !gsonFileWrite.contains(String.valueOf(i))) {
                moodGsonEditor.putString(String.valueOf(i), moodDataGson).apply();
            }
            if (i > 0 && !gsonFileWrite.contains(String.valueOf(i))) {
                moodGsonEditor.putString(String.valueOf(i), firstStartSet).apply();
            }
        }
        //here is the code behavior to handle data about change date if we change the day
         int numberDaysGone = updateAsMuchAsNeeded();
        if(numberDaysGone>0)
        {
            // we must always start the process from the last-1 to the recent just before its data
            // and we repeat the same until the second i also code the deserealisation process first and
            // the serealisation process after
            //key 1 is always set by the user so i don't need to to update that key
            //so tab size is six

            for(int j = 0; j< numberDaysGone; j++) {

//if            the loop has more than one turn i set the code for set default value for the day mood wasn't choose
                //and for each day past i add one unit to be sure to have the right day
                if(j>0)

                {
                    moodSentence ="";
                    int currentDate = j+Integer.parseInt(stringMoodDate);
                    moodManager.recordManyData("Bonne humeur", "", "#65D164", String.valueOf(currentDate));
                    String defaultMood = gsonManager.toJson(moodManager.moodListDataGsonString());
                    //and record
                    moodGsonEditor.putString("0", defaultMood).apply();

                }

                for (int a = 7; a > 0; a--) {

                    String[] arrayOfMood;
                    String currentIterationData;//to record the current string mood of iteration
                    // I take Take data from the inferior number key  and put them in the superior number key
                    String gsonReadFromInferiorKey = getSharedPreferences("mood_file", MODE_PRIVATE).getString(String.valueOf(a - 1), "");

                    String moodDataGsonSuperiorKey = gsonManager.fromJson(gsonReadFromInferiorKey, String.class);
                    //Prepare the data for record
                    arrayOfMood = moodManager.moodReadyRead(moodDataGsonSuperiorKey);
                    //next step

                    //i need to put condition which set defaut value for the days gone since the time the application
                    // didn't get opened by the user or just opened with set mood, when i = 1
                    //if the app didn't get opened since yesterday i set defaut value

                    moodManager.recordManyData(arrayOfMood[1], arrayOfMood[3], arrayOfMood[5], arrayOfMood[7]);

                    // next
                    currentIterationData = gsonManager.toJson(moodManager.moodListDataGsonString());
                    //and record
                    moodGsonEditor.putString(String.valueOf(a), currentIterationData).apply();
                }




            }



        }

        //always put the record instruction after the record of other keys the old data of key one will be erased
        //and the key 1 will not take it but gonna take the new data as key

        moodGsonEditor.putString("0", moodDataGson).apply();




    }

    public void getCurrentMood()
    {
        int currentMood = pager.getCurrentItem();

        switch (currentMood)
        {
            case 0:
                moodColor = "#EAE108";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_superhappy);playMusic.start();

                Toast.makeText(getApplicationContext(),"Très bonne humeur",Toast.LENGTH_SHORT).show();
                moodName ="Super bonne humeur"; break;

            case 1:
                moodColor = "#65D164";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_happy); playMusic.start();
                Toast.makeText(getApplicationContext(),"Bonne humeur",Toast.LENGTH_SHORT).show();
                moodName ="Bonne humeur"; break;

            case 2: moodColor = "#2663EE";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_normal); playMusic.start();
                Toast.makeText(getApplicationContext(),"Humeur normale",Toast.LENGTH_SHORT).show();
                moodName ="Humeur normale"; break;

            case 3:  moodColor = "#6B6C6F";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_disappointed); playMusic.start();
                Toast.makeText(getApplicationContext(),"Mauvaise humeur",Toast.LENGTH_SHORT).show();
                moodName ="Mauvaise humeur";break;

            case 4:
                moodColor = "#D12A2B";playMusic = MediaPlayer.create(getApplicationContext(),R.raw.sound_sad); playMusic.start();
                Toast.makeText(getApplicationContext(),"Très mauvaise  humeur",Toast.LENGTH_SHORT).show();
                moodName ="Très mauvaise humeur"; break;

            default: break;
        }
        launcherMoodData();
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

                getCurrentMood();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        launcherMoodData();

        //If the user don't scroll
        if(moodName == null)
        {
            moodColor ="#65D164"; moodName ="Bonne humeur";
            //and we update the key 0
            launcherMoodData();
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


                        moodSentence = dialogAddComment.getEtComment().getText().toString();
                        if(moodSentence ==null)
                        {
                            moodSentence ="";
                        }
                        //else, If user wrote a comment i record this
                        else
                        {
                            launcherMoodData();
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
                final AlertDialog.Builder shareDialog = new AlertDialog.Builder(currentActivity);
                shareDialog.setTitle("Partager votre humeur");
                shareDialog.setMessage("Voulez vous partagez vos informations d'humeur avec vos connaissances ?");
                shareDialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Code about share operation
                        Toast.makeText(getApplicationContext(),"Ouverture des applications suggérés pour l'envoie",Toast.LENGTH_SHORT).show();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);

                        String[] arrayMessage = moodManager.moodReadyRead(moodManager.moodListDataGsonString());
                        String messageSend = "Salut ! "+arrayMessage[1]+" aujord'hui. "+arrayMessage[3];
                        sendIntent.putExtra(Intent.EXTRA_TEXT, messageSend);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);

                    }
                }).setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                shareDialog.show();
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
        SharedPreferences gsonFile = getSharedPreferences("mood_file", MODE_PRIVATE);

        //If key 0 don't exist and it's first launch of app
        if(!gsonFile.contains("0"))
        {
            pager.setCurrentItem(1); getCurrentMood();
        }
        //if key 0 exist i check about put default value or other values

        else {

            //if we change a day i set the defaut mood
            int day_change = updateAsMuchAsNeeded();
            if(day_change>0)
            {
                pager.setCurrentItem(1);
                getCurrentMood();
            }

            //if day_change=0 so if we didn't changed the day
            else{
                Gson gsonManager = new Gson();
                String gsonReadFrom0 = getSharedPreferences("mood_file", MODE_PRIVATE).getString("0", "");
                String moodDataGsonSuperiorKey = gsonManager.fromJson(gsonReadFrom0, String.class);
                String[] arrayMoodToShow = moodManager.moodReadyRead(moodDataGsonSuperiorKey);
                switch (arrayMoodToShow[5]) {
                    case "#EAE108":
                        pager.setCurrentItem(0); getCurrentMood();
                        break;
                    case "#65D164":
                        pager.setCurrentItem(1);
                        getCurrentMood();
                        break;
                    case "#2663EE":
                        pager.setCurrentItem(2);
                        getCurrentMood();
                        break;
                    case "#6B6C6F":
                        pager.setCurrentItem(3);
                        getCurrentMood();
                        break;
                    case "#D12A2B":
                        pager.setCurrentItem(4);
                        getCurrentMood();
                        break;
                    default:
                        pager.setCurrentItem(1);
                        getCurrentMood();
                        break;
                }

            }
        }
    }


}
