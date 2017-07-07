package com.example.daniel.gry;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class Wyniki extends AppCompatActivity {

    private TextView rekordy;
    private DBAdapter db;
    private Button wyswietlacz;
    private Button kasowacz;
    private Button powrot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyniki);
        rekordy = (TextView)findViewById(R.id.rekordy);
        wyswietlacz = (Button)findViewById(R.id.pokaz);
        kasowacz = (Button)findViewById(R.id.usun);
        powrot = (Button)findViewById(R.id.powrot);
        db = new DBAdapter(getApplicationContext());
        db.open();
    }

    public void wyswietl(View v)
    {
        Cursor k = db.dajWszystko();
        if ( k.getCount() != 0) {
            rekordy.setVisibility(View.VISIBLE);
            powrot.setVisibility(View.VISIBLE);
            kasowacz.setVisibility(View.GONE);
            wyswietlacz.setVisibility(View.GONE);
            k.moveToLast();
            long id = k.getLong(0);
            String nazwa = k.getString(1);
            int wynik = k.getInt(2);
            rekordy.setText(rekordy.getText() +  " " + nazwa + ": " + wynik + "\n");

            while (k.moveToPrevious()) {
                id = k.getLong(0);
                nazwa = k.getString(1);
                wynik = k.getInt(2);
                rekordy.setText(rekordy.getText() + " " + nazwa + ": " + wynik + "\n");
            }
        }
        else
            Toast.makeText(this, "Brak rekordów", Toast.LENGTH_SHORT).show();
    }

    public void usun(View v)
    {
        db.usunWszystko();
        Toast.makeText(this, "Usunięto rekordy", Toast.LENGTH_SHORT).show();
    }

    public void powrot(View v)
    {
        rekordy.setText("");
        rekordy.setVisibility(View.GONE);
        powrot.setVisibility(View.GONE);
        kasowacz.setVisibility(View.VISIBLE);
        wyswietlacz.setVisibility(View.VISIBLE);
    }


    protected void onDestroy() {
        if ( db != null)
            db.close();
        super.onDestroy();
    }

}
