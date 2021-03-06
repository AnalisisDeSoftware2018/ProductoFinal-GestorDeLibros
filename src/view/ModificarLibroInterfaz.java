package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Libro;
import service.LibroService;
import service.LogService;

class ModificarLibroInterfaz {

	private JDialog frame;
	private JLabel lblMensajeDeError;
	private JTextField txfISBN;
	private JTextField txfTitulo;
	private JTextField txfAutor;
	private JTextField txfEditorial;
	private JTextField txfEdicion;
	private JTextField txfAnioPublicacion;
	private Libro libro;
	private String viejoIsbn;
	private ABMInterfaz abmInterfaz;
	private LibroService libroService;
	private LogService logService;
	
	ModificarLibroInterfaz(Libro libro, ABMInterfaz abmInterfaz) {
		this.abmInterfaz = abmInterfaz;
		this.libro = libro;
		this.viejoIsbn = libro.obtenerISBN();
		libroService = LibroService.obtenerSingletonInstance();
		logService = LogService.getSingletonInstance();
		initialize();
	}

	private void initialize() {
		int anchoCampo = 150;
		frame = new JDialog(abmInterfaz,"Modificar libro",true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 381, 282);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCompleteLosCampos = new JLabel("Modifique los campos necesarios:");
		lblCompleteLosCampos.setFont(lblCompleteLosCampos.getFont().deriveFont(lblCompleteLosCampos.getFont().getStyle() | Font.BOLD));
		lblCompleteLosCampos.setBounds(10, 11, 250, 14);
		frame.getContentPane().add(lblCompleteLosCampos);
		
		txfISBN = new JTextField();
		txfISBN.setToolTipText("");
		txfISBN.setBounds(10, 50, anchoCampo, 20);
		txfISBN.setText(libro.obtenerISBN());
		frame.getContentPane().add(txfISBN);
		txfISBN.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(10, 36, anchoCampo, 14);
		frame.getContentPane().add(lblIsbn);
		
		JLabel lblTtulo = new JLabel("Título:");
		lblTtulo.setBounds(10, 81, anchoCampo, 14);
		frame.getContentPane().add(lblTtulo);
		
		txfTitulo = new JTextField();
		txfTitulo.setBounds(10, 95, anchoCampo, 20);
		txfTitulo.setText(libro.obtenerTitulo());
		frame.getContentPane().add(txfTitulo);
		txfTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 126, anchoCampo, 14);
		frame.getContentPane().add(lblAutor);
		
		txfAutor = new JTextField();
		txfAutor.setBounds(10, 140, anchoCampo, 20);
		txfAutor.setText(libro.obtenerAutor());
		frame.getContentPane().add(txfAutor);
		txfAutor.setColumns(10);
		
		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(217, 36, anchoCampo, 14);
		frame.getContentPane().add(lblEditorial);
		
		txfEditorial = new JTextField();
		txfEditorial.setBounds(217, 50, anchoCampo, 20);
		txfEditorial.setText(libro.obtenerEditorial());
		frame.getContentPane().add(txfEditorial);
		txfEditorial.setColumns(10);
		
		JLabel lblEdicin = new JLabel("Edición:");
		lblEdicin.setBounds(217, 81, anchoCampo, 14);
		frame.getContentPane().add(lblEdicin);
		
		txfEdicion = new JTextField();
		txfEdicion.setBounds(217, 95, anchoCampo, 20);
		txfEdicion.setText(libro.obtenerEdicion().toString());
		frame.getContentPane().add(txfEdicion);
		txfEdicion.setColumns(10);
		
		JLabel lblAoPublicacin = new JLabel("Año publicación:");
		lblAoPublicacin.setBounds(217, 126, anchoCampo, 14);
		frame.getContentPane().add(lblAoPublicacin);
		
		txfAnioPublicacion = new JTextField();
		txfAnioPublicacion.setBounds(217, 140, anchoCampo, 20);
		txfAnioPublicacion.setText(libro.obtenerAnioPublicacion().toString());
		frame.getContentPane().add(txfAnioPublicacion);
		txfAnioPublicacion.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(arg0 -> modificar());
		btnAceptar.setBounds(71, 196, 89, 23);
		frame.getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(arg0 -> frame.dispose());
		btnCancelar.setBounds(217, 196, 96, 23);
		frame.getContentPane().add(btnCancelar);
		
		lblMensajeDeError = new JLabel("Mensaje de error");
		lblMensajeDeError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMensajeDeError.setForeground(Color.RED);
		lblMensajeDeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeDeError.setBounds(10, 171, 374, 14);
		lblMensajeDeError.setVisible(false);
		frame.getContentPane().add(lblMensajeDeError);

		frame.setVisible(true);
	}
	
	/** 
	 * Para modificar el libro lo que tengo que hacer es borrar el registro e insertarlo de nuevo modificado,
	 * ya que el formato txt no me deja modificar renglones.
	 * Lo que hago es tomar el objeto libro que viene desde el constructor, y de él saco los datos para un nuevo objeto
	 * 'nuevo' (clase Libro). De paso, lleno los campos con los datos viejos para hacer más rápido el proceso.
	 *
	 * Necesito validar el tema del ISBN:
	 * - Si el ISBN nuevo (del nuevo objeto "modificado") es igual al viejo, estoy modificando el mismo libro, por lo tanto
	 *   no tengo problema de duplicados.
	 *
	 * - Si el ISBN nuevo es distinto al viejo, debo chequear que no esté pisando otro libro ya registrado (recuerden que yo
	 *   BORRO el registro, estaría borrando data que no debería en ese caso).
	 *   En este caso, pruebo insertar de una en el txt. Si pude insertar bien, significa que no es duplicado y uso un ISBN libre,
	 *   entonces borro el registro anterior tranquilo.
	 *   Si no pude insertar, es porque el ISBN ya existía y no es del mismo libro, aborto la inserción y aviso por pantalla.
	 */
	private void modificar() {

		if(campoVacio()) {
			lblMensajeDeError.setText("Complete los campos por favor");
			lblMensajeDeError.setVisible(true);
		} else {
			String isbn = txfISBN.getText();
			String titulo = txfTitulo.getText(); 
			String autor = txfAutor.getText();
			String editorial = txfEditorial.getText();
			String edicion = txfEdicion.getText();
			String anioPublicacion = txfAnioPublicacion.getText();
			
			Libro nuevo;
			try {
				nuevo = new Libro(isbn, titulo, autor, editorial, edicion, anioPublicacion);
				
				if(viejoIsbn.equals(nuevo.obtenerISBN())) {				//estoy modificando otro campo que no es el isbn, no hay problema de insercion
					libroService.eliminar(nuevo.obtenerISBN());
					if(libroService.guardar(nuevo)) {
						abmInterfaz.buscar();
						logService.logModificarLibro(isbn, true);
						JOptionPane.showMessageDialog(null, "Libro modificado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					} else {
						logService.logModificarLibro(isbn, false);
						lblMensajeDeError.setText("No se pudo modificar el libro nuevo");
						lblMensajeDeError.setVisible(true);
					}
				} else {														//si modifique el isbn, puedo tener problema de duplicados con otros libros
					if(libroService.guardar(nuevo)) {
						libroService.eliminar(libro.obtenerISBN());
						abmInterfaz.buscar();
						logService.logModificarLibro(isbn, true);
						JOptionPane.showMessageDialog(null, "Libro modificado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					} else {
						logService.logModificarLibro(isbn, false);
						lblMensajeDeError.setText("No se pudo modificar el libro nuevo");
						lblMensajeDeError.setVisible(true);
					}
				}
			} catch (Exception e) {
				lblMensajeDeError.setText(e.getMessage());
				lblMensajeDeError.setVisible(true);
			}

			
		}
	}
	
	private boolean campoVacio() {
		return  txfISBN.getText().isEmpty() 			|| 
				txfAutor.getText().isEmpty() 			|| 
				txfTitulo.getText().isEmpty() 			|| 
				txfAnioPublicacion.getText().isEmpty() 	||
				txfEditorial.getText().isEmpty() 		||
				txfEdicion.getText().isEmpty();
	}
}
