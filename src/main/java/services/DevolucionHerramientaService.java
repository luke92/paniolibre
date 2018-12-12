package services;

import java.util.List;

import domain.model.DevolucionHerramienta;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.DevolucionHerramientaDAO;

public class DevolucionHerramientaService {
	private DevolucionHerramientaDAO devolucionHerramienta;

	public DevolucionHerramientaService(DAOAbstractFactory metodoPersistencia) {
		this.devolucionHerramienta = metodoPersistencia.createDevolucionHerramientaDAO();
	}

	public boolean existe(DevolucionHerramienta devolucionHerramienta) {
		return (obtenerIdDevolucionHerramienta(devolucionHerramienta) == 0);
	}

	public int obtenerIdDevolucionHerramienta(DevolucionHerramienta devolucionHerramienta) {
		return this.devolucionHerramienta.obtenerIdDevolucionHerramienta(devolucionHerramienta);
	}

	public void agregarDevolucionHerramienta(DevolucionHerramienta devolucionHerramienta) {
		this.devolucionHerramienta.insert(devolucionHerramienta);
	}

	public void eliminarDevolucionHerramienta(DevolucionHerramienta devolucionHerramienta) {
		this.devolucionHerramienta.delete(devolucionHerramienta);
	}

	public void editarDevolucionHerramienta(DevolucionHerramienta devolucionHerramienta) {
		this.devolucionHerramienta.edit(devolucionHerramienta);
	}

	public List<DevolucionHerramienta> obtenerDevolucionHerramientas() {
		return this.devolucionHerramienta.readAll();
	}
}
