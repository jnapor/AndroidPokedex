package com.sharearide.research.jnapor.pokedex;

/**
 * Created by jnapor on 8/24/2016.
 */
public class PokedexUtilityModel {
    private String pokedexUtilityWord;
    private int pokedexUtilityResourceImageId;

    public PokedexUtilityModel(String pokedexUtilityWord, int pokedexUtilityResourceImageId){
        this.pokedexUtilityWord = pokedexUtilityWord;
        this.pokedexUtilityResourceImageId = pokedexUtilityResourceImageId;
    }

    public String getPokedexUtilityWord() {
        return pokedexUtilityWord;
    }

    public int getPokedexUtilityResourceImageId() {
        return pokedexUtilityResourceImageId;
    }
}
