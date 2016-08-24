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

        int val =  cursor.getCount();
        cursor.close();
        return val;

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


        if(usersCursor.getCount() <= 0){
            ContentValues userValues = new ContentValues();
            userValues.put(Users.COLUMN_USERNAME, "admin");
            userValues.put(Users.COLUMN_PASSWORD, "admin");

            long userRowId = insertUser(context, userValues);

            if(userRowId < 0){
                Log.e(LOG_TAG, "Error in inserting new user");
            }
        }else{
            Log.e(LOG_TAG, "USER TABLE IS ALREADY POPULATED");
        }

        if(pokemonTypeCursor.getCount() <= 0){
            ContentValues[] pokemonTypeValues = new ContentValues[17];
            for (int count = 0; count < 17; count++) {
                pokemonTypeValues[count] = new ContentValues();
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


            for (int count = 0; count < 17; count++){
                long pokemonTypeId = insertPokemonType(context, pokemonTypeValues[count]);
                insertPokemonByType(context,pokemonTypeId);
            }
        }else{
            Log.e(LOG_TAG, "POKEMONTYPE TABLE IS ALREADY POPULATED");
        }

        usersCursor.close();
        pokemonTypeCursor.close();
    }

    private static void insertPokemonByType(Context context, long pokemonTypeId) {
        ContentValues[] contentValues = new ContentValues[2];
        contentValues[0] = new ContentValues();
        contentValues[1] = new ContentValues();
        if (pokemonTypeId == 1) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Jigglypuff");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Balloon Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Ratata");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Mouse Pokemon");
        } else if (pokemonTypeId == 2) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Charizard");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Flame Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Delphox");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Fox Pokemon");
        } else if (pokemonTypeId == 3) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Squirtle");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Turtle Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Corphish");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Crustacean Pokemon");
        } else if (pokemonTypeId == 4) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Pikachu");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Mouse Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Manectric");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Dog Pokemon");
        } else if (pokemonTypeId == 5) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Jigglypuff");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Balloon Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Ratata");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Mouse Pokemon");
        } else if (pokemonTypeId == 6) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Snorunt");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Snow hat Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Delibird");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Delivery Pokemon");
        } else if (pokemonTypeId == 7) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Hitmonchan");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Punching Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Snorlax");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Sleeping Pokemon");
        } else if (pokemonTypeId == 8) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Steelix");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Iron Snake Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Wooper");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Water Fish Pokemon");
        }  else if (pokemonTypeId == 9) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Pidgeot");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Bird Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Lugia");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Diving Pokemon");
        }  else if (pokemonTypeId == 10) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Alakazam");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Psi Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Mew");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "New Species Pokemon");
        } else if (pokemonTypeId == 11) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Beedrill");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Poison Bee Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Ninjask");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Ninja Pokemon");
        } else if (pokemonTypeId == 12) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Onix");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Rock Snake Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Golem");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Megaton Pokemon");
        } else if (pokemonTypeId == 13) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Gastly");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Gas Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Giratina");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Renegade Pokemon");
        } else if (pokemonTypeId == 14) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Dragonite");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Dragon Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Rayquaza");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Sky High Pokemon");
        } else if (pokemonTypeId == 15) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Darkrai");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Pitch Black Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Weavile");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Sharp Claw Pokemon");
        } else if (pokemonTypeId == 16) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Lairon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Iron Armor Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Registeel");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Iron Pokemon");
        } else if (pokemonTypeId == 17) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Dragalge");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Mock Kelp Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Toxicroak");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Toxic Mouth Pokemon");
        }

        int count = context.getContentResolver().bulkInsert(Pokemon.CONTENT_URI, contentValues);

        if(count < 0){
            Log.e(LOG_TAG, "Error in bulk insertion");
        }
    }

    public static long insertUser(Context context, ContentValues contentValues){
        Uri insertUri = context.getContentResolver().insert(Users.CONTENT_URI, contentValues);

        long retVal =  ContentUris.parseId(insertUri);

        return retVal;
    }

    public static Cursor getPokemonTypeList(Context context){
        Cursor cursor = context.getContentResolver().query(
                PokemonType.CONTENT_URI, null, null, null, null
        );

        return cursor;
    }

    public static Cursor getPokemonList(Context context){
        Cursor cursor = context.getContentResolver().query(Pokemon.CONTENT_URI, null, null, null ,null);

        return cursor;
    }

    public static long insertPokemonType(Context context, ContentValues contentValues){
        Uri insertUri = context.getContentResolver().insert(PokemonType.CONTENT_URI, contentValues);

        long retVal = ContentUris.parseId(insertUri);

        return retVal;
    }

    public static long insertPokemon(Context context, ContentValues contentValues){
        Uri insertUri = context.getContentResolver().insert(Pokemon.CONTENT_URI, contentValues);

        long retVal = ContentUris.parseId(insertUri);

        return retVal;
    }

}
