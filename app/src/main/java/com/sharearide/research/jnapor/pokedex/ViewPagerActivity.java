package com.sharearide.research.jnapor.pokedex;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        Intent intent = getIntent();
        if(intent != null){
            String pokemontype = intent.getStringExtra("pokemon_type");
            int type = Integer.parseInt(pokemontype);
            Log.e("VIEW PAGER", " POKEMON TYPE IS "+pokemontype);

            int[] pokemonId = new int[2];
            String[] pokemonName = new String[2];
            int x = 0;
            Cursor cursor = DatabaseManipulator.getPokemonListByType(this, type);

            if(cursor.moveToFirst()){
                do{
                    pokemonId[x] = cursor.getInt(cursor.getColumnIndex("_id"));
                    pokemonName[x] = cursor.getString(cursor.getColumnIndex("pokemon_name"));
                    x++;
                }while (cursor.moveToNext());
            }

            cursor.close();

            ViewPager viewPager = (ViewPager) findViewById(R.id.view_page);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), pokemonId, pokemonName);

            viewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            tabLayout.setupWithViewPager(viewPager);

        }else{
            Log.e("VIEW PAGER", " INTENT IS NULL");
        }
    }
}
