package com.example.daniel.gry;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.TimerTask;

public class Kolory extends AppCompatActivity {

    private Button start;
    private Button bRed;
    private Button bGreen;
    private Button bBlue;
    private TextView slowo;
    private TextView pytanie;
    private Random losowanie;
    private int kolor;
    private int wyraz;
    private int zadanie;
    private int temp;
    private int wynik = 0;

    private final int red = 0;
    private final int green = 1;
    private final int blue = 2;

    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kolory);
        slowo = (TextView)findViewById(R.id.slowo);
        pytanie = (TextView)findViewById(R.id.pytanie);
        losowanie = new Random();
        start = (Button)findViewById(R.id.koloryStart);
        bRed = (Button)findViewById(R.id.czerwony);
        bBlue = (Button)findViewById(R.id.niebieski);
        bGreen = (Button)findViewById(R.id.zielony);
        disableColorButtons();
        db = new DBAdapter(getApplicationContext() );
        db.open();
    }

    public void kolorStart(View view) throws InterruptedException {
        disableColorButtons();
        start.setVisibility(View.INVISIBLE);
        wyczyscPytanie();
        ustawSlowo();

        new CountDownTimer(500,500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                wyczyscSlowo();
                ustawPytanie();
                enableColorButtons();
            }
        }.start();

    }

    private void ustawSlowo()
    {
        kolor = losowanie.nextInt(3);
        while ( kolor == temp )
            kolor = losowanie.nextInt(3);
        temp = kolor;
        wyraz = losowanie.nextInt(3);
        while ( wyraz == kolor)
            wyraz = losowanie.nextInt(3);

        switch (wyraz)
        {
            case red:
                slowo.setText("Czerwony");
                break;
            case green:
                slowo.setText("Zielony");
                break;
            case blue:
                slowo.setText("Niebieski");
                break;
        }
        switch (kolor)
        {
            case red:
                slowo.setTextColor(Color.RED);
                break;
            case green:
                slowo.setTextColor(Color.GREEN);
                break;
            case blue:
                slowo.setTextColor(Color.BLUE);
                break;
        }
    }

    private void ustawPytanie()
    {
        zadanie = losowanie.nextInt(2);
        if ( zadanie == 0 )
            pytanie.setText(getString(R.string.kolorSlowa));
        else
            pytanie.setText(getString(R.string.kolorZnaczenie));
    }

    private void wyczyscPytanie()
    {
        pytanie.setText("");
    }

    private void wyczyscSlowo()
    {
        slowo.setText("");
        slowo.setTextColor(Color.WHITE);
    }

    public void checkRed(View view) throws InterruptedException
    {
        check(view,red);
    }

    public void checkGreen(View view) throws InterruptedException
    {
        check(view,green);
    }

    public void checkBlue(View view) throws InterruptedException
    {
        check(view,blue);
    }

    private void disableColorButtons()
    {
        bRed.setEnabled(false);
        bGreen.setEnabled(false);
        bBlue.setEnabled(false);
    }

    private void enableColorButtons()
    {
        bRed.setEnabled(true);
        bGreen.setEnabled(true);
        bBlue.setEnabled(true);
    }

    private void check(View view, int colorNumber) throws InterruptedException {
        if(zadanie == 0)
        {
            if(kolor == colorNumber) {
                wynik++;
                kolorStart(view);
            }
            else {
                disableColorButtons();
                pytanie.setText("");
                start.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Twoj wynik to " + wynik, Toast.LENGTH_SHORT).show();
                db.insertWynik( getResources().getString(R.string.graKolory) , wynik);
                wynik = 0;
            }
        }
        else {
            if (wyraz == colorNumber) {
                wynik++;
                kolorStart(view);
            }
            else {
                disableColorButtons();
                pytanie.setText("");
                start.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Twoj wynik to " + wynik, Toast.LENGTH_SHORT).show();
                db.insertWynik( getResources().getString(R.string.graKolory) , wynik);
                wynik = 0;

            }
        }
    }

    protected void onDestroy() {
        if ( db != null)
            db.close();
        super.onDestroy();
    }
}
