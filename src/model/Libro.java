package model;

public class Libro implements Comparable<Libro> {

	private String isbn;
	private String titulo;
	private String autor;
	private String editorial;
	private Integer edicion;
	private Integer anioPublicacion;

	public Libro() {
	}

	public Libro(String isbn, String titulo, String autor, String editorial, Integer edicion, Integer anioPublicacion) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.edicion = edicion;
		this.anioPublicacion = anioPublicacion;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public Integer getEdicion() {
		return edicion;
	}

	public void setEdicion(Integer edicion) {
		this.edicion = edicion;
	}

	public Integer getAnioPublicacion() {
		return anioPublicacion;
	}

	public void setAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	@Override
	public int compareTo(Libro l) {
		return l.isbn.compareTo(isbn);
	}

	public String[] getRowFormat() {
		String[] cadena = {getIsbn(),getTitulo(),getAutor(),getEditorial(),String.valueOf(getEdicion()),String.valueOf(getAnioPublicacion())};
		return cadena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}
	
}