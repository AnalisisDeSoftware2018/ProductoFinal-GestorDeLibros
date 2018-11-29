package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
			Scanner sc = new Scanner(new File(decodedPath));
			while (sc.hasNextLine()) {
				String[] atributos = sc.nextLine().split(SEPARADOR);
				String isbn = atributos[0];
				String titulo = atributos[1];
				String autor = atributos[2];
				String editorial = atributos[3];
				String edicion = atributos[4];
				String anioPublicacion = atributos[5];
				
				libros.add(new Libro(isbn, titulo, autor, editorial, edicion, anioPublicacion));
			}
			sc.close();
		} catch (Exception evento) {
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
				out = new BufferedWriter(new FileWriter(decodedPath, true));
				out.write(libro.enFormatoRegistro(SEPARADOR));
				out.close();
				
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
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
		try {
			libros.remove(new Libro(isbn,null,null,null,null,null));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
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
        	fi = new File(decodedPath);
        	//fi.mkdirs();
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
		createPath(decodedPath);
		
		path = path + "\\Libros.txt";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		createFile(decodedPath);
	}
	
	private void createPath(String decodedPath) {
		File fi = new File(decodedPath);
		if(!fi.exists()) {
			fi.mkdir();
		}
	}
	
	private void createFile(String decodedPath) {
		File fi = new File(decodedPath);
		if(!fi.exists()) {
			try {
				fi.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
