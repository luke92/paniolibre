package persistencia.dao.interfaz;

import domain.model.IngresoInsumo;

public interface IngresoInsumoDAO {
	public boolean insert(IngresoInsumo ingresoInsumo);

	public boolean insertConOrdenDeTrabajo(IngresoInsumo ingresoInsumo);
}
