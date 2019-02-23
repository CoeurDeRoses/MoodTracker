package com.cdrcoeurderoses.moodtracker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import static com.cdrcoeurderoses.moodtracker.R.layout.activity_dialog_comment;

public class DialogComment extends Dialog {

    // I create the variable needed to interact with the user
    private Button validate, cancel;
    private  EditText AddComment;


    /**
     * i import Activity  to use activity object as a parameter
     *  cause i will use the activity which contains the needed component for custom dialog
     *   i call the super constructor cause i will need to use the context and the the second parameter which
     *    will be the kind of dialog style used in R.style.Theme_Appcomat_DayNight_Dialog resource
     * @param currentActivity
     */
    public DialogComment(Activity currentActivity){
        super(currentActivity,R.style.Theme_AppCompat_DayNight_Dialog);
        // with setContentView method i need to put the relative layout of dialog box
        setContentView(activity_dialog_comment);
        this.validate = findViewById(R.id.btValidate);
        this.cancel = findViewById(R.id.btCancel);
        this.AddComment = findViewById(R.id.editTextComment);


    }

    /**
     * I create these methods cause now, setPositiveButton and setNegativeButton methods are useless to use customized
     * @return
     */
    public Button getBtValidate()
    {
        return this.validate;
    }

    public Button getBtCancel()
    {
        return this.cancel;
    }

    /**
     * To record the posssible comment of the user
     * @return
     */
    public EditText getEtComment()
    {
       return  this.AddComment;
    }

}
