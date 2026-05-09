Taller 2: El camino para ser el mejor - Simulador Pokémon 
Integrantes:

-Patricio Javier Vidal Veas, 22.330.827-9, ICCI, patriciovidal01-hub
-Vicente Antonio Garriga Muñoz,22.380.392-k,ICCI, vicentegarriga-stack

Descripción del Proyecto
Este proyecto es un simulador de batallas y capturas Pokémon desarrollado en Java, implementando el paradigma de Programación Orientada a Objetos (POO). 
El sistema permite cargar partidas, explorar hábitats para capturar criaturas mediante probabilidades dinámicas, 
gestionar el equipo activo frente al almacenamiento del PC, y desafiar a los Líderes de Gimnasio y al Alto Mando. 
El combate se resuelve sumando estadísticas base y aplicando multiplicadores matemáticos extraídos de una matriz de efectividad de tipos. 
Todo el progreso es persistente gracias a la lectura y sobreescritura de archivos.txt.


Estructura del Proyecto
El proyecto está contenido dentro del paquete principal Taller2.
Juego, que es la clase principal que contiene el main. Opera como el motor del juego, gestionando los menús, 
la lectura y/o escritura de archivos (como Registros.txt, Pokedex.txt, etc.), y la lógica de combate.



Pokemon: Es la entidad que modela a las criaturas, encapsulando sus atributos; el nombre, el hábitat, las estadísticas base, el tipo y el estado vital.
Habitat: Es la que Gestiona las zonas de exploración, esta contiene la lista de Pokémones que habitan allí y calcula las probabilidades acumulativas para el 
sistema de captura aleatoria.
Lider.: Es la que modela a los líderes de gimnasio, almacenando su nombre, su estado de derrota y su equipo Pokémon.
AltoMando: Esta modela a la élite del juego, a diferencia de los líderes, estas requieren un desafío consecutivo y 
además no guardan el estado de derrota individual en el archivo de guardado.
TablaTipos: es la clase de utilidad que contiene la matriz estática bidimensional double[][], con los multiplicadores de efectividad de daño, como 0.5, etc

Extra: Librerías Utilizadas y Permitidas

Para cumplir con las restricciones del taller, este proyecto utiliza exclusivamente:
java.util.Scanner: Para la captura de entrada del usuario y lectura de archivos.txt.
java.io.BufferedWriter / FileWriter / File: Para la persistencia y sobreescritura de datos.
java.util.ArrayList: Para la gestión dinámica de colecciones (Equipo, PC, Hábitats).
java.lang.Math.random: Para la generación aleatoria de encuentros de captura.

Instrucciones de Ejecución:
1-n Clonar el repositorio en su entorno local.
2- Abrir el proyecto utilizando el IDE Eclipse, asegurarse antes que los archivos de texto “Registros.txt, Habitats.txt, Pokedex.txt, Gimnasios.txt, Alto Mando.txt” 
se encuentren ubicados en la raíz del proyecto.
3- Compilar y ejecutar la clase principal Juego.
4- Interactuar con el programa a través de la consola ingresando las opciones disponibles de los menús.
