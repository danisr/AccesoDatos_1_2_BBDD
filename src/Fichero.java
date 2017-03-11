import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Fichero {
	BufferedReader breader = null;
	BufferedWriter bwriter = null;
	FileReader fReader = null;
	File archivo = null;

	public boolean crearFichero(HashMap<String, String> hmap) {
		System.out.println("Creando Fichero...");
		archivo = new File("fichero.txt");

		try {
			bwriter = new BufferedWriter(new FileWriter(archivo));

			int contador = 1;
			for (HashMap.Entry<String, String> entrada : hmap.entrySet()) {
				bwriter.write(contador + ";" + entrada.getValue() + ";" + entrada.getKey());
				bwriter.newLine();
				System.out.println(
						"Metodo Crear Fichero: " + contador + ";" + entrada.getValue() + ";" + entrada.getKey());
				contador++;
			}
			bwriter.close();
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		if (hmap.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean crearFicheroArrayList(ArrayList<String> coches) {
		System.out.println("Creando Fichero...");
		archivo = new File("fichero.txt");

		try {
			bwriter = new BufferedWriter(new FileWriter(archivo));

			for (int i = 0; i < coches.size(); i++) {
				bwriter.write(coches.get(i) + ";" + coches.get(++i) + ";" + coches.get(++i));
				bwriter.newLine();
				// System.out.println("Metodo Crear Fichero: " + coches.get(i) +
				// ";" + coches.get(++i) + ";" + coches.get(++i));
			}
			bwriter.close();
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		if (coches.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public HashMap<String, String> leerFichero() {
		HashMap<String, String> hmap = new HashMap<String, String>();

		try {
			archivo = new File("fichero.txt");
			fReader = new FileReader(archivo);
			breader = new BufferedReader(fReader);
			String line;
			while ((line = breader.readLine()) != null) {
				String[] aux = line.split(";");
				String marca = aux[1];
				String modelo = aux[2];
				hmap.put(modelo, marca);
			}
			breader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hmap;
	}
}