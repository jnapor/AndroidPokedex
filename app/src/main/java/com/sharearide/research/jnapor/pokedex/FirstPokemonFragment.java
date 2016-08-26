package com.sharearide.research.jnapor.pokedex;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPokemonFragment extends Fragment {

    public FirstPokemonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_pokemon_info, container, false);

        int id = getArguments().getInt("pokemon_id");

        Cursor cursor = DatabaseManipulator.getPokemonById(getActivity(), id);

        if(cursor.moveToFirst()){

            int pokemonId = cursor.getInt(cursor.getColumnIndex("_id"));
            int imageResourceId = DatabaseManipulator.getImageFromPokemonId(pokemonId);

            RelativeLayout firstRelativeLayout = (RelativeLayout) rootView
                    .findViewById(R.id.pokemon_image_parentlayout);
            ImageView pokemonImage = (ImageView) firstRelativeLayout.findViewById(R.id.pokemon_image);
            pokemonImage.setImageResource(imageResourceId);

            RelativeLayout secondRelativeLayout = (RelativeLayout) rootView
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
            Log.e("FIRST FRAGMENT", "CURSOR IS EMPTY");
        }

        cursor.close();
        return rootView;
    }
}
