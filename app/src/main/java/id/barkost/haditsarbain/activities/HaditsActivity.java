package id.barkost.haditsarbain.activities;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import id.barkost.haditsarbain.R;
import id.barkost.haditsarbain.dbHelper.DatabaseHelper;

public class HaditsActivity extends AppCompatActivity {

    public static String KEY_ITEM = "item";
    private DatabaseHelper myDb;
    private HtmlTextView txTerjemah;
    private TextView txArabic, txHadits, txNo, txLatin, txFootnote, txFaidah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadits);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Bundle session = getIntent().getExtras();
        int sessionId = session.getInt("sessionId");

        txNo = (TextView) findViewById(R.id.tv_det_no);
        txLatin = (TextView) findViewById(R.id.tv_det_latin);
        txHadits = (TextView) findViewById(R.id.tx_det_hadits);
        txTerjemah = (HtmlTextView) findViewById(R.id.tx_det_terjemah);
        txFootnote = (TextView) findViewById(R.id.tv_det_footnote);
        txFaidah = (TextView) findViewById(R.id.tv_det_faidah);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "font.otf");
        txHadits.setTypeface(face);
        txFootnote.setTypeface(null, Typeface.ITALIC);

        myDb = new DatabaseHelper(this);
        Cursor menu = myDb.select_single_data(String.valueOf(sessionId));
        if (menu.getCount() == 0) {
            return;
        }

        while (menu.moveToNext()) {
            txNo.setText(Html.fromHtml("Hadits ke -" + Integer.toString(menu.getInt(0))));
            txLatin.setText(Html.fromHtml(menu.getString(1)));
            txHadits.setText(Html.fromHtml(menu.getString(3)));
            txTerjemah.setText(Html.fromHtml(menu.getString(4)));
            txFootnote.setText(Html.fromHtml(menu.getString(5)));
            txFaidah.setText(Html.fromHtml(menu.getString(6)));
        }
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
