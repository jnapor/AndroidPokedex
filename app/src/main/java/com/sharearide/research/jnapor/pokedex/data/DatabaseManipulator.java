package com.sharearide.research.jnapor.pokedex.data;

import android.content.Context;
import android.database.Cursor;

import com.sharearide.research.jnapor.pokedex.data.PokedexContract.*;
/**
 * Created by jnapor on 8/24/2016.
 */
public class DatabaseManipulator {

    public static int login(Context context, String loginusername, String loginpassword){
        Cursor cursor = context.getContentResolver().query(Users.CONTENT_URI,
                null,
                Users.COLUMN_USERNAME  + " = ? AND " + Users.COLUMN_PASSWORD + "= ?",
                new String[]{loginusername, loginpassword},
                null,
                null
        );

        return cursor.getCount();
    }
}
