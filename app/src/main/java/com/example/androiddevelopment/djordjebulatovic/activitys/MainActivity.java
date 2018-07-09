package com.example.androiddevelopment.djordjebulatovic.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;

import com.example.androiddevelopment.djordjebulatovic.R;
import com.example.androiddevelopment.djordjebulatovic.db.OrmLighthHelper;
import com.example.androiddevelopment.djordjebulatovic.db.model.Prijava;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public OrmLighthHelper databaseHelper;
    public static String KEY = "KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listView = (ListView)findViewById(R.id.listaPrijava);
        try {
            List<Prijava> list = getDatabaseHelper().getPrijavaDao().queryForAll();
            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Prijava p = (Prijava) listView.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(KEY, p.getId());
                    startActivity(intent);

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh(){
        ListView listView = (ListView) findViewById(R.id.listaPrijava);
        ArrayAdapter<Prijava>adapter = (ArrayAdapter<Prijava>) listView.getAdapter();
        if (adapter!=null){
            try {
                adapter.clear();
                List<Prijava>list = getDatabaseHelper().getPrijavaDao().queryForAll();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.prijava_add:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_new_prijava);

                Button cancel = (Button) dialog.findViewById(R.id.cancel_prijava);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button add = (Button) dialog.findViewById(R.id.add_prijava);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText naslov = (EditText) dialog.findViewById(R.id.prijava_naziv);
                        EditText opis = (EditText) dialog.findViewById(R.id.prijava_opis);

                        Prijava p = new Prijava();
                        p.setNaziv(naslov.getText().toString());
                        p.setOpis(opis.getText().toString());

                        try {
                            getDatabaseHelper().getPrijavaDao().create(p);
                            refresh();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
                case R.id.prijava_about:
                    final Dialog dialog1 = new Dialog(MainActivity.this);
                    dialog1.setContentView(R.layout.informacije);
                    Button zatvori = (Button) dialog1.findViewById(R.id.zatvori);
                    zatvori.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });
                    break;
        }


        return super.onOptionsItemSelected(item);
    }

    public OrmLighthHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, OrmLighthHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        super.onDestroy();
    }
}
