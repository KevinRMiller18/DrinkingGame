package com.example.pointingtrial;

import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShowQuestionActivity extends Activity {

    public List<String>  theQuestions = new ArrayList<String>();
    public List<Integer> usedNum = new ArrayList<>();
    public boolean isNew;
    public String curQuestion;
    public int curNum;
    public TextView theCD_T;
    public TextView theQ_T;
    public Button theN_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_show_question);

        theCD_T = (TextView)findViewById(R.id.countdown_text);
        theQ_T = (TextView)findViewById(R.id.question);
        theN_B = (Button)findViewById(R.id.sq_button);

        if(savedInstanceState == null) {
            isNew = false;
            List<Integer> whichArrays = getIntent().getIntegerArrayListExtra("QArrays");
            assert whichArrays != null;
            if (whichArrays.contains(1)) {
                theQuestions.addAll(Arrays.asList(getApplicationContext().getResources().getStringArray(R.array.nice_questions)));
            }
            if (whichArrays.contains(2)) {
                theQuestions.addAll(Arrays.asList(getApplicationContext().getResources().getStringArray(R.array.mean_questions)));
            }
            if (whichArrays.contains(3)) {
                theQuestions.addAll(Arrays.asList(getApplicationContext().getResources().getStringArray(R.array.shitstir_questions)));
            }
        }
        else
        {
            usedNum = savedInstanceState.getIntegerArrayList("usedNum");
            isNew = savedInstanceState.getBoolean("isNew");
            theQuestions = savedInstanceState.getStringArrayList("theQuestions");
            curNum = savedInstanceState.getInt("curNum");
            curQuestion = savedInstanceState.getString("curQuestion");

            theCD_T.setText(savedInstanceState.getString("theCD_T"));
            theN_B.setText(savedInstanceState.getString("theN_B"));
            theQ_T.setText(savedInstanceState.getString("theQ_T"));
        }
    }

    public void onClickQuestion(View view) {
        if(!isNew) {
            curNum = new Random().nextInt(theQuestions.size());
            curQuestion = "";
            if (usedNum.contains(curNum) && usedNum.size() != theQuestions.size()) {
                while (usedNum.contains(curNum)) {
                    curNum = new Random().nextInt(theQuestions.size());
                }
                curQuestion = theQuestions.get(curNum);
                usedNum.add(curNum);
            } else if (usedNum.size() >= theQuestions.size()) {
                //placeholder for case
                curQuestion = "There is no more questions, sorry";
            } else {
                curQuestion = theQuestions.get(curNum);
                usedNum.add(curNum);
            }
            theQ_T.setText(curQuestion);
            theN_B.setText(getString(R.string.cd_ready));
            theCD_T.setText("");
            isNew = true;
        }

        else {
            theN_B.setEnabled(false);
            CountDownTimer pointTimer = new CountDownTimer(4000, 1000) {
                public void onTick(long msToFinish) {
                    int progress = (int) msToFinish / 1000;
                    theCD_T.setText(Integer.toString(progress));
                }

                public void onFinish() {
                    theCD_T.setText(getString(R.string.cd_point));
                    isNew = false;
                    theN_B.setEnabled(true);
                    theN_B.setText(getString(R.string.game_next_question));
                }
            };
            pointTimer.start();
            isNew = false;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putIntegerArrayList("usedNum", (ArrayList<Integer>) usedNum);
        savedInstanceState.putStringArrayList("theQuestions", (ArrayList<String>) theQuestions);
        savedInstanceState.putBoolean("isNew", isNew);
        savedInstanceState.putString("curQuestion", curQuestion);
        savedInstanceState.putInt("curNum", curNum);

        savedInstanceState.putString("theCD_T", (String) theCD_T.getText());
        savedInstanceState.putString("theQ_T", (String) theQ_T.getText());
        savedInstanceState.putString("theN_B", (String) theN_B.getText());
    }
}



