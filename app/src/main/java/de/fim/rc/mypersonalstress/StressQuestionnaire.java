package de.fim.rc.mypersonalstress;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class StressQuestionnaire extends AppCompatActivity {

    public int currentPSSQuestionNumber;
    //public int currentPSSQuestionScore;
    public int currentPSSScore;
    public int[] scoresPSSQuestions;
    public String questionBeginningText;
    public String[] questionEndTexts;
    public Button button1, button2, button3, button4, button5, button6;
    public TextView textView1, textView2, textView3, textView4, textView5, textView6;
    public long aktuellezeit;
    private DatabaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_questionnaire);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        currentPSSScore = 0;
        currentPSSQuestionNumber = 0;
        questionBeginningText = getString(R.string.questionBeginning);
        scoresPSSQuestions = new int[4];
        Resources res = getResources();
        questionEndTexts = res.getStringArray(R.array.questionEndArray);

        textView4.setText(questionBeginningText+""+questionEndTexts[currentPSSQuestionNumber]);

        myDB = new DatabaseHelper(this, "mypersonalstress.db");

        textView5.setVisibility(View.INVISIBLE);
        textView6.setVisibility(View.INVISIBLE);
        button6.setVisibility(View.INVISIBLE);



        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scoresPSSQuestions[currentPSSQuestionNumber] = 4;
                nextQuestion();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scoresPSSQuestions[currentPSSQuestionNumber] = 3;
                nextQuestion();

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scoresPSSQuestions[currentPSSQuestionNumber] = 2;
                nextQuestion();

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scoresPSSQuestions[currentPSSQuestionNumber] = 1;
                nextQuestion();

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scoresPSSQuestions[currentPSSQuestionNumber] = 0;
                nextQuestion();

            }
        });

    }


    public void nextQuestion() {
        System.out.println("Aktuelle Fragenr.:"+currentPSSQuestionNumber);
        if (currentPSSQuestionNumber <3) {
            System.out.println("Erhöhe Fragenr.");
            currentPSSQuestionNumber += 1;
            System.out.println("Aktuelle Fragenr.:"+currentPSSQuestionNumber);
            textView3.setText("Frage "+(currentPSSQuestionNumber+1)+" von 4:");
            textView4.setText(questionBeginningText+""+questionEndTexts[currentPSSQuestionNumber]);
            textView5.setText("Akt. Fr.: "+(currentPSSQuestionNumber+1));
            textView6.setText("Akt. PSS: "+getCurrentPSSScore());
        }
        else if (currentPSSQuestionNumber == 3) {
            Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), "PSS-Score: "+getCurrentPSSScore(), Snackbar.LENGTH_SHORT);
            mySnackbar.show();
            myDB.createPSSScoreTable();
            DateFormat df = DateFormat.getDateTimeInstance();
            aktuellezeit = new Date().getTime();
            myDB.addNewPSSScore(aktuellezeit, getCurrentPSSScore());

            //Intent myIntent = new Intent(StressQuestionnaire.this, MainActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            //StressQuestionnaire.this.startActivity(myIntent);
        }
    };

    public int getCurrentPSSScore() {
        int buff = 0;
        for (int i=0; i<=3; i++) {
            buff += scoresPSSQuestions[i];
        }
        return buff;
    }

}

