package id.barkost.haditsarbain.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

import id.barkost.haditsarbain.util.AppBarStateChangeListener;
import id.barkost.haditsarbain.util.RecyclerTouchListener;
import id.barkost.haditsarbain.adapter.MenuAdapter;
import id.barkost.haditsarbain.dbHelper.DatabaseHelper;
import id.barkost.haditsarbain.model.ModelMenu;
import id.barkost.haditsarbain.R;

public class MainActivity extends AppCompatActivity {

    private List<ModelMenu> menuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MenuAdapter mAdapter;

    private Toolbar toolbar;

    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        final CollapsingToolbarLayout collapsingtoolbarlayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state.name().equals("COLLAPSED")) {
                    collapsingtoolbarlayout.setTitle(getResources().getString(R.string.app_name));
                } else if (state.name().equals("EXPANDED")) {
                    collapsingtoolbarlayout.setTitle("");
                } else if (state.name().equals("IDLE")) {
                    collapsingtoolbarlayout.setTitle("");
                }
            }
        });

        myDB = new DatabaseHelper(this);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        if (prefs.getBoolean("firstLaunch", true)) {
            prefs.edit().putBoolean("firstLaunch", false).commit();
            Resources res = getResources();
            String[] id = res.getStringArray(R.array.id);
            String[] judul_latin = res.getStringArray(R.array.judul_latin);
            String[] judul_arabic = res.getStringArray(R.array.judul_arabic);
            String[] isi_hadits = res.getStringArray(R.array.isi_hadits);
            String[] terjemah_hadits = res.getStringArray(R.array.terjemah_hadits);
            String[] rowi_hadits = res.getStringArray(R.array.footnote_hadits);
            String[] syarah = res.getStringArray(R.array.faidah_hadits);
            int[] media = {
                    R.raw.al_falaq,
                    R.raw.al_falaq,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
                    R.raw.an_naas,
            };
            int Length = id.length;
            for (int i = 0; i < Length; i++) {
                myDB.insert_table(Integer.parseInt(id[i]), judul_latin[i], judul_arabic[i], isi_hadits[i], terjemah_hadits[i], rowi_hadits[i], syarah[i], media[i]);
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MenuAdapter(menuList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(MainActivity.this, HaditsActivity.class);
                HaditsActivity.ID = position + 1;
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        menuList.clear();
        Cursor menu = myDB.select_menu();
        while (menu.moveToNext()) {
            ModelMenu menu1 = new ModelMenu(menu.getInt(0), menu.getString(1), menu.getString(2));
            menuList.add(menu1);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        if (id == R.id.action_settings) {
            i = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(i);
        } else if (id == R.id.action_rate) {
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
            
        } else if (id == R.id.action_about) {
//            displaySharedPreferences();

            new LovelyStandardDialog(this)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(R.string.app_name)
                    .setMessage("kjhckjsdhk")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

//    private void displaySharedPreferences() {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        String prefsHadits = prefs.getString("pref_hadits", "Sedang");
//        String prefsTerjemah = prefs.getString("pref_terjemah", "Sedang");
//        String prefsSyarah = prefs.getString("pref_syarah", "Sedang");
//
//        StringBuilder builder = new StringBuilder();
//        builder.append(prefsHadits + "\n");
//        builder.append(prefsTerjemah + "\n");
//        builder.append(prefsSyarah);
//
//        Toast.makeText(this, builder, Toast.LENGTH_SHORT).show();
//    }
}
