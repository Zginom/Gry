package com.example.daniel.gry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Opcje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcje);
    }

    public void start(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WyborGry.class);
        startActivity(intent);
    }

    public void wyniki(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Wyniki.class);
        startActivity(intent);
    }

    public void autor(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Autor.class);
        startActivity(intent);
    }
}
