package services;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import domain.model.DevolucionHerramienta;
import domain.model.EnumEstadoHerramienta;
import domain.model.Especialidad;
import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.Tecnico;
import domain.model.Usuario;
import persistencia.dao.mysql.DAOSQLFactory;

public class DevolucionHerramientaServiceTest {
	private DevolucionHerramientaService servicio = new DevolucionHerramientaService(new DAOSQLFactory());
	private HerramientaService herramientaServicio = new HerramientaService(new DAOSQLFactory());
	private TecnicoService tecnicoService = new TecnicoService(new DAOSQLFactory());
	private Usuario usuario;
	private Tecnico tecnico;
	private Herramienta herramienta;
	private OrdenDeTrabajo ordenDeTrabajo;
	private DevolucionHerramienta devolucionHerramienta;
	private Calendar fechaDevolucion;
	private Especialidad especialidad;
	private EnumEstadoHerramienta estado;

	@Test
	public void testAltaDevolucionHerramientaSinOrden() {
		usuario = new Usuario(1, "Lucas", "Vargas", "lvargas", "123456", "lucasjv92@gmail.com", 1);
		especialidad = new Especialidad(1, "Electricista");
		tecnico = new Tecnico();
		tecnico.setDni("38737338");
		tecnico.setLegajo("4829848");
		tecnico.setNombre("Walter");
		tecnico.setApellido("Roma");
		// tecnico.setEspecialidad(especialidad);
		tecnico.setActivo(1);
		tecnico.setIdTecnico(tecnicoService.obtenerIdTecnico(tecnico));
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaDevolucion = Calendar.getInstance();
		fechaDevolucion.set(2018, 05, 11);
		estado = EnumEstadoHerramienta.DISPONIBLE;
		devolucionHerramienta = new DevolucionHerramienta(0, usuario, tecnico, herramienta, ordenDeTrabajo,
				fechaDevolucion, estado, "");
		if (!servicio.existe(devolucionHerramienta))
			servicio.agregarDevolucionHerramienta(devolucionHerramienta);
		assertTrue(servicio.existe(devolucionHerramienta));
	}

	@Test
	public void testAltaDevolucionHerramientaConOrden() {
		usuario = new Usuario(1, "Lucas", "Vargas", "lvargas", "123456", "lucasjv92@gmail.com", 1);
		especialidad = new Especialidad(1, "Electricista");
		tecnico = new Tecnico();
		tecnico.setDni("38737338");
		tecnico.setLegajo("4829848");
		tecnico.setNombre("Walter");
		tecnico.setApellido("Roma");
		// tecnico.setEspecialidad(especialidad);
		tecnico.setActivo(1);
		tecnico.setIdTecnico(tecnicoService.obtenerIdTecnico(tecnico));
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaDevolucion = Calendar.getInstance();
		fechaDevolucion.set(2018, 05, 11);
		estado = EnumEstadoHerramienta.DISPONIBLE;
		ordenDeTrabajo = new OrdenDeTrabajo();
		ordenDeTrabajo.setIdOrdenDeTrabajo("2");
		devolucionHerramienta = new DevolucionHerramienta(0, usuario, tecnico, herramienta, ordenDeTrabajo,
				fechaDevolucion, estado, "");
		if (!servicio.existe(devolucionHerramienta))
			servicio.agregarDevolucionHerramienta(devolucionHerramienta);
		assertTrue(servicio.existe(devolucionHerramienta));
	}

	@Test
	public void testEditarDevolucionHerramienta() {
		usuario = new Usuario(1, "Lucas", "Vargas", "lvargas", "123456", "lucasjv92@gmail.com", 1);
		especialidad = new Especialidad(1, "Electricista");
		tecnico = new Tecnico();
		tecnico.setDni("38737338");
		tecnico.setLegajo("4829848");
		tecnico.setNombre("Walter");
		tecnico.setApellido("Roma");
		// tecnico.setEspecialidad(especialidad);
		tecnico.setActivo(1);
		tecnico.setIdTecnico(tecnicoService.obtenerIdTecnico(tecnico));
		herramienta = new Herramienta();
		herramienta.setNombre("Taladro");
		herramienta.setIdHerramienta(herramientaServicio.obtenerIdHerramienta(herramienta));
		fechaDevolucion = Calendar.getInstance();
		fechaDevolucion.set(2018, 05, 11);
		estado = EnumEstadoHerramienta.DISPONIBLE;
		ordenDeTrabajo = new OrdenDeTrabajo();
		ordenDeTrabajo.setIdOrdenDeTrabajo("2");
		devolucionHerramienta = new DevolucionHerramienta();
		devolucionHerramienta.setUsuario(usuario);
		devolucionHerramienta.setTecnico(tecnico);
		devolucionHerramienta.setHerramienta(herramienta);
		devolucionHerramienta
				.setIdDevolucionHerramienta(servicio.obtenerIdDevolucionHerramienta(devolucionHerramienta));
		devolucionHerramienta.setEstado(estado);
		devolucionHerramienta.setFechaDevolucion(fechaDevolucion);
		devolucionHerramienta.setOrdenDeTrabajo(ordenDeTrabajo);
		devolucionHerramienta.setEstado(estado);
		estado = EnumEstadoHerramienta.AVERIADO;
		devolucionHerramienta.setEstado(estado);
		servicio.editarDevolucionHerramienta(devolucionHerramienta);
	}

}
