package services;

import org.junit.Test;

import persistencia.dao.mysql.DAOSQLFactory;

public class OrdenDeTrabajoServiceTest {
	OrdenDeTrabajoService ordenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
	java.util.Date utilDate = new java.util.Date();
	java.sql.Date fecha = new java.sql.Date(utilDate.getTime());

	@Test
	public void testAltaOrdenDeTrabajoService() {
		// OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo(500, fecha, fecha, fecha,
		// "Comentario de prueba", 1);
		// if (!OrdenDeTrabajoService.existe(ordenDeTrabajo))
		// OrdenDeTrabajoService.agregarOrdenDeTrabajo(ordenDeTrabajo);
		// assertTrue(OrdenDeTrabajoService.existe(ordenDeTrabajo));
	}

	@Test
	public void testBajaOrdenDeTrabajo() {
		// OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo(49, fecha, fecha, fecha,
		// "Comentario de prueba", 1);
		// if (!OrdenDeTrabajoService.existe(ordenDeTrabajo))
		// OrdenDeTrabajoService.agregarOrdenDeTrabajo(ordenDeTrabajo);
		// assertTrue(OrdenDeTrabajoService.existe(ordenDeTrabajo));
		// OrdenDeTrabajoService.eliminarOrdenDeTrabajo(ordenDeTrabajo);
		// assertFalse(OrdenDeTrabajoService.existe(ordenDeTrabajo));
	}

	@Test
	public void editarOrdenDeTrabajo() {
		// OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo(500, fecha, fecha, fecha,
		// "Comentario de prueba", 1);
		// if (!OrdenDeTrabajoService.existe(ordenDeTrabajo))
		// OrdenDeTrabajoService.agregarOrdenDeTrabajo(ordenDeTrabajo);
		// assertTrue(OrdenDeTrabajoService.existe(ordenDeTrabajo));
		// // OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
		// ordenDeTrabajo.setFechaInicio(fecha);
		// ordenDeTrabajo.setFechaFinProbable(fecha);
		// ordenDeTrabajo.setFechaFinReal(fecha);
		// ordenDeTrabajo.setComentario("Comentario editado");
		// ordenDeTrabajo.setEstado(1);
		// ordenDeTrabajo.setIdOrdenDeTrabajo(2);
		// OrdenDeTrabajoService.editarOrdenDeTrabajo(ordenDeTrabajo);
	}
}