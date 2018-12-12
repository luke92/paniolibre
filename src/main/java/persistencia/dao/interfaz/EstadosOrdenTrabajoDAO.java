package persistencia.dao.interfaz;

import java.util.List;

import domain.model.EstadosOrdenTrabajo;

public interface EstadosOrdenTrabajoDAO {
	public boolean insert(EstadosOrdenTrabajo estadosOrdenDeTrabajo);

	public boolean delete(EstadosOrdenTrabajo estadosOrdenDeTrabajo);

	public boolean edit(EstadosOrdenTrabajo estadosOrdenDeTrabajo);

	public List<EstadosOrdenTrabajo> readAll();
}