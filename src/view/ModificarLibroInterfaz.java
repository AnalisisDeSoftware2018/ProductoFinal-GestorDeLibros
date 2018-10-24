package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Libro;
import service.LibroService;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarLibroInterfaz {

	private JFrame frame;
	private JTextField txtIsbn;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtEditorial;
	private JTextField txtEdicin;
	private JTextField txtAoPublicacin;
	private LibroService libroService;
	private Libro libro;
	private String viejoIsbn;
	private ABMInterfaz abmInterfaz;
	
	public ModificarLibroInterfaz(Libro libro,ABMInterfaz abmInterfaz) {
		this.abmInterfaz = abmInterfaz;
		this.libro = libro;
		this.viejoIsbn = libro.obtenerISBN();
		libroService = LibroService.obtenerSingletonInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Modificar libro");
		frame.setResizable(false);
		frame.setBounds(100, 100, 358, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblCompleteLosCampos = new JLabel("Modifique los campos necesarios:");
		lblCompleteLosCampos.setFont(lblCompleteLosCampos.getFont().deriveFont(lblCompleteLosCampos.getFont().getStyle() | Font.BOLD));
		lblCompleteLosCampos.setBounds(10, 11, 209, 14);
		frame.getContentPane().add(lblCompleteLosCampos);
		
		txtIsbn = new JTextField();
		txtIsbn.setToolTipText("");
		txtIsbn.setBounds(10, 61, 113, 20);
		txtIsbn.setText(libro.obtenerISBN());
		frame.getContentPane().add(txtIsbn);
		txtIsbn.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(10, 36, 113, 14);
		frame.getContentPane().add(lblIsbn);
		
		JLabel lblTtulo = new JLabel("Título:");
		lblTtulo.setBounds(10, 92, 113, 14);
		frame.getContentPane().add(lblTtulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(10, 117, 113, 20);
		txtTitulo.setText(libro.obtenerTitulo());
		frame.getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 148, 113, 14);
		frame.getContentPane().add(lblAutor);
		
		txtAutor = new JTextField();
		txtAutor.setBounds(10, 173, 113, 20);
		txtAutor.setText(libro.obtenerAutor());
		frame.getContentPane().add(txtAutor);
		txtAutor.setColumns(10);
		
		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(217, 36, 113, 14);
		frame.getContentPane().add(lblEditorial);
		
		txtEditorial = new JTextField();
		txtEditorial.setBounds(217, 61, 113, 20);
		txtEditorial.setText(libro.obtenerEditorial());
		frame.getContentPane().add(txtEditorial);
		txtEditorial.setColumns(10);
		
		JLabel lblEdicin = new JLabel("Edición:");
		lblEdicin.setBounds(217, 92, 113, 14);
		frame.getContentPane().add(lblEdicin);
		
		txtEdicin = new JTextField();
		txtEdicin.setBounds(217, 117, 113, 20);
		txtEdicin.setText(libro.obtenerEdicion().toString());
		frame.getContentPane().add(txtEdicin);
		txtEdicin.setColumns(10);
		
		JLabel lblAoPublicacin = new JLabel("Año publicación:");
		lblAoPublicacin.setBounds(217, 148, 113, 14);
		frame.getContentPane().add(lblAoPublicacin);
		
		txtAoPublicacin = new JTextField();
		txtAoPublicacin.setBounds(217, 173, 113, 20);
		txtAoPublicacin.setText(libro.obtenerAnioPublicacion().toString());
		frame.getContentPane().add(txtAoPublicacin);
		txtAoPublicacin.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				/* Para modificar el libro lo que tengo que hacer es borrar el registro e insertarlo de nuevo modificado,
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
				
				if(txtIsbn.getText().isEmpty() || txtAutor.getText().isEmpty() || txtTitulo.getText().isEmpty()
						|| txtAoPublicacin.getText().isEmpty() || txtEditorial.getText().isEmpty() || txtEdicin.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete los campos por favor", "", JOptionPane.ERROR_MESSAGE);
				} else {
					Libro nuevo = new Libro(txtIsbn.getText(), txtTitulo.getText(), txtAutor.getText(), txtEditorial.getText(), Integer.parseInt(txtEdicin.getText()), Integer.parseInt(txtAoPublicacin.getText()));
					
					if(viejoIsbn.equals(nuevo.obtenerISBN()) == true) {				//estoy modificando otro campo que no es el isbn, no hay problema de insercion
						libroService.eliminar(nuevo.obtenerISBN());
						if(libroService.guardar(nuevo) == true) {
							JOptionPane.showMessageDialog(null, "Libro modificado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
							abmInterfaz.buscarLibros();
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "No se pudo modificar el libro nuevo", "", JOptionPane.ERROR_MESSAGE);
						}
					} else {														//si modifique el isbn, puedo tener problema de duplicados con otros libros
						if(libroService.guardar(nuevo) == true) {
							libroService.eliminar(libro.obtenerISBN());
							JOptionPane.showMessageDialog(null, "Libro modificado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
							abmInterfaz.buscarLibros();
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "No se pudo modificar el libro nuevo", "", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		btnAceptar.setBounds(65, 210, 89, 23);
		frame.getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnCancelar.setBounds(188, 210, 89, 23);
		frame.getContentPane().add(btnCancelar);
		
		frame.setVisible(true);
	}
}
