package services;

import java.util.ArrayList;
import java.util.List;

import domain.model.PedidoInsumo;
import domain.model.PedidoInsumoDetalle;
import dto.PedidoInsumoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PedidoInsumoDAO;
import persistencia.dao.interfaz.PedidoInsumoDetalleDAO;

public class PedidoInsumoService {
	private PedidoInsumoDAO pedidoInsumo;
	private PedidoInsumoDetalleDAO pedidoInsumoDetalle;

	public PedidoInsumoService(DAOAbstractFactory metodoPersistencia) {
		this.pedidoInsumo = metodoPersistencia.createPedidoInsumoDAO();
		this.pedidoInsumoDetalle = metodoPersistencia.createPedidoInsumoDetalleDAO();
	}

	public void cambiarProcesado(PedidoInsumoDetalle pedidoInsumoDetalle) {
		this.pedidoInsumo.cambiarEstadoProcesado(pedidoInsumoDetalle);
	}

	public void agregarPedidoInsumo(PedidoInsumo pedidoInsumo) {
		this.pedidoInsumo.insert(pedidoInsumo);
	}

	public void agregarPedidoInsumoDetalle(PedidoInsumoDetalle pedidoInsumoDetalle) {
		this.pedidoInsumoDetalle.insert(pedidoInsumoDetalle);
	}

	public void eliminarPedidoInsumo(PedidoInsumo pedidoInsumo) {
		this.pedidoInsumo.delete(pedidoInsumo);
	}

	public void eliminarPedidoInsumoDetalle(PedidoInsumoDetalle pedidoInsumoDetalle) {
		this.pedidoInsumoDetalle.delete(pedidoInsumoDetalle);
	}

	public List<PedidoInsumo> obtenerPedidoInsumo() {
		return this.pedidoInsumo.readAll();
	}

	public List<PedidoInsumoDetalle> obtenerPedidoDetallePorIdPedido(PedidoInsumo pedidoInsumo) {
		return this.pedidoInsumo.obtenerPedidosDetalle(pedidoInsumo);
	}

	public List<PedidoInsumoDTO> obtenerPedidoInsumoDTO() {
		List<PedidoInsumoDTO> pedidos = new ArrayList<PedidoInsumoDTO>();
		for (PedidoInsumo pedido : this.obtenerPedidoInsumo()) {
			pedidos.add(pedido.getDTO());
		}
		return pedidos;
	}

	public List<PedidoInsumoDetalle> obtenerPedidoInsumoDetalle() {
		return this.pedidoInsumoDetalle.readAll();
	}

	public void editarPedidoInsumo(PedidoInsumo pedidoInsumo) {
		this.pedidoInsumo.edit(pedidoInsumo);
	}

	public void editarPedidoInsumoDetalle(PedidoInsumoDetalle pedidoInsumoDetalle) {
		this.pedidoInsumoDetalle.edit(pedidoInsumoDetalle);
	}

	public void cambiarEstadoProcesado(PedidoInsumoDetalle pedidoInsumoDetalle) {
		this.pedidoInsumoDetalle.cambiarEstadoProcesado(pedidoInsumoDetalle);
	}

	public int obtenerIdPedido(PedidoInsumo nuevo_pedido) {
		return this.pedidoInsumo.obtenerId(nuevo_pedido);
	}

	public void cambiarEstadoRecibido(PedidoInsumo pedidoInsumo) {
		this.pedidoInsumo.cambiarEstadoRecibido(pedidoInsumo);

	}

	public PedidoInsumo obtenerPedidoInsumoMaster(PedidoInsumo pedidoInsumo) {
		return this.pedidoInsumo.obtenerPedidoInsumo(pedidoInsumo);
	}

	public String obtenerIdMantis(PedidoInsumo pedidoInsumo) {
		return this.pedidoInsumo.obtenerIdMantis(pedidoInsumo);
	}

	public void cambiarEstadoIncompleto(PedidoInsumo pedidoInsumo2) {
		this.pedidoInsumo.cambiarEstadoIncompleto(pedidoInsumo2);
	}

	public void cambiarEstadoParcial(PedidoInsumo pedidoInsumo) {
		this.pedidoInsumo.cambiarEstadoParcial(pedidoInsumo);
	}

	public void cambiarEstadoSinProcesar(PedidoInsumoDetalle pedidoInsumoDetalle) {
		this.pedidoInsumoDetalle.cambiarEstadoSinProcesar(pedidoInsumoDetalle);
	}
}