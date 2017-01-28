package id.barkost.haditsarbain.activities;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import id.barkost.haditsarbain.R;

public class SettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_HADITS = "pref_hadits";
    public static final String KEY_TERJEMAH = "pref_terjemah";
    public static final String KEY_SYARAH = "pref_syarah";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        ListPreference editTextHadits = (ListPreference) findPreference(KEY_HADITS);
        ListPreference editTextTerjemah = (ListPreference) findPreference(KEY_TERJEMAH);
        ListPreference editTextSyarah = (ListPreference) findPreference(KEY_SYARAH);
        editTextHadits.setSummary(sp.getString(KEY_HADITS, "Medium"));
        editTextTerjemah.setSummary(sp.getString(KEY_TERJEMAH, "Medium"));
        editTextSyarah.setSummary(sp.getString(KEY_SYARAH, "Medium"));
    }


    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_HADITS)) {
            Preference prefHadits = findPreference(key);
            prefHadits.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(KEY_TERJEMAH)) {
            Preference prefTerjemah = findPreference(key);
            prefTerjemah.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(KEY_SYARAH)) {
            Preference prefSyarah = findPreference(key);
            prefSyarah.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}