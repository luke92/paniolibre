package services;

import org.junit.Test;

import persistencia.dao.mysql.DAOSQLFactory;

class PedidoInsumoServiceTest {
	PedidoInsumoService pedidoInsumoService = new PedidoInsumoService(new DAOSQLFactory());
	java.util.Date utilDate = new java.util.Date();
	java.sql.Date fecha = new java.sql.Date(utilDate.getTime());

	@Test
	void agregarPedidoInsumo() {
		// PedidoInsumo pedidoInsumo = new PedidoInsumo();
		// pedidoInsumo.setFechaProbableRecepcion(fecha);
		// pedidoInsumo.setComentario("Pedido Urgente");
		// pedidoInsumo.setNroOrdenCompra(111);
		// pedidoInsumoService.agregarPedidoInsumo(pedidoInsumo);
	}
}