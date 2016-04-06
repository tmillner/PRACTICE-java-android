package com.example.macbookpro.myapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;

public class CreaturesActivity extends Activity implements
        CreatureNamesFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatures);
    }

    @Override
    public void onItemSelected(String id) {
        Bundle arguments = new Bundle();
        arguments.putString("name", id);
        if (id.equals("Minotaur")) {
            // The imgs were added to the drawable dir via cmd+c & p (NOT d&d)
            arguments.putInt("imageId", R.drawable.minotaur);
        }
        else if (id.equals("Hydra")) {
            arguments.putInt("imageId", R.drawable.hydra);
        }
        else if (id.equals("Phoenix")) {
            arguments.putInt("imageId", R.drawable.phoenix);
        }
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(arguments);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /* Note: DetailsFragment should not be android.app.support.v4.fragment ! */
        fragmentTransaction.replace(R.id.creaturesDetailsFrame, detailsFragment);
        fragmentTransaction.commit();
    }
}
