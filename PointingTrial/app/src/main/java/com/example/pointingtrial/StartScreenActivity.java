package com.example.pointingtrial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
//import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StartScreenActivity extends Activity {

    public List<Integer> whichArrays = new ArrayList<>();
    public int numOfArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        final  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(savedInstanceState != null)
            whichArrays = savedInstanceState.getIntegerArrayList("whichArrays");
        else {
            //The number of collections within the app
            numOfArrays = 3;

            //See if any saved preferences for the checked collections
            String tempString = sharedPreferences.getString("whichArrayString", "");
            if(!tempString.isEmpty())
            {
                StringTokenizer st = new StringTokenizer(tempString, ",");
                for (int i = 1; i <= numOfArrays; i++) {
                    whichArrays.add(Integer.parseInt(st.nextToken()));
                }
            }

            //If no saved preference, then all checked
            else {
                for (int i = 1; i <= numOfArrays; i++) {
                    whichArrays.add(i);
                }
            }
        }

    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, ShowQuestionActivity.class);
        intent.putIntegerArrayListExtra("QArrays", (ArrayList<Integer>) whichArrays);
        startActivity(intent);
    }

    public void onCollections(View view) {
        Intent intent = new Intent(this, CollectionActivity.class);
        intent.putIntegerArrayListExtra("QArrays", (ArrayList<Integer>) whichArrays);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 1
        if(requestCode==1)
        {
            whichArrays = data.getIntegerArrayListExtra("QArrays");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        //Save the array, mainly just for rotation purposes
        savedInstanceState.putIntegerArrayList("whichArrays", (ArrayList<Integer>) whichArrays);
    }
}
