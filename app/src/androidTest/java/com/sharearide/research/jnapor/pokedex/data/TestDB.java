package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

/**
 * Created by jnapor on 8/22/2016.
 */
public class TestDB extends AndroidTestCase{
    public static final String LOG_TAG = TestDB.class.getSimpleName();

    void deleteTheDatabase(){
        mContext.deleteDatabase(PokemonDBHelper.DATABASE_NAME);
    }

    public void setUp(){
        deleteTheDatabase();
    }

    public void testCreateDB(){
        final HashSet<String> tableNameHashSet = new HashSet<>();

        tableNameHashSet.add(PokedexContract.PokemonType.TABLE_NAME);
        tableNameHashSet.add(PokedexContract.Pokemon.TABLE_NAME);
        tableNameHashSet.add(PokedexContract.Users.TABLE_NAME);

        mContext.deleteDatabase(PokemonDBHelper.DATABASE_NAME);
        SQLiteDatabase db = new PokemonDBHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both pokemontype and pokemon tables",
                tableNameHashSet.isEmpty());

        //check if tables contain the correct columns
        c = db.rawQuery("PRAGMA table_info(" +PokedexContract.PokemonType.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we are unale to query the database for table information",
                c.moveToFirst());

        final HashSet<String> pokemontypeColumnHashSet = new HashSet<String>();
        pokemontypeColumnHashSet.add(PokedexContract.PokemonType._ID);
        pokemontypeColumnHashSet.add(PokedexContract.PokemonType.COLUMN_POKEMON_TYPE);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            pokemontypeColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                pokemontypeColumnHashSet.isEmpty());
        db.close();
    }

    public void testPokemonTypeTable(){
        PokemonDBHelper pokemonDBHelper = new PokemonDBHelper(mContext);
        SQLiteDatabase sqLiteDatabase = pokemonDBHelper.getWritableDatabase();

        ContentValues contentValues = TestUtilities.createGrassTypePokemonTypeValues();

        long row = sqLiteDatabase.insert(PokedexContract.PokemonType.TABLE_NAME, null, contentValues);

        assertTrue(row != -1);

        Cursor c = sqLiteDatabase.query(
                PokedexContract.PokemonType.TABLE_NAME,
                null, //all columns
                null, //condition
                null, //values for the condition
                null, //group by
                null, //row group
                null //sort order
        );

        assertTrue("Error: No records found by pokemon type query", c.moveToFirst());

        c.close();
        sqLiteDatabase.close();
    }
}
