package com.example.androiddevelopment.djordjebulatovic.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = Prijava.TABLE_NAME_PRIJAVA)
public class Prijava {

    public static final String TABLE_NAME_PRIJAVA = "prijava";
    public static final String FIELD_PRIJAVA_ID = "id";
    public static final String FIELD_PRIJAVA_NAZIV = "naziv";
    public static final String FIELD_PRIJAVA_OPIS = "opis";
    public static final String FIELD_PRIJAVA_STATUS = "status";
    public static final String FIELD_PRIJAVA_DATUM = "datum";
    public static final String FIELD_PRIJAVA_STAVKE = "stavke";

    @DatabaseField(columnName = FIELD_PRIJAVA_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_PRIJAVA_NAZIV)
    private String naziv;

    @DatabaseField(columnName = FIELD_PRIJAVA_OPIS)
    private String opis;

    @DatabaseField(columnName = FIELD_PRIJAVA_STATUS)
    private String status;

    @DatabaseField(columnName = FIELD_PRIJAVA_DATUM)
    private String datum;

    @ForeignCollectionField(columnName = Prijava.FIELD_PRIJAVA_STAVKE)
    private ForeignCollection<Stavka> stavke;

    public Prijava(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public ForeignCollection<Stavka> getStavke() {
        return stavke;
    }

    public void setStavke(ForeignCollection<Stavka> stavke) {
        this.stavke = stavke;
    }

    @Override
    public String toString() {
        return naziv;

    }
}
