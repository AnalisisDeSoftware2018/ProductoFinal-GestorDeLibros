package view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Libro;
import service.LibroService;

class NuevoLibroInterfaz {

	private JFrame frame;
	private JTextField txfISBN;
	private JTextField txfTitulo;
	private JTextField txfAutor;
	private JTextField txfEditorial;
	private JTextField txfEdicion;
	private JTextField txfAnioPublicacion;
	private LibroService libroService;
	private ABMInterfaz abmInterfaz;

	NuevoLibroInterfaz(ABMInterfaz abmInterfaz) {
		libroService = LibroService.obtenerSingletonInstance();
		this.abmInterfaz = abmInterfaz;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Registrar nuevo libro");
		frame.setResizable(false);
		frame.setBounds(100, 100, 358, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblCompleteLosCampos = new JLabel("Complete los campos:");
		lblCompleteLosCampos.setFont(lblCompleteLosCampos.getFont().deriveFont(lblCompleteLosCampos.getFont().getStyle() | Font.BOLD));
		lblCompleteLosCampos.setBounds(10, 11, 209, 14);
		frame.getContentPane().add(lblCompleteLosCampos);
		
		txfISBN = new JTextField();
		txfISBN.setToolTipText("");
		txfISBN.setBounds(10, 61, 113, 20);
		frame.getContentPane().add(txfISBN);
		txfISBN.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(10, 36, 113, 14);
		frame.getContentPane().add(lblIsbn);
		
		JLabel lblTtulo = new JLabel("Título:");
		lblTtulo.setBounds(10, 92, 113, 14);
		frame.getContentPane().add(lblTtulo);
		
		txfTitulo = new JTextField();
		txfTitulo.setBounds(10, 117, 113, 20);
		frame.getContentPane().add(txfTitulo);
		txfTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 148, 113, 14);
		frame.getContentPane().add(lblAutor);
		
		txfAutor = new JTextField();
		txfAutor.setBounds(10, 173, 113, 20);
		frame.getContentPane().add(txfAutor);
		txfAutor.setColumns(10);
		
		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(217, 36, 113, 14);
		frame.getContentPane().add(lblEditorial);
		
		txfEditorial = new JTextField();
		txfEditorial.setBounds(217, 61, 113, 20);
		frame.getContentPane().add(txfEditorial);
		txfEditorial.setColumns(10);
		
		JLabel lblEdicion = new JLabel("Edición:");
		lblEdicion.setBounds(217, 92, 113, 14);
		frame.getContentPane().add(lblEdicion);
		
		txfEdicion = new JTextField();
		txfEdicion.setBounds(217, 117, 113, 20);
		frame.getContentPane().add(txfEdicion);
		txfEdicion.setColumns(10);
		
		JLabel lblAnioPublicacion = new JLabel("Año publicación:");
		lblAnioPublicacion.setBounds(217, 148, 113, 14);
		frame.getContentPane().add(lblAnioPublicacion);
		
		txfAnioPublicacion = new JTextField();
		txfAnioPublicacion.setBounds(217, 173, 113, 20);
		frame.getContentPane().add(txfAnioPublicacion);
		txfAnioPublicacion.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(e -> agregar());
		btnAceptar.setBounds(65, 210, 89, 23);
		frame.getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> frame.dispose());
		btnCancelar.setBounds(188, 210, 89, 23);
		frame.getContentPane().add(btnCancelar);
		
		frame.setVisible(true);
	}
	
	private void agregar() {

		if(campoVacio()) {
			JOptionPane.showMessageDialog(null, "Complete los campos por favor", "", JOptionPane.ERROR_MESSAGE);
		} else {
			String isbn = txfISBN.getText();
			String titulo = txfTitulo.getText(); 
			String autor = txfAutor.getText();
			String editorial = txfEditorial.getText();
			Integer edicion = Integer.parseInt(txfEdicion.getText());
			Integer anioPublicacion = Integer.parseInt(txfAnioPublicacion.getText());
			
			Libro nuevo = new Libro(isbn, titulo, autor, editorial, edicion, anioPublicacion);

			if(libroService.guardar(nuevo)) {
				abmInterfaz.buscar();
				JOptionPane.showMessageDialog(null, "Libro guardado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "No se pudo guardar el libro nuevo", "", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private boolean campoVacio() {
		return  txfISBN.getText().isEmpty() 			||
				txfAutor.getText().isEmpty() 			||
				txfTitulo.getText().isEmpty() 			||
				txfAnioPublicacion.getText().isEmpty()	||
				txfEditorial.getText().isEmpty() 		||
				txfEdicion.getText().isEmpty();
	}
}
