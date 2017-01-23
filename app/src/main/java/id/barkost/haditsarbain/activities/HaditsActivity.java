package id.barkost.haditsarbain.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.barkost.haditsarbain.listener.AppBarStateChangeListener;
import id.barkost.haditsarbain.R;
import id.barkost.haditsarbain.dbHelper.DatabaseHelper;

public class HaditsActivity extends AppCompatActivity {

    public static int id = 0;
    private int fav;
    private DatabaseHelper myDb;
    private TextView txHadits, txTerjemah, txNo, txLatin, txFootnote;
    private ImageView visibleHadits, visibleTerjemah;
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
        visibleHadits = (ImageView) findViewById(R.id.img_visible_hadits);
        visibleTerjemah = (ImageView) findViewById(R.id.img_visible_terjemah);

        visibleHadits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleHadits.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.text_show).getConstantState()) {
                    visibleHadits.setImageDrawable(getResources().getDrawable(R.drawable.text_hide));
                    txHadits.setVisibility(View.VISIBLE);
                } else {
                    visibleHadits.setImageDrawable(getResources().getDrawable(R.drawable.text_show));
                    txHadits.setVisibility(View.GONE);
                }
            }
        });

        visibleTerjemah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleTerjemah.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.text_show).getConstantState()) {
                    visibleTerjemah.setImageDrawable(getResources().getDrawable(R.drawable.text_hide));
                    txTerjemah.setVisibility(View.VISIBLE);
                    txFootnote.setVisibility(View.VISIBLE);
                } else {
                    visibleTerjemah.setImageDrawable(getResources().getDrawable(R.drawable.text_show));
                    txTerjemah.setVisibility(View.GONE);
                    txFootnote.setVisibility(View.GONE);
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
            txNo.setText(Html.fromHtml("Hadits ke - " + Integer.toString(menu.getInt(0))));
            txLatin.setText(Html.fromHtml(menu.getString(1)));
            txHadits.setText(Html.fromHtml(menu.getString(3)));
            txTerjemah.setText(Html.fromHtml(menu.getString(4)));
            txFootnote.setText(Html.fromHtml(menu.getString(5)));
            fav = menu.getInt(6);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hadits, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            final CheckBox hadits, translate, syarah;

            LayoutInflater inflater = LayoutInflater.from(this);
            final View dialoglayout1 = inflater.inflate(R.layout.layout_share, null);

            hadits = (CheckBox) dialoglayout1.findViewById(R.id.cb_hadits);
            translate = (CheckBox) dialoglayout1.findViewById(R.id.cb_terjemah);
            syarah = (CheckBox) dialoglayout1.findViewById(R.id.cb_syarah);
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle(R.string.share);
            builder.setView(dialoglayout1);
            builder.setCancelable(true);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String texthadits = "", texttranslate = "";

                    if (hadits.isChecked() == true ){
                        texthadits = txHadits.getText().toString();
                    }
                    if (translate.isChecked() == true ){
                        texttranslate = "\n\n" + txTerjemah.getText().toString();
                    }

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, texthadits + texttranslate);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            });

            builder.setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            final android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
