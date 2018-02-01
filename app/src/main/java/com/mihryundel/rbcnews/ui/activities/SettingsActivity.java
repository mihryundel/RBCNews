package com.mihryundel.rbcnews.ui.activities;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.mihryundel.rbcnews.MySharedPreferences;
import com.mihryundel.rbcnews.R;
import com.mihryundel.rbcnews.RbcNewsApplication;

import javax.inject.Inject;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        public MainPreferenceFragment() {
            RbcNewsApplication.app().appComponent().inject(this);
        }

        @Inject
        MySharedPreferences mySharedPreferences;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.intervalList)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.categoryUrlList)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.subcategoryList)));
        }

        private void checkSubcategoryActivation(String value) {
            String key = mySharedPreferences.getCategoryUrl();
            ListPreference subcategoryList = (ListPreference)findPreference(getString(R.string.subcategoryList));
            subcategoryList.setEnabled(true);
            switch (value) {
                case "https://www.autonews.ru/":
                    subcategoryList.setEntries(R.array.auto_subcategory);
                    subcategoryList.setEntryValues(R.array.auto_subcategory_values);
                    break;
                case "https://sport.rbc.ru/":
                    subcategoryList.setEntries(R.array.sport_subcategory);
                    subcategoryList.setEntryValues(R.array.sport_subcategory_values);
                    break;
                case "https://realty.rbc.ru/":
                    subcategoryList.setEntries(R.array.realty_subcategory);
                    subcategoryList.setEntryValues(R.array.realty_subcategory_values);
                    break;
                default:
                    mySharedPreferences.setSubcategory("Все");
                    subcategoryList.setEnabled(false);
            }
        }


        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                if (preference instanceof ListPreference) {
                    // For list preferences, look up the correct display value in
                    // the preference's 'entries' list.
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? listPreference.getEntries()[index]
                                    : null);
                    if (preference.getKey().equals(getString(R.string.categoryUrlList)))
                        checkSubcategoryActivation(newValue.toString());
                } else {
                    preference.setSummary(stringValue);
                }
                return true;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}