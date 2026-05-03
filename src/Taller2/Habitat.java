package Taller2;

import java.util.ArrayList;

public class Habitat {

	private String nombre;
	private ArrayList<Pokemon> pokemons_habitat = new ArrayList<Pokemon>();
	private ArrayList<Double> probabilidades = new ArrayList<Double>();

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
	
	public Pokemon getPokemon(int index) {
		return pokemons_habitat.get(index);
	}
	
	public ArrayList<Double> getProbabilidades() {
		return probabilidades;
	}

	public void agregarProbabilidades() {
		if (probabilidades.isEmpty()) {

			for (int i = 0; i < pokemons_habitat.size(); i++) {
				double prc_aparicion = pokemons_habitat.get(i).getPrc_aparición();
				if (probabilidades.isEmpty()) {
					probabilidades.add(prc_aparicion);
				} else {
					probabilidades.add(probabilidades.get(i - 1) + prc_aparicion);
				}
			}
		}
	}

}
