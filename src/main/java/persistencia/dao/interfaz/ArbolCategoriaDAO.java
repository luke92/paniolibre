package persistencia.dao.interfaz;

import domain.model.ArbolCategoria;
import util.TipoCategoria;

public interface ArbolCategoriaDAO {

	public ArbolCategoria getArbolCategorias(TipoCategoria tipoCategoria);

	public boolean insert(ArbolCategoria categoria);

	public boolean delete(ArbolCategoria categoria);

	public boolean edit(ArbolCategoria categoria);

	public boolean existeCategoriaEnOtraTabla(ArbolCategoria categoria);

	public boolean existeCategoria(ArbolCategoria categoria);
}
