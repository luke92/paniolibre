package persistencia.dao.interfaz;

import java.util.List;

import domain.model.OrdenesTrabajoTecnicos;

public interface OrdenesTrabajoTecnicosDAO {
	public boolean insert(OrdenesTrabajoTecnicos ordenesTrabajoTecnicos);

	public boolean delete(OrdenesTrabajoTecnicos ordenesTrabajoTecnicos);

	public boolean edit(OrdenesTrabajoTecnicos ordenesTrabajoTecnicos);

	public List<OrdenesTrabajoTecnicos> readAll();
}