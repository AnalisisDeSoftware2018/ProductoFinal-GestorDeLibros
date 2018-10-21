package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Libro;

public class LibroDao {

	private static LibroDao instancia;
	private final static String SEPARADOR = "&&";

	private LibroDao() {
	}

	public static LibroDao getSingletonInstance() {
		if (instancia == null) {
			return new LibroDao();
		}
		return instancia;
	}

	public Libro getLibro(String isbn) {
		return new Libro("1", "Historia I", "Elias Garcia", "Puerto de palos", 2018, 2018);
	}

	public List<Libro> getLibros() {
		List<Libro> libros = new ArrayList<>();
		Scanner sc;
		try {
			sc = new Scanner(new File("Libros.txt"));
			while (sc.hasNextLine()) {
				String[] atributos = sc.nextLine().split(SEPARADOR);
				libros.add(new Libro(atributos[0], atributos[1], atributos[2], atributos[3],
						Integer.parseInt(atributos[4]), Integer.parseInt(atributos[5])));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return libros;
	}

	public void save(Libro libro) {
		// TODO Auto-generated method stub

	}

	public void delete(String isbn) {
		List<Libro> libros = getLibros();
		libros.remove(new Libro(isbn,null,null,null,null,null));
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("Libros.txt");
			pw = new PrintWriter(fw);
			for (Libro libro : libros) {
				pw.println(libro.getIsbn() + SEPARADOR + libro.getTitulo() + SEPARADOR + libro.getAutor() + SEPARADOR
						+ libro.getEditorial() + SEPARADOR + libro.getEdicion() + SEPARADOR
						+ libro.getAnioPublicacion());
			}
			fw.close();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	1&&Historia I&&Elias Garcia&&Puerto de palos&&2018&&1994
//	2&&Historia II&&Elias Garcia&&Puerto de palos&&2018&&1820
//	3&&Historia III&&Elias Garcia&&Puerto de palos&&2018&&2016
//	4&&Matematica&&Elias Garcia&&Puerto de palos&&2018&&2015
//	5&&Lengua&&Elias Garcia&&Puerto de palos&&2018&&1977
//	7&&Salud&&Elias Garcia&&Puerto de palos&&2018&&2012
//	8&&Análisis Sistemas&&Elias Garcia&&Puerto de palos&&2018&&2011
//	9&&Arquitectura&&Elias Garcia&&Puerto de palos&&2018&&2018
//	10&&Programacion&&Elias Garcia&&Puerto de palos&&2018&&1745
//	11&&algebra&&Elias Garcia&&Puerto de palos&&2018&&1810
//	12&&Hibernate&&Elias Garcia&&Puerto de palos&&2018&&2015
//	13&&Spring&&Elias Garcia&&Puerto de palos&&2018&&2014
//	14&&Vaadin 7&&Elias Garcia&&Puerto de palos&&2018&&2013
//	15&&Maria DB&&Elias Garcia&&Puerto de palos&&2018&&2012
//	16&&Informix&&Elias Garcia&&Puerto de palos&&2018&&2011
}
