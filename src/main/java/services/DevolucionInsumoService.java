package services;

import java.util.List;

import domain.model.DevolucionInsumo;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.DevolucionInsumoDAO;

public class DevolucionInsumoService {
	private DevolucionInsumoDAO devolucionInsumo;

	public DevolucionInsumoService(DAOAbstractFactory metodoPersistencia) {
		this.devolucionInsumo = metodoPersistencia.createDevolucionInsumoDAO();
	}

	private void agregarDevolucionInsumo(DevolucionInsumo devolucionInsumo) {
		this.devolucionInsumo.insert(devolucionInsumo);
	}

	public void agregarDevolucionesInsumo(List<DevolucionInsumo> devoluciones) {
		for (DevolucionInsumo devolucionInsumo : devoluciones) {
			this.agregarDevolucionInsumo(devolucionInsumo);
		}
	}

}
