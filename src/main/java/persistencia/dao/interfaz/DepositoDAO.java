package persistencia.dao.interfaz;

import java.util.List;

import domain.model.Deposito;

public interface DepositoDAO {
	public boolean insert(Deposito deposito);

	public boolean delete(Deposito deposito);

	public List<Deposito> readAll();

	public boolean edit(Deposito deposito);

	public int obtenerIdDeposito(Deposito deposito);

	public String obtenerNombreDeposito(Deposito deposito);
}