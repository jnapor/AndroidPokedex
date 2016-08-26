package com.sharearide.research.jnapor.pokedex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jnapor on 8/26/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int[] pokemonId;
    private String [] pokemonName;


    public ViewPagerAdapter(FragmentManager fm,int[] pokemonId, String[] pokemonName) {
        super(fm);
        this.pokemonId = pokemonId;
        this.pokemonName = pokemonName;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        if(position == 0){
            bundle.putInt("pokemon_id",pokemonId[0]);
            FirstPokemonFragment firstPokemonFragment = new FirstPokemonFragment();
            firstPokemonFragment.setArguments(bundle);
            return firstPokemonFragment;
        }else{
            bundle.putInt("pokemon_id",pokemonId[1]);
            SecondPokemonFragment secondPokemonFragment = new SecondPokemonFragment();
            secondPokemonFragment.setArguments(bundle);
            return secondPokemonFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return pokemonName[0];
        }else{
            return  pokemonName[1];
        }
    }
}
