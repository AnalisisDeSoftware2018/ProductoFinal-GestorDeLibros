package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Libro;
import service.LibroService;

import java.awt.Font;
import javax.swing.JButton;

class ModificarLibroInterfaz {

	private JFrame frame;
	private JTextField campoISBN;
	private JTextField campoTitulo;
	private JTextField campoAutor;
	private JTextField campoEditorial;
	private JTextField campoEdicion;
	private JTextField campoAnioPublicacion;
	private LibroService libroService;
	private Libro libro;
	private String viejoIsbn;
	private ABMInterfaz abmInterfaz;
	
	ModificarLibroInterfaz(Libro libro, ABMInterfaz abmInterfaz) {
		this.abmInterfaz = abmInterfaz;
		this.libro = libro;
		this.viejoIsbn = libro.obtenerISBN();
		libroService = LibroService.obtenerSingletonInstance();
		initialize();
	}

	private void initialize() {
		int anchoCampo = 150;
		frame = new JFrame("Modificar libro");
		frame.setResizable(false);
		frame.setBounds(100, 100, 400, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblCompleteLosCampos = new JLabel("Modifique los campos necesarios:");
		lblCompleteLosCampos.setFont(lblCompleteLosCampos.getFont().deriveFont(lblCompleteLosCampos.getFont().getStyle() | Font.BOLD));
		lblCompleteLosCampos.setBounds(10, 11, 209, 14);
		frame.getContentPane().add(lblCompleteLosCampos);
		
		campoISBN = new JTextField();
		campoISBN.setToolTipText("");
		campoISBN.setBounds(10, 61, anchoCampo, 20);
		campoISBN.setText(libro.obtenerISBN());
		frame.getContentPane().add(campoISBN);
		campoISBN.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(10, 36, anchoCampo, 14);
		frame.getContentPane().add(lblIsbn);
		
		JLabel lblTtulo = new JLabel("Título:");
		lblTtulo.setBounds(10, 92, anchoCampo, 14);
		frame.getContentPane().add(lblTtulo);
		
		campoTitulo = new JTextField();
		campoTitulo.setBounds(10, 117, anchoCampo, 20);
		campoTitulo.setText(libro.obtenerTitulo());
		frame.getContentPane().add(campoTitulo);
		campoTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 148, anchoCampo, 14);
		frame.getContentPane().add(lblAutor);
		
		campoAutor = new JTextField();
		campoAutor.setBounds(10, 173, anchoCampo, 20);
		campoAutor.setText(libro.obtenerAutor());
		frame.getContentPane().add(campoAutor);
		campoAutor.setColumns(10);
		
		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(217, 36, anchoCampo, 14);
		frame.getContentPane().add(lblEditorial);
		
		campoEditorial = new JTextField();
		campoEditorial.setBounds(217, 61, anchoCampo, 20);
		campoEditorial.setText(libro.obtenerEditorial());
		frame.getContentPane().add(campoEditorial);
		campoEditorial.setColumns(10);
		
		JLabel lblEdicin = new JLabel("Edición:");
		lblEdicin.setBounds(217, 92, anchoCampo, 14);
		frame.getContentPane().add(lblEdicin);
		
		campoEdicion = new JTextField();
		campoEdicion.setBounds(217, 117, anchoCampo, 20);
		campoEdicion.setText(libro.obtenerEdicion().toString());
		frame.getContentPane().add(campoEdicion);
		campoEdicion.setColumns(10);
		
		JLabel lblAoPublicacin = new JLabel("Año publicación:");
		lblAoPublicacin.setBounds(217, 148, anchoCampo, 14);
		frame.getContentPane().add(lblAoPublicacin);
		
		campoAnioPublicacion = new JTextField();
		campoAnioPublicacion.setBounds(217, 173, anchoCampo, 20);
		campoAnioPublicacion.setText(libro.obtenerAnioPublicacion().toString());
		frame.getContentPane().add(campoAnioPublicacion);
		campoAnioPublicacion.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(arg0 -> modificar());
		btnAceptar.setBounds(65, 210, 89, 23);
		frame.getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(arg0 -> frame.dispose());
		btnCancelar.setBounds(188, 210, 89, 23);
		frame.getContentPane().add(btnCancelar);

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
			JOptionPane.showMessageDialog(null, "Complete los campos por favor", "", JOptionPane.ERROR_MESSAGE);
		} else {
			String isbn = campoISBN.getText();
			String titulo = campoTitulo.getText(); 
			String autor = campoAutor.getText();
			String editorial = campoEditorial.getText();
			Integer edicion = Integer.parseInt(campoEdicion.getText());
			Integer anioPublicacion = Integer.parseInt(campoAnioPublicacion.getText());
			
			Libro nuevo = new Libro(isbn, titulo, autor, editorial, edicion, anioPublicacion);

			if(viejoIsbn.equals(nuevo.obtenerISBN())) {				//estoy modificando otro campo que no es el isbn, no hay problema de insercion
				libroService.eliminar(nuevo.obtenerISBN());
				if(libroService.guardar(nuevo)) {
					abmInterfaz.buscar();
					JOptionPane.showMessageDialog(null, "Libro modificado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "No se pudo modificar el libro nuevo", "", JOptionPane.ERROR_MESSAGE);
				}
			} else {														//si modifique el isbn, puedo tener problema de duplicados con otros libros
				if(libroService.guardar(nuevo)) {
					libroService.eliminar(libro.obtenerISBN());
					abmInterfaz.buscar();
					JOptionPane.showMessageDialog(null, "Libro modificado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "No se pudo modificar el libro nuevo", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private boolean campoVacio() {
		return  campoISBN.getText().isEmpty() 		|| 
				campoAutor.getText().isEmpty() 		|| 
				campoTitulo.getText().isEmpty() 		|| 
				campoAnioPublicacion.getText().isEmpty() ||
				campoEditorial.getText().isEmpty() 	||
				campoEdicion.getText().isEmpty();
	}
}
