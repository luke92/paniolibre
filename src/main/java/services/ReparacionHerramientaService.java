package services;

import java.util.List;

import domain.model.Herramienta;
import domain.model.ReparacionHerramienta;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ReparacionHerramientaDAO;

public class ReparacionHerramientaService {
	private ReparacionHerramientaDAO reparacionHerramienta;

	public ReparacionHerramientaService(DAOAbstractFactory metodoPersistencia) {
		this.reparacionHerramienta = metodoPersistencia.createReparacionHerramientaDAO();
	}

	public void agregarReparacionHerramienta(ReparacionHerramienta reparacionHerramienta) {
		this.reparacionHerramienta.insert(reparacionHerramienta);
	}

	public int obtenerIdReparacionHerramienta(Herramienta reparacionHerramienta) {
		return this.reparacionHerramienta.obtenerIdReparacionHerramienta(reparacionHerramienta);
	}

	public void eliminarReparacionHerramienta(ReparacionHerramienta reparacionHerramienta) {
		this.reparacionHerramienta.delete(reparacionHerramienta);
	}

	public void editarReparacionHerramienta(ReparacionHerramienta reparacionHerramienta) {
		this.reparacionHerramienta.edit(reparacionHerramienta);
	}

	public List<ReparacionHerramienta> obtenerReparacionHerramientas() {
		return this.reparacionHerramienta.readAll();
	}
}
