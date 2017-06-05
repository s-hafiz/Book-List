package com.begginers.booklist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public static class PrefFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener
    {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_menu);

            Preference minMagnitude = findPreference(getString(R.string.settings_max_search_result_key));
            bindPreferenceSummeryToValue(minMagnitude);
        }

        private void bindPreferenceSummeryToValue(Preference minMagnitude) {
            minMagnitude.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(minMagnitude.getContext());
            String prefString = preferences.getString(minMagnitude.getKey(),"");
            onPreferenceChange(minMagnitude,prefString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue)
        {
            String stringValue = newValue.toString();
            preference.setSummary(stringValue);
            return true;
        }

    }
}
