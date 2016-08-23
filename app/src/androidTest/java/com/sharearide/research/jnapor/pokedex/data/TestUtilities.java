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
    static String TEST_POKEMON = "Balbasaur";
    static String TEST_TYPE = "Grass";


    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues){
        assertTrue("Empty cursor returned" + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
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

    static ContentValues createPokemonValues(long pokemonTypeId){
        ContentValues pokemon = new ContentValues();
        pokemon.put(PokedexContract.Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
        pokemon.put(PokedexContract.Pokemon.COLUMN_POKEMON_NAME, TEST_POKEMON);
        pokemon.put(PokedexContract.Pokemon.COLUMN_POKEMON_DESC,"Seed Pokemon");

        return pokemon;
    }

    static ContentValues createGrassTypePokemonTypeValues(){
        ContentValues testValues = new ContentValues();
        testValues.put(PokedexContract.PokemonType.COLUMN_POKEMON_TYPE, TEST_TYPE);

        return testValues;
    }

    static long insertPokemonTypeGrass(Context mContext){
        PokemonDBHelper pokemonDBHelper = new PokemonDBHelper(mContext);
        SQLiteDatabase sqLiteDatabase = pokemonDBHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createGrassTypePokemonTypeValues();

        long locationRowId;
        locationRowId = sqLiteDatabase.insert(PokedexContract.PokemonType.TABLE_NAME, null, testValues);


        //Verify if successfully inserted
        assertTrue("Error: Failure to insert Grass Type Pokemon Value", locationRowId != -1);

        return locationRowId;
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

    public void insertGrassTypePokemonTypeValues(Context context){
        insertPokemonType();
    }

    public void insertPokemon(){
        long locationRowId = insertPokemonType();
        assertFalse("Error: Pokemon Type Not Inserted Correctly", locationRowId == -1L);
        PokemonDBHelper pokemonDBHelper = new PokemonDBHelper(mContext);
        SQLiteDatabase sqLiteDatabase = pokemonDBHelper.getWritableDatabase();

        ContentValues pokemonValues = TestUtilities.createPokemonValues(locationRowId);
        long pokemonRowId = sqLiteDatabase.insert(PokedexContract.Pokemon.TABLE_NAME, null, pokemonValues);

        assertTrue(pokemonRowId != -1);

        Cursor pokemonCursor = sqLiteDatabase.query(
                PokedexContract.Pokemon.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCurrentRecord("testInsertReadDB failed to validate", pokemonCursor,pokemonValues);
        pokemonCursor.close();
        sqLiteDatabase.close();
    }

    public long insertPokemonType(){
        PokemonDBHelper pokemonDBHelper = new PokemonDBHelper(mContext);
        SQLiteDatabase sqLiteDatabase = pokemonDBHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createGrassTypePokemonTypeValues();

        long locationRowId;
        locationRowId = sqLiteDatabase.insert(PokedexContract.PokemonType.TABLE_NAME, null, testValues);

        Cursor c = sqLiteDatabase.query(
                PokedexContract.PokemonType.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        //Verify if successfully inserted
        assertTrue("Error: Failure to insert Grass Type Pokemon Value", locationRowId != -1);

        return locationRowId;
    }

}
