package persistencia.dao.interfaz;

import java.util.List;

import domain.model.CategoriaHerramienta;

public interface CategoriaHerramientaDAO {
	public boolean insert(CategoriaHerramienta categoriaHerramienta);

	public boolean insertPadre(CategoriaHerramienta categoriaHerramienta);

	public boolean delete(CategoriaHerramienta categoriaHerramienta);

	public List<CategoriaHerramienta> readAll();

	public boolean edit(CategoriaHerramienta categoriaHerramienta);

	public int obtenerIdCategoriaHerramienta(CategoriaHerramienta categoriaHerramienta);

	public String obtenerNombreCategoriaHerramienta(CategoriaHerramienta categoriaHerramienta);
}
