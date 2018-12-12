package persistencia.dao.interfaz;

import java.util.List;

import domain.model.PedidoInsumo;
import domain.model.PedidoInsumoDetalle;

public interface PedidoInsumoDAO {
	public boolean insert(PedidoInsumo pedidoInsumo);

	public boolean delete(PedidoInsumo pedidoInsumo);

	public List<PedidoInsumo> readAll();

	public boolean edit(PedidoInsumo pedidoInsumo);

	public int obtenerId(PedidoInsumo pedidoInsumo);

	public boolean cambiarEstadoRecibido(PedidoInsumo pedidoInsumo);

	public PedidoInsumo obtenerPedidoInsumo(PedidoInsumo pedidoInsumo);

	public String obtenerIdMantis(PedidoInsumo pedidoInsumo);

	public boolean cambiarEstadoProcesado(PedidoInsumoDetalle pedidoInsumo);

	public boolean cambiarEstadoIncompleto(PedidoInsumo pedidoInsumo2);

	public List<PedidoInsumoDetalle> obtenerPedidosDetalle(PedidoInsumo pedido);

	public boolean cambiarEstadoParcial(PedidoInsumo pedidoInsumo);
}