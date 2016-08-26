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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = DatabaseManipulator.getPokemonTypeList(this);


        ArrayList<PokedexUtilityModel> pokedexUtilityModels = new ArrayList<PokedexUtilityModel>();
        if(cursor.moveToFirst()){
            do{
                String pokemonType = cursor.getString(cursor.getColumnIndex("pokemon_type"));
                int rowId = cursor.getInt(cursor.getColumnIndex("_id"));

                int imageResourceId = DatabaseManipulator.getImageFromPokemonTypeId(rowId);

                int colorResouceId = DatabaseManipulator.getColorFromPokemonTypeId(rowId);

                PokedexUtilityModel pokedexUtilityModel = new PokedexUtilityModel(pokemonType, imageResourceId, colorResouceId);
                pokedexUtilityModels.add(pokedexUtilityModel);

            }while (cursor.moveToNext());
        }

        PokedexArrayAdapter pokedexArrayAdapter = new PokedexArrayAdapter(this, pokedexUtilityModels);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(pokedexArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(view.getContext(), ViewPagerActivity.class);
                intent.putExtra("pokemon_type",String.valueOf(position+1));
                startActivity(intent);
            }
        });
        cursor.close();
    }

}
