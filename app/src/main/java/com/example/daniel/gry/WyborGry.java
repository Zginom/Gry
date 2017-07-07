package com.example.daniel.gry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WyborGry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_gry);
    }

    public void kolory(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Kolory.class);
        startActivity(intent);
    }

    public void liczenie(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Liczenie.class);
        startActivity(intent);
    }

    public void kierunki(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Kierunki.class);
        startActivity(intent);
    }

}
