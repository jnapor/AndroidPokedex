package com.sharearide.research.jnapor.pokedex;

/**
 * Created by jnapor on 8/24/2016.
 */
public class PokedexUtilityModel {
    private String pokedexUtilityWord;
    private int pokedexUtilityResourceImageId;
    private int pokedexUtilityResourceColorId;

    public PokedexUtilityModel(String pokedexUtilityWord, int pokedexUtilityResourceImageId, int pokedexUtilityResourceColorId){
        this.pokedexUtilityWord = pokedexUtilityWord;
        this.pokedexUtilityResourceImageId = pokedexUtilityResourceImageId;
        this.pokedexUtilityResourceColorId = pokedexUtilityResourceColorId;
    }

    public String getPokedexUtilityWord() {
        return pokedexUtilityWord;
    }

    public int getPokedexUtilityResourceImageId() {
        return pokedexUtilityResourceImageId;
    }

    public int getPokedexUtilityResourceColorId(){
        return pokedexUtilityResourceColorId;
    }
}
