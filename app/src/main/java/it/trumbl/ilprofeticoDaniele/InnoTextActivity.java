package it.trumbl.ilprofeticoDaniele;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import it.trumbl.ilprofeticoDaniele.models.Himno;

public class InnoTextActivity extends AppCompatActivity {

    public static final String INNOPREFERITO = "HIMN_PREFERED_LIST";
    private static final String TAG = "InnoTextActivity";
    private static final String TEXT_SIZE = "text_size";
    private float textSize;
    private TextView textHimno;
    private SharedPreferences sharedpreferences;
    private int numeroInnoCorrente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inno_text);
        MarqueeToolbar toolbar = (MarqueeToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textSize = 20;

        textHimno = (TextView) findViewById(R.id.text_inno);
        Intent intent = getIntent();
        numeroInnoCorrente = intent.getIntExtra("numero", 0);

        Himno inno = InnarioApplication.getInnoByNumber(numeroInnoCorrente);
        textHimno.setText(
                inno.getTesto());

        sharedpreferences = getSharedPreferences("textSize", Context.MODE_PRIVATE);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(String.valueOf(inno.getNumero()) + " " + inno.getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabMinus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Testo ridotto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                textSize -= 1;
                textHimno.setTextSize(textSize);
                SavePreference();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fabplus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textSize += 1;
                Snackbar.make(view, "Testo aumentato a " + textSize, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                textHimno.setTextSize(textSize);
                SavePreference();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fabStar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
                ArrayList arrayListPrefered = sharedPreference.loadFavorites();

                String valore = Integer.toString(numeroInnoCorrente);

                if (!arrayListPrefered.contains(valore)) {
                    Snackbar.make(view, "Inno aggiunto tra i preferiti", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    sharedPreference.addFavorite(valore);
                } else {
                    Snackbar.make(view, "Inno rimosso tra i preferiti", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    sharedPreference.removeFavorite(valore);
                }

                restoreDataSaved();

            }
        });
        restoreDataSaved();

    }

    private void SavePreference() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(TEXT_SIZE, textSize);
        editor.commit();
    }

    private void restoreDataSaved() {
        textSize = sharedpreferences.getFloat(TEXT_SIZE, 20);
        textHimno.setTextSize(textSize);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabStar);

        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        ArrayList arrayListPrefered = sharedPreference.loadFavorites();

        String valore = Integer.toString(numeroInnoCorrente);
        if (!arrayListPrefered.contains(valore)) {
            fab.setImageResource(R.drawable.ic_star_border_white_24dp);
        } else {

            fab.setImageResource(R.drawable.ic_star_white_24dp);
        }

    }




}
