package service;

import dao.LogDao;

public class LogService {

	private LogDao logDao;
	private static LogService instancia;

	private LogService() {
		logDao = LogDao.getSingletonInstance();
	}

	public static LogService getSingletonInstance() {
		return instancia != null ? instancia : new LogService();
	}
	
	public void logAbrir() {
		logDao.logAbrir();
	}
	
	public void logLoggearUser(String user, boolean correcto) {
		logDao.logLoggearUser(user, correcto);
	}
	
	public void logRegistrarUser(String user, boolean correcto) {
		logDao.logRegistrarUser(user, correcto);
	}
	
	public void logRegistrarLibro(String isbn, boolean correcto) {
		logDao.logRegistrarLibro(isbn, correcto);
	}

	public void logModificarLibro(String isbn, boolean correcto) {
		logDao.logModificarLibro(isbn, correcto);
	}
	
	public void logEliminarLibro(String isbn, boolean correcto) {
		logDao.logEliminarLibro(isbn, correcto);
	}
	
	public void logOrdenarLibro() {
		logDao.logOrdenarLibro();
	}
	
	public void logCerrar() {
		logDao.logCerrar();
	}
}
