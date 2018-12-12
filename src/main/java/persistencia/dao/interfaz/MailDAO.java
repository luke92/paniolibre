package persistencia.dao.interfaz;

import domain.model.Mail;

public interface MailDAO {

	public boolean edit(Mail mail);

	public Mail obtener(int id);
}
