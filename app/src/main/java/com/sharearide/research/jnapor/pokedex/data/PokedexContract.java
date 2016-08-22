package com.sharearide.research.jnapor.pokedex.data;

import android.provider.BaseColumns;

/**
 * Created by jnapor on 8/22/2016.
 */
public class PokedexContract {

    public static final class PokemonType implements BaseColumns{
        public static final String TABLE_NAME = "pokemontype";

        public static final String COLUMN_POKEMON_TYPE = "pokemon_type";
    }

    public static final class Pokemon implements BaseColumns{
        public static final String TABLE_NAME = "pokemon";

        public static final String COLUMN_POKEMON_NAME = "pokemon_name";

        public static final String COLUMN_POKEMON_TYPE_ID = "pokemontype_id";

        public static final String COLUMN_POKEMON_DESC = "pokemon_desc";
    }

}
