import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Teclado {
	HashMap<String, String> coches = null;
	BufferedReader breader = null;
	BufferedWriter bwriter = null;
	FileReader fReader = null;
	File archivo = null;

	public HashMap<String, String> leerTeclado() {
		String marca, modelo;
		Scanner scan = new Scanner(System.in);
		System.out.print("¿Cuántos coches quieres introducir: ");
		int cantidad = scan.nextInt();
		scan.nextLine(); // LIMPIAR BUFFER

		coches = new HashMap<String, String>();
		for (int i = 1; i <= cantidad; i++) {
			System.out.print("Marca " + i + "º: ");
			marca = scan.nextLine();
			System.out.print("Modelo " + i + "º: ");
			modelo = scan.nextLine();
			// Se mete como clave el modelo y no marca para comprobar en método ifModeloExists() 
			// por la clave y si da 1 es q ya existe y no se metería en la bbdd
			coches.put(modelo, marca);
		}
		return coches;
	}

	public boolean mostrarPantalla(HashMap<String, String> hmap) {
		if (hmap.isEmpty()) {
			return false;
		}

		int i = 1;
		for (HashMap.Entry<String, String> entrada : hmap.entrySet()) {
			System.out.println(i + "º: " + entrada.getValue() + " - " + entrada.getKey());
			i++;
		}
		return true;
	}

	public boolean mostrarPantallaArrayList(ArrayList<String> coches) {
		if (coches.size() == 0) {
			return false;
		}

		for (int i = 0; i < coches.size(); i++) {
			System.out.println(coches.get(i) + ";" + coches.get(++i) + ";" + coches.get(++i));
		}
		return true;
	}
}