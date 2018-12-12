package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import domain.model.CategoriaHerramienta;
import domain.model.Deposito;
import domain.model.EnumEstadoHerramienta;
import domain.model.Estado;
import domain.model.Herramienta;
import domain.model.UbicacionDeposito;
import persistencia.dao.mysql.DAOSQLFactory;

public class HerramientaServiceTest {
	private HerramientaService herramientaServicio = new HerramientaService(new DAOSQLFactory());
	private Herramienta herramienta;
	private Deposito deposito;
	private Estado estado;
	private UbicacionDeposito ubicacion;
	private CategoriaHerramienta categoriaPadre;
	private CategoriaHerramienta categoria;
	private Calendar fechaAdquisicion;
	private Calendar fechaGarantiaExpiracion;

	@Test
	public void testAltaHerramienta() {
		deposito = new Deposito(1, "UNGS", "Sede Central", Estado.ALTA);
		ubicacion = new UbicacionDeposito(1, "A1", deposito, estado);
		categoriaPadre = new CategoriaHerramienta();
		categoriaPadre.setIdCategoria(1);
		categoriaPadre.setNombre("Electrico");
		categoria = new CategoriaHerramienta(2, "Uso Diario", categoriaPadre, 1);
		herramienta = new Herramienta();
		herramienta.setIdHerramienta(0);
		herramienta.setCodigo("1234");
		herramienta.setMarca("Sthill");
		herramienta.setNombre("Moladora");
		herramienta.setUbicacion(ubicacion);
		herramienta.setCategoria(categoria);
		herramienta.setFactura("C");
		herramienta.setNumeroActivo("123");
		herramienta.setComentario("Comentario");
		herramienta.setMecanismoAdquisicion("Externa");
		herramienta.setEstado(EnumEstadoHerramienta.DISPONIBLE);
		herramienta.setActivo(1);
		fechaAdquisicion = Calendar.getInstance();
		fechaAdquisicion.set(2010, 9, 27);
		herramienta.setFechaAdquisicion(fechaAdquisicion);
		fechaGarantiaExpiracion = Calendar.getInstance();
		fechaGarantiaExpiracion.set(2018, 11, 22);
		herramienta.setFechaGarantiaExpiracion(fechaGarantiaExpiracion);
		herramienta.setProveedor("Atilio");
		if (!herramientaServicio.existe(herramienta))
			herramientaServicio.agregarHerramienta(herramienta);
		assertTrue(herramientaServicio.existe(herramienta));
	}

	@Test
	public void testEditarHerramienta() {
		deposito = new Deposito(1, "Ungs", "Cede Central", Estado.ALTA);
		ubicacion = new UbicacionDeposito(1, "A1", deposito, estado);
		categoriaPadre = new CategoriaHerramienta();
		categoriaPadre.setIdCategoria(1);
		categoriaPadre.setNombre("Electrico");
		categoria = new CategoriaHerramienta(2, "Uso Diario", categoriaPadre, 1);
		herramienta = new Herramienta();
		herramienta.setNombre("Moladora");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		herramienta.setCodigo("1234");
		herramienta.setMarca("Shtill");
		herramienta.setUbicacion(ubicacion);
		herramienta.setCategoria(categoria);
		herramienta.setFactura("C");
		herramienta.setNumeroActivo("123");
		herramienta.setProveedor("Atilio");
		herramienta.setComentario("Comentario");
		herramienta.setMecanismoAdquisicion("Externa");
		herramienta.setEstado(EnumEstadoHerramienta.DISPONIBLE);
		herramienta.setActivo(1);
		fechaAdquisicion = Calendar.getInstance();
		fechaAdquisicion.set(2010, 9, 27);
		herramienta.setFechaAdquisicion(fechaAdquisicion);
		fechaGarantiaExpiracion = Calendar.getInstance();
		fechaGarantiaExpiracion.set(2018, 11, 22);
		herramienta.setFechaGarantiaExpiracion(fechaGarantiaExpiracion);
		herramienta.setNombre("Taladro");
		herramientaServicio.editarHerramienta(herramienta);
	}

	@Test
	public void testObtenerIdHerramienta() {
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setProveedor("Julio");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));

	}

	@Test
	public void testBajaHerramienta() {
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		if (herramientaServicio.existe(herramienta))
			herramientaServicio.eliminaHerramienta(herramienta);
		assertFalse(herramientaServicio.existe(herramienta));

	}

}
