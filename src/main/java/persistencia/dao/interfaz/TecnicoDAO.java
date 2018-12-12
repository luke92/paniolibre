package persistencia.dao.interfaz;

import java.util.List;

import domain.model.Especialidad;
import domain.model.Tecnico;

public interface TecnicoDAO {
	public boolean insert(Tecnico tecnico);

	public boolean insertEspecialidad(Tecnico tecnico, Especialidad especialidad);

	public boolean delete(Tecnico tecnico);

	public List<Tecnico> readAll();

	public boolean edit(Tecnico tecnico);

	public int obtenerIdTecnico(Tecnico tecnico);

	public boolean editEspecialidad(Tecnico tecnico, Especialidad especialidad);

	public boolean deleteEspecialidad(Tecnico tecnico, Especialidad especialidad);

	public List<Especialidad> readAllEspecialidadesxIdTecnico(Tecnico tecnico);

	public List<Tecnico> readAllMantis(String idMantis);

}