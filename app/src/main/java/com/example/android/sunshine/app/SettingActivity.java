package com.example.android.sunshine.app;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        //调用bingPreferenceSummaryToValue() 方法，并把key传入
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));
    }

    //设置偏好监听器，调用onPreferenceChange() 方法
    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        //将用户设置的值(key)转换成字符串
        String stringValue = value.toString();

        //判断用户设置的值(key)是否为ListPreference
        if (preference instanceof ListPreference) {
            //初始化ListPreference
            ListPreference listPreference = (ListPreference) preference;
            //找出用户设置的preference(即 key) 在哪一列，
            //并设置那一列的摘要
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {//
            preference.setSummary(stringValue);
        }
        return true;
    }
}
