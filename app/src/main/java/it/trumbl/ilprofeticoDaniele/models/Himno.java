package it.trumbl.ilprofeticoDaniele.models;

import android.database.Cursor;

import it.trumbl.ilprofeticoDaniele.dataset.DatabaseHelper;

/**
 * Created by jhonlimaster on 07-12-15.
 */
public class Himno {
    private int numeroInno;
    private String titleInno;
    private String testoInno;

    public int getNumero() {
        return numeroInno;
    }

    public void setNumero(int numero) {
        this.numeroInno = numero;
    }

    public String getTitle() {
        return titleInno;
    }

    public void setTitle(String title) {
        this.titleInno = title;
    }

    public String getTesto() {
        return testoInno;
    }

    public void setTesto(String testo) {
        testoInno = testo;
    }

    public static Himno fromCursor(Cursor cursor){
        Himno himno = new Himno();
        himno.setNumero(cursor.getInt(DatabaseHelper.Columns.numero.ordinal()));
        himno.setTitle(cursor.getString(DatabaseHelper.Columns.titolo.ordinal()));
        himno.setTesto(cursor.getString(DatabaseHelper.Columns.testo.ordinal()));
        return himno;
    }
}
