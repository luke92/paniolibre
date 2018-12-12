package persistencia.dao.interfaz;

import java.util.List;

import domain.model.CategoriaInsumo;

public interface CategoriaInsumoDAO {
	public boolean insert(CategoriaInsumo categoriaInsumo);

	public boolean insertPadre(CategoriaInsumo categoriaInsumo);

	public boolean delete(CategoriaInsumo categoriaInsumo);

	public List<CategoriaInsumo> readAll();

	public boolean edit(CategoriaInsumo categoriaInsumo);

	public int obtenerIdCategoriaInsumo(CategoriaInsumo categoriaInsumo);

	public String obtenerNombre(CategoriaInsumo categoriaInsumo);
}