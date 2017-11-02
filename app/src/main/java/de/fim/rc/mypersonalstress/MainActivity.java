package de.fim.rc.mypersonalstress;

import android.content.Intent;
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
                myDB.addNewCoefficients(aktuellezeit, 0.2, 0.4);
            }
        });

    }
}
