package com.sharearide.research.jnapor.pokedex.data;

import android.test.AndroidTestCase;

/**
 * Created by jnapor on 8/22/2016.
 */
public class TestDB extends AndroidTestCase{
    public static final String LOG_TAG = TestDB.class.getSimpleName();

    void deleteTheDatabase(){
        mContext.deleteDatabase(PokemonDBHelper.DATABASE_NAME);
    }

    public void setUp(){
        deleteTheDatabase();
    }
}
