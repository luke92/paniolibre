package services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import domain.model.ArbolCategoria;
import domain.model.CategoriaInsumo;
import domain.model.EnumUnidadMedida;
import domain.model.Estado;
import domain.model.Insumo;
import persistencia.dao.mysql.DAOSQLFactory;

public class InsumoServiceTest {
	InsumoService insumoService = new InsumoService(new DAOSQLFactory());
	CategoriaInsumoService categoriaInsumoService = new CategoriaInsumoService(new DAOSQLFactory());

	@Test
	public void testAltaInsumo() {
		ArbolCategoria categoriaPadre = new ArbolCategoria();
		categoriaPadre.setNombre("Pintura");
		categoriaPadre.setId(2);
		Insumo insumo = new Insumo(null, "BBB000", "Bastidores", "BRB", categoriaPadre, EnumUnidadMedida.CENTIMETROS,
				"Plastico", 5, Estado.ALTA);
		insumoService.agregarInsumo(insumo);
	}

	@Test
	public void testObtenerInsumos() {
		List<Insumo> insumos = insumoService.obtenerInsumos();
		assertTrue(insumos.size() > 0);
	}

	public void editarInsumo() {
		CategoriaInsumo categoriaPadre = new CategoriaInsumo();
		categoriaPadre.setNombre("Pintura");
		categoriaPadre.setActivo(Estado.ALTA);
		int idPadre = categoriaInsumoService.obtenerIdCategoriaInsumo(categoriaPadre);
		categoriaPadre.setIdCategoriaInsumo(idPadre);
		ArbolCategoria categoriaInsumo = new ArbolCategoria();
		categoriaInsumo.setId(3);
		categoriaInsumo.setNombre("Latex");
		categoriaInsumo.setActivo(Estado.ALTA);
		Insumo insumo = new Insumo();
		insumo.setNombre("Balde 40 lts");
		insumo.setUmbralMinimo(5);
		insumo.setEstado(Estado.ALTA);
		insumo.setComentario("Balde de pintura para todo tipo de paredes");
		insumo.setCategoriaInsumo(categoriaInsumo);
		int idInsumo = insumoService.obtenerIdInsumo(insumo);
		insumo.setIdInsumo(idInsumo);
		insumo.setNombre("Balde 20 lts");
		insumoService.editarInsumo(insumo);
	}
}