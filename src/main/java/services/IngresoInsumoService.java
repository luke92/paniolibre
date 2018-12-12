package services;

import domain.model.IngresoInsumo;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.IngresoInsumoDAO;

public class IngresoInsumoService {
	private IngresoInsumoDAO ingresoInsumo;

	public IngresoInsumoService(DAOAbstractFactory metodoPersistencia) {
		this.ingresoInsumo = metodoPersistencia.createIngresoInsumo();
	}

	public void agregarIngresoInsumo(IngresoInsumo ingresoInsumo) {
		this.ingresoInsumo.insert(ingresoInsumo);
	}

	public void agregarIngresoInsumoConOrden(IngresoInsumo ingresoInsumo) {
		this.ingresoInsumo.insertConOrdenDeTrabajo(ingresoInsumo);
	}
}
