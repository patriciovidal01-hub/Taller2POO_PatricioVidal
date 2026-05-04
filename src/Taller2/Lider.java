package Taller2;

import java.util.ArrayList;

public class Lider {
	
	private String nombre; 
	private Boolean derrotado;
	private int num_gim; 
	private ArrayList<Pokemon> pokemons_lider = new ArrayList<Pokemon>();
	
	public Lider(String nombre, Boolean derrotado, int num_gim) {
		this.nombre = nombre;
		this.derrotado = false;
		this.num_gim = num_gim;
	}
	
	public void agregarPokemon(Pokemon pokemon) {
		pokemons_lider.add(pokemon);
	}
	
	
}
