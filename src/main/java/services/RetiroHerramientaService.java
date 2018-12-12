package services;

import java.util.List;

import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroHerramienta;
import domain.model.Tecnico;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.RetiroHerramientaDAO;

public class RetiroHerramientaService {
	private RetiroHerramientaDAO retiroHerramienta;

	public RetiroHerramientaService(DAOAbstractFactory metodoPersistencia) {
		this.retiroHerramienta = metodoPersistencia.createRetiroHerramientaDAO();
	}

	public boolean existe(RetiroHerramienta retiroHerramienta) {
		return (obtenerIdRetiroHerramienta(retiroHerramienta) == 0);
	}

	public int obtenerIdRetiroHerramienta(RetiroHerramienta retiroHerramienta) {
		return this.retiroHerramienta.obtenerIdRetiroHerramienta(retiroHerramienta);
	}

	public void agregarRetiroHerramienta(RetiroHerramienta retiroHerramienta) {
		this.retiroHerramienta.insert(retiroHerramienta);
	}

	public void eliminarRetiroHerramienta(RetiroHerramienta retiroHerramienta) {
		this.retiroHerramienta.delete(retiroHerramienta);
	}

	public void editarRetiroHerramienta(RetiroHerramienta retiroHerramienta) {
		this.retiroHerramienta.edit(retiroHerramienta);
	}

	public void cambiarEstadoDevuelto(Herramienta herramienta) {
		this.retiroHerramienta.cambiarAEstadoDevuelto(herramienta);
	}

	public List<RetiroHerramienta> obtenerRetiroHerramientas() {
		return this.retiroHerramienta.readAll();
	}

	public List<RetiroHerramienta> obtenerRetiroPorTecnicos(Tecnico tecnico) {
		return this.retiroHerramienta.obtenerRetiroPorTecnico(tecnico);
	}

	public List<RetiroHerramienta> obtenerReiroPorOrdenDeTrabajo(OrdenDeTrabajo orden) {
		return this.retiroHerramienta.obtenerRetiroPorOrden(orden);
	}

}
