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
import android.widget.ListView;
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

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String pokemontype = intent.getStringExtra("pokemon_type");
            final int type = Integer.parseInt(pokemontype);
            int color = DatabaseManipulator.getColorFromPokemonTypeId(type);
            final Cursor cursor = DatabaseManipulator.getPokemonListByType(this.getContext(), type);

            if (cursor.moveToFirst()) {
                ArrayList<PokedexUtilityModel> arrayList = new ArrayList<PokedexUtilityModel>();
                final int[] pokemonId = new int[2];
                int x = 0;

                do {
                    Log.e("TEST", "" + cursor.getInt(cursor.getColumnIndex("_id")) + ", "
                            + cursor.getString(cursor.getColumnIndex("pokemon_name"))
                            + cursor.getString(cursor.getColumnIndex("pokemon_height"))
                            + cursor.getString(cursor.getColumnIndex("pokemon_weight"))
                            + cursor.getString(cursor.getColumnIndex("ability")));
                    pokemonId[x] = cursor.getInt(cursor.getColumnIndex("_id"));

                    int resourceID = DatabaseManipulator.getImageFromPokemonId(pokemonId[x]);

                    String pokemonName = cursor.getString(cursor.getColumnIndex("pokemon_name"));
                    PokedexUtilityModel model = new PokedexUtilityModel(pokemonName, resourceID, color);

                    arrayList.add(model);
                    x++;
                } while (cursor.moveToNext());

                PokedexArrayAdapter adapter = new PokedexArrayAdapter(this.getContext(), arrayList);
                ListView listView = (ListView) rootView.findViewById(R.id.listView);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent anotherIntent = new Intent(view.getContext(), PokemonInfoActivity.class);
                        anotherIntent.putExtra("pokemon_id", String.valueOf(pokemonId[position]));
                        Log.e("POKEMON TYPE LIST", "putting pokemon ID" + pokemonId[position]);
                        startActivity(anotherIntent);
                    }
                });
            }
            cursor.close();
        }

        return rootView;
    }
}
