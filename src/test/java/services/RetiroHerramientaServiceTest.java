package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import domain.model.Especialidad;
import domain.model.Herramienta;
import domain.model.RetiroHerramienta;
import domain.model.Tecnico;
import domain.model.Usuario;
import persistencia.dao.mysql.DAOSQLFactory;

public class RetiroHerramientaServiceTest {
	private RetiroHerramientaService servicio = new RetiroHerramientaService(new DAOSQLFactory());
	private HerramientaService herramientaServicio = new HerramientaService(new DAOSQLFactory());
	private Usuario usuario;
	private Tecnico tecnico;
	private Herramienta herramienta;
	// private OrdenDeTrabajo ordenDeTrabajo;
	private RetiroHerramienta retiroHerramienta;
	private Calendar fechaPrestamo;
	private Especialidad especialidad;

	@Test
	public void testAltaRetiroHerramienta() {
		usuario = new Usuario(1, "Lucas", "Vargas", "lvargas", "123456", "lucasjv92@gmail.com", 1);
		especialidad = new Especialidad(1, "Electricista");
		// tecnico = new Tecnico(1,"38737338","4829848","Walter", "Roma",especialidad,
		// 1);
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaPrestamo = Calendar.getInstance();
		fechaPrestamo.set(2018, 05, 11);
		retiroHerramienta = new RetiroHerramienta();
		retiroHerramienta.setIdRetiroHerramienta(0);
		retiroHerramienta.setUsuario(usuario);
		retiroHerramienta.setTecnico(tecnico);
		retiroHerramienta.setHerramienta(herramienta);
		retiroHerramienta.setFechaPrestamo(fechaPrestamo);
		if (!servicio.existe(retiroHerramienta))
			servicio.agregarRetiroHerramienta(retiroHerramienta);
		assertTrue(servicio.existe(retiroHerramienta));
	}

	@Test
	public void testBajaRetiroHerramienta() {
		usuario = new Usuario(1, "Lucas", "Vargas", "lvargas", "123456", "lucasjv92@gmail.com", 1);
		especialidad = new Especialidad(1, "Electricista");
		// tecnico = new Tecnico(1,"38737338","4829848","Walter", "Roma",especialidad,
		// 1);
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaPrestamo = Calendar.getInstance();
		fechaPrestamo.set(2018, 05, 11);
		retiroHerramienta = new RetiroHerramienta();
		retiroHerramienta.setUsuario(usuario);
		retiroHerramienta.setTecnico(tecnico);
		retiroHerramienta.setHerramienta(herramienta);
		retiroHerramienta.setFechaPrestamo(fechaPrestamo);
		retiroHerramienta.setIdRetiroHerramienta(servicio.obtenerIdRetiroHerramienta(retiroHerramienta));
		if (servicio.existe(retiroHerramienta))
			servicio.eliminarRetiroHerramienta(retiroHerramienta);
		assertFalse(servicio.existe(retiroHerramienta));
	}

	@Test
	public void testEditarRetiroHerramienta() {
		usuario = new Usuario(1, "Lucas", "Vargas", "lvargas", "123456", "lucasjv92@gmail.com", 1);
		especialidad = new Especialidad(1, "Electricista");
		// tecnico = new Tecnico(1,"38737338","4829848","Walter", "Roma",especialidad,
		// 1);
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaPrestamo = Calendar.getInstance();
		fechaPrestamo.set(2018, 05, 11);
		retiroHerramienta = new RetiroHerramienta();
		retiroHerramienta.setUsuario(usuario);
		retiroHerramienta.setTecnico(tecnico);
		retiroHerramienta.setHerramienta(herramienta);
		retiroHerramienta.setFechaPrestamo(fechaPrestamo);
		retiroHerramienta.setIdRetiroHerramienta(servicio.obtenerIdRetiroHerramienta(retiroHerramienta));
		fechaPrestamo = Calendar.getInstance();
		fechaPrestamo.set(2017, 05, 11);
		retiroHerramienta.setFechaPrestamo(fechaPrestamo);
		servicio.editarRetiroHerramienta(retiroHerramienta);
	}

}
