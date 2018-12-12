package persistencia.dao.interfaz;

import java.util.List;

import domain.model.UbicacionDeposito;

public interface UbicacionDepositoDAO {
	public boolean insert(UbicacionDeposito ubicacionDeposito);

	public boolean delete(UbicacionDeposito ubicacionDeposito);

	public List<UbicacionDeposito> readAll();

	public boolean edit(UbicacionDeposito ubicacionDeposito);

	public int obtenerIdUbicacionDeposito(UbicacionDeposito ubicacionDeposito);

	public UbicacionDeposito obtenerUbicacionDeposito(UbicacionDeposito ubicacion);

	public boolean existeNombre(UbicacionDeposito ubicacionDeposito);
}