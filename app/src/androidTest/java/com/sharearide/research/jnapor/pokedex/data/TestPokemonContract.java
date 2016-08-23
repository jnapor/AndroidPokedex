package com.sharearide.research.jnapor.pokedex.data;

import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by jnapor on 8/23/2016.
 */
public class TestPokemonContract extends AndroidTestCase {
    private static final String TEST_POKEMON_POKETYPE = "/Grass";
    private static final String TEST_POKEMON_NAME = "Balbasaur";

    public void testBuildPokemonAndType(){
        Uri pokemonAndtypeUri = PokedexContract.Pokemon.buildPokemonPokeType(TEST_POKEMON_POKETYPE);
        assertNotNull("Error: Null Uri returned. You must fill-in buildPokemonAndType" +
            " in PokemonContract.", pokemonAndtypeUri);

        assertEquals("Error: Pokemon type not properly appended to the end of the Uri",
                TEST_POKEMON_POKETYPE,pokemonAndtypeUri.getLastPathSegment());

        assertEquals("Error: Pokemon Type Uri doesn't match our expected result",
                pokemonAndtypeUri.toString(), "content://com.sharearide.research.jnapor.pokedex/pokemon/%2FGrass");
    }
}
