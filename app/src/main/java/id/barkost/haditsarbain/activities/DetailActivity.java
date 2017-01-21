package id.barkost.haditsarbain.activities;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import id.barkost.haditsarbain.R;
import id.barkost.haditsarbain.dbHelper.DatabaseHelper;

public class DetailActivity extends AppCompatActivity {

    public static String KEY_ITEM = "item";
    private DatabaseHelper myDb;
    private HtmlTextView txNo, txLatin, txTerjemah, txFootnote, txFaidah;
    private TextView txArabic, txHadits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle session = getIntent().getExtras();
        int sessionId = session.getInt("sessionId");

        txNo = (HtmlTextView) findViewById(R.id.tv_det_no);
        txLatin = (HtmlTextView) findViewById(R.id.tv_det_latin);
        txArabic = (TextView) findViewById(R.id.tv_det_arabic);
        txHadits = (TextView) findViewById(R.id.tv_det_hadits);
        txTerjemah = (HtmlTextView) findViewById(R.id.tv_det_terjemah);
        txFootnote = (HtmlTextView) findViewById(R.id.tv_det_footnote);
        txFaidah = (HtmlTextView) findViewById(R.id.tv_det_faidah);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "font.otf");
        txArabic.setTypeface(face);
        txHadits.setTypeface(face);


        myDb = new DatabaseHelper(this);
        Cursor menu = myDb.select_single_data(String.valueOf(sessionId));
        if (menu.getCount() == 0) {
            return;
        }

        while (menu.moveToNext()) {
            txNo.setHtml(Integer.toString(menu.getInt(0)), new HtmlResImageGetter(txNo));
            txLatin.setHtml(menu.getString(1), new HtmlResImageGetter(txLatin));
            txArabic.setText(menu.getString(2));
            txHadits.setText(menu.getString(3));
            txTerjemah.setHtml(menu.getString(4), new HtmlResImageGetter(txTerjemah));
            txFootnote.setHtml(menu.getString(5), new HtmlResImageGetter(txFootnote));
            txFaidah.setHtml(menu.getString(6), new HtmlResImageGetter(txFaidah));
        }
    }

}
