package persistencia.dao.interfaz;

import java.util.List;

import domain.model.OrdenDeTrabajo;
import domain.model.RetiroInsumo;
import domain.model.Tecnico;

public interface RetiroInsumoDAO {
	public boolean insert(RetiroInsumo retiroInsumo);

	public boolean delete(RetiroInsumo retiroInsumo);

	public List<RetiroInsumo> readAll();

	public boolean edit(RetiroInsumo retiroInsumo);

	public List<RetiroInsumo> obtenerRetiroPorTecnico(Tecnico tecnico);

	public List<RetiroInsumo> obtenerRetiroPorOrden(OrdenDeTrabajo orden);

	public boolean cerrarRetirosInsumosPendientes(OrdenDeTrabajo orden);
}