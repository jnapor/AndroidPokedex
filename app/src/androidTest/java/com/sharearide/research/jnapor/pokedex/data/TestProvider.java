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
import com.sharearide.research.jnapor.pokedex.data.PokedexContract.Users;

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

        mContext.getContentResolver().delete(
                Users.CONTENT_URI,
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


        cursor = mContext.getContentResolver().query(
                Users.CONTENT_URI,
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
        sqLiteDatabase.delete(PokemonType.TABLE_NAME,null,null);
        sqLiteDatabase.delete(Users.TABLE_NAME, null,null);
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

        String username = TestUtilities.TEST_USERNAME;
        type = mContext.getContentResolver().getType(Users.CONTENT_URI);
        assertEquals("Error: The USERS CONTENT_URI with type and name should return USERS.CONTENT_TYPE", Users.CONTENT_TYPE, type);

        type  = mContext.getContentResolver().getType(Users.buildUsersWithUserName(TestUtilities.TEST_USERNAME));
        assertEquals("Error: The USERS_WITH_USERNAME_AND PASSWORD URI with type and name should return USERS.CONTENT_TYPE",
                Users.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(Users.buildUserLogin(TestUtilities
                .TEST_USERNAME, TestUtilities.TEST_PASSWORD));
        assertEquals("Error: The USERS_WITH_USERNAME_AND PASSWORD URI with type and name should return USERS.CONTENT_ITEM_TYPE",
                Users.CONTENT_ITEM_TYPE, type);
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

        ContentValues values = TestUtilities.createUserValues();
        TestUtilities.TestContentObserver observer = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(Users.CONTENT_URI, true, observer);

        Uri userUri = mContext.getContentResolver().insert(Users.CONTENT_URI, values);

        testContentObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        long userId = ContentUris.parseId(userUri);

        assertTrue(userId != -1);

        Cursor cur = mContext.getContentResolver().query(
                Users.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error UserType", cur, values);
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

    public void testUpdateUser(){
        ContentValues contentValues = TestUtilities.createUserValues();

        Uri userUri = mContext.getContentResolver().insert(Users.CONTENT_URI, contentValues);
        long userRowId = ContentUris.parseId(userUri);

        assertTrue(userRowId != -1);
        Log.e(LOG_TAG, "New user row id: "+userRowId);

        ContentValues updatedValues = new ContentValues(contentValues);
        updatedValues.put(Users._ID, userRowId);
        updatedValues.put(Users.COLUMN_USERNAME, "Nichol");

        Cursor pokemonCursor = mContext.getContentResolver().query(Users.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver testContentObserver = TestUtilities.getTestContentObserver();
        pokemonCursor.registerContentObserver(testContentObserver);

        int count = mContext.getContentResolver().update(
                Users.CONTENT_URI, updatedValues, Users._ID + "= ?",
                new String[] {Long.toString(userRowId)});

        assertEquals(count, 1);

        testContentObserver.waitForNotificationOrFail();
        pokemonCursor.unregisterContentObserver(testContentObserver);
        pokemonCursor.close();

        Cursor cursor = mContext.getContentResolver().query(
                Users.CONTENT_URI,
                null,
                Users._ID + " = " + userRowId,
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

        TestUtilities.TestContentObserver userObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(Pokemon.CONTENT_URI, true, userObserver);

        deleteAllRecordsFromProvider();

        pokemonTypeObserver.waitForNotificationOrFail();
        pokemonObserver.waitForNotificationOrFail();
        userObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(pokemonTypeObserver);
        mContext.getContentResolver().unregisterContentObserver(pokemonObserver);
        mContext.getContentResolver().unregisterContentObserver(userObserver);
    }
}
