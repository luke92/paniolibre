package persistencia.dao.interfaz;

import java.util.List;

import domain.model.Herramienta;
import domain.model.ReparacionHerramienta;

public interface ReparacionHerramientaDAO {
	public boolean insert(ReparacionHerramienta reparacionHerramienta);

	public boolean delete(ReparacionHerramienta reparacionHerramienta);

	public List<ReparacionHerramienta> readAll();

	public boolean edit(ReparacionHerramienta reparacionHerramienta);

	public int obtenerIdReparacionHerramienta(Herramienta herramienta);
}
