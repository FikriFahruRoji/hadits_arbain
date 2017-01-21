package id.barkost.haditsarbain.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import id.barkost.haditsarbain.touchListener.RecyclerTouchListener;
import id.barkost.haditsarbain.adapter.MenuAdapter;
import id.barkost.haditsarbain.dbHelper.DatabaseHelper;
import id.barkost.haditsarbain.model.ModelMenu;
import id.barkost.haditsarbain.R;

public class MainActivity extends AppCompatActivity {

    private List<ModelMenu> menuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MenuAdapter mAdapter;

    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        myDB = new DatabaseHelper(this);

//        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
//        if (prefs.getBoolean("firstLaunch", true)) {
//            prefs.edit().putBoolean("firstLaunch", false).commit();

        myDB.delete();

        String[] id = {"1"};
        String[] judul_latin = {"Amal Tergantung Niat"};
        String[] judul_arabic = {"الحــديث الأول"};
        String[] isi_hadits = {"عَنْ أَمِيْرِ الْمُؤْمِنِيْنَ أَبِيْ حَفْصٍ عُمَرَ بْنِ الْخَطَّابِ رَضِيَ اللهُ عَنْهُ قَالَ: سَمِعْتُ رَسُوْلَ اللهِ صلى الله عليه وسلم يَقُوْلُ: إِنَّمَا اْلأَعْمَالُ بِالنِّيَّاتِ وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى. فَمَنْ كَانَتْ هِجْرَتُهُ إِلَى اللهِ وَرَسُوْلِهِ فَهِجْرَتُهُ إِلَى اللهِ وَرَسُوْلِهِ، وَمَنْ كَانَتْ هِجْرَتُهُ لِدُنْيَا يُصِيْبُهَا أَوْ امْرَأَةٍ يَنْكِحُهَا فَهِجْرَتُهُ إِلَى مَا هَاجَرَ إِلَيْهِ. [رواه إماما المحدثين أبو عبد الله محمد بن إسماعيل بن إبراهيم بن المغيرة بن بردزبة البخاري وأبو الحسين مسلم بن الحجاج بن مسلم القشيري النيسابوري في صحيحيهما اللذين]"};
        String[] terjemah_hadits = {"Dari Amirul Mu’minin, Abi Hafs Umar bin Al Khattab radhiallahuanhu, dia berkata, \"Saya mendengar Rasulullah shallahu`alaihi wa sallam bersabda: Sesungguhnya setiap perbuatan tergantung niatnya. Dan sesungguhnya setiap orang (akan dibalas) berdasarkan apa yang dia niatkan. Siapa yang hijrahnya karena (ingin mendapatkan keridhaan) Allah dan Rasul-Nya, maka hijrahnya kepada (keridhaan) Allah dan Rasul-Nya. Dan siapa yang hijrahnya karena menginginkan kehidupan yang layak di dunia atau karena wanita yang ingin dinikahinya maka hijrahnya (akan bernilai sebagaimana) yang dia niatkan."};
        String[] rowi_hadits = {"Riwayat dua imam hadits, Abu Abdullah Muhammad bin Isma’il bin Ibrahim bin Al Mughirah bin Bardizbah Al Bukhari dan Abu Al Husain, Muslim bin Al Hajjaj bin Muslim Al Qusyairi An Naisaaburi di dalam dua kitab Shahih, yang merupakan kitab yang paling shahih yang pernah dikarang."};
        String[] faidah_hadits = {"1. Niat merupakan syarat layak/diterima atau tidaknya amal perbuatan, dan amal ibadah tidak akan menghasilkankan pahala kecuali berdasarkan niat (karena Allah ta’ala)."};
        int Length = id.length;
        for (int i = 0; i < Length; i++) {
            myDB.insert_table(Integer.parseInt(id[i]), judul_latin[i], judul_arabic[i], isi_hadits[i], terjemah_hadits[i], rowi_hadits[i], faidah_hadits[i]);
        }
//        }

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
//                String asd = null;
//                Cursor menu = myDB.select_single_data(String.valueOf(position + 1));
//                if (menu.getCount() == 0) {
//                    return;
//                }
//
//                while (menu.moveToNext()) {
//                    asd = Integer.toString(menu.getInt(0)) + "\n" +menu.getString(1) + "\n" +menu.getString(2) + "\n" +menu.getString(3) + "\n" +menu.getString(4) + "\n" +menu.getString(5) + "\n" +menu.getString(6);
//                }
//                Toast.makeText(getApplicationContext(), asd, Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(MainActivity.this, DetailActivity.class);
//                i.putExtra("sessionId", position + 1);
//                startActivity(i);

                Intent i = new Intent(MainActivity.this, HaditsActivity.class);
                i.putExtra("sessionId", position + 1);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();
    }

    private void prepareMovieData() {
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

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
