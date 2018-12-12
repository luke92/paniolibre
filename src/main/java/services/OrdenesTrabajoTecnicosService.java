package services;

import java.util.List;

import domain.model.OrdenesTrabajoTecnicos;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.OrdenesTrabajoTecnicosDAO;

public class OrdenesTrabajoTecnicosService {
	private static OrdenesTrabajoTecnicosDAO ordenesTrabajoTecnicosDAO;

	public OrdenesTrabajoTecnicosService(DAOAbstractFactory metodoPersistencia) {
		OrdenesTrabajoTecnicosService.ordenesTrabajoTecnicosDAO = metodoPersistencia.createOrdenesTrabajoTecnicosDAO();
	}

	public static void asignarOrdenesTrabajoTecnicos(OrdenesTrabajoTecnicos ott) {
		ordenesTrabajoTecnicosDAO.insert(ott);
	}

	public static void editarOrdenesTrabajoTecnicos(OrdenesTrabajoTecnicos ordenesTrabajoTecnicos) {
		ordenesTrabajoTecnicosDAO.edit(ordenesTrabajoTecnicos);
	}

	public static void eliminarOrdenesTrabajoTecnicos(OrdenesTrabajoTecnicos ott) {
		OrdenesTrabajoTecnicosService.ordenesTrabajoTecnicosDAO.delete(ott);
	}

	public static List<OrdenesTrabajoTecnicos> obtenerOrdenesTrabajoTecnicoss() {
		return OrdenesTrabajoTecnicosService.ordenesTrabajoTecnicosDAO.readAll();
	}

	public static boolean existe(OrdenesTrabajoTecnicos ordenesTrabajoTecnicos) {
		return (buscarOrdenesTrabajoTecnicos(ordenesTrabajoTecnicos) == null);
	}

	private static OrdenesTrabajoTecnicos buscarOrdenesTrabajoTecnicos(OrdenesTrabajoTecnicos ordenesTrabajoTecnicos) {
		for (OrdenesTrabajoTecnicos o : obtenerOrdenesTrabajoTecnicoss())
			if (o.toString().equals(ordenesTrabajoTecnicos.toString()))
				return o;
		return null;
	}
}