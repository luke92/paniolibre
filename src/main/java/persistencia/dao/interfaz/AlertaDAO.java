package persistencia.dao.interfaz;

import java.util.List;

import dto.AlertaInsumoDTO;
import dto.AlertaPedidoDTO;
import dto.AlertaReparacionDTO;

public interface AlertaDAO {
	
	public List<AlertaInsumoDTO> obtenerAlertasInsumos();
	
	public List<AlertaPedidoDTO> obtenerAlertasPedidos();
	
	public List<AlertaReparacionDTO> obtenerAlertasReparaciones();
	
	public boolean enviarPorMailAlertaInsumo(int idAlerta);
	
	public boolean enviarPorMailAlertaPedido(int idAlerta);
	
	public boolean enviarPorMailAlertaReparacion(int idAlerta);
	
}
