package persistencia.dao.interfaz;

import java.util.List;

import domain.model.Especialidad;

public interface EspecialidadesDAO {
	public boolean insert(Especialidad especialidad);

	public boolean delete(Especialidad especialidad);

	public List<Especialidad> readAll();

}
