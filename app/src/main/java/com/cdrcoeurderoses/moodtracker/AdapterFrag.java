package com.cdrcoeurderoses.moodtracker;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdapterFrag extends FragmentPagerAdapter {

    private int[] colors;


    public AdapterFrag  (FragmentManager mgr, int[] colors)
    {
        super(mgr);
        this.colors=colors;

    }

    /**
     * This method return the number of elements
     * @return
     */
    @Override
    public int getCount() {
        return 5;
    }

    /**
     * Return the fragment position with the right color
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return(MainFragment.newInstance(position, this.colors[position]));
    }
}
