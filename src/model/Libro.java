package model;

import java.util.Comparator;

public class Libro implements Comparable<Libro>, Comparator<Libro> {

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

	public void especificarISBN(String isbn) {
		this.isbn = isbn;
	}
	
	public void especificarTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void especificarAutor(String autor) {
		this.autor = autor;
	}
	
	public void especificarEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	public void especificarEdicion(Integer edicion) {
		this.edicion = edicion;
	}
	
	public void especificarAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}
	
	public String obtenerISBN() {
		return isbn;
	}
	
	public String obtenerTitulo() {
		return titulo;
	}

	public String obtenerAutor() {
		return autor;
	}
	
	public String obtenerEditorial() {
		return editorial;
	}
	
	public Integer obtenerEdicion() {
		return edicion;
	}
	
	public Integer obtenerAnioPublicacion() {
		return anioPublicacion;
	}

	@Override
	public int compareTo(Libro otro) {
		return Long.valueOf(this.isbn).intValue() - Long.valueOf(otro.isbn).intValue();
	}
	
	@Override
	public int compare(Libro uno, Libro dos) {
		return uno.compareTo(dos);
	}

	public String[] obtenerFormatoFila() {
		return new String[]{obtenerISBN(),obtenerTitulo(),obtenerAutor(),obtenerEditorial(),String.valueOf(obtenerEdicion()),String.valueOf(obtenerAnioPublicacion())};
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
		if (this == obj) return true;
		
		if (obj == null) return false;
		
		if (getClass() != obj.getClass()) return false;
		
		Libro other = (Libro) obj;
		
		if (isbn == null) {
			return other.isbn == null;
		} else if (!isbn.equals(other.isbn)) return false;

		return true;
	}

	
	
}