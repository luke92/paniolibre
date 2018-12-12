package persistencia.dao.interfaz;

import java.sql.Date;
import java.util.List;

import domain.model.Insumo;
import domain.model.RetiroInsumo;
import dto.ArbolCategoriaDTO;
import dto.InsumoReporteDTO;

public interface InsumoDAO {
	public boolean insert(Insumo insumo);

	public boolean delete(Insumo insumo);

	public List<Insumo> readAll();

	public boolean edit(Insumo insumo);

	public int obtenerIdInsumo(Insumo insumo);

	public Insumo obtenerInsumoMaestro(Insumo insumo);

	public Insumo getById(Insumo insumo);

	public List<InsumoReporteDTO> obtenerInsumosMasPrestadas(ArbolCategoriaDTO categoriaDTO, Date fechaInicio,
			Date fechaFin);

	public RetiroInsumo obtenerPrimerRetiroInsumo();
}