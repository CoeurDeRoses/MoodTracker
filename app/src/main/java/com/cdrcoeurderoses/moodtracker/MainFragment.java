package com.cdrcoeurderoses.moodtracker;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    // 1 - Create keys for our Bundle
    private static final String KEY_POSITION="position";
    private static final String KEY_COLOR="color";


    public MainFragment() {
        // Required empty public constructor

    }

    public static MainFragment newInstance(int position, int color)
    {
            MainFragment fragment = new MainFragment();

        // 2.2 Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);
        fragment.setArguments(args);



        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //here i put the view setting relative to the right mood
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 3 - Get layout of PageFragment
        View result = inflater.inflate(R.layout.fragment_main, container, false);

        // 4 - Get widgets from layout and serialise it
        RelativeLayout rootView=  result.findViewById(R.id.layout_fragment);
        ImageView imageView = result.findViewById(R.id.fragMood);

        // 5 - Get data from Bundle (created in method newInstance)
        int color = getArguments().getInt(KEY_COLOR, -1);
        int position = getArguments().getInt(KEY_POSITION,-1);
        Drawable drawable;

        switch (position)
        {
            case 0: drawable = ContextCompat.getDrawable(getContext(),R.drawable.smiley_super_happy); break;
            case 1: drawable = ContextCompat.getDrawable(getContext(),R.drawable.smiley_happy); break;
            case 2: drawable =  ContextCompat.getDrawable(getContext(),R.drawable.smiley_normal); break;
            case 3: drawable =  ContextCompat.getDrawable(getContext(),R.drawable.smiley_disappointed); break;
            case 4: drawable =  ContextCompat.getDrawable(getContext(),R.drawable.smiley_sad); break;
            default: drawable = ContextCompat.getDrawable(getContext(),R.drawable.smiley_super_happy); break;
        }

        // 6 - Update widgets with it
        rootView.setBackgroundColor(color);
        imageView.setBackground(drawable);

        Log.e(getClass().getSimpleName(), "fragment number "+position);


        return result;
    }

}
