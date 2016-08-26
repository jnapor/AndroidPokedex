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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FirstPokemonFragment())
                .commit();

    }
}
