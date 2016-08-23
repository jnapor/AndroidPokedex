package com.sharearide.research.jnapor.pokedex.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.sharearide.research.jnapor.pokedex.data.PokedexContract.Pokemon;
import com.sharearide.research.jnapor.pokedex.data.PokedexContract.PokemonType;

import android.test.AndroidTestCase;
import android.util.Log;


public class TestProvider extends AndroidTestCase {
    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void deleteAllRecordsFromProvider(){
        mContext.getContentResolver().delete(
            Pokemon.CONTENT_URI,
                null,
                null
        );

        mContext.getContentResolver().delete(
                PokemonType.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                Pokemon.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from Pokemon table during delete", 0 , cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                PokemonType.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from PokemonType table during delete", 0 , cursor.getCount());
        cursor.close();
    }

    public void deleteAllRecordsFromDB(){
        PokemonDBHelper pokemonDBHelper = new PokemonDBHelper(mContext);
        SQLiteDatabase sqLiteDatabase = pokemonDBHelper.getWritableDatabase();

        sqLiteDatabase.delete(Pokemon.TABLE_NAME,null,null);
        sqLiteDatabase.delete(Pokemon.TABLE_NAME,null,null);
        sqLiteDatabase.close();
    }

    public void deleteAllRecords(){
        deleteAllRecordsFromProvider();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry(){
        PackageManager packageManager = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(),PokemonProvider.class.getName());

        try{
            ProviderInfo providerInfo = packageManager.getProviderInfo(componentName,0);

            assertEquals("Error: PokemonProvider registered with authority: " + providerInfo.authority +
                    " instead of authority: " + PokedexContract.CONTENT_AUTHORITY,
                    providerInfo.authority, PokedexContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: WeatherProvider not registered at "+mContext.getPackageName(), false);
        }
    }

    public void testGetType(){
        String type = mContext.getContentResolver().getType(Pokemon.CONTENT_URI);
        assertEquals("Error: Pokemon CONTENT_URI should return Pokemon.CONTENT_TYPE",
                Pokemon.CONTENT_TYPE, type);

        String testType = TestUtilities.TEST_TYPE;
        type = mContext.getContentResolver().getType(Pokemon.buildPokemonPokeType(type));

        assertEquals("Error: The Pokemon CONTENT_URI with type should return Pokemon.CONTENT_TYPE",
                Pokemon.CONTENT_TYPE, type);

        String name = TestUtilities.TEST_POKEMON;
        type = mContext.getContentResolver().getType(
                Pokemon.buildPokemonPokeTypeWithName(testType, name));
        assertEquals("Error: The Pokemon CONTENT_URI with type and name should return Pokemon.CONTENT_ITEM_TYPE",
                Pokemon.CONTENT_ITEM_TYPE, type);

        type = mContext.getContentResolver().getType(PokemonType.CONTENT_URI);
        assertEquals("Error: The PokemonType URI should return PokemonType.CONTENT_TYPE", PokemonType.CONTENT_TYPE, type);
    }

    public void testBasicPokemonQuery(){
        PokemonDBHelper pokemonDBHelper = new PokemonDBHelper(mContext);
        SQLiteDatabase sqLiteDatabase = pokemonDBHelper.getWritableDatabase();

        ContentValues contentValues = TestUtilities.createGrassTypePokemonTypeValues();
        long rowId = TestUtilities.insertPokemonTypeGrass(mContext);

        ContentValues pokemonValues = TestUtilities.createPokemonValues(rowId);
        long pokemonRowId = sqLiteDatabase.insert(Pokemon.TABLE_NAME, null, pokemonValues);
        assertTrue("Unable to Insert Pokemon into the Database", pokemonRowId != -1);


        sqLiteDatabase.close();

        Cursor pokemonCursor = mContext.getContentResolver().query(
                Pokemon.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("testBasicPokemonQuery", pokemonCursor, pokemonValues);
    }

    public void testBasicPokemonTypeQuery(){
        PokemonDBHelper pokemonDBHelper = new PokemonDBHelper(mContext);
        SQLiteDatabase sqLiteDatabase = pokemonDBHelper.getWritableDatabase();

        ContentValues contentValues = TestUtilities.createGrassTypePokemonTypeValues();
        long rowId = TestUtilities.insertPokemonTypeGrass(mContext);

        Cursor pokemonCursor = mContext.getContentResolver().query(
                PokemonType.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("testBasicQueries, location query", pokemonCursor, contentValues);
    }

    public void testInsertReadProvider(){
        ContentValues contentValues = TestUtilities.createGrassTypePokemonTypeValues();
        TestUtilities.TestContentObserver testContentObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PokemonType.CONTENT_URI, true, testContentObserver);

        Uri pokemonTypeUri = mContext.getContentResolver().insert(PokemonType.CONTENT_URI, contentValues);

        testContentObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(testContentObserver);

        long pokemonRowId = ContentUris.parseId(pokemonTypeUri);

        assertTrue(pokemonRowId != -1);

        Cursor cursor = mContext.getContentResolver().query(
                PokemonType.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error PokemonType", cursor, contentValues);

        ContentValues pokemonValues = TestUtilities.createPokemonValues(pokemonRowId);

        testContentObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(Pokemon.CONTENT_URI, true, testContentObserver);

        Uri pokemonInsertionUri = mContext.getContentResolver().insert(Pokemon.CONTENT_URI, pokemonValues);

        assertTrue(pokemonInsertionUri != null);

        testContentObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(testContentObserver);

        Cursor pokemonCursor = mContext.getContentResolver().query(
                Pokemon.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        TestUtilities.validateCursor("Error validating Pokemon Insert", pokemonCursor, pokemonValues);

        pokemonValues.putAll(contentValues);

        pokemonCursor = mContext.getContentResolver().query(
                Pokemon.buildPokemonPokeType(TestUtilities.TEST_TYPE),
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("Error validating joined pokemon and pokemon type", pokemonCursor, pokemonValues);

        pokemonCursor = mContext.getContentResolver().query(
                Pokemon.buildPokemonPokeTypeWithName(TestUtilities.TEST_TYPE,TestUtilities.TEST_POKEMON),
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("Error validating joined pokemon and pokemon type for a specific name",
                pokemonCursor, pokemonValues);
    }

    public void testUpdatePokemonType(){
        ContentValues contentValues = TestUtilities.createGrassTypePokemonTypeValues();

        Uri pokemonTypeUri = mContext.getContentResolver().insert(PokemonType.CONTENT_URI, contentValues);
        long pokemonRowId = ContentUris.parseId(pokemonTypeUri);

        assertTrue(pokemonRowId != -1);
        Log.e(LOG_TAG, "New row id: "+pokemonRowId);

        ContentValues updatedValues = new ContentValues(contentValues);
        updatedValues.put(PokemonType._ID, pokemonRowId);
        updatedValues.put(PokemonType.COLUMN_POKEMON_TYPE, "Poison");

        Cursor pokemonCursor = mContext.getContentResolver().query(PokemonType.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver testContentObserver = TestUtilities.getTestContentObserver();
        pokemonCursor.registerContentObserver(testContentObserver);

        int count = mContext.getContentResolver().update(
                PokemonType.CONTENT_URI, updatedValues, PokemonType._ID + "= ?",
                new String[] {Long.toString(pokemonRowId)});

        assertEquals(count, 1);

        testContentObserver.waitForNotificationOrFail();
        pokemonCursor.unregisterContentObserver(testContentObserver);
        pokemonCursor.close();

        Cursor cursor = mContext.getContentResolver().query(
                PokemonType.CONTENT_URI,
                null,
                PokemonType._ID + " = " + pokemonRowId,
                null,
                null
        );

        cursor.moveToFirst();
        TestUtilities.validateCurrentRecord("Error validating pokemon entry update",
                cursor, updatedValues);

        cursor.close();
    }

    public void testDeleteRecords(){
        testInsertReadProvider();

        TestUtilities.TestContentObserver pokemonTypeObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PokemonType.CONTENT_URI, true, pokemonTypeObserver);

        TestUtilities.TestContentObserver pokemonObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(Pokemon.CONTENT_URI, true, pokemonObserver);

        deleteAllRecordsFromProvider();

        pokemonTypeObserver.waitForNotificationOrFail();
        pokemonObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(pokemonTypeObserver);
        mContext.getContentResolver().unregisterContentObserver(pokemonObserver);
    }
}
