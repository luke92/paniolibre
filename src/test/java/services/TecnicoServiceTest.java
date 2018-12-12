package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import domain.model.Especialidad;
import domain.model.Tecnico;
import persistencia.dao.mysql.DAOSQLFactory;

public class TecnicoServiceTest {
	private TecnicoService servicio = new TecnicoService(new DAOSQLFactory());
	private Especialidad especialidad;
	private Tecnico tecnico;

	@Test
	public void testAltaTecnico() {
		especialidad = new Especialidad();
		especialidad.setIdEspecialidad(1);
		especialidad.setNombre("Electricista");
		// tecnico = new Tecnico(0, "38737338", "4829848", "Walter", "Roma",
		// especialidad, 1);
		if (!servicio.existe(tecnico))
			servicio.agregarTecnico(tecnico);
		assertTrue(servicio.existe(tecnico));
	}

	@Test
	public void testEditarTecnico() {
		especialidad = new Especialidad();
		especialidad.setIdEspecialidad(1);
		especialidad.setNombre("Electricista");
		tecnico = new Tecnico();
		// tecnico.setEspecialidad(especialidad);
		tecnico.setDni("38737338");
		tecnico.setLegajo("4829848");
		tecnico.setIdTecnico(servicio.obtenerIdTecnico(tecnico));
		tecnico.setNombre("Walter");
		tecnico.setApellido("Roma");
		tecnico.setActivo(1);
		tecnico.setNombre("Walter");
		servicio.editarTecnico(tecnico);
	}

	@Test
	public void testBajaTecnico() {
		tecnico = new Tecnico();
		tecnico.setDni("38737338");
		tecnico.setIdTecnico(servicio.obtenerIdTecnico(tecnico));
		if (servicio.existe(tecnico))
			servicio.eliminarTecnico(tecnico);
		assertFalse(servicio.existe(tecnico));
	}

}
