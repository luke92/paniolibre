package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.CategoriaInsumo;
import domain.model.Estado;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CategoriaInsumoDAO;

public class CategoriaInsumoDAOSQL implements CategoriaInsumoDAO {

	private static final String INSERT = "INSERT INTO CategoriasInsumos(id,Nombre,Activo,CategoriaPadre) VALUES(?,?,?,?)";
	private static final String INSERT_PADRE = "INSERT INTO CategoriasInsumos(Nombre) VALUES(?)";
	private static final String DELETE = "DELETE FROM CategoriasInsumos WHERE id = ?";
	private static final String READ_ALL = "{ call obtenerCategoriasInsumos()}";
	private static final String EDIT = "UPDATE CategoriasInsumos set Nombre = ?,Activo = ?,CategoriaPadre = ? WHERE CategoriasInsumos.id = ? ";
	private static final String GET_ID = "SELECT id from CategoriasInsumos where CategoriasInsumos.Nombre = ?";
	private static final String GET_NOMBRE = "SELECT CategoriasInsumos.nombre from CategoriasInsumos where CategoriasInsumos.id = ?";

	public boolean insert(CategoriaInsumo categoriaInsumo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT);
			statement.setInt(1, categoriaInsumo.getIdCategoriaInsumo());
			statement.setString(2, categoriaInsumo.getNombre());
			statement.setInt(3, categoriaInsumo.getActivo().getInt());
			statement.setInt(4, categoriaInsumo.getCategoriaPadre().getIdCategoriaInsumo());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertPadre(CategoriaInsumo categoriaInsumo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT_PADRE);
			statement.setString(1, categoriaInsumo.getNombre());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(CategoriaInsumo categoriaInsumoAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(categoriaInsumoAEliminar.getIdCategoriaInsumo()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<CategoriaInsumo> readAll() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<CategoriaInsumo> categoriaInsumos = new ArrayList<CategoriaInsumo>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				CategoriaInsumo categoria = new CategoriaInsumo();
				CategoriaInsumo categoriaPadre = new CategoriaInsumo();
				categoriaPadre.setIdCategoriaInsumo(resultSet.getInt("idPadre"));
				categoriaPadre.setNombre(resultSet.getString("nombrePadre"));
				categoriaPadre.setActivo(Estado.getEstado(resultSet.getInt("activoPadre")));
				categoria.setIdCategoriaInsumo(resultSet.getInt("idHijo"));
				categoria.setNombre(resultSet.getString("nombreHijo"));
				categoria.setActivo(Estado.getEstado(resultSet.getInt("activo")));
				categoria.setCategoriaPadre(categoriaPadre);
				categoriaInsumos.add(categoria);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoriaInsumos;
	}

	public boolean edit(CategoriaInsumo categoriaInsumoAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, categoriaInsumoAEditar.getNombre());
			statement.setInt(2, categoriaInsumoAEditar.getActivo().getInt());
			statement.setInt(3, categoriaInsumoAEditar.getCategoriaPadre().getIdCategoriaInsumo());
			statement.setInt(4, categoriaInsumoAEditar.getIdCategoriaInsumo());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int obtenerIdCategoriaInsumo(CategoriaInsumo categoriaInsumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idCategoriaInsumo = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setString(1, categoriaInsumo.getNombre());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idCategoriaInsumo = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idCategoriaInsumo;
	}

	@Override
	public String obtenerNombre(CategoriaInsumo categoriaInsumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String nombreCategoriaInsumo = "";
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_NOMBRE);
			statement.setInt(1, categoriaInsumo.getIdCategoriaInsumo());
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