package services;

import java.util.ArrayList;
import java.util.List;

import domain.model.Deposito;
import dto.DepositoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.DepositoDAO;

public class DepositoService {
	private static DepositoDAO depositoDAO;

	public DepositoService(DAOAbstractFactory metodoPersistencia) {
		depositoDAO = metodoPersistencia.createDepositoDAO();
	}

	public List<Deposito> obtenerDepositos() {
		return depositoDAO.readAll();
	}

	public List<DepositoDTO> obtenerDepositosDTO() {
		List<DepositoDTO> depositos = new ArrayList<DepositoDTO>();
		for (Deposito deposito : this.obtenerDepositos()) {
			depositos.add(deposito.getDTO());
		}
		return depositos;
	}

	public int obtenerIdDeposito(Deposito deposito) {
		return depositoDAO.obtenerIdDeposito(deposito);
	}

	public void agregarDeposito(Deposito deposito) {
		depositoDAO.insert(deposito);
	}

	public void editarDeposito(Deposito deposito) {
		depositoDAO.edit(deposito);
	}
}
