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

	public String getNombre() {
		return nombre;
	}

	public Boolean getDerrotado() {
		return derrotado;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDerrotado(Boolean derrotado) {
		this.derrotado = derrotado;
	}

	public ArrayList<Pokemon> getPokemons_lider() {
		return pokemons_lider;
	}

	public void setPokemons_lider(ArrayList<Pokemon> pokemons_lider) {
		this.pokemons_lider = pokemons_lider;
	}
	
	
	
}