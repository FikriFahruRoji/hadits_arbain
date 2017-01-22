package id.barkost.haditsarbain.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.barkost.haditsarbain.listener.AppBarStateChangeListener;
import id.barkost.haditsarbain.R;
import id.barkost.haditsarbain.dbHelper.DatabaseHelper;

public class HaditsActivity extends AppCompatActivity {

    public static int id = 0;
    private DatabaseHelper myDb;
    private TextView txHadits, txTerjemah, txNo, txLatin, txFootnote;
    private ImageView visibleHadits;
    private Button mediaPlay, mediaNext, mediaPrev, mediaRepeat;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadits);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        final CollapsingToolbarLayout collapsingtoolbarlayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state.name().equals("COLLAPSED")) {
                    collapsingtoolbarlayout.setTitle("Hadits ke - " + Integer.toString(id + 1));
                } else if (state.name().equals("EXPANDED")) {
                    collapsingtoolbarlayout.setTitle("");
                } else if (state.name().equals("IDLE")) {
                    collapsingtoolbarlayout.setTitle("");
                }
            }
        });

        txNo = (TextView) findViewById(R.id.tv_det_no);
        txLatin = (TextView) findViewById(R.id.tv_det_latin);
        txHadits = (TextView) findViewById(R.id.tx_det_hadits);
        txTerjemah = (TextView) findViewById(R.id.tx_det_terjemah);
        txFootnote = (TextView) findViewById(R.id.tv_det_footnote);
        visibleHadits = (ImageView) findViewById(R.id.img_visible);

        visibleHadits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleHadits.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.text_show).getConstantState()) {
                    visibleHadits.setImageDrawable(getResources().getDrawable(R.drawable.text_hide));
                    txHadits.setVisibility(View.GONE);
                } else {
                    visibleHadits.setImageDrawable(getResources().getDrawable(R.drawable.text_show));
                    txHadits.setVisibility(View.VISIBLE);
                }
            }
        });

        mediaNext = (Button) findViewById(R.id.btn_media_next);
        mediaNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HaditsActivity.this, HaditsActivity.class);
                id = id + 1;
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
        mediaPrev = (Button) findViewById(R.id.btn_media_prev);
        mediaPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HaditsActivity.this, HaditsActivity.class);
                if (id == 1) {
                    id = 1;
                } else {
                    id = id - 1;
                }
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "font.otf");
        txHadits.setTypeface(face);
        txFootnote.setTypeface(null, Typeface.ITALIC);

        myDb = new DatabaseHelper(this);
        Cursor menu = myDb.select_single_data(String.valueOf(id));
        if (menu.getCount() == 0) {
            return;
        }

        while (menu.moveToNext()) {
            txNo.setText(Html.fromHtml("Hadits ke -" + Integer.toString(menu.getInt(0))));
            txLatin.setText(Html.fromHtml(menu.getString(1)));
            txHadits.setText(Html.fromHtml(menu.getString(3)));
            txTerjemah.setText(Html.fromHtml(menu.getString(4)));
            txFootnote.setText(Html.fromHtml(menu.getString(5)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hadits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.action_bookmark) {
            Toast.makeText(this, "Bookmark", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
