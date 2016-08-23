package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.sharearide.research.jnapor.pokedex.data.PokedexContract.Pokemon;
import com.sharearide.research.jnapor.pokedex.data.PokedexContract.PokemonType;

import android.test.AndroidTestCase;


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
        deleteAllRecordsFromDB();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }
}
