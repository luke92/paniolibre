package persistencia.dao.interfaz;

import java.util.List;

import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroHerramienta;
import domain.model.Tecnico;

public interface RetiroHerramientaDAO {
	public boolean insert(RetiroHerramienta retiroHerramienta);

	public boolean delete(RetiroHerramienta retiroHerramienta);

	public List<RetiroHerramienta> readAll();

	public boolean edit(RetiroHerramienta retiroHerramienta);

	public int obtenerIdRetiroHerramienta(RetiroHerramienta etiroHerramienta);

	public List<RetiroHerramienta> obtenerRetiroPorTecnico(Tecnico tecnico);

	public List<RetiroHerramienta> obtenerRetiroPorOrden(OrdenDeTrabajo orden);

	public boolean cambiarAEstadoDevuelto(Herramienta herramienta);
}
