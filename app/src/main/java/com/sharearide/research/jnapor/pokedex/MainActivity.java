package com.sharearide.research.jnapor.pokedex;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = DatabaseManipulator.getPokemonTypeList(this);
        Cursor pokemon = DatabaseManipulator.getPokemonList(this);
        TextView textView = (TextView) findViewById(R.id.test);
        Log.e("TESTING", "Count is : "+cursor.getCount() +": "+pokemon.getCount());
        if(cursor.getCount() > 0 && pokemon.getCount() > 0)
            textView.setText(""+cursor.getCount() + " ,"+pokemon.getCount());
        else
            textView.setText("AMAW");
        if (cursor.moveToFirst()) {
            int x = 0;
            do {
                Log.e("TESTING2", "Iteration "+x+":"+cursor.getString(cursor.getColumnIndex("pokemon_type")));
                x++;
            }while (cursor.moveToNext());
        }

        if (pokemon.moveToFirst()){
            int x = 0;
            do{
                Log.e("TESTING3", "Iteration "+x+":"+pokemon.getString(pokemon.getColumnIndex("pokemon_name"))
                        +", "+pokemon.getInt(pokemon.getColumnIndex("pokemontype_id")) + ", "
                        +pokemon.getString(pokemon.getColumnIndex("pokemon_desc")));
                x++;
            }while (pokemon.moveToNext());
        }
    }
}
