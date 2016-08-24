package com.sharearide.research.jnapor.pokedex;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = DatabaseManipulator.getPokemonTypeList(this);
        Cursor pokemon = DatabaseManipulator.getPokemonList(this);

        Log.e("TESTING", "Count is : "+cursor.getCount() +": "+pokemon.getCount());

        ArrayList<PokedexUtilityModel> pokedexUtilityModels = new ArrayList<PokedexUtilityModel>();
        if(cursor.moveToFirst()){
            do{
                String pokemonType = cursor.getString(cursor.getColumnIndex("pokemon_type"));
                int rowId = cursor.getInt(cursor.getColumnIndex("_id"));

                int imageResourceId = getImageFromPokemonTypeId(rowId);

                PokedexUtilityModel pokedexUtilityModel = new PokedexUtilityModel(pokemonType, imageResourceId);
                pokedexUtilityModels.add(pokedexUtilityModel);

            }while (cursor.moveToNext());
        }

        PokedexArrayAdapter pokedexArrayAdapter = new PokedexArrayAdapter(this, pokedexUtilityModels);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(pokedexArrayAdapter);

        cursor.close();
//
//        if (cursor.moveToFirst()) {
//            int x = 0;
//            do {
//                Log.e("TESTING2", ""+cursor.getInt(cursor.getColumnIndex("_id"))+","+cursor.getString(cursor.getColumnIndex("pokemon_type")));
//                x++;
//            }while (cursor.moveToNext());
//        }

        if (pokemon.moveToFirst()){
            int x = 0;
            do{
                Log.e("TESTING3", ""+pokemon.getInt(pokemon.getColumnIndex("_id"))+pokemon.getString(pokemon.getColumnIndex("pokemon_name"))
                        +", "+pokemon.getInt(pokemon.getColumnIndex("pokemontype_id")) + ", "
                        +pokemon.getString(pokemon.getColumnIndex("pokemon_desc")));
                x++;
            }while (pokemon.moveToNext());
        }
        pokemon.close();
    }



    private int getImageFromPokemonTypeId(int id){
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
}
