package com.sharearide.research.jnapor.pokedex.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by jnapor on 8/23/2016.
 */
public class TestUriMatcher extends AndroidTestCase{
    private static final String POKEMON_TYPE = "Grass";
    private static final String POKEMON_NAME = "Balbasaur";
    private static final String USER_NAME = TestUtilities.TEST_USERNAME;
    private static final String PASSWORD = TestUtilities.TEST_PASSWORD;

    private static final Uri TEST_USER_DIR = PokedexContract.Users.CONTENT_URI;

    private static final Uri TEST_USER_WITH_USERNAME = PokedexContract.Users.buildUsersWithUserName(USER_NAME);

    private static final Uri TEST_USERNAME_WITH_USERNAME_AND_PASSWORD = PokedexContract
            .Users.buildUserLogin(USER_NAME, PASSWORD);

    private static final Uri TEST_POKEMON_DIR = PokedexContract.Pokemon.CONTENT_URI;

    private static final Uri TEST_POKEMON_WITH_TYPE = PokedexContract.
            Pokemon.buildPokemonPokeType(POKEMON_TYPE);

    private static final Uri TEST_POKEMON_WITH_TYPE_AND_NAME = PokedexContract.
            Pokemon.buildPokemonPokeTypeWithName(POKEMON_TYPE, POKEMON_NAME);

    private static final Uri TEST_POKEMONTYPE_DIR = PokedexContract.PokemonType.CONTENT_URI;

    public void testUriMatcher(){
        UriMatcher uriMatcher = PokemonProvider.buildUriMatcher();

        assertEquals("Error: The POKEMON URI was matched incorrectly.",
                uriMatcher.match(TEST_POKEMON_DIR), PokemonProvider.POKEMON);

        assertEquals("Error: The POKEMON WITH POKEMON TYPE URI was matched incorrectly.",
                uriMatcher.match(TEST_POKEMON_WITH_TYPE), PokemonProvider.POKEMON_WITH_TYPE);

        assertEquals("Error: The POKEMON WITH POKEMON TYPE AND NAME URI was matched incorrectly",
                uriMatcher.match(TEST_POKEMON_WITH_TYPE_AND_NAME), PokemonProvider.POKEMON_WITH_TYPE_AND_NAME);

        assertEquals("Error: The POKEMON TYPE URI was matched incorrectly",
                uriMatcher.match(TEST_POKEMONTYPE_DIR), PokemonProvider.POKEMON_TYPE);

        assertEquals("Error: The USER URI was matched incorrectly.",
                uriMatcher.match(TEST_USER_DIR), PokemonProvider.USERS);

        assertEquals("Error: The USER WITH USERNAME URI was matched incorrectly.",
                uriMatcher.match(TEST_USER_WITH_USERNAME), PokemonProvider.USERS_WITH_USERNAME);

        assertEquals("Error: The USER WITH USERNAME AND PASSWORD URI was matched incorrectly.",
                uriMatcher.match(TEST_USERNAME_WITH_USERNAME_AND_PASSWORD), PokemonProvider.USERS_WTIH_USERNAME_AND_PASSWORD);


    }
}
