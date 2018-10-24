package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Libro;
import service.LibroService;

public class NuevoLibroInterfaz {

	private JFrame frame;
	private JTextField txtIsbn;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtEditorial;
	private JTextField txtEdicin;
	private JTextField txtAoPublicacin;
	private LibroService libroService;
	private ABMInterfaz abmInterfaz;

	public NuevoLibroInterfaz(ABMInterfaz abmInterfaz) {
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
		
		txtIsbn = new JTextField();
		txtIsbn.setToolTipText("");
		txtIsbn.setBounds(10, 61, 113, 20);
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
		frame.getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 148, 113, 14);
		frame.getContentPane().add(lblAutor);
		
		txtAutor = new JTextField();
		txtAutor.setBounds(10, 173, 113, 20);
		frame.getContentPane().add(txtAutor);
		txtAutor.setColumns(10);
		
		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(217, 36, 113, 14);
		frame.getContentPane().add(lblEditorial);
		
		txtEditorial = new JTextField();
		txtEditorial.setBounds(217, 61, 113, 20);
		frame.getContentPane().add(txtEditorial);
		txtEditorial.setColumns(10);
		
		JLabel lblEdicin = new JLabel("Edición:");
		lblEdicin.setBounds(217, 92, 113, 14);
		frame.getContentPane().add(lblEdicin);
		
		txtEdicin = new JTextField();
		txtEdicin.setBounds(217, 117, 113, 20);
		frame.getContentPane().add(txtEdicin);
		txtEdicin.setColumns(10);
		
		JLabel lblAoPublicacin = new JLabel("Año publicación:");
		lblAoPublicacin.setBounds(217, 148, 113, 14);
		frame.getContentPane().add(lblAoPublicacin);
		
		txtAoPublicacin = new JTextField();
		txtAoPublicacin.setBounds(217, 173, 113, 20);
		frame.getContentPane().add(txtAoPublicacin);
		txtAoPublicacin.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtIsbn.getText().isEmpty() || txtAutor.getText().isEmpty() || txtTitulo.getText().isEmpty()
						|| txtAoPublicacin.getText().isEmpty() || txtEditorial.getText().isEmpty() || txtEdicin.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete los campos por favor", "", JOptionPane.ERROR_MESSAGE);
				} else {
					Libro nuevo = new Libro(txtIsbn.getText(), txtTitulo.getText(), txtAutor.getText(), txtEditorial.getText(), Integer.parseInt(txtEdicin.getText()), Integer.parseInt(txtAoPublicacin.getText()));
					
					if(libroService.guardar(nuevo) == true) {
						abmInterfaz.buscarLibros();
						JOptionPane.showMessageDialog(null, "Libro guardado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "No se pudo guardar el libro nuevo", "", JOptionPane.ERROR_MESSAGE);
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
