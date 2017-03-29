package br.com.banconeon.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * A Singleton for managing your SharedPreferences.
 *
 * You should make sure to change the SETTINGS_NAME to what you want
 * and choose the operating made that suits your needs, the default is
 * MODE_PRIVATE.
 *
 * IMPORTANT: The class is not thread safe. It should work fine in most 
 * circumstances since the write and read operations are fast. However
 * if you call edit for bulk updates and do not commit your changes
 * there is a possibility of data loss if a background thread has modified
 * preferences at the same time.
 * 
 * Usage:
 * 
 * int sampleInt = LocalStorage.getInstance(context).getInt(Key.SAMPLE_INT);
 * LocalStorage.getInstance(context).set(Key.SAMPLE_INT, sampleInt);
 * 
 * If LocalStorage.getInstance(Context) has been called once, you can 
 * simple use LocalStorage.getInstance() to save some precious line space.
 */
public class LocalStorage {
    // TODO: CHANGE THIS TO SOMETHING MEANINGFUL
    private static final String SETTINGS_NAME = "default_settings";
    private static LocalStorage sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;

    /**
     * Class for keeping all the keys used for shared preferences in one place.
     */
    public static class Key {
        /* Recommended naming convention:
         * ints, floats, doubles, longs:
         * SAMPLE_NUM or SAMPLE_COUNT or SAMPLE_INT, SAMPLE_LONG etc.
         *
         * boolean: IS_SAMPLE, HAS_SAMPLE, CONTAINS_SAMPLE
         *
         * String: SAMPLE_KEY, SAMPLE_STR or just SAMPLE
         */
        public static final String SAMPLE_INT = "a_sample_key";

    }

    private LocalStorage(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }


    public static LocalStorage getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new LocalStorage(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static LocalStorage getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    public void put(String key, String val) throws Exception {
        doEdit();
        mEditor.putString(key, SecurityData.encrypt(val));
        doCommit();
    }

    public void put(String key, int val) throws Exception {
        doEdit();
        mEditor.putInt(key, Integer.parseInt(SecurityData.encrypt(Integer.toString(val))));
        doCommit();
    }

    public void put(String key, boolean val) throws Exception {
        doEdit();
        mEditor.putBoolean(key, Boolean.parseBoolean(SecurityData.encrypt(Boolean.toString(val))));
        doCommit();
    }

    public void put(String key, float val) throws Exception {
        doEdit();
        mEditor.putFloat(key, Float.parseFloat(SecurityData.encrypt(Float.toString(val))));
        doCommit();
    }

    /**
     * Convenience method for storing doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to store.
     * @param val The new value for the preference.
     */
    public void put(String key, double val) throws Exception {
        doEdit();
        mEditor.putString(key, SecurityData.encrypt(String.valueOf(val)));
        doCommit();
    }

    public void put(String key, long val) throws Exception {
        doEdit();
        mEditor.putLong(key, Long.parseLong(SecurityData.encrypt(Long.toString(val))));
        doCommit();
    }

    public String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    public String getString(String key) throws Exception {
        return mPref.getString(key, "").isEmpty() ? mPref.getString(key, "") : SecurityData.decrypt(mPref.getString(key, ""));
    }

    public int getInt(String key) throws Exception {
        return Integer.parseInt(SecurityData.decrypt(Integer.toString(mPref.getInt(key, 0))));
    }

    public int getInt(String key, int defaultValue) throws Exception {
        return Integer.parseInt(SecurityData.decrypt(Integer.toString(mPref.getInt(key, defaultValue))));
    }

    public long getLong(String key) throws Exception {
        return Long.parseLong(SecurityData.decrypt(Long.toString(mPref.getLong(key, 0))));
    }

    public long getLong(String key, long defaultValue) throws Exception {
        return Long.parseLong(SecurityData.decrypt(Long.toString(mPref.getLong(key, defaultValue))));
    }

    public float getFloat(String key) throws Exception {
        return Float.parseFloat(SecurityData.decrypt(Float.toString(mPref.getFloat(key, 0))));
    }

    public float getFloat(String key, float defaultValue) throws Exception {
        return Float.parseFloat(SecurityData.decrypt(Float.toString(mPref.getFloat(key, defaultValue))));
    }

    /**
     * Convenience method for retrieving doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to fetch.
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
     * Convenience method for retrieving doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to fetch.
     */
    public double getDouble(String key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The name of the key(s) to be removed.
     */
    public void remove(String... keys) {
        doEdit();
        for (String key : keys) {
            mEditor.remove(key);
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }
}