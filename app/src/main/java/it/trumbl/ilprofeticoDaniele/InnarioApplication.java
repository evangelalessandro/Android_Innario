package it.trumbl.ilprofeticoDaniele;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import it.trumbl.ilprofeticoDaniele.dataset.DBAdapter;
import it.trumbl.ilprofeticoDaniele.models.Himno;

/**
 * Created by jhonlimaster on 25-12-15.
 */
public class InnarioApplication extends Application {


    private static boolean versionHimno;
    private static ArrayList<Himno> _inniPerNumero;
    private static ArrayList<Himno> himnosASC = new ArrayList<>();


    private static Context mContext;
    private Tracker tracker;

    public static ArrayList<Himno> InniPerNumero() {
        return _inniPerNumero;
    }

    public static ArrayList<Himno> getHimnosASC() {
        return himnosASC;
    }

    public static ArrayList getHimnByText(String textFilter) {
        DBAdapter dbAdapter = new DBAdapter(mContext);

        String filter = !TextUtils.isEmpty(textFilter) ? textFilter : null;
        dbAdapter.open();
        ArrayList inni = new ArrayList<>();
        Cursor himnoForTitle;
        if (filter != null) {
            himnoForTitle = dbAdapter.getHimnoForTitle(filter, versionHimno);
        } else {
            himnoForTitle = dbAdapter.getAllHimnoASC(versionHimno);
        }
        while (himnoForTitle.moveToNext()) {
            inni.add(Himno.fromCursor(himnoForTitle));
        }
        dbAdapter.close();
        return inni;

    }

    public static Context getAppContext() {
        return mContext;
    }

    public static Himno getInnoByNumber(int num) {
        for (Himno him : _inniPerNumero) {
            if (him.getNumero() == num) {
                return him;
            }
        }
        return null;
    }

    synchronized public Tracker getDefaultracker(){
        if (tracker == null){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        getData();

    }

    private void getData() {
        DBAdapter dbAdapter = new DBAdapter(mContext);
        dbAdapter.open();
        _inniPerNumero = new ArrayList<>();
        Cursor allHimno = dbAdapter.getAllHimno(versionHimno);
        while (allHimno.moveToNext()) {
            _inniPerNumero.add(Himno.fromCursor(allHimno));
        }

        allHimno = dbAdapter.getAllHimnoASC(versionHimno);
        while (allHimno.moveToNext()) {
            himnosASC.add(Himno.fromCursor(allHimno));
        }

        dbAdapter.close();
    }

}
