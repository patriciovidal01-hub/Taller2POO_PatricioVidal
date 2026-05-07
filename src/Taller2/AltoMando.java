package Taller2;

import java.util.ArrayList;

public class AltoMando {

	private String nombre; 
	private Boolean derrotado;
	private int num_mando;
	private ArrayList<Pokemon> pokemons_mando = new ArrayList<Pokemon>();
		
	
	public AltoMando(String nombre, Boolean derrotado, int num_mando) {
		this.nombre = nombre;
		this.derrotado = false;
		this.num_mando = num_mando;
	}
	
	
	public void agregarPokemon(Pokemon pokemon) {
		pokemons_mando.add(pokemon);
	}


	public ArrayList<Pokemon> getPokemons_mando() {
		return pokemons_mando;
	}


	public void setPokemons_mando(ArrayList<Pokemon> pokemons_mando) {
		this.pokemons_mando = pokemons_mando;
	}
	
	
	
}
