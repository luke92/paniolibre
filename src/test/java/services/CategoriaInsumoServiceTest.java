package services;

import org.junit.Test;

import domain.model.CategoriaInsumo;
import domain.model.Estado;
import persistencia.dao.mysql.DAOSQLFactory;

public class CategoriaInsumoServiceTest {

	CategoriaInsumoService categoriaInsumoService = new CategoriaInsumoService(new DAOSQLFactory());

	@Test
	public void testAltaCategoriaInsumo() {
		CategoriaInsumo categoriaPadre = new CategoriaInsumo();
		categoriaPadre.setNombre("Pintura");
		categoriaInsumoService.agregarCategoriaInsumoPadre(categoriaPadre);
		int idPadre = categoriaInsumoService.obtenerIdCategoriaInsumo(categoriaPadre);
		categoriaPadre.setIdCategoriaInsumo(idPadre);
		CategoriaInsumo categoriaInsumo = new CategoriaInsumo(0, "Latex", Estado.ALTA, categoriaPadre);
		categoriaInsumoService.agregarCategoriaInsumo(categoriaInsumo);
		// List<CategoriaInsumo> categoriasInsumos =
		// categoriaInsumoService.obtenerCategoriaInsumos();
		// assertEquals(2, categoriasInsumos.size());
	}

	@Test
	public void testBajaCategoriaInsumo() {
		CategoriaInsumo categoriaPadre = new CategoriaInsumo();
		categoriaPadre.setNombre("Pintura");
		int idPadre = categoriaInsumoService.obtenerIdCategoriaInsumo(categoriaPadre);
		categoriaPadre.setIdCategoriaInsumo(idPadre);
		CategoriaInsumo categoriaInsumo = new CategoriaInsumo();
		categoriaInsumo.setNombre("Latex");
		categoriaInsumo.setActivo(Estado.ALTA);
		int idCategoriaI = categoriaInsumoService.obtenerIdCategoriaInsumo(categoriaInsumo);
		categoriaInsumo.setIdCategoriaInsumo(idCategoriaI);
		categoriaInsumo.setCategoriaPadre(categoriaPadre);
		categoriaInsumoService.eliminaCategoriaInsumo(categoriaInsumo);
	}

	@Test
	public void editarCategoriaInsumo() {
		CategoriaInsumo categoriaPadre = new CategoriaInsumo();
		categoriaPadre.setNombre("Pintura");
		int idPadre = categoriaInsumoService.obtenerIdCategoriaInsumo(categoriaPadre);
		categoriaPadre.setIdCategoriaInsumo(idPadre);
		CategoriaInsumo categoriaInsumo = new CategoriaInsumo();
		categoriaInsumo.setNombre("Latex");
		categoriaInsumo.setActivo(Estado.ALTA);
		int idCategoriaI = categoriaInsumoService.obtenerIdCategoriaInsumo(categoriaInsumo);
		categoriaInsumo.setIdCategoriaInsumo(idCategoriaI);
		categoriaInsumo.setCategoriaPadre(categoriaPadre);
		categoriaInsumo.setNombre("Barnis");
		categoriaInsumoService.editarCategoriaInsumo(categoriaInsumo);
	}
}