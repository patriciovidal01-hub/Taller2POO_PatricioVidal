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
	public static ArrayList<AltoMando> altos_mandos = new ArrayList<AltoMando>();
	public static ArrayList<Lider> lideres = new ArrayList<Lider>();
	public static int contador_pklider = 0;
	public static int contador_pkmio = 0;

	public static void main(String[] args) {
		// Patricio Javier Vidal Veas 22.330.827-9 ICCI
		// Vicente Antonio Garriga Muñoz 22.380.392-k ICCI

		cargarPokedex(); // Creacion de las instancias pokemon y habitat, se agregan a la respectiva
		// lista.
		cargarLideres();
		cargarAltoMando();

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
		
		equipo.clear();
		medallas.clear();

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
						lideres.get(i - 1).setDerrotado(true);
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
				accesoPC();
				break;

			case ("4"):
				retarGimnasio();
				break;

			case ("5"):
				retarAltoMando();
				break;

			case ("6"):
				curarPokemon();
				break;

			case ("7"):
				guardar();
				System.out.println("Guardando progreso...");
				break;

			case ("8"):
				guardar();
				System.out.println("Saliendo y guardando progreso....");
				break;
			}
		} while (!op.equals("8"));
	}
	
	public static void guardar() {

		try (BufferedWriter br = new BufferedWriter(new FileWriter("Registros.txt"))) { // Reescribe el archivo
			// Registros.txt
			br.write(usuario);
			if (medallas.isEmpty()) {
				br.write(";none");
			} else {
				for (String medalla : medallas) {
					br.write(";" + medalla);
				}
			}

			if (!equipo.isEmpty()) {
				for (Pokemon pokemon : equipo) {
					br.newLine();
					String derrotado = "";
					if (pokemon.getVivo() == true) {
						derrotado = "Vivo";
					} else {
						derrotado = "Derrotado";
					}

					br.write(pokemon.getNombre() + ";" + derrotado);
				}
			}

		} catch (IOException e) {
			System.out.println("Archivo no encontrado");
		}

	}
	
	
	public static void guardar() {

		try (BufferedWriter br = new BufferedWriter(new FileWriter("Registros.txt"))) { // Reescribe el archivo
			// Registros.txt
			br.write(usuario);
			if (medallas.isEmpty()) {
				br.write(";none");
			} else {
				for (String medalla : medallas) {
					br.write(";" + medalla);
				}
			}

			if (!equipo.isEmpty()) {
				for (Pokemon pokemon : equipo) {
					br.newLine();
					String derrotado = "";
					if (pokemon.getVivo() == true) {
						derrotado = "Vivo";
					} else {
						derrotado = "Derrotado";
					}

					br.write(pokemon.getNombre() + ";" + derrotado);
				}
			}

		} catch (IOException e) {
			System.out.println("Archivo no encontrado");
		}

	}

	public static void curarPokemon() {

		if (!equipo.isEmpty()) {
			for (Pokemon pokemon : equipo) {
				pokemon.setVivo(true);

			}
			System.out.println("Pokemons curados exitosamente!");
		}
	}

	public static void retarGimnasio() {

		int op = 0;

		if (!equipo.isEmpty()) {
			do {
				System.out.println("¿Con cual deseas luchar?");
				for (int i = 0; i < lideres.size(); i++) {
					String derrotado = "";
					if (lideres.get(i).getDerrotado() == false) {
						derrotado = "Sin derrotar";
					} else {
						derrotado = "Derrotado";
					}
					System.out.println((i + 1) + ") " + lideres.get(i).getNombre() + " - Estado: " + derrotado);

				}

				System.out.println((lideres.size() + 1) + ") Volver al menu.");

				try {
					op = Integer.parseInt(s.nextLine());
					if (op > 0 && op <= lideres.size()) {
						poderBatallar(op - 1);
						break;
					}

				} catch (Exception e) {
					System.out.println("Escriba un numero");
					System.out.println("Ha ocurrido un error:");
					e.printStackTrace();
				}

			} while (op != lideres.size() + 1);
		} else {
			System.out.println("No tienes pokemons para luchar");
		}
	}

	public static void poderBatallar(int num_gim) {

		if (medallas.isEmpty()) {
			if (num_gim != 0) {
				System.out.println("No puedes pelear contra " + lideres.get(num_gim).getNombre()
						+ " debes derrotar a los lideres anteriores");
			} else {
				pelearLider(num_gim);
			}
		} else if (medallas.size() < num_gim) {
			System.out.println("No puedes pelear contra " + lideres.get(num_gim).getNombre()
					+ " debes derrotar a los lideres anteriores");
		} else {
			if (lideres.get(num_gim).getDerrotado()) {
				System.out.println("No puedes luchar contra un gimnasio que ya derrotaste");
			} else {
				pelearLider(num_gim);
			}
		}
	}

	public static void pelearLider(int num_gim) {

		String op;
		ArrayList<Pokemon> equipo_lider = lideres.get(num_gim).getPokemons_lider();
		Lider lider = lideres.get(num_gim);
		contador_pklider = 0;
		contador_pkmio = 0;

		for (int i = 0; i < equipo.size(); i++) {
			if (equipo.get(i) != null) {
				if (!equipo.get(i).getVivo()) {
					contador_pkmio++;
				}
			}

			if (i == 5) {
				break;
			}
		}

		do {

			if (contador_pklider == equipo_lider.size()) {
				System.out.println("Has ganado a " + lider.getNombre());
				medallas.add(lider.getNombre());
				lider.setDerrotado(true);
				break;
			}
			if (equipo.size() > 6) {
				if (contador_pkmio == 6) {
					System.out.println("Perdiste :( volviendo al menu...");
					break;
				}
			} else {
				if (contador_pkmio == equipo.size()) {
					System.out.println("Perdiste :( volviendo al menu...");
					break;
				}

			}

			System.out.println(lider.getNombre() + " saca a " + equipo_lider.get(contador_pklider).getNombre() + "!");
			System.out.println(usuario + " saca a " + equipo.get(contador_pkmio).getNombre() + "!");

			System.out.println("¿Que deseas hacer?");
			System.out.println("1) Atacar");
			System.out.println("2) Cambiar de pokemon");
			System.out.println("3) Rendirse");

			op = s.nextLine();

			switch (op) {

			case ("1"):
				atacar(equipo_lider.get(contador_pklider), equipo.get(contador_pkmio));
				break;
			case ("2"):
				cambiarPokemonBatalla();
				break;
			case ("3"):
				System.out.println("Te rindes, volviendo al menu...");
				break;

			default:
				System.out.println("Eliga una opción valida");
			}

		} while (!op.equals("3"));
	}

	public static void atacar(Pokemon rival, Pokemon mio) {

		System.out.println(rival.getNombre() + " | Stats: " + rival.getStats());
		System.out.println(mio.getNombre() + " | Stats: " + mio.getStats());

		double eficaz = efectividad(rival, mio);
		double stats_nueva_mio = mio.getStats() * eficaz;

		if (eficaz == 0.5) {

			System.out.println(mio.getNombre() + " es poco efectivo contra " + rival.getNombre() + "...");
			System.out.println("Modificando stats...");
			System.out.println(rival.getNombre() + " | Stats: " + rival.getStats());
			System.out.println(mio.getNombre() + " | Stats: " + stats_nueva_mio);

		} else if (eficaz == 2.0) {

			System.out.println(mio.getNombre() + " es superefectivo contra " + rival.getNombre() + "!");
			System.out.println("Modificando stats...");
			System.out.println(rival.getNombre() + " | Stats: " + rival.getStats());
			System.out.println(mio.getNombre() + " | Stats: " + stats_nueva_mio);
		}

		if (rival.getStats() > stats_nueva_mio) {
			mio.setVivo(false);
			System.out.println(mio.getNombre() + " fue derrotado!");
			contador_pkmio++;
		} else if (rival.getStats() == stats_nueva_mio) {
			System.out.println("Empate!");
		} else {
			System.out.println(rival.getNombre() + " fue derrotado!");
			contador_pklider++;
		}
		System.out.println("");
	}

	public static double efectividad(Pokemon rival, Pokemon mio) {

		int tipo_rival = encontrarTipo(rival);
		int tipo_mio = encontrarTipo(mio);
		double[][] efectivo = TablaTipos.getEfectividad();
		double eficaz = efectivo[tipo_mio][tipo_rival];
		return eficaz;

	}

	public static int encontrarTipo(Pokemon pokemon) {

		switch (pokemon.getTipo()) {

		case ("Normal"):
			return 0;
		case ("Fuego"):
			return 1;
		case ("Agua"):
			return 2;
		case ("Planta"):
			return 3;
		case ("Electrico"):
			return 4;
		case ("Hielo"):
			return 5;
		case ("Lucha"):
			return 6;
		case ("Veneno"):
			return 7;
		case ("Tierra"):
			return 8;
		case ("Volador"):
			return 9;
		case ("Psiquico"):
			return 10;
		case ("Bicho"):
			return 11;
		case ("Roca"):
			return 12;
		case ("Fantasma"):
			return 13;
		case ("Dragon"):
			return 14;
		case ("Acero"):
			return 15;
		case ("Siniestro"):
			return 16;
		case ("Hada"):
			return 17;

		}
		return 0;
	}

	public static void accesoPC() {

		if (!equipo.isEmpty() && equipo.size() != 1) {

			String op;

			do {

				for (int i = 0; i < equipo.size(); i++) {
					Pokemon pokemon = equipo.get(i);
					System.out.println((i + 1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
							+ pokemon.getStats());
				}

				System.out.println("1) Cambiar Pokémon.");
				System.out.println("2) Salir.");

				op = s.nextLine();

				switch (op) {

				case ("1"):
					cambiarPokemon();
					break;
				case ("2"):
					break;
				default:
					System.out.println("Eliga una opción valida");
				}

			} while (!op.equals("2"));
		} else {
			System.out.println("No hay pokemon suficientes para intercambiar");
		}
	}

	public static void cambiarPokemonBatalla() {

		int contador = 0;

		for (int i = 0; i < equipo.size(); i++) {
			if (equipo.get(i).getVivo()) {
				contador++;
			}
		}

		int op = 0;
		int op2 = 0;
		int contador2 = 0;
		if (contador >= 2) {

			for (int j = 0; j < equipo.size(); j++) {
				Pokemon pokemon = equipo.get(j);
				
				if(contador2 == 6) {
					break;
				}
				
				if (pokemon.getVivo()) {
					System.out.println((j+1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
							+ pokemon.getStats());

				} else {
					System.out.println((j+1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
							+ pokemon.getStats() + " | Derrotado");
				}
				contador2++;
			}

			try {

				System.out.println("¿Que pokémon desea cambiar?, Escriba el numero de la lista");
				op = Integer.parseInt(s.nextLine());

				System.out.println("¿Por que pokemon lo desea cambiar?, Escriba el numero de la lista");
				op2 = Integer.parseInt(s.nextLine());

			} catch (Exception e) {
				System.out.println("Opcion invalida");
			}

			if (equipo.get(op - 1).getVivo() && equipo.get(op2 - 1).getVivo()) {
				if (op > 0 && op <= equipo.size() && (op2 > 0 && op2 <= equipo.size())) {
					Pokemon pokemon_mover = equipo.get(op - 1);
					equipo.set((op - 1), equipo.get(op2 - 1));
					equipo.set((op2 - 1), pokemon_mover);

				} else {
					System.out.println("No existe un pokemon en ese numero");
				}
			} else {
				System.out.println("No puedes intercambiar con un pokemon derrotado");
			}

		} else {
			System.out.println("No hay pokemons para cambiar");
		}

	}

	public static void cambiarPokemon() {

		int op = 0;
		int op2 = 0;

		try {

			System.out.println("¿Que pokémon desea cambiar?, Escriba el numero de la lista");
			op = Integer.parseInt(s.nextLine());

			System.out.println("¿Por que pokemon lo desea cambiar?, Escriba el numero de la lista");
			op2 = Integer.parseInt(s.nextLine());

		} catch (Exception e) {
			System.out.println("Opcion invalida");
		}

		if (op > 0 && op <= equipo.size() && (op2 > 0 && op2 <= equipo.size())) {
			Pokemon pokemon_mover = equipo.get(op - 1);
			equipo.set((op - 1), equipo.get(op2 - 1));
			equipo.set((op2 - 1), pokemon_mover);

		} else {
			System.out.println("No existe un pokemon en ese numero");
		}

	}

	public static void revisarEquipo() {

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
			
			boolean capturado = false;
			
			switch (op) {

			case ("1"):
				for(Pokemon pokemon : equipo) {
					if(pokemon.getNombre().equals(pokemon_salvaje.getNombre())) {
						System.out.println("Ya has capturado ese pokemon, no puedes capturarlo denuevo");
						capturado = true;
						break;
					}
				}
			
				if(capturado == false) {
				equipo.add(pokemon_salvaje);
				System.out.println(pokemon_salvaje.getNombre() + " fue capturado!");
				System.out.println(pokemon_salvaje.getNombre() + " agregado al equipo.");
				op = "2";
				}
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
				lideres.add(lider_gimnasio);
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
		File arch = new File("Alto Mando.txt");
		Scanner s_arch;

		try {
			s_arch = new Scanner(arch);
			while (s_arch.hasNextLine()) { // Creacion de las instancias AltoMando
				String linea = s_arch.nextLine();
				String[] partes = linea.split(";");
				int num_mando = Integer.parseInt(partes[0]);
				String nombre = partes[1];

				AltoMando alto_mando = new AltoMando(nombre, false, num_mando);
				altos_mandos.add(alto_mando);
				for (int i = 2; i < partes.length; i++) {
					String nombre_pokemon = partes[i];
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
				pokemons_globales.add(pokemon);

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
