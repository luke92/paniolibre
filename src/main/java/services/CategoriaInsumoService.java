package services;

import java.util.ArrayList;
import java.util.List;

import domain.model.CategoriaInsumo;
import dto.CategoriaInsumoDTO;
import persistencia.dao.interfaz.CategoriaInsumoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class CategoriaInsumoService {
	private CategoriaInsumoDAO categoriaInsumo;

	public CategoriaInsumoService(DAOAbstractFactory metodoPersistencia) {
		this.categoriaInsumo = metodoPersistencia.createCategoriaInsumoDAO();
	}

	public void agregarCategoriaInsumo(CategoriaInsumo c) {
		this.categoriaInsumo.insert(c);
	}

	public void agregarCategoriaInsumoPadre(CategoriaInsumo c) {
		this.categoriaInsumo.insertPadre(c);
	}

	public void eliminaCategoriaInsumo(CategoriaInsumo c) {
		this.categoriaInsumo.delete(c);
	}

	public void editarCategoriaInsumo(CategoriaInsumo c) {
		this.categoriaInsumo.edit(c);
	}

	public List<CategoriaInsumo> obtenerCategoriaInsumos() {
		return this.categoriaInsumo.readAll();
	}

	public List<CategoriaInsumoDTO> obtenerCategoriaInsumosDTO() {
		List<CategoriaInsumoDTO> categorias = new ArrayList<CategoriaInsumoDTO>();
		for (CategoriaInsumo categoria : this.obtenerCategoriaInsumos()) {
			categorias.add(categoria.getDTO());
		}
		return categorias;
	}

	public int obtenerIdCategoriaInsumo(CategoriaInsumo categoriaInsumo) {
		return this.categoriaInsumo.obtenerIdCategoriaInsumo(categoriaInsumo);
	}

	public String obtenerNombre(CategoriaInsumo categoriaInsumo) {
		return this.categoriaInsumo.obtenerNombre(categoriaInsumo);
	}
}