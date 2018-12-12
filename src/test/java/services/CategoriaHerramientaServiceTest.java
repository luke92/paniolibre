package services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import domain.model.CategoriaHerramienta;
import persistencia.dao.mysql.DAOSQLFactory;

public class CategoriaHerramientaServiceTest {

	private CategoriaHerramientaService servicio = new CategoriaHerramientaService(new DAOSQLFactory());
	private CategoriaHerramienta categoriaPadre;
	private CategoriaHerramienta categoria;

	@Test
	public void testAltaCategoriaHerramientaPadre() {
		categoriaPadre = new CategoriaHerramienta();
		categoriaPadre.setNombre("Electrico");
		categoriaPadre.setIdCategoria(0);
		if (!servicio.existe(categoriaPadre))
			servicio.agregarCategoriaHerramientaPadre(categoriaPadre);
		;
		assertTrue(servicio.existe(categoriaPadre));

	}

	@Test
	public void testAltaCategoriaHerramientaHijo() {
		categoriaPadre = new CategoriaHerramienta();
		categoriaPadre.setNombre("Electrico");
		categoriaPadre.setIdCategoria(servicio.obtenerIdCategoriaHerramienta(categoriaPadre));
		categoria = new CategoriaHerramienta(0, "Uso diario", categoriaPadre, 1);
		if (!servicio.existe(categoria))
			servicio.agregarCategoriaHerramienta(categoria);
		;
		assertTrue(servicio.existe(categoria));
	}

	@Test
	public void testEditarCategoria() {
		categoriaPadre = new CategoriaHerramienta();
		categoriaPadre.setNombre("Electrico");
		categoriaPadre.setIdCategoria(servicio.obtenerIdCategoriaHerramienta(categoriaPadre));
		categoria = new CategoriaHerramienta(0, "Uso Normal", categoriaPadre, 1);
		if (!servicio.existe(categoria))
			servicio.agregarCategoriaHerramienta(categoria);
		;
		categoriaPadre = new CategoriaHerramienta();
		categoriaPadre.setNombre("Electrico");
		categoriaPadre.setIdCategoria(servicio.obtenerIdCategoriaHerramienta(categoriaPadre));
		categoria = new CategoriaHerramienta();
		categoria.setNombre("Uso Normal");
		categoria.setIdCategoria(servicio.obtenerIdCategoriaHerramienta(categoria));
		categoria.setCategoriaPadre(categoriaPadre);
		categoria.setActivo(1);
		categoria.setNombre("LLaves");
		servicio.editarCategoriaHerramienta(categoria);
	}

	@Test
	public void testBajaCategoria() {
		categoria = new CategoriaHerramienta();
		categoria.setNombre("LLaves");
		categoria.setIdCategoria(servicio.obtenerIdCategoriaHerramienta(categoria));
		servicio.eliminaCategoriaHerramienta(categoria);
	}

}
