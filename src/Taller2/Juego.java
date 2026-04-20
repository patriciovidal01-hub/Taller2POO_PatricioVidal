package Taller2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Juego {

	public static Scanner s = new Scanner(System.in);
	public static String usuario;
	public static ArrayList<Pokemon> pokemons_globales = new ArrayList<Pokemon>();	
	public static ArrayList<Habitat> habitats = new ArrayList<Habitat>();
	
	public static void main(String[] args) {
	// Patricio Javier Vidal Veas 22.330.827-9 ICCI
	// Vicente Antonio Garriga Muñoz???  22.380.392-k ICCI
		
		
		
	String op;	
		
	do {	
		System.out.println("1) Continuar");
		System.out.println("2) Nueva partida");
		System.out.println("3) Salir");
		
		op = s.nextLine();
		
		switch(op) {
		
		case("1"):
			continuar();
			break;
		case("2"):
			nuevaPartida();
			System.out.println("¡Bienvenido " + usuario + "!");
			menuJuego();
			break;
		case("3"):
			System.out.println("Saliendo...");
			break;
		default:
			System.out.println("Opcion invalida");
			break;		
		} 
	}while (!op.equals("3"));	
}
	
	
	
	public static void continuar() {
		
		if (usuario == null) { // Si no hay usuario no entra a la partida
			System.out.println("Usuario no encontrado");
		} else {
			System.out.println("¡Bienvenido " + usuario + "!");
			menuJuego();
		}
		
	}
	
	
	
	public static void nuevaPartida() {
		
		System.out.println("Ingresa tu nombre de usuario: ");
		usuario = s.nextLine();
		
		try(BufferedWriter br = new BufferedWriter(new FileWriter("Registros.txt"))){ //Reescribe el archivo Registros.txt
			br.write(usuario);
			br.write(";none");
			
		} catch(IOException e) {
			System.out.println("Archivo no encontrado");
		}
		
	}
	
	public static void menuJuego() {
		
		cargarPokedex(); // Creacion de las instancias pokemon y habitat, se agregan a la respectiva lista
		
		
		System.out.println("1) Revisar equipo");
		System.out.println("2) Salir a capturar");
		System.out.println("3) Acceso al PC");
		System.out.println("4) Retar a un gimnasio");
		System.out.println("5) Desafio al Alto Mando");
		System.out.println("6) Curar Pokemon");
		System.out.println("7) Guardar");
		System.out.println("8) Guardar y salir");
		
		String op = s.nextLine();
		
		
		do {
		
		switch(op) {
		
		case("1"):
			revisarEquipo();
			break;
		
		case("2"):
			break;
		
		case("3"):
			break;
		
		case("4"):
			break;
		
		case("5"):
			break;
		
		case("6"):
			break;
		
		case("7"):
			System.out.println("Guardando progreso");
			break;
		case("8"):
			System.out.println("Saliendo y guardando progreso....");
			break;
		} 
		}while(!op.equals("8"));
	}
	
	
	
	public static void revisarEquipo() {
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void cargarPokedex()  {
		File arch = new File("Pokedex.txt");
		Scanner s_arch;
		try {
			s_arch = new Scanner(arch);
			while(s_arch.hasNextLine()) { // Creacion de las instancias Pokemon, a su vez de la instancia Habitat
				String linea = s_arch.nextLine();
				String[] partes = linea.split(";");
				String nombre = partes[0];
				String habitat_pokemon = partes[1];
				double prc_aparicion = Double.parseDouble(partes[2]);
				int vida = Integer.parseInt(partes[3]);
				int ataque = Integer.parseInt(partes[4]);
				int defensa = Integer.parseInt(partes[5]);;
				int atq_especial = Integer.parseInt(partes[6]);
				int dfs_especial = Integer.parseInt(partes[7]);
				int velocidad = Integer.parseInt(partes[8]);
				String tipo = partes[9];
				
				Habitat habitat = agregarHabitat(habitat_pokemon); // Creacion del habitat o simplemente direccionar al habitat
				Pokemon pokemon = new Pokemon(nombre, habitat, prc_aparicion, vida, ataque, defensa, atq_especial, dfs_especial, velocidad, tipo);
				// Creacion del pokemon con sus stats, nombre y habitat
				habitat.agregarPokemon(pokemon); // Se le agrega ese pokemon a la instancia habitat
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static Habitat agregarHabitat(String nombre_habitat) { // Creacion de las instancias Habitat y retorno del habitat 
		Boolean existe = false;									  // Para la instancia de pokemon
		
		if (habitats != null) {
			Habitat habitat_nuevo = new Habitat(nombre_habitat);  
			habitats.add(habitat_nuevo);// Agregar la instancia habitat nueva a nuestra lista habitats
			return habitat_nuevo;
		} else {
			for(int i=0; i < habitats.size(); i++) {
				Habitat habitat_comparar = habitats.get(i);
				if (habitat_comparar.getNombre().equals(nombre_habitat)) { // Retorna el habitat ya existente
					existe = true;
					return habitats.get(i);
				}
			}
			
			if (existe == false) {
				Habitat habitat_nuevo = new Habitat(nombre_habitat);
				habitats.add(habitat_nuevo);
				return habitat_nuevo;
			}
			
		}
		return null;
		
	}
	
	
}
