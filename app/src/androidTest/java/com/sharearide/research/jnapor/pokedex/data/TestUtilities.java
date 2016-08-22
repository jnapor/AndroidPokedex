package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import com.sharearide.research.jnapor.pokedex.util.PollingCheck;

import java.util.Map;
import java.util.Set;

/**
 * Created by jnapor on 8/22/2016.
 */
public class TestUtilities extends AndroidTestCase{

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues){
        assertTrue("Empty cursor returned" + error, valueCursor.moveToFirst());
        validateCursor(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createPokemonValues(int pokemonTypeId){
        ContentValues pokemon = new ContentValues();
        pokemon.put(PokedexContract.Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
        pokemon.put(PokedexContract.Pokemon.COLUMN_POKEMON_NAME, "Bulbasaur");
        pokemon.put(PokedexContract.Pokemon.COLUMN_POKEMON_DESC,"Seed Pokemon");

        return pokemon;
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    static ContentValues createGrassTypePokemonTypeValues(){
        ContentValues testValues = new ContentValues();
        testValues.put(PokedexContract.PokemonType.COLUMN_POKEMON_TYPE, "Grass");

        return testValues;
    }

    static long insertGrassTypePokemonTypeValues(Context context){
        PokemonDBHelper dbHelper = new PokemonDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createGrassTypePokemonTypeValues();

        long locationRowId;
        locationRowId = db.insert(PokedexContract.PokemonType.TABLE_NAME, null, testValues);

        //Verify if successfully inserted
        assertTrue("Error: Failure to insert Grass Type Pokemon Value", locationRowId != -1);

        return locationRowId;
    }
}
