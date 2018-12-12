package services;

import java.util.ArrayList;
import java.util.List;

import dto.AlertaDTO;
import dto.AlertaInsumoDTO;
import dto.AlertaPedidoDTO;
import dto.AlertaReparacionDTO;
import dto.TipoAlerta;
import persistencia.dao.interfaz.AlertaDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class AlertaService {

	private static AlertaDAO alertaDAO;

	public AlertaService(DAOAbstractFactory metodoPersistencia) {
		alertaDAO = metodoPersistencia.createAlertaDAO();
	}

	public List<AlertaDTO> obtenerAlertas() {
		List<AlertaDTO> alertas = new ArrayList<>();

		for (AlertaInsumoDTO alerta : alertaDAO.obtenerAlertasInsumos()) {
			alertas.add(getAlertaInsumo(alerta));
		}

		for (AlertaPedidoDTO alerta : alertaDAO.obtenerAlertasPedidos()) {
			alertas.add(getAlertaPedido(alerta));
		}

		for (AlertaReparacionDTO alerta : alertaDAO.obtenerAlertasReparaciones()) {
			alertas.add(getAlertaReparacion(alerta));
		}

		return alertas;
	}

	public List<AlertaDTO> obtenerAlertasNoEnviadasPorMail() {
		List<AlertaDTO> alertas = new ArrayList<>();

		for (AlertaInsumoDTO alerta : alertaDAO.obtenerAlertasInsumos()) {

			if (!alerta.isEnviadoPorMail()) {
				enviarPorMailAlertaInsumo(alerta);
				alertas.add(getAlertaInsumo(alerta));
			}
		}

		for (AlertaPedidoDTO alerta : alertaDAO.obtenerAlertasPedidos()) {
			if (!alerta.isEnviadoPorMail()) {
				enviarPorMailAlertaPedido(alerta);
				alertas.add(getAlertaPedido(alerta));
			}
		}

		for (AlertaReparacionDTO alerta : alertaDAO.obtenerAlertasReparaciones()) {
			if (!alerta.isEnviadoPorMail()) {
				enviarPorMailAlertaReparacion(alerta);
				alertas.add(getAlertaReparacion(alerta));
			}
		}

		return alertas;
	}

	public int obtenerCantidadAlertas() {
		return obtenerAlertas().size();
	}
	
	public void actualizarAlertas() {
		
	}
	
	private void enviarPorMailAlertaInsumo(AlertaInsumoDTO alerta) {
		alertaDAO.enviarPorMailAlertaInsumo(alerta.getIdAlerta());
	}
	
	private void enviarPorMailAlertaPedido(AlertaPedidoDTO alerta) {
		alertaDAO.enviarPorMailAlertaPedido(alerta.getIdAlerta());
	}
	
	private void enviarPorMailAlertaReparacion(AlertaReparacionDTO alerta) {
		alertaDAO.enviarPorMailAlertaReparacion(alerta.getIdAlerta());
	}
	
	private AlertaDTO getAlertaInsumo(AlertaInsumoDTO alerta) {
		AlertaDTO alertaDTO = new AlertaDTO();
		if (alerta.getStock() == 0) {
			alertaDTO.setTipoAlerta(TipoAlerta.STOCKCERO.getNombre());
		} else {
			alertaDTO.setTipoAlerta(TipoAlerta.UMBRALMINIMO.getNombre());
		}
		alertaDTO.setDetalleAlerta(alerta.toString());
		return alertaDTO;
	}

	private AlertaDTO getAlertaPedido(AlertaPedidoDTO alerta) {
		AlertaDTO alertaDTO = new AlertaDTO();
		alertaDTO.setTipoAlerta(TipoAlerta.PEDIDOS.getNombre());
		alertaDTO.setDetalleAlerta(alerta.toString());
		return alertaDTO;
	}

	private AlertaDTO getAlertaReparacion(AlertaReparacionDTO alerta) {
		AlertaDTO alertaDTO = new AlertaDTO();
		alertaDTO.setTipoAlerta(TipoAlerta.REPARACIONES.getNombre());
		alertaDTO.setDetalleAlerta(alerta.toString());
		return alertaDTO;
	}
}
