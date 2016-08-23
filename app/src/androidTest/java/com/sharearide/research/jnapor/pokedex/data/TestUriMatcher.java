package com.sharearide.research.jnapor.pokedex.data;

import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by jnapor on 8/23/2016.
 */
public class TestUriMatcher extends AndroidTestCase{
    private static final String POKEMON_TYPE = "Grass";
    private static final String POKEMON_NAME = "Balbasaur";


    private static final Uri TEST_POKEMON_DIR = PokedexContract.Pokemon.CONTENT_URI;

    private static final Uri TEST_POKEMON_WITH_TYPE = PokedexContract.
            Pokemon.buildPokemonPokeType(POKEMON_TYPE);

    private static final Uri TEST_POKEMON_WITH_TYPE_AND_NAME = PokedexContract.
            Pokemon.buildPokemonPokeTypeWithName(POKEMON_TYPE, POKEMON_NAME);

    private static final Uri TEST_POKEMONTYPE_DIR = PokedexContract.PokemonType.CONTENT_URI;
}
