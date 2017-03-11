import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ConexionBBDD {
	Connection conexion;

	public void conexion() {
		System.out.println("Conectando...");
		Properties propiedades = new Properties();
		InputStream entrada = null;

		try {
			entrada = new FileInputStream("configbbdd.ini");
			propiedades.load(entrada);

			String bbdd = propiedades.getProperty("bbdd");
			String user = propiedades.getProperty("user");
			String pwd = propiedades.getProperty("pass");
			String url = "jdbc:mysql://localhost/" + bbdd;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					conexion = DriverManager.getConnection(url, user, pwd);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Conexion establecida");
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				System.out.println("No tengo el driver cargado");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean insertBBDD(HashMap<String, String> hmap) {	
		ArrayList<String> ok = new ArrayList<String>();
		ArrayList<String> ko = new ArrayList<String>();
		
		try {
			for (HashMap.Entry<String, String> entrada : hmap.entrySet()) {
				String modelo = entrada.getKey();
				String marca = entrada.getValue();
				
				boolean existModelo = ifModeloExists(entrada.getKey()); //Se le pasa el modelo
				if(existModelo) {
					ko.add(marca + " " + modelo);
				} else {
					String query = "INSERT INTO concesionario.coches (marca, modelo) VALUES ( ?, ?)";
					PreparedStatement pstmt = conexion.prepareStatement(query);
					pstmt.setString(1, marca);
					pstmt.setString(2, modelo);
					pstmt.executeUpdate();
					pstmt.close();
					ok.add(marca + " " + modelo);
				}											
			}
			System.out.println("Inserts Correctos: " + ok.size() + ", Inserts Incorrectos: " + ko.size());
			
			//System.out.println("Incorrectos:");
			for (int i = 0; i < ko.size(); i++) {
				System.out.println("Incorrectos: \n" + ko.get(i));
			}
			
		} catch (SQLException e) {
			System.out.println("El coche introducido ya se encuentra registrado");
			e.printStackTrace(); //muestra error sql
		}

		if (ko.size() == 0) { //Si se han introducido todos bien
			return true;
		} else {
			return false;
		}		
	}
	
	public boolean ifModeloExists(String modelo) {		
		int iModelo = 0;
		try {
			String sql = "SELECT count(*) FROM concesionario.coches WHERE modelo=?";
			PreparedStatement pstm = conexion.prepareStatement(sql);
			pstm.setString(1, modelo);
			ResultSet rs = pstm.executeQuery();
			
			if (rs.next()) {
				iModelo = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		if(iModelo == 1) { //Existe el modelo de coche
			return true;
		} else {
			return false;
		}
		//return iModelo == 1;
	}	

	public ArrayList<String> selectCoches() {
		System.out.println("Lectura de tabla coches");
		ArrayList<String> coches = new ArrayList<String>();

		try {
			if (conexion != null) {
				String query = "SELECT * FROM concesionario.coches ORDER BY idcoches ASC";
				PreparedStatement pstmt = conexion.prepareStatement(query);
				ResultSet rset = pstmt.executeQuery();

				while (rset.next()) {
					String idCoche = rset.getString("idcoches");
					String marca = rset.getString("marca");
					String modelo = rset.getString("modelo");

					coches.add(idCoche);
					coches.add(marca);
					coches.add(modelo);
				}
				pstmt.close();
				rset.close();
			} else {
				System.out.println("Conexion nula");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coches;
	}
	
	public void limpiarBBDD() {
		try {
			if (conexion != null) {
				String query = "DROP TABLE concesionario.coches;";
				Statement stmt = conexion.createStatement();
				stmt.execute(query);
				String query2 = "CREATE TABLE `concesionario`.`coches` (`idcoches` INT(11) NOT NULL AUTO_INCREMENT, `marca` VARCHAR(45) NOT NULL, `modelo` VARCHAR(45) NOT NULL, PRIMARY KEY (`idcoches`));";
				Statement stmt2 = conexion.createStatement();
				stmt2.execute(query2);

				System.out.println("Borrado de tabla correcta");
			} else {
				System.out.println("No se pudo borar la tabla");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}