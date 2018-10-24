package service;

import java.util.ArrayList;
import java.util.List;

import dao.LibroDao;
import model.Libro;

public class LibroService {

	private LibroDao libroDao;
	private static LibroService instancia;

	private LibroService() {
		libroDao = LibroDao.obtenerSingletonInstance();
	}

	public static LibroService obtenerSingletonInstance() {

		return instancia != null ? instancia : new LibroService();
	}

	public Libro obtenerLibro(String isbn) {
		return libroDao.obtenerLibro(isbn);
	}

	public boolean guardar(Libro libro) {
		return libroDao.guardar(libro);
	}

	public List<Libro> ordenar() {
		return libroDao.ordenar();
	}

	public void eliminar(String isbn) {
		libroDao.eliminar(isbn);
	}

	public List<Libro> obtenerLibroConFiltro(String isbn, String titulo, String autor, String editorial, String edicion,
			String anioPublicacion) {
		List<Libro> libros = this.obtenerLibros(), result = new ArrayList<>();
		for (Libro libro : libros) {
			if (libroEsValido(libro, isbn, titulo, autor, editorial, edicion, anioPublicacion)) {
				result.add(libro);
			}
		}
		return result;
	}

	private List<Libro> obtenerLibros() {
		return libroDao.obtenerLibros();
	}

	private boolean libroEsValido(Libro libro, String isbn, String titulo, String autor, String editorial,
			String edicion, String anioPublicacion) {

		if (atributoEsIgualAValor(libro.obtenerISBN(), isbn)) {
			return false;
		}
		if (atributoContieneValor(libro.obtenerTitulo(), titulo)) {
			return false;
		}
		if (atributoContieneValor(libro.obtenerAutor(), autor)) {
			return false;
		}
		if (atributoContieneValor(libro.obtenerEditorial(), editorial)) {
			return false;
		}
		if (atributoComienzaConValor(libro.obtenerEdicion(), edicion)) {
			return false;
		}
		if (atributoComienzaConValor(libro.obtenerAnioPublicacion(), anioPublicacion)) {
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
