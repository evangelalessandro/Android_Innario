package it.trumbl.ilprofeticoDaniele;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import it.trumbl.ilprofeticoDaniele.models.Himno;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String CURRENT_TEXT_NUMBER = "current_text_number";
    private static final String APP_PNAME = "it.trumbl.ilprofeticoDaniele";
    private static final String TOOLBAR_PANEL_TITLE = "toolbar_panel_title";
    private static final String NUM_STRING = "num_string";
    private static final String NUMERO = "numero";
    private static final int SEARCH_HIMNO = 7;
    private static final String TEXT_HIMNO = "text_himno";
    private static final String TEXT_SIZE = "text_size";
    private static final int OLD_LIMIT = 527;
    private static final int NEW_LIMIT = 613;

    private TextView numberHimno;
    private TextView placeholderHimno;
    private SlidingUpPanelLayout upPanelLayout;

    private float textSize;
    private String numString;
    private int numero;
    private int limit;
    private AdView adView;
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adsMethod();
        analitycsMethod();

        limit = NEW_LIMIT;
        numString = "";
        textSize = 20;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }


        numberHimno = (TextView) findViewById(R.id.number_himno);
        placeholderHimno = (TextView) findViewById(R.id.placeholder_himno);
        setupUpPanel();

        ImageView backSpaceButton = (ImageView) findViewById(R.id.back_space);
        backSpaceButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                numberHimno.setText("");
                setPlaceholderHimno();
                numero = 0;
                numString = "";
                return true;
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        restoreDataSaved(savedInstanceState);
    }


//    private void adsMethod() {
//        adView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//    }

    private void analitycsMethod() {
        InnarioApplication application = (InnarioApplication) getApplication();
        tracker = application.getDefaultracker();

        Log.i(TAG, "Setting screen name: Main");
        tracker.setScreenName("Activity~Main");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_TEXT_NUMBER, numberHimno.getText().toString());
        outState.putString(TOOLBAR_PANEL_TITLE, numberHimno.getText().toString());
        outState.putString(NUM_STRING, numString);
        outState.putInt(NUMERO, numero);
        Log.i(TAG, "save instance: " + outState.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivityForResult(new Intent(this, SearchActivity.class), SEARCH_HIMNO);
                return true;

            /*case R.id.action_version_himno:
                if (item.isChecked()) {
                    // himnario Nuevo
                    versionHimno = false;
                    limit = NEW_LIMIT;
                    item.setChecked(false);
                } else {
                    // himanrio antiguo
                    versionHimno = true;
                    limit = OLD_LIMIT;
                    item.setChecked(true);
                }
                getData();
                numberHimno.setText("");
                numString = "";
                numero = 0;
                setPlaceholderHimno();
                return true;
*/
            case R.id.action_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                return true;

            /*case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Innario Avventista");

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+APP_PNAME);
                startActivity(Intent.createChooser(sharingIntent, "Condividi via..."));
                return true;*/
/*
            case R.id.action_about:

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.app_name) + versionName)
                        .setMessage("Ringraziamento per:" +
                                "\nAlessandro Evangelisti" +
                                "\nEmail: evangel.alessandro@gmail.com")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (upPanelLayout != null &&
                (upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED ||
                        upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


    public void numOk(View view) {
        if (numero > 0) {
            Intent intent = new Intent(this, InnoTextActivity.class).putExtra("numero", numero);

            setResult(RESULT_OK, intent);

            startActivity(intent);
        }
//            Himno himn = InnarioApplication.getInnoByNumber(numero);
//            if (himn != null) {
//
//
//                upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
//                String titleShow = numString + ". " + himn.getTitle();
//                toolbarPanel.setTitle(titleShow);
//                textHimno.setText(himn.getTesto());
//                scrollHimno.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        scrollHimno.smoothScrollTo(0, 0);
//                    }
//                }, 300);
//
//                Log.i(TAG, "Setting screen name: inno");
//                tracker.setScreenName("Show-inno");
//                tracker.send(new HitBuilders.EventBuilder()
//                        .setCategory("Action")
//                        .setAction("ShowImno")
//                        .build());
//            }
//        }
    }

    public void inputDelete(View view) {
        menosUno();
    }

    public void number0(View view) {
        masUno(0);
    }

    public void number9(View view) {
        masUno(9);
    }

    public void number8(View view) {
        masUno(8);
    }

    public void number7(View view) {
        masUno(7);
    }

    public void number6(View view) {
        masUno(6);
    }

    public void number5(View view) {
        masUno(5);
    }

    public void number4(View view) {
        masUno(4);
    }

    public void number3(View view) {
        masUno(3);
    }

    public void number2(View view) {
        masUno(2);
    }

    public void number1(View view) {
        masUno(1);
    }


    public void masUno(int num) {
        placeholderHimno.setText("");

        numString = numString + num;
        numero = Integer.parseInt(numString);

        boolean findItem = false;
        if (numero > 0) {


            Himno himn = InnarioApplication.getInnoByNumber(numero);
            if (himn != null) {
                // cerca titolo da mostrare
                String titleShow = numString + ". " + himn.getTitle();
                numberHimno.setText(titleShow);
                findItem = true;
            }
        }
        if (!findItem) {
            numString = numString.substring(0, numString.length() - 1);
            if (numString.length() > 0) {
                numero = Integer.parseInt(numString);
            } else {
                numero = 0;
                setPlaceholderHimno();
            }
        }
    }

    public void menosUno() {
        if (numString.length() <= 1) {
            numString = "";
            numero = 0;
        } else {
            numString = numString.substring(0, numString.length() - 1);
            numero = Integer.parseInt(numString);
        }
        if (numero > 0 && numero <= limit) {
            // buscar titulo para mostrar
            String titleShow = numString + ". " + InnarioApplication.InniPerNumero().get(numero - 1).getTitle();
            numberHimno.setText(titleShow);
        } else {
            // mostrar placeholder
            numberHimno.setText("");
            setPlaceholderHimno();
        }
    }

    private void restoreDataSaved(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            numberHimno.setText(savedInstanceState.getString(CURRENT_TEXT_NUMBER));
            if (numberHimno.getText().toString().length() > 0) {
                placeholderHimno.setText("");
            } else {
                setPlaceholderHimno();
            }
            numString = savedInstanceState.getString(NUM_STRING);
            numero = savedInstanceState.getInt(NUMERO);
            textSize = savedInstanceState.getFloat(TEXT_SIZE);
        }
    }

    private void setupUpPanel() {

        upPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void setPlaceholderHimno() {
        placeholderHimno.setText(R.string.placeholder_himno);

    }
}
