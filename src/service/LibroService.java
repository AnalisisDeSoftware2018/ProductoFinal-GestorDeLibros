package service;

import java.util.ArrayList;
import java.util.List;

import dao.LibroDao;
import model.Libro;

public class LibroService {

	private LibroDao libroDao;
	private static LibroService instancia;

	private LibroService() {
		libroDao = LibroDao.getSingletonInstance();
	}

	public static LibroService getSingletonInstance() {
		if (instancia == null) {
			return new LibroService();
		}
		return instancia;
	}

	public Libro getLibro(String isbn) {
		return libroDao.getLibro(isbn);
	}

	public List<Libro> getLibros() {
		return libroDao.getLibros();
	}

	public void save(Libro libro) {
		libroDao.save(libro);
	}

	public void delete(String isbn) {
		libroDao.delete(isbn);
	}

	public List<Libro> getLibroByFiltro(String isbn, String titulo, String autor, String editorial, String edicion,
			String anioPublicacion) {
		List<Libro> libros = this.getLibros(), result = new ArrayList<>();
		for (Libro libro : libros) {
			if (libroEsValido(libro, isbn, titulo, autor, editorial, edicion, anioPublicacion)) {
				result.add(libro);
			}
		}
		return result;
	}

	private boolean libroEsValido(Libro libro, String isbn, String titulo, String autor, String editorial,
			String edicion, String anioPublicacion) {

		if (atributoEsIgualAValor(libro.getIsbn(), isbn)) {
			return false;
		}
		if (atributoContieneValor(libro.getTitulo(), titulo)) {
			return false;
		}
		if (atributoContieneValor(libro.getAutor(), autor)) {
			return false;
		}
		if (atributoContieneValor(libro.getEditorial(), editorial)) {
			return false;
		}
		if (atributoComienzaConValor(libro.getEdicion(), edicion)) {
			return false;
		}
		if (atributoComienzaConValor(libro.getAnioPublicacion(), anioPublicacion)) {
			return false;
		}
		return true;
	}

	private boolean atributoEsIgualAValor(String atributo, String valor) {
		if (!valor.isEmpty() && !atributo.equalsIgnoreCase(valor)) {
			return true;
		}
		return false;
	}

	private boolean atributoContieneValor(String atributo, String valor) {
		if (!valor.isEmpty() && !atributo.toUpperCase().contains(valor.toUpperCase())) {
			return true;
		}
		return false;
	}

	private boolean atributoComienzaConValor(Integer atributo, String valor) {
		if (!valor.isEmpty() && !String.valueOf(atributo).startsWith(valor)) {
			return true;
		}
		return false;
	}
}
