package aethers.notebook.azure.appender;

import java.util.ArrayList;

import aethers.notebook.azure.appender.Configuration.ConnectionType;
import aethers.notebook.core.ui.IntegerPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class ConfigurationActivity 
extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(Configuration.SHARED_PREFERENCES_NAME);
        addPreferencesFromResource(R.xml.configuration);
        
        ListPreference connTypes = (ListPreference)findPreference(
                getString(R.string.Preferences_connectionType));
        ArrayList<String> entries = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();
        for(ConnectionType ct : ConnectionType.values())
        {
            entries.add(ct.friendlyName);
            values.add(ct.toString());
        }
        connTypes.setEntries(entries.toArray(new String[0]));
        connTypes.setEntryValues(values.toArray(new String[0]));
        
        Preference maxSize = 
                findPreference(getString(R.string.Preferences_maxFileSize));
        maxSize.setOnPreferenceChangeListener(new IntegerPreferenceChangeListener(
                1, Integer.MAX_VALUE, 
                "Maximum must be a number greater than or equal to 1", this));
        
        Preference maxFiles = 
            findPreference(getString(R.string.Preferences_maxFiles));
        maxFiles.setOnPreferenceChangeListener(
                new IntegerPreferenceChangeListener(
                        1, Integer.MAX_VALUE,
                        "Leave blank for unlimited, otherwise must be a number greater than or equal to 1",
                        true,
                        this));
    }
}
