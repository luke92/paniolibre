package services;

import java.util.List;

import domain.model.CategoriaHerramienta;
import persistencia.dao.interfaz.CategoriaHerramientaDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class CategoriaHerramientaService {
	private CategoriaHerramientaDAO categoriaHerramienta;

	public CategoriaHerramientaService(DAOAbstractFactory metodoPersistencia) {
		this.categoriaHerramienta = metodoPersistencia.createCategoriaHerramientaDAO();
	}

	public void agregarCategoriaHerramienta(CategoriaHerramienta c) {
		this.categoriaHerramienta.insert(c);
	}

	public void agregarCategoriaHerramientaPadre(CategoriaHerramienta c) {
		this.categoriaHerramienta.insertPadre(c);
	}

	public void eliminaCategoriaHerramienta(CategoriaHerramienta c) {
		this.categoriaHerramienta.delete(c);
	}

	public void editarCategoriaHerramienta(CategoriaHerramienta c) {
		this.categoriaHerramienta.edit(c);
	}

	public List<CategoriaHerramienta> obtenerCategoriaHerramienta() {
		return this.categoriaHerramienta.readAll();
	}

	public int obtenerIdCategoriaHerramienta(CategoriaHerramienta categoriaHerramienta) {
		return this.categoriaHerramienta.obtenerIdCategoriaHerramienta(categoriaHerramienta);
	}

	public boolean existe(CategoriaHerramienta categoriaHerramienta) {
		return obtenerIdCategoriaHerramienta(categoriaHerramienta) == 0;
	}

	public String obtenerNombreCategoriaHerramienta(CategoriaHerramienta categoriaHerramienta) {
		return this.categoriaHerramienta.obtenerNombreCategoriaHerramienta(categoriaHerramienta);
	}

}
