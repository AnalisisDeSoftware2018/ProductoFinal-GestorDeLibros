package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import model.Libro;

public class LibroDao {

	private static LibroDao instancia;
	private final static String SEPARADOR = "&&";

	private LibroDao() {
	}

	public static LibroDao obtenerSingletonInstance() {
//		if (instancia == null)
//			return new LibroDao();
//		
//		return instancia;
		
		return instancia != null ? instancia : new LibroDao();
	}

	public Libro obtenerLibro(String isbn) {
		return new Libro("1", "Historia I", "Elias Garcia", "Puerto de palos", 2018, 2018);
	}

	public List<Libro> obtenerLibros() {
		List<Libro> libros = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new File("Libros.txt"));
			while (sc.hasNextLine()) {
				String[] atributos = sc.nextLine().split(SEPARADOR);
				libros.add(new Libro(atributos[0], atributos[1], atributos[2], atributos[3],
						Integer.parseInt(atributos[4]), Integer.parseInt(atributos[5])));
			}
			sc.close();
		} catch (FileNotFoundException evento) {
			JOptionPane.showMessageDialog(null, evento.getMessage());
		}

		return libros;
	}

	public void guardar(Libro libro) {
		// TODO Auto-generated method stub

	}
	
	public List<Libro> ordenar() {
		List<Libro> libros = obtenerLibros();
		libros.sort(new Libro());
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("Libros.txt");
			pw = new PrintWriter(fw);
			for (Libro libro : libros) {
				pw.println(libro.obtenerISBN() + SEPARADOR + libro.obtenerTitulo() + SEPARADOR + libro.obtenerAutor() + SEPARADOR
						+ libro.obtenerEditorial() + SEPARADOR + libro.obtenerEdicion() + SEPARADOR
						+ libro.obtenerAnioPublicacion());
			}
			fw.close();
			pw.close();
		} catch (IOException evento) {
			JOptionPane.showMessageDialog(null, evento.getMessage());
		}
		
		return libros;
	}

	public void eliminar(String isbn) {
		List<Libro> libros = obtenerLibros();
		libros.remove(new Libro(isbn,null,null,null,null,null));
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("Libros.txt");
			pw = new PrintWriter(fw);
			for (Libro libro : libros) {
				pw.println(libro.obtenerISBN() + SEPARADOR + libro.obtenerTitulo() + SEPARADOR + libro.obtenerAutor() + SEPARADOR
						+ libro.obtenerEditorial() + SEPARADOR + libro.obtenerEdicion() + SEPARADOR
						+ libro.obtenerAnioPublicacion());
			}
			fw.close();
			pw.close();
		} catch (IOException evento) {
			JOptionPane.showMessageDialog(null, evento.getMessage());
		}
	}
	
	public static void main(String[] args) {
		LibroDao dao = new LibroDao();
		
		List<Libro> lista;
		
		lista = dao.obtenerLibros();
		
		for (Libro libro : lista) {
			System.out.println(Arrays.toString(libro.obtenerFormatoFila()));
		}
		
		lista = dao.ordenar();
		
		for (Libro libro : lista) {
			System.out.println(Arrays.toString(libro.obtenerFormatoFila()));
		}
	}
}
