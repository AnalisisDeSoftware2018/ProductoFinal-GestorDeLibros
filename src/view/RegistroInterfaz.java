package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Usuario;
import service.LogService;
import service.UsuarioService;

import javax.swing.JButton;

import javax.swing.JPasswordField;

class RegistroInterfaz {

	private JFrame frame;
	private JTextField userField;
	private JPasswordField passwordField; 
	private UsuarioService usuarioService;
	private LogService logService;

	/**
	 * Create the application.
	 */
	RegistroInterfaz() {
		usuarioService = UsuarioService.getSingletonInstance();
		logService = LogService.getSingletonInstance();
		initialize();
	} 

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Registro de usuario");
		frame.setResizable(false);
		frame.setBounds(100, 100, 266, 164);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 23, 82, 20);
		frame.getContentPane().add(lblUsuario);
		
		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(10, 58, 82, 20);
		frame.getContentPane().add(lblPass);
		
		userField = new JTextField();
		userField.setBounds(102, 23, 113, 20);
		frame.getContentPane().add(userField);
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(102, 58, 113, 20);
		frame.getContentPane().add(passwordField);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(e -> registrar()); 
		btnConfirmar.setBounds(74, 92, 103, 23);
		frame.getContentPane().add(btnConfirmar);
		
		frame.setVisible(true);
		
	}

	private void registrar() {
		String user = userField.getText();
		String pass = new String(passwordField.getPassword());
		
		if(user.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Complete los campos por favor", "", JOptionPane.ERROR_MESSAGE);
		} else {
			Usuario usuario = new Usuario(userField.getText(), new String(passwordField.getPassword()));
			
			if(!usuarioService.registrarUsuario(usuario)) {
				logService.logRegistrarUser(user, false);
				JOptionPane.showMessageDialog(null, "El usuario ya existe", "", JOptionPane.ERROR_MESSAGE);
			} else {
				logService.logRegistrarUser(user, true);
				JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}
		}
		
	}
}
