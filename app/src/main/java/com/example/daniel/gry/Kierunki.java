package com.example.daniel.gry;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Kierunki extends AppCompatActivity implements SensorEventListener{

    private TextView kierunek;
    private Button start;
    private SensorManager sensorManager;
    private Sensor orientacja;
    private ImageView kula;
    private Random losowa;
    private CountDownTimer licznikPionowy;
    private CountDownTimer licznikPoziomy;
    private float wiatrGoraDol;
    private float wiatrPrawoLewo;
    private int czasGoraDol;
    private int czasPrawoLewo;
    private int znakGoraDol;
    private int znakPrawoLewo;
    private float SensorY;
    private float SensorX;
    private int punkty;
    private long timerStart;
    private long timerEnd;
    private float granicaLewa;
    private float granicaPrawa;
    private float granicaGorna;
    private float granicaDolna;
    private Boolean lose = false;
    private Boolean sprawdzone;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kierunki);
        kierunek = (TextView)findViewById(R.id.kierunek);
        kula = (ImageView)findViewById(R.id.kula);
        start = (Button)findViewById(R.id.kierunkiStart);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        orientacja = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this,orientacja,SensorManager.SENSOR_DELAY_FASTEST);
        losowa = new Random();
        kula.setVisibility(View.GONE);
        granicaGorna = 0;
        granicaLewa = 0;
        granicaPrawa = 410;
        granicaDolna = 586;
        db = new DBAdapter(getApplicationContext() );
        db.open();
   }

    private void setWiatrGoraDol()
    {
        wiatrGoraDol = 0;
        znakGoraDol = losowa.nextInt(2);
        czasGoraDol = 100 * (losowa.nextInt(30)+1);
        licznikPionowy = new CountDownTimer(czasGoraDol,20)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                if ( czasGoraDol / 2 < millisUntilFinished)
                {
                    wiatrGoraDol += 0.1;
                    if ( znakGoraDol == 0)
                        kula.setY(kula.getY() + wiatrGoraDol - SensorY);
                    else
                        kula.setY(kula.getY() - wiatrGoraDol - SensorY);
                }
                else
                {
                    wiatrGoraDol -= 0.1;
                    if ( znakPrawoLewo == 0)
                        kula.setY(kula.getY() + wiatrGoraDol - SensorY);
                    else
                        kula.setY(kula.getY() - wiatrGoraDol - SensorY);
                }
                if ( kula.getY() < granicaGorna || kula.getY() > granicaDolna) {
                    przegrana();
                }
            }

            @Override
            public void onFinish() {
                if(!lose)
                    setWiatrGoraDol();
            }
        }.start();
    }

    private void setWiatrPrawoLewo()
    {
        wiatrPrawoLewo = 0;
        znakPrawoLewo = losowa.nextInt(2);
        czasPrawoLewo = 100 * (losowa.nextInt(30)+1);
        licznikPoziomy = new CountDownTimer(czasPrawoLewo,20)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                if ( czasPrawoLewo / 2 < millisUntilFinished)
                {
                    wiatrPrawoLewo += 0.15;
                    if ( znakPrawoLewo == 0)
                        kula.setX(kula.getX() + wiatrPrawoLewo - SensorX);
                    else
                        kula.setX(kula.getX() - wiatrPrawoLewo - SensorX);
                }
                else
                {
                    wiatrPrawoLewo -= 0.15;
                    if ( znakPrawoLewo == 0)
                        kula.setX(kula.getX() + wiatrPrawoLewo - SensorX);
                    else
                        kula.setX(kula.getX() - wiatrPrawoLewo - SensorX);
                }
                if ( kula.getX() < granicaLewa || kula.getX() > granicaPrawa) {
                    przegrana();
                }
            }

            @Override
            public void onFinish() {
                if ( !lose )
                    setWiatrPrawoLewo();
            }
        }.start();
    }

    private void przegrana()
    {
        if ( !sprawdzone ) {
            sprawdzone = true;
            timerEnd = System.currentTimeMillis();
            punkty = (int) (timerEnd - timerStart) / 1000;
            Toast.makeText(this, "Wynik: " + punkty, Toast.LENGTH_SHORT).show();
            db.insertWynik( getResources().getString(R.string.graKierunki) , punkty);
        }
        lose = true;
        kula.setVisibility(View.GONE);
        licznikPoziomy.cancel();
        licznikPionowy.cancel();
        kierunek.setVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
        kula.setX(200);
    }

    public void kierunkiStart(View view)
    {
        if (lose)
            kula.setX(200);
        sprawdzone = false;
        lose = false;
        kula.setVisibility(View.VISIBLE);
        kula.setY(granicaDolna/2);
        kierunek.setVisibility(View.GONE);
        start.setVisibility(View.GONE);
        setWiatrGoraDol();
        setWiatrPrawoLewo();
        timerStart = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        kierunek.setText("Ustaw telefon płasko, tak aby oba wskaźniki były ustawione na 0\n" + "Góra - dół:  " +Math.round(event.values[1] ) + "\n" + "Prawo - lewo:  " + Math.round(event.values[2] ) );
        SensorY = event.values[1] ;
        SensorX = event.values[2] ;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onDestroy() {
        if ( db != null)
            db.close();
        super.onDestroy();
    }

}
