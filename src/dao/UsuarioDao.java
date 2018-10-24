package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.mindrot.jbcrypt.*;
import model.Usuario;

public class UsuarioDao {

	private static UsuarioDao instancia;
	private final static String SEPARADOR = "&&";

	private UsuarioDao() {
	}

	public static UsuarioDao getSingletonInstance() {
		if (instancia == null) {
			return new UsuarioDao();
		}
		return instancia;
	}
	
	public boolean usuarioValido(Usuario user) {
		
		Usuario userIngresado = new Usuario(user.getUser(), BCrypt.hashpw(user.getPass(), BCrypt.gensalt(4)));
		List<Usuario> usuarios = new ArrayList<>();

		cargarUsuarios(usuarios);

		for(Usuario userSeguro : usuarios) {
			if(userSeguro.equals(userIngresado)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean loginValido(Usuario user) {
		
		List<Usuario> usuarios = new ArrayList<>();

		cargarUsuarios(usuarios);

		for(Usuario userSeguro : usuarios) {
			if(userSeguro.getUser().equals(user.getUser())) {
				if(BCrypt.checkpw(user.getPass(), userSeguro.getPass())) {
					return true;
				}
			}
		}
		
		return false;
	}

	private void cargarUsuarios(List<Usuario> usuarios) {
		Scanner sc;
		try {
			sc = new Scanner(new File("Usuarios.txt"));
			while (sc.hasNextLine()) {
				String[] atributos = sc.nextLine().split(SEPARADOR);
				usuarios.add(new Usuario(atributos[0], atributos[1]));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean generarUsuario(Usuario user) {
		
		Usuario userIngresado = new Usuario(user.getUser(), BCrypt.hashpw(user.getPass(), BCrypt.gensalt(4)));
		BufferedWriter out;
		
		if(usuarioValido(userIngresado)) {
			try {
				out = new BufferedWriter(new FileWriter("Usuarios.txt", true));
				out.write(userIngresado.getUser() + SEPARADOR + userIngresado.getPass() + '\n');
				out.close();
				
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

}
