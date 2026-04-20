package Taller2;

import java.util.ArrayList;

public class Habitat {
	
	private String nombre;
	private ArrayList<Pokemon> pokemons_habitat = new ArrayList<Pokemon>();
	
	public Habitat(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void agregarPokemon(Pokemon pokemon) {
		pokemons_habitat.add(pokemon);
	}
	
}
