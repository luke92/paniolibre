package services;

import java.util.Calendar;

import org.junit.Test;

import domain.model.Herramienta;
import domain.model.ReparacionHerramienta;
import persistencia.dao.mysql.DAOSQLFactory;

public class ReparacionHerramientaServiceTest {
	private HerramientaService herramientaServicio = new HerramientaService(new DAOSQLFactory());
	private ReparacionHerramientaService servicio = new ReparacionHerramientaService(new DAOSQLFactory());
	private ReparacionHerramienta reparacionHerramienta;
	private Herramienta herramienta;
	private Calendar fechaEnviada;
	private Calendar fechaRecibida;
	private Calendar fechaExpiracionGarantia;

	@Test
	public void testAltaReparacionHerramienta() {
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaEnviada = Calendar.getInstance();
		fechaEnviada.set(2018, 05, 11);
		fechaRecibida = Calendar.getInstance();
		fechaRecibida.set(2018, 05, 15);
		fechaExpiracionGarantia = Calendar.getInstance();
		fechaExpiracionGarantia.set(2018, 11, 01);
		// reparacionHerramienta = new ReparacionHerramienta(0, herramienta, 0, "Se
		// trabo el mandril", "Funciona Bien",
		// fechaEnviada, fechaRecibida, fechaExpiracionGarantia);
		// if (!servicio.existe(reparacionHerramienta))
		// servicio.agregarReparacionHerramienta(reparacionHerramienta);
		// assertTrue(servicio.existe(reparacionHerramienta));

	}

	@Test
	public void testBajaReparacionHerramienta() {
		reparacionHerramienta = new ReparacionHerramienta();
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		reparacionHerramienta.setHerramienta(herramienta);
		// reparacionHerramienta
		// .setIdReparacionHerramienta(servicio.obtenerIdReparacionHerramienta(reparacionHerramienta));
		// if (servicio.existe(reparacionHerramienta))
		// servicio.eliminarReparacionHerramienta(reparacionHerramienta);
		// assertFalse(servicio.existe(reparacionHerramienta));
	}

	@Test
	public void testEditarReparacionHerramienta() {
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaEnviada = Calendar.getInstance();
		fechaEnviada.set(2018, 05, 11);
		fechaRecibida = Calendar.getInstance();
		fechaRecibida.set(2018, 05, 15);
		fechaExpiracionGarantia = Calendar.getInstance();
		fechaExpiracionGarantia.set(2018, 11, 01);
		// reparacionHerramienta = new ReparacionHerramienta(0, herramienta, 0, "Se
		// trabo el mandril", "Funciona Bien",
		// fechaEnviada, fechaRecibida, fechaExpiracionGarantia);
		// reparacionHerramienta.setIdReparacionHerramienta(servicio.obtenerIdReparacionHerramienta(reparacionHerramienta));
		// reparacionHerramienta.setComentarioEnvio("Se trabo la mecha");
		servicio.editarReparacionHerramienta(reparacionHerramienta);
	}

}
