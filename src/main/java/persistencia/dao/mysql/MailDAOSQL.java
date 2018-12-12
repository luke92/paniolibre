package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.model.Mail;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.MailDAO;

public class MailDAOSQL implements MailDAO {
	private static final String READ_ALL = "SELECT * FROM Mail WHERE Mail.id = ?";
	private static final String EDIT = "UPDATE Mail set mail= ?,clave= ? WHERE Mail.id = ? ";

	@Override
	public boolean edit(Mail mail) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, mail.getMail());
			statement.setString(2, mail.getClave());
			statement.setInt(3, mail.getIdMail());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Mail obtener(int id) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Mail mail1 = null;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				mail1 = new Mail(resultSet.getInt("id"), resultSet.getString("mail"), resultSet.getString("clave"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mail1;
	}

}
