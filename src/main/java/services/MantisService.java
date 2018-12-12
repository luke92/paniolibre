package services;

import dto.MantisDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.MantisDAO;

public class MantisService {
	private static MantisDAO mantisDAO;
	
	public MantisService(DAOAbstractFactory metodoPersistencia) {
		mantisDAO = metodoPersistencia.createMantisDAO();
	}
	
	public boolean actualizarConfiguracionMantis(MantisDTO mantisDTO) {
		return mantisDAO.actualizarConfiguracionMantis(mantisDTO);
	}
	
	public MantisDTO obtenerConfiguracionMantis() {
		return mantisDAO.obtenerConfiguracionMantis();
	}
	
}
