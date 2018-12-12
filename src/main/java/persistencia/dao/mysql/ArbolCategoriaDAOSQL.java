package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import domain.model.ArbolCategoria;
import domain.model.Estado;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ArbolCategoriaDAO;
import util.TipoCategoria;

public class ArbolCategoriaDAOSQL implements ArbolCategoriaDAO {

	private String insertCategoriaInsumo = "{CALL cargarCategoriaInsumoHijo(?,?)}";
	private String insertCategoriaHerramienta = "{CALL cargarCategoriaHerramientaHijo(?,?)}";

	private String deleteCategoriaInsumo = "DELETE FROM CategoriasInsumos WHERE nombre = ? ";
	private String deleteCategoriaHerramienta = "DELETE FROM CategoriasHerramientas WHERE nombre = ?";

	private String arbolCategoriaInsumoGetAllRaices = "{CALL obtenerCategoriasInsumosSinPadre()}";
	private String arbolCategoriaInsumoGetAllHijosPorId = "{CALL obtenerCategoriasInsumoHijasxId(?)}";

	private String arbolCategoriaHerramientaGetAllRaices = "{CALL obtenerCategoriasHerramientasSinPadre()}";
	private String arbolCategoriaHerramientaGetAllHijosPorId = "{CALL obtenerCategoriasHerramientaHijasxId(?)}";

	private String editCategoriaInsumo = "{ CALL editarCategoriaInsumo(?,?,?,?) }";
	private String editCategoriaHerramienta = "{ CALL editarCategoriaHerramienta(?,?,?,?) }";

	private String cantidadInsumosPorCategoria = "{ CALL cantidadInsumosPorCategoriaId(?) } ";
	private String cantidadHerramientasPorCategoria = "{ CALL cantidadHerramientasPorCategoriaId(?) } ";

	private String obtenerCategoriaInsumoxNombre = "{ CALL obtenerCategoriaInsumoxNombre(?) } ";
	private String obtenerCategoriaHerramientaxNombre = "{ CALL obtenerCategoriaHerramientaxNombre(?) } ";

	@Override
	public ArbolCategoria getArbolCategorias(TipoCategoria tipoCategoria) {
		ArbolCategoria arbol = null;
		List<ArbolCategoria> padres = this.getAllRaices(tipoCategoria);
		if (!padres.isEmpty()) {
			arbol = padres.get(0);
			List<ArbolCategoria> categoriasHijas = this.getAllHijasPorId(arbol, tipoCategoria);
			arbol.setHijas(categoriasHijas);
		}
		return arbol;
	}

	private List<ArbolCategoria> getAllRaices(TipoCategoria tipoCategoria) {
		CallableStatement statement;
		ResultSet resultSet = null; // Guarda el resultado de la query
		ArrayList<ArbolCategoria> categorias = new ArrayList<>();
		Conexion conexion = Conexion.getConexion();
		try {
			if (tipoCategoria == TipoCategoria.INSUMOS)
				statement = conexion.getSQLConexion().prepareCall(arbolCategoriaInsumoGetAllRaices);
			else
				statement = conexion.getSQLConexion().prepareCall(arbolCategoriaHerramientaGetAllRaices);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ArbolCategoria categoria = new ArbolCategoria();

				categoria.setId(resultSet.getInt("id"));
				categoria.setNombre(resultSet.getString("nombre"));
				categoria.setActivo(Estado.getEstado(resultSet.getInt("activo")));
				categoria.setPadre(null);
				categoria.setTipoCategoria(tipoCategoria);
				categorias.add(categoria);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Logger.getLogger("Exito");
		}
		return categorias;
	}

	private List<ArbolCategoria> getAllHijasPorId(ArbolCategoria categoriaPadre, TipoCategoria tipoCategoria) {
		CallableStatement statement = null;
		ResultSet resultSet = null; // Guarda el resultado de la query
		ArrayList<ArbolCategoria> categoriaInsumos = new ArrayList<>();
		Conexion conexion = Conexion.getConexion();
		try {
			if (categoriaPadre.getTipoCategoria() == TipoCategoria.INSUMOS)
				statement = conexion.getSQLConexion().prepareCall(arbolCategoriaInsumoGetAllHijosPorId);
			else
				statement = conexion.getSQLConexion().prepareCall(arbolCategoriaHerramientaGetAllHijosPorId);
			statement.setInt(1, categoriaPadre.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ArbolCategoria categoria = new ArbolCategoria();
				categoria.setId(resultSet.getInt("idHijo"));
				categoria.setNombre(resultSet.getString("nombreHijo"));
				categoria.setActivo(Estado.getEstado(resultSet.getInt("activo")));
				categoria.setPadre(categoriaPadre);
				categoria.setTipoCategoria(tipoCategoria);
				categoria.setHijas(this.getAllHijasPorId(categoria, tipoCategoria));
				categoriaInsumos.add(categoria);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Logger.getLogger("Exito");
		}
		return categoriaInsumos;
	}

	@Override
	public boolean insert(ArbolCategoria categoria) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (categoria.getTipoCategoria() == TipoCategoria.INSUMOS)
				statement = conexion.getSQLConexion().prepareCall(insertCategoriaInsumo);
			else
				statement = conexion.getSQLConexion().prepareCall(insertCategoriaHerramienta);
			statement.setString(1, categoria.getNombre());
			statement.setInt(2, categoria.getPadre().getId());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Logger.getLogger("Exito");
		}
		return false;
	}

	@Override
	public boolean delete(ArbolCategoria categoria) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			if (categoria.getTipoCategoria() == TipoCategoria.INSUMOS)
				statement = conexion.getSQLConexion().prepareStatement(deleteCategoriaInsumo);
			else
				statement = conexion.getSQLConexion().prepareStatement(deleteCategoriaHerramienta);
			statement.setString(1, categoria.getNombre());
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Logger.getLogger("Exito");
		}
		return false;
	}

	@Override
	public boolean edit(ArbolCategoria categoria) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (categoria.getTipoCategoria() == TipoCategoria.INSUMOS)
				statement = conexion.getSQLConexion().prepareCall(editCategoriaInsumo);
			else
				statement = conexion.getSQLConexion().prepareCall(editCategoriaHerramienta);
			statement.setInt(1, categoria.getId());
			statement.setString(2, categoria.getNombre());
			statement.setInt(3, categoria.getPadre().getId());
			statement.setInt(4, categoria.getActivo().getInt());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Logger.getLogger("Exito");
		}
		return false;
	}

	@Override
	public boolean existeCategoriaEnOtraTabla(ArbolCategoria categoria) {
		CallableStatement statement;
		ResultSet resultSet = null;
		boolean existe = false;
		Conexion conexion = Conexion.getConexion();
		try {
			if (categoria.getTipoCategoria() == TipoCategoria.INSUMOS)
				statement = conexion.getSQLConexion().prepareCall(cantidadInsumosPorCategoria);
			else
				statement = conexion.getSQLConexion().prepareCall(cantidadHerramientasPorCategoria);
			statement.setInt(1, categoria.getId());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int cantidad = resultSet.getInt("cantidad");
				if (cantidad > 0)
					existe = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Logger.getLogger("Exito");
		}
		return existe;
	}

	@Override
	public boolean existeCategoria(ArbolCategoria categoria) {
		CallableStatement statement;
		ResultSet resultSet = null;
		boolean existe = false;
		Conexion conexion = Conexion.getConexion();
		try {
			if (categoria.getTipoCategoria() == TipoCategoria.INSUMOS)
				statement = conexion.getSQLConexion().prepareCall(obtenerCategoriaInsumoxNombre);
			else
				statement = conexion.getSQLConexion().prepareCall(obtenerCategoriaHerramientaxNombre);
			statement.setString(1, categoria.getNombre());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				existe = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Logger.getLogger("Exito");
		}
		return existe;
	}

}
