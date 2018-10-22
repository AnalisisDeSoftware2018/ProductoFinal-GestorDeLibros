package view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Libro;
import service.LibroService;

public class ABMInterfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private LibroService libroService;
	private JTextField txfIsbn;
	private JTextField txfTitulo;
	private JTextField txfAutor;
	private JTextField txfEditorial;
	private JTextField txfEdicion;
	private JTextField txfAnioPublicion;
	private JTable tabla;
	private TableModel tablaModel;
	private String[] columnas = { "ISBN", "Titulo", "Autor", "Editorial", "Edicion", "Año publicacion" };
	private JButton btnNuevo;
	private JButton btnEliminar;
	private JButton btnModificar;
	private JButton btnBuscar;
	private JButton btnLimpiar;
	private JButton btnOrdenar;

	public ABMInterfaz() {
		libroService = LibroService.obtenerSingletonInstance();
		especificarComponents();
		especificarListeners();
	}

	private void especificarComponents() {
		setTitle("Gestor de Libros");
		setBounds(100, 100, 900, 506);
		setResizable(Boolean.FALSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 108, 874, 324);
		getContentPane().add(scrollPane);

		tablaModel = new DefaultTableModel(null, columnas);
		tabla = new JTable(tablaModel);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tabla);

		txfIsbn = new JTextField();
		txfIsbn.setBounds(12, 71, 105, 25);
		getContentPane().add(txfIsbn);
		txfIsbn.setColumns(10);

		txfTitulo = new JTextField();
		txfTitulo.setColumns(10);
		txfTitulo.setBounds(124, 71, 105, 25);
		getContentPane().add(txfTitulo);

		txfAutor = new JTextField();
		txfAutor.setColumns(10);
		txfAutor.setBounds(231, 71, 105, 25);
		getContentPane().add(txfAutor);

		txfEditorial = new JTextField();
		txfEditorial.setColumns(10);
		txfEditorial.setBounds(348, 71, 105, 25);
		getContentPane().add(txfEditorial);

		txfEdicion = new JTextField();
		txfEdicion.setColumns(10);
		txfEdicion.setBounds(457, 71, 105, 25);
		getContentPane().add(txfEdicion);

		txfAnioPublicion = new JTextField();
		txfAnioPublicion.setColumns(10);
		txfAnioPublicion.setBounds(569, 71, 105, 25);
		getContentPane().add(txfAnioPublicion);

		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(12, 54, 70, 15);
		getContentPane().add(lblIsbn);

		JLabel lblNombre = new JLabel("Titulo");
		lblNombre.setBounds(124, 54, 78, 15);
		getContentPane().add(lblNombre);

		JLabel lblEdicion = new JLabel("Autor");
		lblEdicion.setBounds(238, 54, 70, 15);
		getContentPane().add(lblEdicion);

		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setBounds(345, 54, 70, 15);
		getContentPane().add(lblEditorial);

		JLabel label_1 = new JLabel("Edicion");
		label_1.setBounds(457, 54, 70, 15);
		getContentPane().add(label_1);

		JLabel lblAoPublicacion = new JLabel("Año public.");
		lblAoPublicacion.setBounds(569, 54, 94, 15);
		getContentPane().add(lblAoPublicacion);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(694, 34, 192, 25);
		getContentPane().add(btnNuevo);
		
		btnOrdenar = new JButton("Ordenar");
		btnOrdenar.setBounds(551, 444, 100, 25);
		getContentPane().add(btnOrdenar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(769, 444, 100, 25);
		getContentPane().add(btnEliminar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(660, 444, 100, 25);
		getContentPane().add(btnModificar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(686, 71, 94, 25);
		getContentPane().add(btnBuscar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(792, 71, 94, 25);
		getContentPane().add(btnLimpiar);
	}

	private void buscarLibros() {
		String isbn = txfIsbn.getText();
		String titulo = txfTitulo.getText();
		String autor = txfAutor.getText();
		String editorial = txfEditorial.getText();
		String edicion = txfEdicion.getText();
		String anioPublicacion = txfAnioPublicion.getText();

		List<Libro> libros = libroService.obtenerLibroConFiltro(isbn, titulo, autor, editorial, edicion, anioPublicacion);

		tablaModel = new DefaultTableModel(obtenerLibrosToModel(libros), columnas);

		tabla.setModel(tablaModel);
	}
	
	private void ordenarLibros() {
		
		tablaModel = new DefaultTableModel(obtenerLibrosToModel(libroService.ordenar()), columnas);

		tabla.setModel(tablaModel);
	}

	private Object[][] obtenerLibrosToModel(List<Libro> libros) {
		String[][] filas = new String[libros.size()][6];
		int i = 0;
		for (Libro libro : libros)
			filas[i++] = libro.obtenerFormatoFila();

		return filas;
	}

	private void especificarListeners() {
		btnBuscar.addActionListener(e -> buscarLibros());
		btnLimpiar.addActionListener(e -> limpiar());
		// btnNuevo.addActionListener(e->nuevoLibro());
		// btnModificar.addActionListener(e->modificar());
		btnOrdenar.addActionListener(e -> ordenarLibros());
		btnEliminar.addActionListener(e -> eliminar());
	}

	private void eliminar() {
		if (tabla.getSelectedRow() == -1)
			JOptionPane.showMessageDialog(null, "Debe seleccionar un libro.");
		else
			try {
				String isbn = String.valueOf(tabla.getModel().getValueAt(tabla.getSelectedRow(), 0));
				libroService.eliminar(isbn);
				buscarLibros();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "No se pudo eliminar correctamente.");
			}
	}

	private void limpiar() {
		txfIsbn.setText("");
		txfTitulo.setText("");
		txfAutor.setText("");
		txfEditorial.setText("");
		txfEdicion.setText("");
		txfAnioPublicion.setText("");
	}
}
