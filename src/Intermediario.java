import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Intermediario {
	Fichero encargadoFichero;
	Teclado encargadoTeclado;
	Scanner teclado;
	ConexionBBDD conexion;

	public Intermediario() {
		teclado = new Scanner(System.in);
		encargadoFichero = new Fichero();
		encargadoTeclado = new Teclado();
		conexion = new ConexionBBDD();
	}

	public void ejecucion() {
		conexion.conexion();
		int op = 0;
		boolean salir = false;

		while (!salir) {
			System.out.println("SELECCIONE UNA OPCIÓN");
			System.out.println("1- Teclado a Fichero  \n" + "2- Teclado a BBDD \n" + "3- Fichero a Pantalla \n"
					+ "4- Fichero a BBDD \n" + "5- BBDD a Pantalla \n" + "6- BBDD a Fichero \n" + "7- Limpiar BBDD \n"
					+ "8- Salir");
			try {
				System.out.print("Opción: ");
				op = teclado.nextInt();
				// teclado.nextLine();
				System.out.println("OPCION SELECCIONADA:" + op);
				switch (op) {
				case 1:
					System.out.println("De Teclado a Fichero...");
					tecladoFichero();
					break;
				case 2:
					System.out.println("De Teclado a BBDD...");
					tecladoBBDD();
					break;
				case 3:
					System.out.println("De Fichero a Pantalla...");
					ficheroPantalla();
					break;
				case 4:
					System.out.println("De Fichero a BBDD...");
					ficheroBBDD();
					break;
				case 5:
					System.out.println("De BBDD a Pantalla...");
					bbddPantalla();
					break;
				case 6:
					System.out.println("De BBDD a Fichero...");
					bbddFichero();
					break;
				case 7:
					System.out.println("Limpiando BBDD...");
					limpiarBBDD();
					break;
				case 8:
					salir = true;
					break;
				default:
					System.out.println("Opcion invalida: marque un numero de 1 a 7");
					break;
				}
			} catch (Exception e) {
				System.out.println("Excepcion por opcion invalida: marque un numero de 1 a 7");
				// flushing scanner
				e.printStackTrace();
				teclado.next();
			}
		}
		// teclado.close();
	}

	public void tecladoFichero() {
		HashMap<String, String> coches = encargadoTeclado.leerTeclado();
		boolean ok = encargadoFichero.crearFichero(coches);
		if (ok) {
			System.out.println("Se han guardado correctamente los datos en el fichero");
		} else {
			System.out.println("Ha ocurrido un error");
		}
	}

	public void tecladoBBDD() {
		HashMap<String, String> coches = encargadoTeclado.leerTeclado();
		boolean ok = conexion.insertBBDD(coches);
		if (ok) {
			System.out.println("Se han guardado correctamente los datos en la BBDD");
		} else {
			System.out.println("Ha ocurrido un error");
		}
	}

	public void ficheroPantalla() {
		HashMap<String, String> coches = encargadoFichero.leerFichero();
		boolean ok = encargadoTeclado.mostrarPantalla(coches);
		if (ok) {
			System.out.println("Estos son los datos del fichero");
		} else {
			System.out.println("Ha ocurrido un error");
		}
	}

	public void ficheroBBDD() {
		conexion.limpiarBBDD();
		HashMap<String, String> coches = encargadoFichero.leerFichero();
		boolean ok = conexion.insertBBDD(coches);
		if (ok) {
			System.out.println("Se han guardado correctamente los datos en la BBDD");
		} else {
			System.out.println("Ha ocurrido un error");
		}
	}

	public void bbddPantalla() {
		ArrayList<String> coches = conexion.selectCoches();
		boolean ok = encargadoTeclado.mostrarPantallaArrayList(coches);
		if (ok) {
			System.out.println("Mostrando los datos de la BBDD por pantalla");
		} else {
			System.out.println("Ha ocurrido un error");
		}
	}

	public void bbddFichero() {
		ArrayList<String> coches = conexion.selectCoches();
		boolean ok = encargadoFichero.crearFicheroArrayList(coches);
		if (ok) {
			System.out.println("Se han guardado correctamente los datos de la BBDD en el fichero");
		} else {
			System.out.println("Ha ocurrido un error");
		}
	}

	public void limpiarBBDD() {
		conexion.limpiarBBDD();
	}
}