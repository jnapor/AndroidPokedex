package com.sharearide.research.jnapor.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jnapor on 8/24/2016.
 */
public class PokedexArrayAdapter extends ArrayAdapter<PokedexUtilityModel> {

    public PokedexArrayAdapter(Context context, ArrayList<PokedexUtilityModel> pokedexUtilityModels) {
        super(context, 0, pokedexUtilityModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;

        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.pokemon_types_row, parent, false);
        }

        PokedexUtilityModel model = getItem(position);

        TextView textView = (TextView) listView.findViewById(R.id.pokemon_types_label);
        textView.setText(model.getPokedexUtilityWord());

        textView.setBackgroundResource(model.getPokedexUtilityResourceColorId());

        ImageView imageView = (ImageView) listView.findViewById(R.id.pokemon_types);
        imageView.setImageResource(model.getPokedexUtilityResourceImageId());

        return listView;
    }
}


