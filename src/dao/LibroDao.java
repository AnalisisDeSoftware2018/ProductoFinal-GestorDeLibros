package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
	private String pathBackup;
	private String decodedPath;
	private String decodedPathBackup;

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
		BufferedWriter outBackup;
		
		if(libroValido(libro)) {
			try {
				out = new BufferedWriter(new FileWriter(decodedPath, true));
				out.write(libro.enFormatoRegistro(SEPARADOR));
				out.close();
				
				outBackup = new BufferedWriter(new FileWriter(decodedPathBackup, true));
				outBackup.write(libro.enFormatoRegistro(SEPARADOR));
				outBackup.close();
				
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
		int i = 0;
		for (Libro libro : libros) {
			if(libro.obtenerISBN().equals(isbn)) break;
			i++;
        }
		try {
			libros.remove(i);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Libro no encontrado");
		}
        escribirArchivo(libros);
        
		return libros;
    }
    
    private void escribirArchivo(List<Libro> libros) {
        FileWriter fw, fwBackup;
        PrintWriter pw, pwBackup;
        File fi, fiBackup;
        
        try {
        	fi = new File(decodedPath);
        	fi.delete();
        	fi.createNewFile();
            fw = new FileWriter(fi);
            pw = new PrintWriter(fw);
            
            fiBackup = new File(decodedPathBackup);
        	fiBackup.delete();
        	fiBackup.createNewFile();
            fwBackup = new FileWriter(fiBackup);
            pwBackup = new PrintWriter(fwBackup);
            
            for (Libro libro : libros) {
            	pw.print(libro.enFormatoRegistro(SEPARADOR));
            	pwBackup.print(libro.enFormatoRegistro(SEPARADOR));
            }
            
            fw.close();
            pw.close();
            fwBackup.close();
            pwBackup.close();
        } catch (IOException evento) {
            JOptionPane.showMessageDialog(null, evento.getMessage());
        }
    }
    
	private void getPath() {
		path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Gestor de Libros";
		pathBackup = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Gestor de Libros backup";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPathBackup = URLDecoder.decode(pathBackup, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		createPath(decodedPath);
		createPath(decodedPathBackup);
		
		path = path + "/Libros.txt";
		pathBackup = pathBackup + "/Libros.txt";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPathBackup = URLDecoder.decode(pathBackup, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		createFile(decodedPath, decodedPathBackup);
	}
	
	private void createPath(String decodedPath) {
		File fi = new File(decodedPath);
		if(!fi.exists()) {
			fi.mkdir();
		}
	}
	
	private void createFile(String decodedPath, String decodedPathBackup) {
		File fi = new File(decodedPath);
		File fiBackup = new File(decodedPathBackup);
		
		if(!fi.exists()) {
			if(fiBackup.exists()) {
				BufferedReader br;
				PrintWriter pw;
				String cad;
				
				try {
					br = new BufferedReader(new FileReader(fiBackup));
					pw = new PrintWriter(new FileWriter(fi));
					
					while((cad = br.readLine()) != null) {
						pw.print(cad);
					}
					
					br.close();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					fi.createNewFile();
					fiBackup.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
