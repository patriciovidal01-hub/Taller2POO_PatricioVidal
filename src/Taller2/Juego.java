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

		cargarPokedex(); // Creacion de las instancias pokemon y habitat.
		cargarLideres(); // Creacion de las instancias lideres y sus equipos.
		cargarAltoMando(); // Creacion de las intancias altos mandos y sus equipos.
		leerArchivo(); // Lee el archivo para registrar la partida anterior.
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
				nuevaPartida(); // Elimina datos anteriores y crea usuario.
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

		equipo.clear(); // Se elimina el equipo.
		medallas.clear(); // Se reinicia el progreso de medallas

		for (Lider lider : lideres) { // Los lideres vuelven a estar sin derrotar.
			lider.setDerrotado(false);
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
				if (!partes[1].equals("none")) { // Significa que hay lideres derrotados.
					for (int i = 1; i < partes.length; i++) {
						medallas.add(partes[i]); // Ingresa las medallas ganadas.
						lideres.get(i - 1).setDerrotado(true); // Lideres seteados como derrotados.
					}
				}
			} else { // Si no hay primera linea usuario queda null.
				usuario = null;
			}
			while (s_arch.hasNextLine()) { // Si hay más lineas hay pokemons.
				linea = s_arch.nextLine();
				String[] partes2 = linea.split(";");
				Pokemon pokemon_equipo = buscarPokemon(partes2[0]);
				equipo.add(pokemon_equipo); // Agrega el pokemon al equipo.
				if (partes2[1].equals("Debilitado")) { // Si esta debilitado, vivo es false.
					pokemon_equipo.setVivo(false);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("La partida no se ha encontrado");
		}
	}

	public static Pokemon buscarPokemon(String nombre) {
		for (Pokemon pokemon : pokemons_globales) { // Busca con el nombre del pokemon entre todos los pokemons.
			if (pokemon.getNombre().equals(nombre)) {
				return pokemon; // Si lo encuentra regresa la instancia de ese pokemon.
			}
		}
		return null; // Si no lo encuentra es null.
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

	public static void retarAltoMando() {

		if (equipo.isEmpty()) { // Si no hay pokemons no puede luchar.
			System.out.println("No tienes pokemons para luchar");
		} else if (medallas.size() < 8) { // Si no tiene las 8 medallas no puede desafiar al alto mando.
			System.out.println("Debes derrotar a todos los lideres de gimnasio primero.");
		} else {
			pelearAltoMando();
		}

	}

	public static void pelearAltoMando() {

		boolean derrota = false; // Analiza si el jugador perdio.
		for (int j = 0; j < altos_mandos.size(); j++) { // Ciclo para que el combate continue con todos los altos
														// mandos.
			if (derrota == true) { // Si perdio, sale del ciclo.
				break;
			}

			String op;
			ArrayList<Pokemon> equipo_mando = altos_mandos.get(j).getPokemons_mando();
			AltoMando mando = altos_mandos.get(j);
			contador_pklider = 0; // Contadores para ver el pokemon enemigo en batalla.
			contador_pkmio = 0; // Contadores para ver el pokemon nuestro en batalla.

			boolean pokemons_debilitados = false;
			pokemons_debilitados = pokemonVivo(); // Ve si tiene todos sus pokemons debilitados, si no es asi
													// contador_pkmio es del primer pokemon vivo.

			do {

				if (contador_pklider == equipo_mando.size()) { // Si el contador llega a su cantidad de pokemons
																// significa que derrotaste a todos sus pokemons.
					System.out.println("Has ganado a " + mando.getNombre());
					break;
				}

				if (pokemons_debilitados == true) { // Se explica solo.
					System.out.println("Perdiste :( volviendo al menu...");
					derrota = true; // Si derrota es true se sale del ciclo y por tanto termina la batalla en ese
									// momento.
					break;
				}

				System.out
						.println(mando.getNombre() + " saca a " + equipo_mando.get(contador_pklider).getNombre() + "!");
				System.out.println(usuario + " saca a " + equipo.get(contador_pkmio).getNombre() + "!");
				System.out.println("¿Que deseas hacer?");
				System.out.println("1) Atacar");
				System.out.println("2) Cambiar de pokemon");
				System.out.println("3) Rendirse");

				op = s.nextLine();

				switch (op) {

				case ("1"):
					atacar(equipo_mando.get(contador_pklider), equipo.get(contador_pkmio)); // Se compara a los 2
																							// pokemons en batalla para
																							// ver quien gana;
					pokemons_debilitados = pokemonVivo(); // Se analiza el siguiente pokemon vivo o si ya no tienes mas
															// pokemons.
					break;
				case ("2"):
					cambiarPokemonBatalla(); // Cambia al pokemon actual en batalla por otro.
					break;
				case ("3"):
					System.out.println("Te rindes, volviendo al menu..."); // Te rindes asi de simple.
					derrota = true; // Para que salga del ciclo.
					break;

				default:
					System.out.println("Eliga una opción valida");
				}

			} while (!op.equals("3"));
		}
		int l = 0;
		if (derrota != true) { // En caso de ganar unas pequeñas felicitaciones!
			System.out.println("FELICIDADES ERES EL NUEVO MAESTRO POKEMON!!!!");
			for (Pokemon pokemon : equipo) { // Imprime tu equipo pokemon como en los juegos, mas o menos.
				System.out.println(pokemon.getNombre() + "!");
				l++;
				if (l == 6) { // Solo los primeros 6.
					break;
				}
			}
			System.out.println(""); // Mensajito epico.
			System.out.println("Ya no hay más adelante de tu aventura, conseguiste el mayor logro, enhorabuena!");
			System.out.println("");

		}
	}

	public static void guardar() { // Guarda el progreso actual.

		try (BufferedWriter br = new BufferedWriter(new FileWriter("Registros.txt"))) { // Reescribe el archivo
			// Registros.txt
			br.write(usuario); // Escribe el usuario que esta jugando.
			if (medallas.isEmpty()) {
				br.write(";none");
			} else {
				for (String medalla : medallas) { // Escribe las medallas obtenidas.
					br.write(";" + medalla);
				}
			}

			if (!equipo.isEmpty()) { // Escribe el pokemon y su estado.
				for (Pokemon pokemon : equipo) {
					br.newLine();
					String derrotado = "";
					if (pokemon.getVivo() == true) {
						derrotado = "Vivo";
					} else {
						derrotado = "Debilitado";
					}

					br.write(pokemon.getNombre() + ";" + derrotado);
				}
			}

		} catch (IOException e) {
			System.out.println("Archivo no encontrado");
		}

	}

	public static void curarPokemon() { // Cura a todos los pokemons.

		if (!equipo.isEmpty()) {
			for (Pokemon pokemon : equipo) { // Cambia el estado a vivo en todos los pokemons del equipo.
				pokemon.setVivo(true);

			}
			System.out.println("Pokemons curados exitosamente!");
		}
	}

	public static void retarGimnasio() {

		int op = 0;

		if (!equipo.isEmpty()) { // Ve si tiene pokemons para luchar.
			do {
				System.out.println("¿Con cual deseas luchar?");
				for (int i = 0; i < lideres.size(); i++) { // Printeo de los lideres de gimnasio.
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
					op = Integer.parseInt(s.nextLine()); // Eleccion del lider a luchar.
					if (op > 0 && op <= lideres.size()) { // Que sea una opcion valida.
						poderBatallar(op - 1); // Ve si se puede batallar contra el lider.
						break;
					}

				} catch (Exception e) {
					System.out.println("Escriba un numero");
					System.out.println("Ha ocurrido un error:");
					e.printStackTrace();
				}

			} while (op != lideres.size() + 1); // Mientras sea diferente en este caso de la novena opcion que es salir.
		} else {
			System.out.println("No tienes pokemons para luchar");
		}
	}

	public static void poderBatallar(int num_gim) {

		if (medallas.isEmpty()) { // Si no hay medallas no puede luchar contra ningun lider, excepto..
			if (num_gim != 0) { // El primer lider.
				System.out.println("No puedes pelear contra " + lideres.get(num_gim).getNombre()
						+ " debes derrotar a los lideres anteriores");
			} else {
				pelearLider(num_gim);
			}
		} else if (medallas.size() < num_gim) { // Si lider elegido es menor a la cantidad de medallas, ej; lider 6
												// teniendo 4 medallas, no se puede, aclaracion num_gim es uno menos que
												// la opcion elegida entonces si pickeas el 4 liders e considera
												// como 3, por eso es posible pelear contra el si se tiene 3 medallas.
			System.out.println("No puedes pelear contra " + lideres.get(num_gim).getNombre()
					+ " debes derrotar a los lideres anteriores");
		} else {
			if (lideres.get(num_gim).getDerrotado()) { // Se explica solo.
				System.out.println("No puedes luchar contra un gimnasio que ya derrotaste");
			} else {
				pelearLider(num_gim); // Si todo lo demás no es problema lucha contra el lider.
			}
		}
	}

	public static void pelearLider(int num_gim) {

		String op;
		ArrayList<Pokemon> equipo_lider = lideres.get(num_gim).getPokemons_lider();
		Lider lider = lideres.get(num_gim);
		contador_pklider = 0;
		contador_pkmio = 0;

		boolean pokemons_debilitados = false;
		pokemons_debilitados = pokemonVivo(); // Ve si tiene todos sus pokemons debilitados, si no es asi
												// contador_pkmio es del primer pokemon vivo.

		do {

			if (contador_pklider == equipo_lider.size()) { // Si el contador llega a su cantidad de pokemons
															// significa que derrotaste a todos sus pokemons.
				System.out.println("Has ganado a " + lider.getNombre());
				medallas.add(lider.getNombre()); // Se agrega la medalla a la lista de medallas.
				lider.setDerrotado(true); // El lider queda como derrotado.
				break;
			}

			if (pokemons_debilitados == true) { // Se explica solo
				System.out.println("Perdiste :( volviendo al menu...");
				break;
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
				atacar(equipo_lider.get(contador_pklider), equipo.get(contador_pkmio)); // Se compara a los 2pokemons en
																						// batalla para ver quien gana;
				pokemons_debilitados = pokemonVivo();// Se analiza el siguiente pokemon vivo o si ya no tienes mas
														// pokemons.
				break;
			case ("2"):
				cambiarPokemonBatalla(); // Cambia al pokemon actual en batalla por otro.
				break;
			case ("3"):
				System.out.println("Te rindes, volviendo al menu...");
				break;

			default:
				System.out.println("Eliga una opción valida");
			}

		} while (!op.equals("3"));
	}

	public static boolean pokemonVivo() {

		for (int i = 0; i < equipo.size(); i++) { // Busca el primer pokemon vivo, pokemons_debilitados queda como
													// false, por que hay un pokemon vivo.
			if (equipo.get(i).getVivo()) {
				contador_pkmio = i;
				return false;
			}

			if (i == 5) { // Se termina cuando analiza a los 6 primeros pokemons de tu equipo.
				break;
			}

		}
		return true;

	}

	public static void atacar(Pokemon rival, Pokemon mio) {

		System.out.println(rival.getNombre() + " | Stats: " + rival.getStats()); // Consigue los stats de cada uno.
		System.out.println(mio.getNombre() + " | Stats: " + mio.getStats());

		double eficaz = efectividad(rival, mio); // Ve la eficacia del tipo mio contra el rival.
		double stats_nueva_mio = mio.getStats() * eficaz; // Multiplica esa eficacia por mis stats.

		if (eficaz == 0.5) { // Mensaje especifico si no es muy eficaz.

			System.out.println(mio.getNombre() + " es poco efectivo contra " + rival.getNombre() + "...");
			System.out.println("Modificando stats...");
			System.out.println(rival.getNombre() + " | Stats: " + rival.getStats()); // Printeo de los stats de cada uno
			System.out.println(mio.getNombre() + " | Stats: " + stats_nueva_mio);

		} else if (eficaz == 2.0) { // Mensaje especifico si es muy eficaz.

			System.out.println(mio.getNombre() + " es superefectivo contra " + rival.getNombre() + "!");
			System.out.println("Modificando stats...");
			System.out.println(rival.getNombre() + " | Stats: " + rival.getStats()); // Printeo de los stats de cada uno
			System.out.println(mio.getNombre() + " | Stats: " + stats_nueva_mio);
		}

		if (rival.getStats() > stats_nueva_mio) { // Resultados posibles.
			mio.setVivo(false); // Pierdes
			System.out.println(mio.getNombre() + " fue derrotado!");
		} else if (rival.getStats() == stats_nueva_mio) {
			System.out.println("Empate!");
		} else {
			System.out.println(rival.getNombre() + " fue derrotado!"); // Ganas.
			contador_pklider++; // Pasa al siguiente pokemon.
		}
		System.out.println("");
	}

	public static double efectividad(Pokemon rival, Pokemon mio) {

		int tipo_rival = encontrarTipo(rival); // Ve el tipo de cada uno.
		int tipo_mio = encontrarTipo(mio);
		double[][] efectivo = TablaTipos.getEfectividad(); // De la clasa TablaTipos saca la matriz.
		double eficaz = efectivo[tipo_mio][tipo_rival]; // Ve la eficacia entre los tipos de la matriz.
		return eficaz;

	}

	public static int encontrarTipo(Pokemon pokemon) {

		switch (pokemon.getTipo()) { // Bien arcaico, devuelve el numero de la matriz del tipo del pokemon.

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

		if (!equipo.isEmpty() && equipo.size() != 1) { // Para cambiar necesita pokemons o mas de un pokemon.

			String op;

			do {

				for (int i = 0; i < equipo.size(); i++) { // Printeo de los pokemons.
					Pokemon pokemon = equipo.get(i);
					System.out.println((i + 1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
							+ pokemon.getStats());
				}

				System.out.println("1) Cambiar Pokémon.");
				System.out.println("2) Salir.");

				op = s.nextLine();

				switch (op) {

				case ("1"):
					cambiarPokemon(); // Cambia los pokemons.
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

		for (int i = 0; i < equipo.size(); i++) { // Cuenta la cantidad de pokemons vivos.
			if (equipo.get(i).getVivo()) {
				contador++;
			}
		}

		int op = 0;
		int op2 = 0;
		int contador2 = 0;
		if (contador >= 2) { // Si la cantidad de pokemons vivos es mayor o igual a 2 significa que hay
								// pokemons suficientes para el intercambio.

			for (int j = 0; j < equipo.size(); j++) { // Printeo de los pokemons con sus caracteristicas y estado.
				Pokemon pokemon = equipo.get(j);

				if (contador2 == 6) { // Si ya printeo los 6 pokemons, salga del ciclo.
					break;
				}

				if (pokemon.getVivo()) {
					System.out.println((j + 1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
							+ pokemon.getStats());

				} else {
					System.out.println((j + 1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
							+ pokemon.getStats() + " | Derrotado");
				}
				contador2++;
			}

			try {
				// Pide los pokemons a cambiar.
				System.out.println("¿Que pokémon desea cambiar?, Escriba el numero de la lista");
				op = Integer.parseInt(s.nextLine());

				System.out.println("¿Por que pokemon lo desea cambiar?, Escriba el numero de la lista");
				op2 = Integer.parseInt(s.nextLine());

			} catch (Exception e) {
				System.out.println("Opcion invalida");
			}
			if (op > 0 && op <= 6 && op <= equipo.size() && (op2 > 0 && op2 <= 6 && op2 <= equipo.size())) {
				// Uff deja ver como explico esto.
				// Primero las opciones dichas de pokemons a intercambiar deben ser 1. Mayor a 0
				// 2. Menor a 6, por que no hay más de 6 pokemons y 3. En el caso de que el
				// equipo sea de menos de 6 pokemons las opciones deben ser menor a la cantidad
				// de pokemons que tiene el equipo, no tendria logica elegir 5 entre solo 4.
				if (equipo.get(op - 1).getVivo() && equipo.get(op2 - 1).getVivo()) { // Ve si esten vivos los pokemons.

					Pokemon pokemon_mover = equipo.get(op - 1); // Los intercambia
					equipo.set((op - 1), equipo.get(op2 - 1));
					equipo.set((op2 - 1), pokemon_mover);

				} else { // Los else se explican solos.
					System.out.println("No puedes intercambiar con un pokemon derrotado");
				}
			} else {
				System.out.println("No existe un pokemon en ese numero");
			}
		} else {
			System.out.println("No hay pokemons para cambiar");
		}

	}

	public static void cambiarPokemon() {

		int op = 0;
		int op2 = 0;

		try {
			// Pide los pokemons a cambiar.
			System.out.println("¿Que pokémon desea cambiar?, Escriba el numero de la lista");
			op = Integer.parseInt(s.nextLine());

			System.out.println("¿Por que pokemon lo desea cambiar?, Escriba el numero de la lista");
			op2 = Integer.parseInt(s.nextLine());

		} catch (Exception e) {
			System.out.println("Opcion invalida");
		}

		if (op > 0 && op <= equipo.size() && (op2 > 0 && op2 <= equipo.size())) {
			// Las opciones deben ser mayor a 0 y menor o igual a la cantidad de pokemons en
			// el equipo, equipo referido a todos los pokemons capturados.
			// Si se cumplen las condiciones, se intercambia.
			Pokemon pokemon_mover = equipo.get(op - 1);
			equipo.set((op - 1), equipo.get(op2 - 1));
			equipo.set((op2 - 1), pokemon_mover);

		} else {
			System.out.println("No existe un pokemon en ese numero");
		}

	}

	public static void revisarEquipo() {

		if (equipo.isEmpty()) { // Si no hay pokemons no hay nada que revisar claramente.
			System.out.println("No hay pokemons en el equipo");
		} else {
			for (int i = 0; i < equipo.size(); i++) { // Busca los pokemons con sus caracteristicas.
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
			for (Habitat i : habitats) { // Printea los habitats.
				System.out.println(contador + ") " + i.getNombre());
				contador++;
			}
			System.out.println(contador + ") Volver al menu");
			op = s.nextLine(); // Eleccion del jugador.
			try {
				int num = Integer.parseInt(op);
				if (num < contador && num > 0) {
					// Eleccion debe ser mayor a 0 pero menor a la cantidad de habitats que hay,
					// esto se debe a que contador empezo en 1.
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
		habitat.agregarProbabilidades(); // Crea una lista con las probabilidades sumadas de cada pokemon.
		double random = Math.random();
		Pokemon pokemon_salvaje = null;
		ArrayList<Double> probabilidades = habitat.getProbabilidades();
		for (int i = 0; i < probabilidades.size(); i++) { // Recorre la lista y busca donde estaria el numero creado
															// aleatoriamente.
			if (random <= probabilidades.get(i)) { // Ej: [0.15 - 0.30 - 0.38 - 0.46 ... - 1.00], random = 0,37
				pokemon_salvaje = habitat.getPokemon(i); // Ej: [0.15 - 0.30, 0.37 , 0.38 | 0.46 ... | 1.00],
															// Corresponde al pokemon entre esos rangos
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

			boolean capturado = false; // Si el pokemon ha sido capturado anteriormente.

			switch (op) {

			case ("1"):
				for (Pokemon pokemon : equipo) {
					// Si entre tus pokemons, se encuentra el pokemon a capturar, no puedes
					// capturarlo denuevo.
					if (pokemon.getNombre().equals(pokemon_salvaje.getNombre())) {
						System.out.println("Ya has capturado ese pokemon, no puedes capturarlo denuevo");
						capturado = true;
						break;
					}
				}
				// Si el pokemon no fue capturado anteriormente entonces se agrega al equipo.
				if (capturado == false) {
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
			while (s_arch.hasNextLine()) { // Se lee el archivo Gimnasios.txt
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

				Lider lider_gimnasio = new Lider(nombre, derrotado, num_gim); // Se crea la instancia lider.
				lideres.add(lider_gimnasio); // Se agrega el lider instanciado a lista de lideres.
				int cantidad_pokemons = Integer.parseInt(partes[3]);

				for (int i = 0; i < cantidad_pokemons; i++) { // Se agrega los pokemons del lider a su equipo.
					String nombre_pokemon = partes[i + 4];
					Pokemon pokemon_lider = buscarPokemon(nombre_pokemon);
					lider_gimnasio.agregarPokemon(pokemon_lider);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo cargar el archivo");
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

				AltoMando alto_mando = new AltoMando(nombre, false, num_mando); // Se instancia el AltoMando
				altos_mandos.add(alto_mando); // Se agrega el AltoMando instanciado a la lista de altosmandos.
				for (int i = 2; i < partes.length; i++) { // Se agrega los pokemons del alto mando a su equipo.
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
		File arch_habitat = new File("Habitats.txt");
		Scanner s_arch_habitat;
		try {
			s_arch = new Scanner(arch);
			s_arch_habitat = new Scanner(arch_habitat);

			while (s_arch_habitat.hasNextLine()) { 
				// Se escanea los habitats del archivo Habitats.txt y se agrega a la lista de habitats.
				String linea = s_arch_habitat.nextLine();
				Habitat habitat = new Habitat(linea);
				habitats.add(habitat);
			}

			while (s_arch.hasNextLine()) { // Leer archivo Pokedex.
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

				habitat = buscarHabitat(habitat_pokemon); // Busca el habitat del pokemon
				Pokemon pokemon = new Pokemon(nombre, habitat, prc_aparicion, vida, ataque, defensa, atq_especial,
						dfs_especial, velocidad, tipo);
				// Creacion de la instancia pokemon con sus stats, nombre y habitat.
				pokemons_globales.add(pokemon); // Agrega el pokemon a la lista de pokemonsGlobales.

				if (!(habitat == null)) {
					habitat.agregarPokemon(pokemon); // Se le agrega ese pokemon a la instancia habitat.
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo cargar la pokedex");
		}

	}

	public static Habitat buscarHabitat(String nombre_habitat) { 
		// Busca con el nombre, el habitat.
		for (int i = 0; i < habitats.size(); i++) {
			if (habitats.get(i).getNombre().equals(nombre_habitat)) {
				return habitats.get(i); // Si lo encuentra regresa la instancia habitat.
			}
		}
		return null; // Si no regresa null.
	}

}
