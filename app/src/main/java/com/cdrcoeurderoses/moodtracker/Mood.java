package com.cdrcoeurderoses.moodtracker;


public class Mood {
    //this class handle all the property and method about the mood

    private String moodName;
    private String moodSentence;
    // i use the hexadecimal value to choose the right color and i will convert the value with the parse method
    // later in MHistoryMood class cause the setBackground method of a component accept only int value so the get method of color
    // will return an int value
    private String moodColor;
    private String moodDate;



    public Mood(){

    }


    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }


    public void setMoodSentence(String moodSentence) {
        this.moodSentence = moodSentence;
    }


    public void setMoodColor(String moodColor) {
        this.moodColor = moodColor;
    }

    public void setMoodDate(String moodDate) {
        this.moodDate = moodDate;
    }

    /**
     * here the method which record many data at same time by calling other method
     * @param moodName
     * @param moodSentence
     * @param moodColor
     * @param moodDate
     */
    public void recordManyData(String moodName, String moodSentence, String moodColor, String moodDate )
    {
        setMoodName(moodName);
        setMoodSentence(moodSentence);
        setMoodColor(moodColor);
        setMoodDate(moodDate);
    }

    /**
     * Method to prepare the strings for JSON format way
     * when i extract data
     * @return
     */
    public String moodListDataGsonString()
    {
        String moodList = "{";

        moodList += "\"moodName\":\""+ moodName +"\",";
        moodList += "\"moodSentence\":\""+ moodSentence +"\",";
        moodList += "\"moodColor\":\""+ moodColor +"\",";
        moodList += "\"moodDate\":\""+ moodDate +"\"}";

        return moodList;
    }

    /**
     * This method format the string to make him able to be managed and for handle data
     * to show them well in the mood history i delete many kind of char useless to show
     * @param theString
     * @return
     */
    public String[] moodReadyRead(String theString){

        //Curly bracket deleted
        theString = theString.replaceAll("\\{|\\}","");
        //quote
        theString = theString.replaceAll("\"","");
        // i replace all the comma by ":" to make this character the split parameter to create an array
        theString = theString.replaceAll(",",":");
        //I split all the data and put them in an array
        String[] theArrayString = theString.split(":");
        // Data are recorded in the array in this way
        // array[0] moodName array[1] moodName value. the exact same order of the data send by user after user select mood data
        // and recorded in JSON file

        return theArrayString;
    }




}
