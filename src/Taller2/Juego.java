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
	public static ArrayList<Pokemon> equipo = new ArrayList<Pokemon>();
	public static ArrayList<Habitat> habitats = new ArrayList<Habitat>();
	public static ArrayList<String> medallas = new ArrayList<String>();

	public static void main(String[] args) {
		// Patricio Javier Vidal Veas 22.330.827-9 ICCI
		// Vicente Antonio Garriga Muñoz 22.380.392-k ICCI

		leerArchivo(); // Lee el archivo para ver el usuario que esta registrado
		String op;

		do {
			System.out.println("1) Continuar");
			System.out.println("2) Nueva partida");
			System.out.println("3) Salir");

			op = s.nextLine();

			switch (op) {

			case ("1"):
				continuar();
				break;
			case ("2"):
				nuevaPartida();
				System.out.println("¡Bienvenido " + usuario + "!");
				menuJuego();
				break;
			case ("3"):
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opcion invalida");
				break;
			}
		} while (!op.equals("3"));
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

		try (BufferedWriter br = new BufferedWriter(new FileWriter("Registros.txt"))) { // Reescribe el archivo
																						// Registros.txt
			br.write(usuario);
			br.write(";none");

		} catch (IOException e) {
			System.out.println("Archivo no encontrado");
		}

	}

	public static void leerArchivo() {
		File arch = new File("Registros.txt"); // Lee el archivo de la partida
		Scanner s_arch;
		try {
			s_arch = new Scanner(arch);
			String linea = s_arch.nextLine();
			if (linea != null) { // Confirma que este la primera linea
				String[] partes = linea.split(";");
				usuario = partes[0];
				if (!partes[1].equals("none")) {
					for (int i = 1; i < partes.length; i++) {
						medallas.add(partes[i]);
					}
				}
			} else { // Si no hay usuario queda null
				usuario = null;
			}
			while (s_arch.hasNextLine()) {
				linea = s_arch.nextLine();
				String[] partes2 = linea.split(";");
				Pokemon pokemon_equipo = buscarPokemon(partes2[0]);
				equipo.add(pokemon_equipo);
				if (partes2[1].equals("Derrotado")) {
					pokemon_equipo.setVivo(false);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("La partida no se ha encontrado");
		}
	}

	public static Pokemon buscarPokemon(String nombre) {
		for (Pokemon pokemon : pokemons_globales) {
			if (pokemon.getNombre().equals(nombre)) {
				return pokemon;
			}
		}
		return null;
	}

	public static void menuJuego() {
		cargarLideres();
		cargarAltoMando();
		cargarPokedex(); // Creacion de las instancias pokemon y habitat, se agregan a la respectiva
							// lista
		String op;

		do {
			System.out.println("1) Revisar equipo");
			System.out.println("2) Salir a capturar");
			System.out.println("3) Acceso al PC");
			System.out.println("4) Retar a un gimnasio");
			System.out.println("5) Desafio al Alto Mando");
			System.out.println("6) Curar Pokemon");
			System.out.println("7) Guardar");
			System.out.println("8) Guardar y salir");

			op = s.nextLine();
			System.out.println(op);

			switch (op) {

			case ("1"):
				revisarEquipo();
				break;

			case ("2"):
				capturarPokemon();
				break;

			case ("3"):
				break;

			case ("4"):
				break;

			case ("5"):
				break;

			case ("6"):
				break;

			case ("7"):
				System.out.println("Guardando progreso");
				break;

			case ("8"):
				System.out.println("Saliendo y guardando progreso....");
				break;
			}
		} while (!op.equals("8"));
	}

	public static void revisarEquipo() {
		// HABRIA QUE TENER UN LEER EQUIPO EN EL ARCHIVO PARA QUE SE GUARDE BIEN LOS
		// DATOS Y PODER CONTINUAR

		if (equipo.isEmpty()) {
			System.out.println("No hay pokemons en el equipo");
		} else {
			for (int i = 0; i < equipo.size(); i++) {
				Pokemon pokemon = equipo.get(i);
				System.out.println((i + 1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
						+ pokemon.getStats());
			}
		}

	}

	public static void capturarPokemon() {
		String op;
		int contador = 1;

		do {
			System.out.println("¿Donde desea capturar?:");

			contador = 1;
			for (Habitat i : habitats) {
				System.out.println(contador + ") " + i.getNombre());
				contador++;
			}
			System.out.println(contador + ") Volver al menu");
			op = s.nextLine();
			try {
				int num = Integer.parseInt(op);
				if (num < contador && num > 0) {
					modoCaptura(habitats.get(num - 1));
					break;
				}
			} catch (Exception e) {
				System.out.println("Por favor escriba un numero");
			}

		} while (!op.equals(String.valueOf(contador)));

		System.out.println("Volviendo al menu...");

	}

	public static void modoCaptura(Habitat habitat) {
		habitat.agregarProbabilidades(); // Crea una lista con las probabilidades sumadas de cada pokemon
		double random = Math.random();
		Pokemon pokemon_salvaje = null;
		ArrayList<Double> probabilidades = habitat.getProbabilidades();
		for (int i = 0; i < probabilidades.size(); i++) { // Recorre la lista y busca donde estaria el numero creado
															// aleatoriamente
			if (random <= probabilidades.get(i)) { // Ej: [0.15 - 0.30 - 0.38 - 0.46 ... - 1.00], random = 0,37
				pokemon_salvaje = habitat.getPokemon(i); // Ej: [0.15 - 0.30, 0.37 , 0.38 | 0.46 ... | 1.00],
															// corresponde al pokemon entre esos rangos
				break;
			}
		}
		System.out.println("Un " + pokemon_salvaje.getNombre() + " salvaje ha aparecido!");
		String op;

		do {
			System.out.println("¿Que deseas hacer?");
			System.out.println("1) Capturar");
			System.out.println("2) Huir");

			op = s.nextLine();

			switch (op) {

			case ("1"):
				equipo.add(pokemon_salvaje);
				System.out.println(pokemon_salvaje.getNombre() + " fue capturado!");
				System.out.println(pokemon_salvaje.getNombre() + " agregado al equipo.");
				op = "2";
				break;

			case ("2"):
				break;
			default:
				System.out.println("Opcion invalida");
			}

		} while (!op.equals("2"));

	}

	public static void cargarLideres() {
		File arch = new File("Gimnasios.txt");
		Scanner s_arch;

		try {
			s_arch = new Scanner(arch);
			while (s_arch.hasNextLine()) { // Creacion de las instancias Lider
				String linea = s_arch.nextLine();
				String[] partes = linea.split(";");
				int num_gim = Integer.parseInt(partes[0]);
				String nombre = partes[1];
				String estado = partes[2];
				boolean derrotado;
				if (estado.equals("Sin derrotar")) {
					derrotado = false;
				} else {
					derrotado = true;
				}

				Lider lider_gimnasio = new Lider(nombre, derrotado, num_gim);

				int cantidad_pokemons = Integer.parseInt(partes[3]);

				for (int i = 0; i < cantidad_pokemons; i++) {
					String nombre_pokemon = partes[i + 4];
					Pokemon pokemon_lider = buscarPokemon(nombre_pokemon);
					lider_gimnasio.agregarPokemon(pokemon_lider);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo cargar la pokedex");
		}

	}

	public static void cargarAltoMando() {
		File arch = new File("Gimnasios.txt");
		Scanner s_arch;

		try {
			s_arch = new Scanner(arch);
			while (s_arch.hasNextLine()) { // Creacion de las instancias AltoMando
				String linea = s_arch.nextLine();
				String[] partes = linea.split(";");
				int num_mando = Integer.parseInt(partes[0]);
				String nombre = partes[1];

				AltoMando alto_mando = new AltoMando(nombre, false, num_mando);
				for (int i = 0; i < 6; i++) {
					String nombre_pokemon = partes[i + 2];
					Pokemon pokemon_mando = buscarPokemon(nombre_pokemon);
					alto_mando.agregarPokemon(pokemon_mando);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo cargar la pokedex");
		}

	}

	public static void cargarPokedex() {
		File arch = new File("Pokedex.txt");
		Scanner s_arch;
		try {
			s_arch = new Scanner(arch);
			while (s_arch.hasNextLine()) { // Creacion de las instancias Pokemon, a su vez de la instancia Habitat
				String linea = s_arch.nextLine();
				String[] partes = linea.split(";");
				String nombre = partes[0];
				String habitat_pokemon = partes[1];
				double prc_aparicion = Double.parseDouble(partes[2]);
				int vida = Integer.parseInt(partes[3]);
				int ataque = Integer.parseInt(partes[4]);
				int defensa = Integer.parseInt(partes[5]);
				int atq_especial = Integer.parseInt(partes[6]);
				int dfs_especial = Integer.parseInt(partes[7]);
				int velocidad = Integer.parseInt(partes[8]);
				String tipo = partes[9];
				Habitat habitat;

				if (habitat_pokemon.equals("none")) {
					habitat = null;
				} else {
					habitat = agregarHabitat(habitat_pokemon); // Creacion del habitat o simplemente direccionar al
				} // habitat.
				Pokemon pokemon = new Pokemon(nombre, habitat, prc_aparicion, vida, ataque, defensa, atq_especial,
						dfs_especial, velocidad, tipo);
				// Creacion del pokemon con sus stats, nombre y habitat.

				if (!(habitat == null)) {
					habitat.agregarPokemon(pokemon); // Se le agrega ese pokemon a la instancia habitat.
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo cargar la pokedex");
		}

	}

	public static Habitat agregarHabitat(String nombre_habitat) { // Creacion de las instancias Habitat y retorno del
																	// habitat
		Boolean existe = false; // Para la instancia de pokemon

		if (habitats.isEmpty()) {
			Habitat habitat_nuevo = new Habitat(nombre_habitat);
			habitats.add(habitat_nuevo);// Agregar la instancia habitat nueva a nuestra lista habitats
			return habitat_nuevo;
		} else {
			for (int i = 0; i < habitats.size(); i++) {
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
