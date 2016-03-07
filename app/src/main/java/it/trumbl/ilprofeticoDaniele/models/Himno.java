package it.trumbl.ilprofeticoDaniele.models;

import android.database.Cursor;

import it.trumbl.ilprofeticoDaniele.dataset.DatabaseHelper;

/**
 * Created by jhonlimaster on 07-12-15.
 */
public class Himno {
    private int numero;
    private String titulo;
    private String letra;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public static Himno fromCursor(Cursor cursor){
        Himno himno = new Himno();
        himno.setNumero(cursor.getInt(DatabaseHelper.Columns.numero.ordinal()));
        himno.setTitulo(cursor.getString(DatabaseHelper.Columns.titulo.ordinal()));
        himno.setLetra(cursor.getString(DatabaseHelper.Columns.letra.ordinal()));
        return himno;
    }
}
