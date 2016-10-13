package it.trumbl.ilprofeticoDaniele;

/**
 * Created by mery on 06/10/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreference {
    public static final String PREFS_NAME = "NKDROID_APP";
    public static final String FAVORITES = "Favorite";
    private Context mContext;

    public SharedPreference(Context context) {
        super();
        mContext = context;
    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    public void storeFavorites(List favorites) {
// used for store arrayList in json format
        SharedPreferences settings;
        Editor editor;
        settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }

    public ArrayList<String> loadFavorites() {
// used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        List favorites = new ArrayList<String>();
        settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            try {


                return (ArrayList<String>) fromJson(jsonFavorites,
                        new TypeToken<ArrayList<String>>() {
                        }.getType());

//                favorites = Arrays.asList(favoriteItems);
//                favorites = new ArrayList(favorites);
//                return (ArrayList<String>) favorites;
            } catch (Exception e) {

            }
            return (ArrayList<String>) favorites;
        } else {
            return new ArrayList<String>();
        }

    }

    public void addFavorite(String item) {
        List favorites = loadFavorites();
        favorites.add(item);
        storeFavorites(favorites);
    }

    public void removeFavorite(String item) {
        ArrayList favorites = loadFavorites();
        if (favorites != null) {

            favorites.remove(item);
            storeFavorites(favorites);
        }
    }
}