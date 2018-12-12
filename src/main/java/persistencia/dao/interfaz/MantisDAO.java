package persistencia.dao.interfaz;

import dto.MantisDTO;

public interface MantisDAO {
	
	public MantisDTO obtenerConfiguracionMantis();
	
	public boolean actualizarConfiguracionMantis(MantisDTO mantisDTO);
}
