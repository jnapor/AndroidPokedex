package com.sharearide.research.jnapor.pokedex;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;

import java.util.ArrayList;

public class PokemonTypeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent != null){
            String pokemontype = intent.getStringExtra("pokemon_type");
            int type = Integer.parseInt(pokemontype);
            Log.e("TYPE", pokemontype+" "+type);
            int color = DatabaseManipulator.getColorFromPokemonTypeId(type);
            final Cursor cursor = DatabaseManipulator.getPokemonListByType(this, type);

            if(cursor.moveToFirst()){
                ArrayList<PokedexUtilityModel> arrayList = new ArrayList<PokedexUtilityModel>();

                do{
                    Log.e("TEST", ""+cursor.getInt(cursor.getColumnIndex("_id")) + ", "+cursor.getString(cursor.getColumnIndex("pokemon_name")));
                    int pokemonId = cursor.getInt(cursor.getColumnIndex("_id"));

                    int resourceID = DatabaseManipulator.getImageFromPokemonId(pokemonId);

                    String pokemonName = cursor.getString(cursor.getColumnIndex("pokemon_name"));
                    PokedexUtilityModel model = new PokedexUtilityModel(pokemonName,resourceID, color);

                    arrayList.add(model);
                }while (cursor.moveToNext());

                PokedexArrayAdapter adapter = new PokedexArrayAdapter(this,arrayList);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }

        }
    }
}
