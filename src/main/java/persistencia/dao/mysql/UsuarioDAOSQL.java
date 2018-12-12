package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Usuario;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.UsuarioDAO;
import util.Encriptador;

public class UsuarioDAOSQL implements UsuarioDAO {

	private static final String INSERT = "{call cargarUsuario(?,?,?,?,?,?,?,?)}";

	private static final String DELETE = "DELETE FROM usuarios WHERE id= ?";

	private static final String READ_ALL = "SELECT * FROM usuarios";

	private static final String GET_ID = "{call obtenerUsuarioxId(?)}";

	private static final String EDIT = "UPDATE usuarios set username = ?,clave = ?,nombre = ?, apellido = ?, activo = ?, email = ?, recibeAlertasPorMail = ?, userMantis = ?, claveMantis = ? WHERE id = ? ";

	@Override
	public boolean insert(Usuario usuario) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getApellido());
			statement.setString(3, usuario.getUserName());
			statement.setString(4, Encriptador.obtenerClaveEncriptada(usuario.getClave()));
			statement.setString(5, usuario.getMail());
			statement.setBoolean(6, usuario.isRecibeAlertasPorMail());
			statement.setString(7, usuario.getUserMantis());
			statement.setString(8, Encriptador.obtenerClaveEncriptada(usuario.getClaveMantis()));
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Usuario usuario) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(usuario.getIdUsuario()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Usuario> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("id"));
				usuario.setNombre(resultSet.getString("nombre"));
				usuario.setApellido(resultSet.getString("apellido"));
				usuario.setUserName(resultSet.getString("username"));
				usuario.setClave(Encriptador.obtenerClaveDesencriptada(resultSet.getString("clave")));
				usuario.setActivo(resultSet.getInt("activo"));
				usuario.setMail(resultSet.getString("email"));
				if(resultSet.getInt("recibeAlertasPorMail") == 1)
					usuario.setRecibeAlertasPorMail(true);
				else usuario.setRecibeAlertasPorMail(false);
				usuario.setUserMantis(resultSet.getString("userMantis"));
				usuario.setClaveMantis(Encriptador.obtenerClaveDesencriptada(resultSet.getString("claveMantis")));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}

	@Override
	public boolean edit(Usuario usuario) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, usuario.getUserName());
			statement.setString(2, Encriptador.obtenerClaveEncriptada(usuario.getClave()));
			statement.setString(3, usuario.getNombre());
			statement.setString(4, usuario.getApellido());
			statement.setInt(5, usuario.getActivo());
			statement.setString(6, usuario.getMail());
			statement.setBoolean(7, usuario.isRecibeAlertasPorMail());
			statement.setString(8, usuario.getUserMantis());
			statement.setString(9, Encriptador.obtenerClaveEncriptada(usuario.getClaveMantis()));
			statement.setInt(10, usuario.getIdUsuario());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int obtenerIdUsuario(Usuario usuario) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idUsuario = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setInt(1, usuario.getIdUsuario());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idUsuario = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idUsuario;
	}
}