package com.example.androiddevelopment.djordjebulatovic.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = Stavka.TABLE_NAME_STAVKA)
public class Stavka {

    public static final String TABLE_NAME_STAVKA = "stavka";
    public static final String FIELD_STAVKA_ID = "id";
    public static final String FIELD_STAVKA_NASLOV = "naslov";
    public static final String FIELD_STAVKA_OPIS = "opis";
    public static final String FIELD_STAVKA_NIVO = "nivo ozbiljnosti";
    public static final String FIELD_STAVKA_KOMENTAR = "komentar";
    public static final String FIELD_STAVKA_DATUM = "datum";
    public static final String FIELD_STAVKA_SLIKA = "slika";

    @DatabaseField(columnName = FIELD_STAVKA_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_STAVKA_NASLOV)
    private String naslov;

    @DatabaseField(columnName = FIELD_STAVKA_OPIS)
    private String opis;

    @DatabaseField(columnName = FIELD_STAVKA_NIVO)
    private float nivo;

    @DatabaseField(columnName = FIELD_STAVKA_KOMENTAR)
    private String komentar;

    @DatabaseField(columnName = FIELD_STAVKA_DATUM)
    private Date datum;

    @DatabaseField(columnName = FIELD_STAVKA_SLIKA)
    private String slika;

    public Stavka(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public float getNivo() {
        return nivo;
    }

    public void setNivo(float nivo) {
        this.nivo = nivo;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    @Override
    public String toString() {
        return naslov;
    }
}
