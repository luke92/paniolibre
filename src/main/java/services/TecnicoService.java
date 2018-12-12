package services;

import java.util.List;

import domain.model.Especialidad;
import domain.model.Tecnico;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.TecnicoDAO;

public class TecnicoService {
	private TecnicoDAO tecnico;

	public TecnicoService(DAOAbstractFactory metodoPersistencia) {
		this.tecnico = metodoPersistencia.createTecnicoDAO();
	}

	public void agregarTecnico(Tecnico tecnico) {
		this.tecnico.insert(tecnico);
	}

	public void agregarTecnicoConEspecialidad(Tecnico tecnico, Especialidad especialidad) {
		this.tecnico.insertEspecialidad(tecnico, especialidad);
	}

	public void eliminarTecnico(Tecnico tecnico) {
		this.tecnico.delete(tecnico);
	}

	public void eliminarTecnicoXEspecialidad(Tecnico tecnico, Especialidad especialidad) {
		this.tecnico.deleteEspecialidad(tecnico, especialidad);
	}

	public List<Especialidad> obtenerEspecialidadesXTecnico(Tecnico tecnico) {
		return this.tecnico.readAllEspecialidadesxIdTecnico(tecnico);
	}

	public void editarTecnico(Tecnico tecnico) {
		this.tecnico.edit(tecnico);
	}

	public void editarEspecialidadTecnico(Tecnico tecnico, Especialidad especialidad) {
		this.tecnico.editEspecialidad(tecnico, especialidad);
	}

	public List<Tecnico> obtenerTecnicos() {
		return this.tecnico.readAll();
	}

	public boolean existe(Tecnico tecnico) {
		return (obtenerIdTecnico(tecnico) == 0);
	}

	public int obtenerIdTecnico(Tecnico tecnico) {
		return this.tecnico.obtenerIdTecnico(tecnico);
	}

	public boolean existeTecnicoXEspecialidad(Tecnico tecnico, Especialidad especialidad) {
		List<Especialidad> especialidades = obtenerEspecialidadesXTecnico(tecnico);
		for (Especialidad especialidad2 : especialidades) {
			if (especialidad.getIdEspecialidad() == especialidad2.getIdEspecialidad()) {
				return true;
			}

		}
		return false;
	}

	public List<Tecnico> readAllMantis(String idMantis) {
		return tecnico.readAllMantis(idMantis);
	}
}