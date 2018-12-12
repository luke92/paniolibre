package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.ArbolCategoria;
import domain.model.EnumUnidadMedida;
import domain.model.Estado;
import domain.model.Insumo;
import domain.model.RetiroInsumo;
import dto.ArbolCategoriaDTO;
import dto.InsumoReporteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.InsumoDAO;
import util.Fechas;

public class InsumoDAOSQL implements InsumoDAO {

	private static final String INSERT = "{ CALL cargarInsumo(?,?,?,?,?,?,?)}";
	private static final String DELETE = "DELETE FROM Insumos WHERE id= ?";
	private static final String READ_ALL = "{ CALL obtenerInsumos()}";
	private static final String EDIT = "{ CALL editarInsumo(?,?,?,?,?,?,?,?,?)}";
	private static final String GET_ID = "SELECT id from Insumos where Insumos.codigo = ?";
	private static final String GET_INSUMO = "SELECT codigo,nombre,marca,categoria_id,unidadMedida_id,comentario,umbralMinimo,activo from Insumos where Insumos.id = ?";
	private static final String GET_BY_ID = "{ CALL obtenerInsumoxId(?)}";
	private static final String OBTENER_INSUMOS_MAS_PRESTADAS = "{CALL obtenerInsumosMasPrestadas(?,?,?)}";
	private static final String OBTENER_PRIMER_PRESTAMO_INSUMO = "{CALL obtenerPrimerRetiroInsumo()}";
	public boolean insert(Insumo insumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setString(1, insumo.getCodigoInsumo());
			statement.setString(2, insumo.getNombre());
			statement.setString(3, insumo.getMarca());
			statement.setInt(4, insumo.getCategoriaInsumo().getId());
			statement.setInt(5, insumo.getUnidadMedida().getValor());
			statement.setString(6, insumo.getComentario());
			statement.setInt(7, insumo.getUmbralMinimo());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Insumo insumoAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(insumoAEliminar.getIdInsumo()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Insumo> readAll() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Insumo> insumos = new ArrayList<Insumo>();
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareCall(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Insumo insumo = new Insumo();

				ArbolCategoria categoriaInsumo = new ArbolCategoria();
				categoriaInsumo.setId(resultSet.getInt("categoria_id"));
				categoriaInsumo.setNombre(resultSet.getString("nombreCategoria"));

				insumo.setCategoriaInsumo(categoriaInsumo);
				insumo.setIdInsumo(resultSet.getInt("id"));
				insumo.setNombre(resultSet.getString("nombreInsumo"));
				insumo.setMarca(resultSet.getString("marca"));
				insumo.setCodigoInsumo(resultSet.getString("codigo"));
				insumo.setComentario(resultSet.getString("comentario"));
				insumo.setUmbralMinimo(resultSet.getInt("umbralMinimo"));
				EnumUnidadMedida enumUnidadMedida = EnumUnidadMedida
						.getUnidadMedida(resultSet.getInt("unidadMedida_id"));
				insumo.setUnidadMedida(enumUnidadMedida);
				insumo.setEstado(Estado.getEstado(resultSet.getInt("activo")));
				insumos.add(insumo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insumos;
	}

	public boolean edit(Insumo insumoAEditar) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareCall(EDIT);
			statement.setInt(1, insumoAEditar.getIdInsumo());
			statement.setString(2, insumoAEditar.getCodigoInsumo());
			statement.setString(3, insumoAEditar.getNombre());
			statement.setString(4, insumoAEditar.getMarca());
			statement.setInt(5, insumoAEditar.getCategoriaInsumo().getId());
			statement.setInt(6, insumoAEditar.getUnidadMedida().getValor());
			statement.setString(7, insumoAEditar.getComentario());
			statement.setInt(8, insumoAEditar.getUmbralMinimo());
			int estado = (insumoAEditar.getEstado() == Estado.ALTA) ? 1 : 0;
			statement.setInt(9, estado);

			if (statement.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public int obtenerIdInsumo(Insumo insumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idInsumo = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setString(1, insumo.getCodigoInsumo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idInsumo = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idInsumo;
	}

	public Insumo obtenerInsumoMaestro(Insumo insumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Insumo insumoM = null;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_INSUMO);
			statement.setInt(1, insumo.getIdInsumo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				insumoM = new Insumo();
				insumoM.setIdInsumo(insumo.getIdInsumo());
				insumoM.setCodigoInsumo(resultSet.getString("codigo"));
				insumoM.setNombre(resultSet.getString("nombre"));
				insumoM.setMarca(resultSet.getString("marca"));
				insumoM.setUmbralMinimo(resultSet.getInt("umbralMinimo"));
				ArbolCategoria categoriaInsumo = new ArbolCategoria();
				categoriaInsumo.setId(resultSet.getInt("categoria_id"));
				insumoM.setCategoriaInsumo(categoriaInsumo);
				insumoM.setComentario(resultSet.getString("comentario"));
				EnumUnidadMedida enumUnidadMedida = EnumUnidadMedida
						.getUnidadMedida(resultSet.getInt("unidadMedida_id"));
				insumo.setUnidadMedida(enumUnidadMedida);
				insumo.setEstado(Estado.getEstado(resultSet.getInt("activo")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insumoM;
	}

	public Insumo getById(Insumo insumoAObtener) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		Insumo insumo = null;
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_BY_ID);
			statement.setInt(1, insumoAObtener.getIdInsumo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				insumo = new Insumo();

				ArbolCategoria categoriaInsumo = new ArbolCategoria();
				categoriaInsumo.setId(resultSet.getInt("categoria_id"));
				categoriaInsumo.setNombre(resultSet.getString("nombreCategoria"));

				insumo.setCategoriaInsumo(categoriaInsumo);
				insumo.setIdInsumo(resultSet.getInt("id"));
				insumo.setNombre(resultSet.getString("nombreInsumo"));
				insumo.setMarca(resultSet.getString("marca"));
				insumo.setCodigoInsumo(resultSet.getString("codigo"));
				insumo.setComentario(resultSet.getString("comentario"));
				insumo.setUmbralMinimo(resultSet.getInt("umbralMinimo"));
				EnumUnidadMedida enumUnidadMedida = EnumUnidadMedida
						.getUnidadMedida(resultSet.getInt("unidadMedida_id"));
				insumo.setUnidadMedida(enumUnidadMedida);
				insumo.setEstado(Estado.getEstado(resultSet.getInt("activo")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insumo;
	}

	@Override
	public List<InsumoReporteDTO> obtenerInsumosMasPrestadas(ArbolCategoriaDTO categoriaDTO, Date fechaInicio,
			Date fechaFin) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		List<InsumoReporteDTO> lista = new ArrayList<>();
		try {
			statement = conexion.getSQLConexion().prepareCall(OBTENER_INSUMOS_MAS_PRESTADAS);
			statement.setInt(1, categoriaDTO.getId());
			statement.setDate(2, fechaInicio);
			statement.setDate(3, fechaFin);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				InsumoReporteDTO reporte = new InsumoReporteDTO();
				reporte.setIdCategoriaHijo(resultSet.getInt("idCategoriaHijo"));
				reporte.setIdCategoriaPadre(resultSet.getInt("idCategoriaPadre"));
				reporte.setNombreInsumo(resultSet.getString("nombre_Insumo"));
				reporte.setNombreTecnico(resultSet.getString("nombre_Tecnico"));
				reporte.setCantidadPrestada(resultSet.getInt("cantidad"));
				if(resultSet.getString("nombreCategoriaHijo") != null)
				reporte.setCategoria(resultSet.getString("nombreCategoriaHijo"));
				else reporte.setCategoria("No tiene");
				reporte.setFechaInicio(Fechas.Date_a_Calendar(fechaInicio));
				reporte.setFechaFin(Fechas.Date_a_Calendar(fechaFin));
				reporte.setFechaFinHerramienta(fechaFin.toString());
				reporte.setFechaInicioHerramienta(fechaInicio.toString());
				reporte.setCantidadRetirada(resultSet.getInt("cantidad_Retirada"));
				reporte.setNombreCategoriaPadre(resultSet.getString("nombreCategoriaPadre"));
				reporte.setCategoriaSeleccionada(categoriaDTO.getNombre());
				reporte.setCodigo(resultSet.getString("codigo"));
				lista.add(reporte);
			}
			if(categoriaDTO.tieneHijas())
			{
				for(int i = 0; i < categoriaDTO.getHijas().size(); i ++)
				{
					List<InsumoReporteDTO> listaHijos = obtenerInsumosMasPrestadas(categoriaDTO.getHijas().get(i), fechaInicio, fechaFin);
					if(!listaHijos.isEmpty()) lista.addAll(listaHijos);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public RetiroInsumo obtenerPrimerRetiroInsumo() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		RetiroInsumo retiro = null;
		try {
			statement = conexion.getSQLConexion().prepareCall(OBTENER_PRIMER_PRESTAMO_INSUMO);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				retiro = new RetiroInsumo();
				retiro.setFecha(Fechas.Date_a_Calendar(resultSet.getDate("fecha")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retiro;
	}
}