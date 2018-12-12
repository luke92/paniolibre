package services;

import java.util.List;

import domain.model.InsumoDeposito;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroInsumo;
import domain.model.Tecnico;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.InsumoDepositoDAO;
import persistencia.dao.interfaz.RetiroInsumoDAO;

public class RetiroInsumoService {
	private RetiroInsumoDAO retiroInsumo;
	private InsumoDepositoDAO insumoDeposito;

	public RetiroInsumoService(DAOAbstractFactory metodoPersistencia) {
		this.retiroInsumo = metodoPersistencia.createRetiroInsumoDAO();
		this.insumoDeposito = metodoPersistencia.createInsumoDepositoDAO();
	}

	public void agregarRetiroInsumo(RetiroInsumo retiroInsumo) {
		this.retiroInsumo.insert(retiroInsumo);
	}

	public void editarInsumoDeposito(InsumoDeposito insumoDeposito) {
		this.insumoDeposito.edit(insumoDeposito);
	}

	public void eliminarRetiroInsumo(RetiroInsumo retiroInsumo) {
		this.retiroInsumo.delete(retiroInsumo);
	}

	public void editarRetiroInsumo(RetiroInsumo retiroInsumo) {
		this.retiroInsumo.edit(retiroInsumo);
	}

	public List<RetiroInsumo> obtenerRetiroInsumos() {
		return this.retiroInsumo.readAll();
	}

	public List<InsumoDeposito> obtenerInsumoDepositos() {
		return this.insumoDeposito.readAll();
	}

	public List<RetiroInsumo> obtenerRetiroPorTecnicos(Tecnico tecnico) {
		return this.retiroInsumo.obtenerRetiroPorTecnico(tecnico);
	}

	public List<RetiroInsumo> obtenerRetiroPorOrden(OrdenDeTrabajo ordenDeTrabajo) {
		return this.retiroInsumo.obtenerRetiroPorOrden(ordenDeTrabajo);
	}

	public void cerrarRetirosInsumosPendientes(OrdenDeTrabajo orden) {
		this.retiroInsumo.cerrarRetirosInsumosPendientes(orden);
	}
}