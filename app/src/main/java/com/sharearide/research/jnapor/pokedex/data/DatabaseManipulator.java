package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.sharearide.research.jnapor.pokedex.R;
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
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.51");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"5.5");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Friend Guard");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Ratata");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Mouse Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.19");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"0.30");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Hustle");
        } else if (pokemonTypeId == 2) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Charizard");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Flame Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.70");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"90.5");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Solar Power");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Delphox");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Fox Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.5");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"39.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Magician");
        } else if (pokemonTypeId == 3) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Squirtle");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Turtle Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.07");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"0.51");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Rain Dish");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Corphish");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Ruffian Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.61");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"11.5");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Adaptability");
        } else if (pokemonTypeId == 4) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Pikachu");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Mouse Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.41");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"6.0");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Lightning Rod");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Manectric");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Dog Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.5");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"40.2");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Minus");
        } else if (pokemonTypeId == 5) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Leafeon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Verdant Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.99");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"25.5");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Chlorophyll");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Celebi");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Time Travel Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.61");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"5.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Natural Cure");
        } else if (pokemonTypeId == 6) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Snorunt");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Snow hat Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.71");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"16.8");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Moody");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Delibird");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Delivery Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.89");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"16.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Insomnia");
        } else if (pokemonTypeId == 7) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Hitmonchan");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Punching Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.40");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"50.2");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Inner Focus");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Snorlax");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Sleeping Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"2.11");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"460.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Gluttony");
        } else if (pokemonTypeId == 8) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Steelix");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Iron Snake Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"9.19");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"400.0");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Sheer Force");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Wooper");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Water Fish Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.41");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"8.5");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Unaware");
        }  else if (pokemonTypeId == 9) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Pidgeot");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Bird Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.50");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"39.5");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Big Pecks");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Lugia");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Diving Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"5.21");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"216.");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Multiscale");
        }  else if (pokemonTypeId == 10) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Alakazam");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Psi Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.50");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"48.0");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Magic Guard");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Mew");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "New Species Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.41");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"4.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Synchronize");
        } else if (pokemonTypeId == 11) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Beedrill");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Poison Bee Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.99");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"29.5");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Sniper");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Ninjask");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Ninja Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.79");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"12.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Infiltrator");
        } else if (pokemonTypeId == 12) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Onix");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Rock Snake Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"8.79");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"210.0");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Weak Armor");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Golem");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Megaton Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.40");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"300.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Sand Veil");
        } else if (pokemonTypeId == 13) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Gastly");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Gas Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.30");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"0.1");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Levitate");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Giratina");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Renegade Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"4.50");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"750.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Telepathy");
        } else if (pokemonTypeId == 14) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Dragonite");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Dragon Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"2.21");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"210.0");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Multiscale");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Rayquaza");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Sky High Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"7.01");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"206.5");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Air Lock");
        } else if (pokemonTypeId == 15) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Darkrai");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Pitch Black Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.50");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"50.5");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Bad dreams");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Weavile");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Sharp Claw Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.09");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"34.0");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"PickPocket");
        } else if (pokemonTypeId == 16) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Lairon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Iron Armor Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"0.89");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"120.0");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Heavy Metal");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Registeel");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Iron Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.91");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"205.5");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Light Metal");
        } else if (pokemonTypeId == 17) {
            contentValues[0].put(Pokemon.COLUMN_POKEMON_NAME, "Dragalge");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[0].put(Pokemon.COLUMN_POKEMON_DESC, "Mock Kelp Pokemon");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.80");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_WEIGHT,"81.5");
            contentValues[0].put(Pokemon.COLUMN_POKEMON_ABILITY,"Adaptability");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_NAME, "Toxicroak");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_TYPE_ID, pokemonTypeId);
            contentValues[1].put(Pokemon.COLUMN_POKEMON_DESC, "Toxic Mouth Pokemon");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_HEIGHT,"1.30");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_WEIGHT,"44.4");
            contentValues[1].put(Pokemon.COLUMN_POKEMON_ABILITY,"Poison Touch");
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

    public static Cursor getPokemonListByType(Context context, int type){
        Cursor cursor = context.getContentResolver().query(Pokemon.CONTENT_URI,
                null,
                Pokemon.COLUMN_POKEMON_TYPE_ID + " = ?",
                new String[]{String.valueOf(type)},
                null
                );

        return cursor;
    }

    public static Cursor getPokemonById(Context context, int id){
        Cursor cursor = context.getContentResolver().query(Pokemon.CONTENT_URI,
                null,
                Pokemon._ID + " = ?",
                new String[]{String.valueOf(id)},
                null
        );
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

    public static int getImageFromPokemonTypeId(int id){
        int retVal = 0;
        if (id == 1) {
            retVal = R.drawable.normal_400px;
        } else if (id == 2){
            retVal = R.drawable.fire_400px;
        } else if (id == 3){
            retVal = R.drawable.water_400px;
        } else if (id == 4){
            retVal = R.drawable.lightning_400px;
        } else if (id == 5){
            retVal = R.drawable.leaf_400px;
        } else if (id == 6){
            retVal = R.drawable.ice_400px;
        } else if (id == 7){
            retVal = R.drawable.fightingl_400px;
        } else if (id == 8){
            retVal = R.drawable.ground_400px;
        } else if (id == 9){
            retVal = R.drawable.flying_400px;
        } else if (id == 10){
            retVal = R.drawable.psychic_400px;
        } else if (id == 11){
            retVal = R.drawable.bug_400px;
        } else if (id == 12){
            retVal = R.drawable.rock_400px;
        } else if (id == 13){
            retVal = R.drawable.ghost_400px;
        } else if (id == 14){
            retVal = R.drawable.dragon_400px;
        } else if (id == 15){
            retVal = R.drawable.dark_400px;
        } else if (id == 16){
            retVal = R.drawable.steel_400px;
        } else if (id == 17){
            retVal = R.drawable.poison_400px;
        }

        return retVal;
    }

    public static int getColorFromPokemonTypeId(int id){
        int retVal = 0;
        if (id == 1) {
            retVal = R.color.normal;
        } else if (id == 2){
            retVal = R.color.fire;
        } else if (id == 3){
            retVal = R.color.water;
        } else if (id == 4){
            retVal = R.color.electric;
        } else if (id == 5){
            retVal = R.color.grass;
        } else if (id == 6){
            retVal = R.color.ice;
        } else if (id == 7){
            retVal = R.color.fighting;
        } else if (id == 8){
            retVal = R.color.ground;
        } else if (id == 9){
            retVal = R.color.flying;
        } else if (id == 10){
            retVal = R.color.psychic;
        } else if (id == 11){
            retVal = R.color.bug;
        } else if (id == 12){
            retVal = R.color.rock;
        } else if (id == 13){
            retVal = R.color.ghost;
        } else if (id == 14){
            retVal = R.color.dragon;
        } else if (id == 15){
            retVal = R.color.dark;
        } else if (id == 16){
            retVal = R.color.steel;
        } else if (id == 17){
            retVal = R.color.poison;
        }
        return retVal;
    }

    public static int getImageFromPokemonId(int id){
        int retVal = 0;
        if (id == 1) {
            retVal = R.drawable.jigglypuff;
        } else if (id == 2){
            retVal = R.drawable.rattata;
        } else if (id == 3){
            retVal = R.drawable.charizard;
        } else if (id == 4){
            retVal = R.drawable.delphox;
        } else if (id == 5){
            retVal = R.drawable.squirtle;
        } else if (id == 6){
            retVal = R.drawable.corphish;
        } else if (id == 7){
            retVal = R.drawable.pikachu;
        } else if (id == 8){
            retVal = R.drawable.manectric;
        } else if (id == 9){
            retVal = R.drawable.leafeon;
        } else if (id == 10){
            retVal = R.drawable.celebi;
        } else if (id == 11){
            retVal = R.drawable.snorunt;
        } else if (id == 12){
            retVal = R.drawable.delibird;
        } else if (id == 13){
            retVal = R.drawable.hitmonchan;
        } else if (id == 14){
            retVal = R.drawable.snorlax;
        } else if (id == 15){
            retVal = R.drawable.steelix;
        } else if (id == 16){
            retVal = R.drawable.wooper;
        } else if (id == 17){
            retVal = R.drawable.pidgeot;
        } else if (id == 18){
            retVal = R.drawable.lugia;
        } else if (id == 19){
            retVal = R.drawable.alakazam;
        } else if (id == 20){
            retVal = R.drawable.mew;
        } else if (id == 21){
            retVal = R.drawable.beedrill;
        } else if (id == 22){
            retVal = R.drawable.ninjask;
        } else if (id == 23){
            retVal = R.drawable.onix;
        } else if (id == 24){
            retVal = R.drawable.golem;
        } else if (id == 25){
            retVal = R.drawable.gastly;
        } else if (id == 26){
            retVal = R.drawable.giratina;
        } else if (id == 27){
            retVal = R.drawable.dragonite;
        } else if (id == 28){
            retVal = R.drawable.rayquaza;
        } else if (id == 29){
            retVal = R.drawable.darkrai;
        } else if (id == 30){
            retVal = R.drawable.weavile;
        } else if (id == 31){
            retVal = R.drawable.lairon;
        } else if (id == 32){
            retVal = R.drawable.registeel;
        } else if (id == 33){
            retVal = R.drawable.dragalge;
        }else if (id == 34){
            retVal = R.drawable.toxicroak;
        }


        return retVal;
    }

}
