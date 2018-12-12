package services;

import domain.model.ArbolCategoria;
import domain.model.Estado;
import dto.ArbolCategoriaDTO;
import persistencia.dao.interfaz.ArbolCategoriaDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import util.TipoCategoria;

public class ArbolCategoriaService {

	private ArbolCategoriaDAO categoriaService;

	public ArbolCategoriaService(DAOAbstractFactory metodoPersistencia) {
		this.categoriaService = metodoPersistencia.createArbolCategoriaDAO();
	}

	public ArbolCategoriaDTO obtenerArbolCategoriasInsumoDTO() {
		return this.obtenerArbolCategorias(TipoCategoria.INSUMOS).getDTO();
	}

	public ArbolCategoriaDTO obtenerArbolCategoriasHerramientaDTO() {
		return this.obtenerArbolCategorias(TipoCategoria.HERRAMIENTAS).getDTO();
	}

	public void agregarCategoria(ArbolCategoriaDTO categoria) {
		ArbolCategoria arbol = new ArbolCategoria();
		arbol.setNombre(categoria.getNombre());
		arbol.setTipoCategoria(categoria.getTipoCategoria());
		ArbolCategoria padre = new ArbolCategoria();
		padre.setId(categoria.getPadre().getId());
		arbol.setPadre(padre);

		this.agregarCategoria(arbol);
	}

	public void editarCategoria(ArbolCategoriaDTO categoria) {
		ArbolCategoria arbol = new ArbolCategoria();
		arbol.setId(categoria.getId());
		arbol.setNombre(categoria.getNombre());
		arbol.setActivo(Estado.getEstado(categoria.getActivo().getValor().get()));

		ArbolCategoria padre = new ArbolCategoria();
		padre.setId(categoria.getPadre().getId());

		arbol.setPadre(padre);
		arbol.setTipoCategoria(categoria.getTipoCategoria());

		this.editarCategoria(arbol);
	}

	public void borrarCategoria(ArbolCategoriaDTO categoria) {
		ArbolCategoria arbol = new ArbolCategoria();
		arbol.setId(categoria.getId());
		arbol.setNombre(categoria.getNombre());
		arbol.setTipoCategoria(categoria.getTipoCategoria());
		this.borrarCategoria(arbol);
	}

	public boolean existeCategoriaEnOtraTabla(ArbolCategoriaDTO categoria) {
		ArbolCategoria arbol = new ArbolCategoria();
		arbol.setId(categoria.getId());
		arbol.setTipoCategoria(categoria.getTipoCategoria());
		return this.existeCategoriaEnOtraTabla(arbol);
	}

	public boolean existeCategoria(ArbolCategoriaDTO categoria) {
		ArbolCategoria arbol = new ArbolCategoria();
		arbol.setNombre(categoria.getNombre());
		arbol.setTipoCategoria(categoria.getTipoCategoria());
		return this.existeCategoria(arbol);
	}

	private ArbolCategoria obtenerArbolCategorias(TipoCategoria tipoCategoria) {
		return this.categoriaService.getArbolCategorias(tipoCategoria);
	}

	private void agregarCategoria(ArbolCategoria categoria) {
		this.categoriaService.insert(categoria);
	}

	private void editarCategoria(ArbolCategoria categoria) {
		this.categoriaService.edit(categoria);
	}

	private void borrarCategoria(ArbolCategoria categoria) {
		this.categoriaService.delete(categoria);
	}

	private boolean existeCategoriaEnOtraTabla(ArbolCategoria categoria) {
		return this.categoriaService.existeCategoriaEnOtraTabla(categoria);
	}

	private boolean existeCategoria(ArbolCategoria categoria) {
		return this.categoriaService.existeCategoria(categoria);
	}
}
