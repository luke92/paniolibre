package persistencia.dao.interfaz;

import java.util.List;

import domain.model.PedidoInsumoDetalle;

public interface PedidoInsumoDetalleDAO {
	public boolean insert(PedidoInsumoDetalle pedidoInsumoDetalle);

	public boolean delete(PedidoInsumoDetalle pedidoInsumoDetalle);

	public List<PedidoInsumoDetalle> readAll();

	public boolean edit(PedidoInsumoDetalle pedidoInsumoDetalle);

	public boolean cambiarEstadoProcesado(PedidoInsumoDetalle pedidoInsumoDetalle_a_editar);

	public boolean cambiarEstadoSinProcesar(PedidoInsumoDetalle pedidoInsumoDetalle_a_editar);

}