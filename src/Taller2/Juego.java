package Taller2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Juego {

	public static Scanner s = new Scanner(System.in);
	public static String usuario;

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
			menuJuego();
			break;
		case("3"):
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
		
		
	}
	
	
}
