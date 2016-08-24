package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sharearide.research.jnapor.pokedex.data.PokedexContract.Pokemon;
import com.sharearide.research.jnapor.pokedex.data.PokedexContract.PokemonType;
import com.sharearide.research.jnapor.pokedex.data.PokedexContract.Users;

/**
 * Created by jnapor on 8/22/2016.
 */
public class PokemonDBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 8;

    static final String DATABASE_NAME = "pokedex.db";

    public PokemonDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_POKEMON_TABLE = "CREATE TABLE " + Pokemon.TABLE_NAME + " ( " +
                Pokemon._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Pokemon.COLUMN_POKEMON_NAME + " TEXT NOT NULL, " +
                Pokemon.COLUMN_POKEMON_TYPE_ID + " TEXT NOT NULL, " +
                Pokemon.COLUMN_POKEMON_DESC + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + Pokemon.COLUMN_POKEMON_TYPE_ID + ") REFERENCES " +
                PokemonType.TABLE_NAME + " (" + PokemonType._ID + "))";

        final String SQL_CREATE_POKEMON_TYPE_TABLE = "CREATE TABLE " + PokemonType.TABLE_NAME + " ( " +
                PokemonType._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PokemonType.COLUMN_POKEMON_TYPE + " TEXT NOT NULL" + ")";

        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + Users.TABLE_NAME + " ( " +
                Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Users.COLUMN_USERNAME + " TEXT NOT NULL," + Users.COLUMN_PASSWORD +" TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_POKEMON_TYPE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_POKEMON_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.e("DBHelper: ", "Creating New Database Version");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Pokemon.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PokemonType.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Users.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

