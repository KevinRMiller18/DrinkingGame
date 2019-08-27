package com.example.pointingtrial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
//import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends Activity {

    public CheckBox L1;
    public CheckBox L2;
    public CheckBox L3;

    public List<Integer> whichArrays = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_collection_activity);

        L1 = findViewById(R.id.ca_L1);
        L2 = findViewById(R.id.ca_L2);
        L3 = findViewById(R.id.ca_L3);


        //get the correct boxes to check
        if(savedInstanceState != null)
            whichArrays = savedInstanceState.getIntegerArrayList("whichArrays");
        else
            whichArrays = getIntent().getIntegerArrayListExtra("QArrays");

        //Check the boxes that are already chosen
        assert whichArrays != null;
        if(whichArrays.contains(1))
        {
            L1.setChecked(true);
        }
        if(whichArrays.contains(2))
        {
            L2.setChecked(true);
        }
        if(whichArrays.contains(3))
        {
            L3.setChecked(true);
        }
    }

    @Override
    public void onBackPressed()
    {
        onSendResult();
    }


    public void onSendResult()
    {
        //This function is send back to the start menu which collections should be used in the game
        addChecks();

        Intent intent = new Intent();
        intent.putIntegerArrayListExtra("QArrays", (ArrayList<Integer>) whichArrays);
        setResult(1,intent);
        finish();
    }

    public void addChecks()
    {
        //Add the checked boxes into the array

        whichArrays.clear();
        if(L1.isChecked())
        {
            whichArrays.add(1);
        }
        if(L2.isChecked())
        {
            whichArrays.add(2);
        }

        if(L3.isChecked())
        {
            whichArrays.add(3);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        addChecks();
        //Save the array, mainly just for rotation purposes
        savedInstanceState.putIntegerArrayList("whichArrays", (ArrayList<Integer>) whichArrays);
    }

    public void onSaveChecks(View view)
    {
        //save to shared preferences, which can then be accessed by the start screen
        final SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < whichArrays.size(); i++) {
            str.append(whichArrays.get(i)).append(",");
        }

        prefEditor.putString("whichArrayString", str.toString());
        prefEditor.apply();
    }
}
