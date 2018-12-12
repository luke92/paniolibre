package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import domain.model.CategoriaHerramienta;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CategoriaHerramientaDAO;

public class CategoriaHerramientaDAOSQL implements CategoriaHerramientaDAO {

	private static final String INSERT = "INSERT INTO CategoriasHerramientas(id,Nombre,CategoriaPadre,Activo) VALUES(?,?,?,?)";
	private static final String INSERT_PADRE = "INSERT INTO CategoriasHerramientas(Nombre) VALUES(?)";
	private static final String DELETE = "DELETE FROM CategoriasHerramientas WHERE id = ?";
	private static final String READ_ALL = "SELECT CategoriasHerramientas.id,CategoriasHerramientas.nombre FROM CategoriasHerramientas";
	private static final String EDIT = "UPDATE CategoriasHerramientas set Nombre = ?,Activo = ?,CategoriaPadre = ? WHERE CategoriasHerramientas.id = ? ";
	private static final String GET_ID = "SELECT id from CategoriasHerramientas where CategoriasHerramientas.Nombre = ?";
	private static final String GET_NOMBRE = "SELECT nombre from CategoriasHerramientas where CategoriasHerramientas.id = ?";

	public boolean insert(CategoriaHerramienta categoriaHerramienta) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT);
			statement.setInt(1, categoriaHerramienta.getIdCategoria());
			statement.setString(2, categoriaHerramienta.getNombre());
			statement.setInt(3, categoriaHerramienta.getCategoriaPadre().getIdCategoria());
			statement.setInt(4, categoriaHerramienta.getActivo());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertPadre(CategoriaHerramienta categoria) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT_PADRE);
			statement.setString(1, categoria.getNombre());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(CategoriaHerramienta categoriaHerramientaAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(categoriaHerramientaAEliminar.getIdCategoria()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<CategoriaHerramienta> readAll() {
		String log = "";
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<CategoriaHerramienta> categoriaHerramientas = new ArrayList<>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				CategoriaHerramienta categoria = new CategoriaHerramienta();
				categoria.setIdCategoria(resultSet.getInt("id"));
				categoria.setNombre(resultSet.getString("nombre"));
				categoriaHerramientas.add(categoria);
			}
		} catch (SQLException e) {
			log = e.getMessage();
		} finally {
			if (log == null)
				Logger.getLogger("funco");
			else
				Logger.getLogger(log);
		}
		return categoriaHerramientas;
	}

	public boolean edit(CategoriaHerramienta categoriaHerramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, categoriaHerramientaAEditar.getNombre());
			statement.setInt(2, categoriaHerramientaAEditar.getActivo());
			statement.setInt(3, categoriaHerramientaAEditar.getCategoriaPadre().getIdCategoria());
			statement.setInt(4, categoriaHerramientaAEditar.getIdCategoria());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int obtenerIdCategoriaHerramienta(CategoriaHerramienta categoriaHerramienta) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idCategoriaInsumo = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setString(1, categoriaHerramienta.getNombre());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idCategoriaInsumo = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idCategoriaInsumo;
	}

	public String obtenerNombreCategoriaHerramienta(CategoriaHerramienta categoriaHerramienta) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String nombreCategoriaInsumo = "";
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_NOMBRE);
			statement.setInt(1, categoriaHerramienta.getIdCategoria());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				nombreCategoriaInsumo = resultSet.getString("nombre");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombreCategoriaInsumo;
	}

}
