package com.sharearide.research.jnapor.pokedex;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;

public class PokemonInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);
        Intent intent = getIntent();
        if(intent != null){
            String pokemonNumber = intent.getStringExtra("pokemon_id");
            int id = Integer.parseInt(pokemonNumber);
            int imageResourceId = DatabaseManipulator.getImageFromPokemonId(id);

            Cursor cursor = DatabaseManipulator.getPokemonById(this, id);

            if(cursor.moveToFirst()){
                LinearLayout parentLayout = (LinearLayout) findViewById(R.id.pokemon_info_mainLayout);
                RelativeLayout firstRelativeLayout = (RelativeLayout) parentLayout
                        .findViewById(R.id.pokemon_image_parentlayout);
                ImageView pokemonImage = (ImageView) firstRelativeLayout.findViewById(R.id.pokemon_image);
                pokemonImage.setImageResource(imageResourceId);

                RelativeLayout secondRelativeLayout = (RelativeLayout) parentLayout
                        .findViewById(R.id.pokemon_info_parentlayout);

                LinearLayout pokemonName = (LinearLayout) secondRelativeLayout
                        .findViewById(R.id.pokemon_name);

                TextView pokemonNameData = (TextView) pokemonName.findViewById(R.id.pokemon_name_data);
                pokemonNameData.setText(cursor.getString(cursor.getColumnIndex("pokemon_name")));

                LinearLayout pokemonDesc = (LinearLayout) secondRelativeLayout
                        .findViewById(R.id.pokemon_desc);

                TextView pokemonDescData = (TextView) pokemonDesc.findViewById(R.id.pokemon_desc_data);
                pokemonDescData.setText(cursor.getString(cursor.getColumnIndex("pokemon_desc")));

                LinearLayout pokemonHeight = (LinearLayout) secondRelativeLayout
                        .findViewById(R.id.pokemon_height);

                TextView pokemonHeightData = (TextView) pokemonHeight.findViewById(R.id.pokemon_height_data);
                pokemonHeightData.setText(cursor.getString(cursor.getColumnIndex("pokemon_height")));

                LinearLayout pokemonWeight = (LinearLayout) secondRelativeLayout
                        .findViewById(R.id.pokemon_weight);

                TextView pokemonWeightData = (TextView) pokemonWeight.findViewById(R.id.pokemon_weight_data);
                pokemonWeightData.setText(cursor.getString(cursor.getColumnIndex("pokemon_weight")));

                LinearLayout pokemonAbility = (LinearLayout) secondRelativeLayout
                        .findViewById(R.id.pokemon_ability);

                TextView pokemonAbilityData = (TextView) pokemonAbility.findViewById(R.id.pokemon_ability_data);
                pokemonAbilityData.setText(cursor.getString(cursor.getColumnIndex("ability")));

            }else{
                Log.e("POKEMON INFO ACTIVITY", "EMPTY CURSOR");
            }

            cursor.close();
        }else{
            Log.e("POKEMON INFO ACTIVITY", "EMPTY INTENT");
        }
    }
}