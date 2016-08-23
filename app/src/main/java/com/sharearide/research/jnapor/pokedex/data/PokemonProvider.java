package com.sharearide.research.jnapor.pokedex.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by jnapor on 8/23/2016.
 */
public class PokemonProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher = buildUrMatcher();
    private PokemonDBHelper mPokedexHelper;

    static final int POKEMON = 100;
    static final int POKEMON_WITH_TYPE = 101;
    static final int POKEMON_WITH_TYPE_AND_NAME = 102;
    static final int POKEMON_TYPE = 300;

    private static final SQLiteQueryBuilder mPokemonByTypeQueryBuilder;

    static{
        mPokemonByTypeQueryBuilder = new SQLiteQueryBuilder();

        mPokemonByTypeQueryBuilder.setTables(
                PokedexContract.Pokemon.TABLE_NAME + " INNER JOIN " +
                        PokedexContract.PokemonType.TABLE_NAME +
                        " ON " + PokedexContract.Pokemon.TABLE_NAME +
                        "." + PokedexContract.Pokemon.COLUMN_POKEMON_TYPE_ID +
                        " = " + PokedexContract.PokemonType.TABLE_NAME +
                        "." + PokedexContract.PokemonType._ID
        );
    }

    private static final String mPokemonType = PokedexContract.PokemonType.TABLE_NAME + "." +
            PokedexContract.PokemonType.COLUMN_POKEMON_TYPE + " = ?";

    private static final String mPokemonTypeAndName = PokedexContract.PokemonType.TABLE_NAME + "." +
            PokedexContract.PokemonType.COLUMN_POKEMON_TYPE + " = ? AND " +
            PokedexContract.Pokemon.COLUMN_POKEMON_NAME + " = ? ";

    private Cursor getPokemonByType(Uri uri, String[] projection, String order){
        String type = PokedexContract.Pokemon.getPokemonTypeFromUri(uri);


        return mPokemonByTypeQueryBuilder.query(
                mPokedexHelper.getReadableDatabase(),
                projection,
                mPokemonType,
                new String[]{type},
                null,
                null,
                order
        );
    }

    private Cursor getPokemonByTypeAndName(Uri uri, String[] projection, String order){
        String type = PokedexContract.Pokemon.getPokemonTypeFromUri(uri);
        String name = PokedexContract.Pokemon.getPokemonTypeFromUri(uri);

        return mPokemonByTypeQueryBuilder.query(
                mPokedexHelper.getReadableDatabase(),
                projection,
                mPokemonTypeAndName,
                new String[]{type, name},
                null,
                null,
                order
        );
    }

    static UriMatcher buildUrMatcher(){
        return null;
    }


    @Override
    public boolean onCreate() {
        mPokedexHelper = new PokemonDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] args, String order) {
        Cursor retCursor;

        switch (mUriMatcher.match(uri)){
            case POKEMON_WITH_TYPE_AND_NAME:{
                retCursor = getPokemonByTypeAndName(uri, projection, order);
                break;
            }
            case POKEMON_WITH_TYPE:{
                retCursor = getPokemonByType(uri, projection, order);
                break;
            }
            case POKEMON:{
                retCursor = null;
                break;
            }
            case POKEMON_TYPE:{
                retCursor = null;
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+ uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);

        switch (match){
//            case POKEMON_WITH_TYPE_AND_NAME :
//            case POKEMON_WITH_TYPE :
            case POKEMON:
                return PokedexContract.Pokemon.CONTENT_TYPE;
            case POKEMON_TYPE:
                return PokedexContract.PokemonType.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase sqLiteDatabase = mPokedexHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        Uri retUri;

        switch (match){
            case POKEMON:{
                long _id = sqLiteDatabase.insert(PokedexContract.Pokemon.TABLE_NAME, null, contentValues);
                if(_id > 0){
                    retUri = PokedexContract.Pokemon.buildPokemonUri(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into "+uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase sqLiteDatabase = mPokedexHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        Uri retUri;
        int count = 0;
        switch (match) {
            case POKEMON:{
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        long _id = sqLiteDatabase.insert(PokedexContract.Pokemon.TABLE_NAME, null, value);
                        if (_id != -1) {
                            count++;
                        }
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }

                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        mPokedexHelper.close();
        super.shutdown();
    }
}


