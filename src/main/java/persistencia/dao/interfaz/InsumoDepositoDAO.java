package persistencia.dao.interfaz;

import java.util.List;

import domain.model.InsumoDeposito;
import domain.model.UbicacionDeposito;
import dto.AlertaInsumoDTO;

public interface InsumoDepositoDAO {
	public boolean insert(InsumoDeposito insumoDeposito);

	public boolean delete(InsumoDeposito insumoDeposito);

	public List<InsumoDeposito> readAll();

	public boolean edit(InsumoDeposito insumoDeposito);
	
	public boolean edit(InsumoDeposito insumoDepositoAEditar, UbicacionDeposito ubicacionNueva);

	public boolean ajustarStock(InsumoDeposito insumoDeposito);

	public InsumoDeposito getById(InsumoDeposito insumoDeposito);

	public List<AlertaInsumoDTO> obtenerAlertaUmbralMinimo();

	public boolean existeInsumoEnDepositos(InsumoDeposito insumo);

	public List<InsumoDeposito> readAllPorNumeroDeOrden(int id);
}