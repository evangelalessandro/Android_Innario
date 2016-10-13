package it.trumbl.ilprofeticoDaniele;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import it.trumbl.ilprofeticoDaniele.adapters.HimnoAdapter;
import it.trumbl.ilprofeticoDaniele.models.Himno;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "SearchActivity";
    private ListView listView;
    private HimnoAdapter himnoAdapter;
    private ArrayList<Himno> himnos;

    private boolean versionHimno;

    private Tracker tracker;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        analitycsMethod();
        listView = (ListView) findViewById(R.id.listView);

        himnos = InnarioApplication.getHimnosASC();
        himnoAdapter = new HimnoAdapter(this, himnos);
        listView.setAdapter(himnoAdapter);


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.e(TAG, "onItemClick: " + i);

                        OpenHimn(i);
                        //finish();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();

        himnoAdapter.ReloadPrefered();
        himnoAdapter.notifyDataSetChanged();

    }

    private void OpenHimn(int numeroInno) {
        Intent intent = new Intent(this, InnoTextActivity.class).putExtra("numero", himnos.get(numeroInno).getNumero());

        setResult(RESULT_OK, intent);

        startActivity(intent);

    }

    private void analitycsMethod() {
        InnarioApplication application = (InnarioApplication) getApplication();
        tracker = application.getDefaultracker();

        Log.i(TAG, "Setting screen name: Main");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        tracker.setScreenName("Search-Himno");
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("SearchHimno")
                .build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint(getResources().getString(R.string.Title_himn));
        searchView.requestFocus();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //https://github.com/mancj/MaterialSearchBar seacrh bar da aggiungeree
    @Override
    public boolean onQueryTextChange(String newText) {
        himnos = InnarioApplication.getHimnByText(newText);
        himnoAdapter.setData(himnos);
        himnoAdapter.notifyDataSetChanged();

        return false;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Search Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

}
