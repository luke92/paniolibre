package services;

import domain.model.Mail;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.MailDAO;

public class MailService {
	private static MailDAO mailDAO;

	public MailService(DAOAbstractFactory metodoPersistencia) {
		mailDAO = metodoPersistencia.createMailDAO();
	}

	public void editarMail(Mail mail) {
		mailDAO.edit(mail);
	}

	public Mail obtenerMail(int id) {
		return mailDAO.obtener(id);
	}
}
