package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jnapor on 8/22/2016.
 */
public class PokedexContract {
    public static final String CONTENT_AUTHORITY = "com.sharearide.research.jnapor.pokedex";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_POKEMONTYPE = "pokemontype";

    public static final String PATH_POKEMON = "pokemon";

    public static final class PokemonType implements BaseColumns{
        public static final String TABLE_NAME = "pokemontype";

        public static final String COLUMN_POKEMON_TYPE = "pokemon_type";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(PATH_POKEMONTYPE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_POKEMONTYPE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_POKEMONTYPE;

        public static Uri buildPokemonTypeUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Pokemon implements BaseColumns{
        public static final String TABLE_NAME = "pokemon";

        public static final String COLUMN_POKEMON_NAME = "pokemon_name";

        public static final String COLUMN_POKEMON_TYPE_ID = "pokemontype_id";

        public static final String COLUMN_POKEMON_DESC = "pokemon_desc";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(PATH_POKEMON).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_POKEMON;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_POKEMON;

        public static Uri buildPokemonUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri builPokemonPokeType(String type){
            return null;
        }

        public static Uri buildPokemonPokeTypeWithName(String type, String name){
            return CONTENT_URI.buildUpon().appendPath(type).appendPath(name).build();
        }

        public static String getPokemonTypeFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }

        public static String getPokemonNameFromUri(Uri uri){
            return uri.getPathSegments().get(2);
        }
    }

}
