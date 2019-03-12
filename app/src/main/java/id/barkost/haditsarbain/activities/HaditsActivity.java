package id.barkost.haditsarbain.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import id.barkost.haditsarbain.R;
import id.barkost.haditsarbain.dbHelper.DatabaseHelper;
import id.barkost.haditsarbain.util.AppBarStateChangeListener;

public class HaditsActivity extends AppCompatActivity {

    public static int ID = 0;

    private int fav;
    private DatabaseHelper myDb;
    private TextView txNo, txLatin, txHadits, txHaditsHead, txTerjemah, txTerjemahHead, txSyarah, txSyarahHead, txFootnote;
    private ImageView visibleHadits, visibleTerjemah, visibleSyarah;
    private Button mediaPlay, mediaNext, mediaPrev, mediaStop;
    private Toolbar toolbar;
    private MediaPlayer mediaPlayer;

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
                    collapsingtoolbarlayout.setTitle("Hadits ke - " + Integer.toString(ID));
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
        txHaditsHead = (TextView) findViewById(R.id.tv_hadits);
        txTerjemah = (TextView) findViewById(R.id.tx_det_terjemah);
        txTerjemahHead = (TextView) findViewById(R.id.tv_terjemah);
        txFootnote = (TextView) findViewById(R.id.tv_det_footnote);
        txSyarah = (TextView) findViewById(R.id.tv_det_syarah);
        txSyarahHead = (TextView) findViewById(R.id.tv_syarah);

        setTextSize();

        visibleHadits = (ImageView) findViewById(R.id.img_visible_hadits);
        visibleTerjemah = (ImageView) findViewById(R.id.img_visible_terjemah);
        visibleSyarah = (ImageView) findViewById(R.id.img_visible_syarah);

        visibleHadits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleHadits.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.text_show).getConstantState()) {
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
                if (visibleTerjemah.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.text_show).getConstantState()) {
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
        visibleSyarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleSyarah.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.text_show).getConstantState()) {
                    visibleSyarah.setImageDrawable(getResources().getDrawable(R.drawable.text_hide));
                    txSyarah.setVisibility(View.VISIBLE);
                } else {
                    visibleSyarah.setImageDrawable(getResources().getDrawable(R.drawable.text_show));
                    txSyarah.setVisibility(View.GONE);
                }
            }
        });

        mediaNext = (Button) findViewById(R.id.btn_media_next);
        mediaNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HaditsActivity.this, HaditsActivity.class);
                ID = ID + 1;
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
                if (ID == 1) {
                    ID = 1;
                } else {
                    ID = ID - 1;
                }
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
        mediaStop = (Button) findViewById(R.id.btn_media_stop);
        mediaStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(-1);
                mediaPlayer.pause();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mediaPlay.setBackground(getResources().getDrawable(R.drawable.button_media_play));
                }
            }
        });
        mediaPlay = (Button) findViewById(R.id.btn_media_play);
        mediaPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mediaPlay.setBackground(getResources().getDrawable(R.drawable.button_media_play));
                        }
                        mediaPlayer.pause();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mediaPlay.setBackground(getResources().getDrawable(R.drawable.button_media_pause));
                        }
                        mediaPlayer.start();
                    }
                }
            }
        });


        if (ID == 1) {
            mediaPrev.setEnabled(false);
        } else if (ID == 42) {
            mediaNext.setEnabled(false);
        }

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "font.otf");
        txHadits.setTypeface(face);
        txFootnote.setTypeface(null, Typeface.ITALIC);

        myDb = new DatabaseHelper(this);
        Cursor menu = myDb.select_single_data(String.valueOf(ID));
        if (menu.getCount() == 0) {
            return;
        }
        while (menu.moveToNext()) {
            txNo.setText(Html.fromHtml(getResources().getString(R.string.hadits_ke) + Integer.toString(menu.getInt(0))));
            txLatin.setText(Html.fromHtml(menu.getString(1)));
            txHadits.setText(Html.fromHtml(menu.getString(3)));
            txTerjemah.setText(Html.fromHtml(menu.getString(4)));
            txFootnote.setText(Html.fromHtml(menu.getString(6)));
            txSyarah.setText(menu.getString(5));
            fav = menu.getInt(6);
            mediaPlayer = MediaPlayer.create(HaditsActivity.this, menu.getInt(7));
        }
    }

    public void setTextSize() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HaditsActivity.this);
        String prefsHadits = prefs.getString("pref_hadits", getResources().getStringArray(R.array.listTextSize)[2]);
        String prefsTerjemah = prefs.getString("pref_terjemah", getResources().getStringArray(R.array.listTextSize)[2]);
        String prefsSyarah = prefs.getString("pref_syarah", getResources().getStringArray(R.array.listTextSize)[2]);

        if (prefsHadits.equals(getResources().getStringArray(R.array.listTextSize)[0])) {
            txHadits.setTextSize(getResources().getDimension(R.dimen.textSizeExtraSmallArabic));
            txHaditsHead.setTextSize(getResources().getDimension(R.dimen.textSizeExtraSmall));
        } else if (prefsHadits.equals(getResources().getStringArray(R.array.listTextSize)[1])) {
            txHadits.setTextSize(getResources().getDimension(R.dimen.textSizeSmallArabic));
            txHaditsHead.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
        } else if (prefsHadits.equals(getResources().getStringArray(R.array.listTextSize)[2])) {
            txHadits.setTextSize(getResources().getDimension(R.dimen.textSizeMediumArabic));
            txHaditsHead.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
        } else if (prefsHadits.equals(getResources().getStringArray(R.array.listTextSize)[3])) {
            txHadits.setTextSize(getResources().getDimension(R.dimen.textSizeLargeArabic));
            txHaditsHead.setTextSize(getResources().getDimension(R.dimen.textSizeMedium));
        } else if (prefsHadits.equals(getResources().getStringArray(R.array.listTextSize)[4])) {
            txHadits.setTextSize(getResources().getDimension(R.dimen.textSizeExtraLargeArabic));
            txHaditsHead.setTextSize(getResources().getDimension(R.dimen.textSizeLarge));
        }

        if (prefsTerjemah.equals(getResources().getStringArray(R.array.listTextSize)[0])) {
            txTerjemah.setTextSize(getResources().getDimension(R.dimen.textSizeExtraSmall));
            txFootnote.setTextSize(getResources().getDimension(R.dimen.textSizeExtraSmall));
            txTerjemahHead.setTextSize(getResources().getDimension(R.dimen.textSizeExtraSmall));
        } else if (prefsTerjemah.equals(getResources().getStringArray(R.array.listTextSize)[1])) {
            txTerjemah.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
            txFootnote.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
            txTerjemahHead.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
        } else if (prefsTerjemah.equals(getResources().getStringArray(R.array.listTextSize)[2])) {
            txTerjemah.setTextSize(getResources().getDimension(R.dimen.textSizeMedium));
            txFootnote.setTextSize(getResources().getDimension(R.dimen.textSizeMedium));
            txTerjemahHead.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
        } else if (prefsTerjemah.equals(getResources().getStringArray(R.array.listTextSize)[3])) {
            txTerjemah.setTextSize(getResources().getDimension(R.dimen.textSizeLarge));
            txFootnote.setTextSize(getResources().getDimension(R.dimen.textSizeLarge));
            txTerjemahHead.setTextSize(getResources().getDimension(R.dimen.textSizeMedium));
        } else if (prefsTerjemah.equals(getResources().getStringArray(R.array.listTextSize)[4])) {
            txTerjemah.setTextSize(getResources().getDimension(R.dimen.textSizeExtraLarge));
            txFootnote.setTextSize(getResources().getDimension(R.dimen.textSizeExtraLarge));
            txTerjemahHead.setTextSize(getResources().getDimension(R.dimen.textSizeLarge));
        }

        if (prefsSyarah.equals(getResources().getStringArray(R.array.listTextSize)[0])) {
            txSyarah.setTextSize(getResources().getDimension(R.dimen.textSizeExtraSmall));
            txSyarahHead.setTextSize(getResources().getDimension(R.dimen.textSizeExtraSmall));
        } else if (prefsSyarah.equals(getResources().getStringArray(R.array.listTextSize)[1])) {
            txSyarah.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
            txSyarahHead.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
        } else if (prefsSyarah.equals(getResources().getStringArray(R.array.listTextSize)[2])) {
            txSyarah.setTextSize(getResources().getDimension(R.dimen.textSizeMedium));
            txSyarahHead.setTextSize(getResources().getDimension(R.dimen.textSizeSmall));
        } else if (prefsSyarah.equals(getResources().getStringArray(R.array.listTextSize)[3])) {
            txSyarah.setTextSize(getResources().getDimension(R.dimen.textSizeLarge));
            txSyarahHead.setTextSize(getResources().getDimension(R.dimen.textSizeMedium));
        } else if (prefsSyarah.equals(getResources().getStringArray(R.array.listTextSize)[4])) {
            txSyarah.setTextSize(getResources().getDimension(R.dimen.textSizeExtraLarge));
            txSyarahHead.setTextSize(getResources().getDimension(R.dimen.textSizeLarge));
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(HaditsActivity.this, AdsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
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
                    String texthadits = "", texttranslate = "", textsyarah = "";

                    if (hadits.isChecked() == true) {
                        texthadits = getResources().getString(R.string.hadits) + "\n" + txHadits.getText().toString() + "\n\n";
                    }
                    if (translate.isChecked() == true) {
                        texttranslate = getResources().getString(R.string.terjemah) + "\n" + txTerjemah.getText().toString() + "\n" + txFootnote.getText().toString() + "\n\n";
                    }
                    if (syarah.isChecked() == true) {
                        textsyarah = getResources().getString(R.string.syarah) + "\n" + txSyarah.getText().toString() + "\n\n";
                    }

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, texthadits + texttranslate + textsyarah);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
