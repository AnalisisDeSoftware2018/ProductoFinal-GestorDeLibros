package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Libro;

public class LibroDao {

	private static LibroDao instancia;
	private final static String SEPARADOR = "&&";
	private String path;
	private String decodedPath;

	private LibroDao() {
		getPath();
	}

	public static LibroDao obtenerSingletonInstance() {
		return instancia != null ? instancia : new LibroDao();
	}

	public Libro obtenerLibro(String isbn) {
		for(Libro libro: obtenerLibros()) {
			if(libro.obtenerISBN().equals(isbn)) {
				return libro;
			}
		}
		return null;
	}

	public List<Libro> obtenerLibros() {
		List<Libro> libros = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new File(decodedPath + "\\Libros.txt"));
			while (sc.hasNextLine()) {
				String[] atributos = sc.nextLine().split(SEPARADOR);
				String isbn = atributos[0];
				String titulo = atributos[1];
				String autor = atributos[2];
				String editorial = atributos[3];
				Integer edicion = Integer.parseInt(atributos[4]);
				Integer anioPublicacion = Integer.parseInt(atributos[5]);
				
				libros.add(new Libro(isbn, titulo, autor, editorial, edicion, anioPublicacion));
			}
			sc.close();
		} catch (FileNotFoundException evento) {
			JOptionPane.showMessageDialog(null, evento.getMessage());
		}

		return libros;
	}

	private boolean libroValido(Libro libro) {					//verifica que el libro a agregar no tenga ISBN repetido
		List<Libro> libros = obtenerLibros();
		
		for(Libro l : libros) {
			if(l.obtenerISBN().equals(libro.obtenerISBN())) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean guardar(Libro libro) {
		BufferedWriter out;
		
		if(libroValido(libro)) {
			try {
				out = new BufferedWriter(new FileWriter(decodedPath + "\\Libros.txt", true));
				out.write(libro.enFormatoRegistro(SEPARADOR));
				out.close();
				
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public List<Libro> ordenar() {
		List<Libro> libros = obtenerLibros();
		libros.sort(new Libro());
        escribirArchivo(libros);

        return libros;
	}

    public List<Libro> eliminar(String isbn) {
		List<Libro> libros = obtenerLibros();
		libros.remove(new Libro(isbn,null,null,null,null,null));
        escribirArchivo(libros);
        
		return libros;
    }

    public void backup() {
    	List<Libro> libros = obtenerLibros();
    	escribirArchivo(libros);
    }
    
    private void escribirArchivo(List<Libro> libros) {
        FileWriter fw;
        PrintWriter pw;
        File fi;
        
        try {
        	fi = new File(decodedPath + "\\Libros.txt");
        	fi.mkdirs();
        	fi.createNewFile();
            fw = new FileWriter(fi);
            pw = new PrintWriter(fw);
            
            for (Libro libro : libros)
            	pw.print(libro.enFormatoRegistro(SEPARADOR));
            
            fw.close();
            pw.close();
        } catch (IOException evento) {
            JOptionPane.showMessageDialog(null, evento.getMessage());
        }
    }
    
	private void getPath() {
		path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\Gestor de Libros";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		File fi = new File(decodedPath);
		if(!fi.exists()) {
			fi.mkdir();
		}
	}

	public static void main(String[] args) {
		LibroDao dao = new LibroDao();
		
		List<Libro> lista;
		
		lista = dao.obtenerLibros();
		
		for (Libro libro : lista)
			System.out.println(Arrays.toString(libro.enFormatoFila()));
		
		System.out.println();
		
		lista = dao.eliminar("10");
		
		for (Libro libro : lista)
			System.out.println(Arrays.toString(libro.enFormatoFila()));

		System.out.println();

		//dao.guardar(new Libro("10","Programacion","Elias Garcia","Puerto de palos",2018,1745));
		
		lista = dao.obtenerLibros();
		
		for (Libro libro : lista)
			System.out.println(Arrays.toString(libro.enFormatoFila()));

		System.out.println();
		
		lista = dao.ordenar();
		
		for (Libro libro : lista)
			System.out.println(Arrays.toString(libro.enFormatoFila()));
	}
}
