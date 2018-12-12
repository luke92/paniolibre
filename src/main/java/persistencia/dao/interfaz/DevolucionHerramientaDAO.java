package persistencia.dao.interfaz;

import java.util.List;

import domain.model.DevolucionHerramienta;

public interface DevolucionHerramientaDAO {
	public boolean insert(DevolucionHerramienta devolucionHerramienta);

	public boolean delete(DevolucionHerramienta devolucionHerramienta);

	public List<DevolucionHerramienta> readAll();

	public boolean edit(DevolucionHerramienta devolucionHerramienta);

	public int obtenerIdDevolucionHerramienta(DevolucionHerramienta devolucionHerramienta);
}
