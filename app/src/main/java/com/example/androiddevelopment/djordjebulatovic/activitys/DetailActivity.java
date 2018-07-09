package com.example.androiddevelopment.djordjebulatovic.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.djordjebulatovic.R;
import com.example.androiddevelopment.djordjebulatovic.db.OrmLighthHelper;
import com.example.androiddevelopment.djordjebulatovic.db.model.Prijava;
import com.example.androiddevelopment.djordjebulatovic.db.model.Stavka;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class DetailActivity extends AppCompatActivity{
    private OrmLighthHelper databaseHelperl;
    private Prijava p;

    private EditText naziv;
    private EditText opis;
    private EditText status;
    private EditText datum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        int key = getIntent().getExtras().getInt(MainActivity.KEY);
        try {
            p = getDatabaseHelper().getPrijavaDao().queryForId(key);

            naziv = (EditText) findViewById(R.id.detail_naziv);
            opis = (EditText) findViewById(R.id.detail_opis);
            status = (EditText) findViewById(R.id.detail_status);
            datum = (EditText) findViewById(R.id.detail_datum);

            naziv.setText(p.getNaziv());
            opis.setText(p.getOpis());
            status.setText(p.getStatus());
            datum.setText(p.getDatum());


        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ListView listView = (ListView) findViewById(R.id.detail_stavke);
        try {
            final List<Stavka>list = getDatabaseHelper().getStavkaDao().queryBuilder()
                    .where()
                    .eq(Stavka.FIELD_STAVKA_PRIJAVA, p.getId())
                    .query();
            ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Stavka stavka = (Stavka) listView.getItemAtPosition(position);
                    Toast.makeText(DetailActivity.this, stavka.getNaslov() +" "+stavka.getOpis()+" "+stavka.getDatum(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void refresh(){
        ListView listView = (ListView) findViewById(R.id.detail_stavke);
        ArrayAdapter<Stavka>adapter = (ArrayAdapter<Stavka>) listView.getAdapter();
        if (adapter!=null){
            try {
                adapter.clear();
                List<Stavka> list = getDatabaseHelper().getStavkaDao().queryForAll();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case  R.id.prijava_remove:
            try {
                getDatabaseHelper().getPrijavaDao().delete(p);
                finish();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
            case R.id.stavka_add:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.add_new_stavka);
                Button add = (Button) dialog.findViewById(R.id.add_stavka);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText naslov = (EditText) dialog.findViewById(R.id.stavka_naslov);
                        EditText opis = (EditText) dialog.findViewById(R.id.stavka_opis);

                        Stavka stavka = new Stavka();
                        stavka.setNaslov(naslov.getText().toString());
                        stavka.setOpis(opis.getText().toString());
                        stavka.setPrijava(p);
                        try {
                            getDatabaseHelper().getStavkaDao().create(stavka);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        refresh();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public OrmLighthHelper getDatabaseHelper() {
        if (databaseHelperl == null) {
            databaseHelperl = OpenHelperManager.getHelper(this, OrmLighthHelper.class);
        }
        return databaseHelperl;
    }

    @Override
    protected void onDestroy() {
        OpenHelperManager.releaseHelper();
        databaseHelperl = null;
        super.onDestroy();
    }
}
