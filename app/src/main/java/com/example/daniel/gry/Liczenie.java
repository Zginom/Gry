package com.example.daniel.gry;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class Liczenie extends AppCompatActivity {

    private int punkty;
    private TextView licznik;
    private TextView rachunek;
    private TextView wynik;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b0;
    private Button cofnij;
    private Button licz;
    private Button start;
    private Random losowa;
    private Button[] tabela;
    private int znak;
    private int liczba1;
    private int liczba2;
    private int wynikRownania;
    private int wynikGracza;
    private final int plus = 1;
    private final int minus = 0;
    private CountDownTimer cdt;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liczenie);
        licznik = (TextView)findViewById(R.id.licznik);
        rachunek = (TextView)findViewById(R.id.rachunek);
        wynik = (TextView)findViewById(R.id.wynik);
        b7 = (Button)findViewById(R.id.siedem);
        b8 = (Button)findViewById(R.id.osiem);
        b9 = (Button)findViewById(R.id.dziewiec);
        b4 = (Button)findViewById(R.id.cztery);
        b5 = (Button)findViewById(R.id.piec);
        b6 = (Button)findViewById(R.id.szesc);
        b1 = (Button)findViewById(R.id.jeden);
        b2 = (Button)findViewById(R.id.dwa);
        b3= (Button)findViewById(R.id.trzy);
        b0 = (Button)findViewById(R.id.zero);
        cofnij = (Button)findViewById(R.id.cofnij);
        licz = (Button)findViewById(R.id.licz);
        start = (Button)findViewById(R.id.liczenieStart);
        losowa = new Random();
        tabela = new Button[]{b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,cofnij,licz};
        disableTableButtons();
        licznik.setVisibility(View.GONE);
        db = new DBAdapter(getApplicationContext() );
        db.open();
    }

    private void disableTableButtons()
    {
        for ( int i = 0; i < tabela.length ; i++)
        {
            tabela[i].setEnabled(false);
        }
    }

    private void enableTableButtons()
    {
        for ( int i = 0; i < tabela.length ; i++)
        {
            tabela[i].setEnabled(true);
        }
    }

    private void losujRownanie()
    {
        znak = losowa.nextInt(2);
        losujLiczby(znak);
    }

    private void losujLiczby(int znak) {
        if (znak == plus) {
            liczba1 = losowa.nextInt(99) + 1;
            liczba2 = losowa.nextInt(100 - liczba1) + 1;
            wynikRownania = liczba1 + liczba2;
            rachunek.setText(liczba1 + " + " + liczba2);
        } else {
            liczba1 = losowa.nextInt(100) + 1;
            liczba2 = losowa.nextInt(liczba1) + 1;
            wynikRownania = liczba1 - liczba2;
            rachunek.setText(liczba1 + " - " + liczba2);
        }
        ustawOdliczanie();
    }
    private void ustawOdliczanie()
    {
        cdt = new CountDownTimer(5000,100)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                String czas = Long.toString(millisUntilFinished/100);
                if ( czas.length() == 2)
                    licznik.setText( czas.charAt(0) + "." + czas.charAt(1) );
                else
                    licznik.setText( "0." + czas.charAt(0));
            }

            @Override
            public void onFinish() {
                sprawdz();
            }
        }.start();
    }

    private void przegrana()
    {
        cdt.cancel();
        wynik.setText("");
        rachunek.setText("");
        disableTableButtons();
        licznik.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Twoj wynik to: " + punkty, Toast.LENGTH_SHORT).show();
        db.insertWynik( getResources().getString(R.string.graLiczenie) , punkty);
    }

    private void sprawdz()
    {
        if ( wynik.getText().length() == 0)
        {
            przegrana();
        }
        else {
            wynikGracza = Integer.parseInt(wynik.getText().toString());
            wynik.setText("");
            if (wynikGracza == wynikRownania) {
                cdt.cancel();
                punkty++;
                losujRownanie();
            } else {
                przegrana();
            }
        }
    }

    public void liczenieStart(View view)
    {
        punkty = 0;
        licznik.setVisibility(View.VISIBLE);
        start.setVisibility(View.GONE);
        enableTableButtons();
        losujRownanie();
    }

    public void cofnij(View view)
    {
        int rozm = wynik.getText().length();
        if ( rozm != 0)
            wynik.setText(((String) wynik.getText()).substring(0,rozm-1));
    }

    public void licz(View view)
    {
        if ( wynik.getText().length() != 0)
            sprawdz();
    }


    public void add7(View view)
    {
        wynik.setText(wynik.getText() + "7");
    }
    public void add8(View view)
    {
        wynik.setText(wynik.getText() + "8");
    }
    public void add9(View view)
    {
        wynik.setText(wynik.getText() + "9");
    }
    public void add4(View view)
    {
        wynik.setText(wynik.getText() + "4");
    }
    public void add5(View view)
    {
        wynik.setText(wynik.getText() + "5");
    }
    public void add6(View view)
    {
        wynik.setText(wynik.getText() + "6");
    }
    public void add1(View view)
    {
        wynik.setText(wynik.getText() + "1");
    }
    public void add2(View view)
    {
        wynik.setText(wynik.getText() + "2");
    }
    public void add3(View view)
    {
        wynik.setText(wynik.getText() + "3");
    }
    public void add0(View view)
    {
        wynik.setText(wynik.getText() + "0");
    }

    protected void onDestroy() {
        if ( db != null)
            db.close();
        super.onDestroy();
    }

}
