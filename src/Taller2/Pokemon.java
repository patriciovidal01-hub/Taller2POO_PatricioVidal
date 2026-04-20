package Taller2;

public class Pokemon {

	private String nombre;
	private Habitat habitat;
	private Double prc_aparición;
	private int vida;
	private int ataque;
	private int defensa;
	private int atq_especial;
	private int dfs_especial;
	private int velocidad;
	private String tipo;
	private Boolean vivo;
	
	
	public Pokemon(String nombre, Habitat habitat, Double prc_aparición, int vida, int ataque, int defensa,
			int atq_especial, int dfs_especial, int velocidad, String tipo) {
		this.nombre = nombre;
		this.habitat = habitat;
		this.prc_aparición = prc_aparición;
		this.vida = vida;
		this.ataque = ataque;
		this.defensa = defensa;
		this.atq_especial = atq_especial;
		this.dfs_especial = dfs_especial;
		this.velocidad = velocidad;
		this.tipo = tipo;
		this.vivo = true;
	}
	
	
	
	
	
}
