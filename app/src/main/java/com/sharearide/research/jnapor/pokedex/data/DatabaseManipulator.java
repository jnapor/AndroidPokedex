package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.sharearide.research.jnapor.pokedex.data.PokedexContract.*;
/**
 * Created by jnapor on 8/24/2016.
 */
public class DatabaseManipulator {
    private static final String LOG_TAG = DatabaseManipulator.class.getSimpleName();


    public static int login(Context context, String loginusername, String loginpassword){
        Cursor cursor = context.getContentResolver().query(
                Users.CONTENT_URI,
                null,
                Users.COLUMN_USERNAME  + " = ? AND " + Users.COLUMN_PASSWORD + "= ?",
                new String[]{loginusername, loginpassword},
                null
        );

        return cursor.getCount();
    }

    public static void initializeDatabase(Context context){
        Cursor usersCursor = context.getContentResolver().query(Users.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        Cursor pokemonTypeCursor = context.getContentResolver().query(PokemonType.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        Cursor pokemonCursor = context.getContentResolver().query(Pokemon.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if(usersCursor.getCount() < 0){
            ContentValues userValues = new ContentValues();
            userValues.put(Users.COLUMN_USERNAME, "admin");
            userValues.put(Users.COLUMN_PASSWORD, "admin");

            long userRowId = insertUser(context, userValues);

            if(userRowId < 0){
                Log.e(LOG_TAG, "Error in inserting new user");
            }
        }

        if(pokemonTypeCursor.getCount() < 0){
            ContentValues[] pokemonTypeValues = new ContentValues[17];
            for (int count = 0; count < 17; count++) {
                pokemonTypeValues[0] = new ContentValues();
            }
            pokemonTypeValues[0].put(PokemonType.COLUMN_POKEMON_TYPE, "NORMAL");
            pokemonTypeValues[1].put(PokemonType.COLUMN_POKEMON_TYPE, "FIRE");
            pokemonTypeValues[2].put(PokemonType.COLUMN_POKEMON_TYPE, "WATER");
            pokemonTypeValues[3].put(PokemonType.COLUMN_POKEMON_TYPE, "ELECTRIC");
            pokemonTypeValues[4].put(PokemonType.COLUMN_POKEMON_TYPE, "GRASS");
            pokemonTypeValues[5].put(PokemonType.COLUMN_POKEMON_TYPE, "ICE");
            pokemonTypeValues[6].put(PokemonType.COLUMN_POKEMON_TYPE, "FIGHTING");
            pokemonTypeValues[7].put(PokemonType.COLUMN_POKEMON_TYPE, "GROUND");
            pokemonTypeValues[8].put(PokemonType.COLUMN_POKEMON_TYPE, "FLYING");
            pokemonTypeValues[9].put(PokemonType.COLUMN_POKEMON_TYPE, "PSYCHIC");
            pokemonTypeValues[10].put(PokemonType.COLUMN_POKEMON_TYPE, "BUG");
            pokemonTypeValues[11].put(PokemonType.COLUMN_POKEMON_TYPE, "ROCK");
            pokemonTypeValues[12].put(PokemonType.COLUMN_POKEMON_TYPE, "GHOST");
            pokemonTypeValues[13].put(PokemonType.COLUMN_POKEMON_TYPE, "DRAGON");
            pokemonTypeValues[14].put(PokemonType.COLUMN_POKEMON_TYPE, "DARK");
            pokemonTypeValues[15].put(PokemonType.COLUMN_POKEMON_TYPE, "STEEL");
            pokemonTypeValues[16].put(PokemonType.COLUMN_POKEMON_TYPE, "POISON");

            int count = context.getContentResolver().bulkInsert(PokemonType.CONTENT_URI,pokemonTypeValues);

            if(count < 0){
                Log.e(LOG_TAG, "Error in inserting pokemon types");
            }
        }

        if(pokemonCursor.getCount() < 0){

        }

    }

    public static long insertUser(Context context, ContentValues contentValues){
        Uri insertUri = context.getContentResolver().insert(Users.CONTENT_URI, contentValues);

        long retVal =  ContentUris.parseId(insertUri);

        return retVal;
    }

}
