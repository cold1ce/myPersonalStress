package de.fim.rc.mypersonalstress;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button1 = findViewById(R.id.button1);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);
        final Button button4 = findViewById(R.id.button4);
        final Button button5 = findViewById(R.id.button5);
        final Button button6 = findViewById(R.id.button6);
        myDB = new DatabaseHelper(this, "mypersonalstress.db");


        //Intent myIntent = new Intent(MainActivity.this, StressQuestionnaire.class);
        //myIntent.putExtra("key", value); //Optional parameters
        //MainActivity.this.startActivity(myIntent);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, StressQuestionnaire.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent2 = new Intent(MainActivity.this, AndroidDatabaseManager.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDB.createCoefficientsTable();
                DateFormat df = DateFormat.getDateTimeInstance();
                long aktuellezeit = new Date().getTime();
                myDB.addNewCoefficients(aktuellezeit,  myDB.getAggrMin(),  getZufallszahl(-1,1));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDB.createTestSensorTable();
                DateFormat df = DateFormat.getDateTimeInstance();
                long aktuellezeit = new Date().getTime();
                for (int i=0; i<10; i++) {
                    myDB.addNewTestSensorValues(aktuellezeit, getZufallszahl(0, 100));
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double min = 0.0;
                min = myDB.getAggrMin();
                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), "Minimum: "+min, Snackbar.LENGTH_SHORT);
                mySnackbar.show();

            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double x = 3.0;
                double ualt = 4.5;
                double oalt = 0.5;
                int N = 2;
                /*double uneu = myDB.updateMeans(x, ualt, N);
                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), "uneu: "+uneu+" o: "+myDB.updateStandardDeviation(x, uneu, N, oalt), Snackbar.LENGTH_SHORT);
                mySnackbar.show();*/
                double zval = myDB.standardizise(x, ualt, oalt, N);
                Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), "z-Value: "+zval, Snackbar.LENGTH_LONG);
                mySnackbar.show();



            }
        });

    }

    public double getZufallszahl(int min, int max) {
        double  random = Math.random() * (max - min) + min;
        return random;
    }
}
