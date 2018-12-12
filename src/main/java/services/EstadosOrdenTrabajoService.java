package services;

import java.util.List;

import domain.model.EstadosOrdenTrabajo;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EstadosOrdenTrabajoDAO;

public class EstadosOrdenTrabajoService {
	private static EstadosOrdenTrabajoDAO estadosOrdenTrabajoDAO;

	public EstadosOrdenTrabajoService(DAOAbstractFactory metodoPersistencia) {
		EstadosOrdenTrabajoService.estadosOrdenTrabajoDAO = metodoPersistencia.createEstadosOrdenTrabajoDAO();
	}

	public static void asignarEstadosOrdenTrabajo(EstadosOrdenTrabajo eot) {
		estadosOrdenTrabajoDAO.insert(eot);
	}

	public static void editarEstadosOrdenTrabajo(EstadosOrdenTrabajo estadosOrdenTrabajo) {
		estadosOrdenTrabajoDAO.edit(estadosOrdenTrabajo);
	}

	public static void eliminarEstadosOrdenTrabajo(EstadosOrdenTrabajo eot) {
		EstadosOrdenTrabajoService.estadosOrdenTrabajoDAO.delete(eot);
	}

	public static List<EstadosOrdenTrabajo> obtenerEstadosOrdenTrabajos() {
		return EstadosOrdenTrabajoService.estadosOrdenTrabajoDAO.readAll();
	}

	public static boolean existe(EstadosOrdenTrabajo estadosOrdenTrabajo) {
		return (buscarEstadosOrdenTrabajo(estadosOrdenTrabajo) == null);
	}

	private static EstadosOrdenTrabajo buscarEstadosOrdenTrabajo(EstadosOrdenTrabajo estadosOrdenTrabajo) {
		for (EstadosOrdenTrabajo o : obtenerEstadosOrdenTrabajos())
			if (o.toString().equals(estadosOrdenTrabajo.toString()))
				return o;
		return null;
	}
}