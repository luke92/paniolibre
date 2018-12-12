package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import domain.model.Insumo;
import domain.model.RetiroInsumo;
import dto.ArbolCategoriaDTO;
import dto.InsumoDTO;
import dto.InsumoReporteDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.InsumoDAO;

public class InsumoService {
	private InsumoDAO insumo;

	public InsumoService(DAOAbstractFactory metodoPersistencia) {
		this.insumo = metodoPersistencia.createInsumoDAO();
	}

	public RetiroInsumo obtenerPrimerRetiroInsumo() {
		return insumo.obtenerPrimerRetiroInsumo();
	}

	public void agregarInsumo(Insumo insumo) {
		this.insumo.insert(insumo);
	}

	public void eliminarInsumo(Insumo insumo) {
		this.insumo.delete(insumo);
	}

	public void editarInsumo(Insumo insumo) {
		this.insumo.edit(insumo);
	}

	public List<Insumo> obtenerInsumos() {
		return this.insumo.readAll();
	}

	public List<InsumoDTO> obtenerInsumosDTO() {
		List<InsumoDTO> insumos = new ArrayList<InsumoDTO>();
		for (Insumo i : this.insumo.readAll()) {
			InsumoDTO insumo = i.getInsumoDTO();
			insumos.add(insumo);
		}
		return insumos;
	}

	public int obtenerIdInsumo(Insumo insumo) {
		return this.insumo.obtenerIdInsumo(insumo);
	}

	public boolean existeInsumo(Insumo insumo) {
		return (obtenerIdInsumo(insumo) != 0);
	}

	public Insumo obtenerInsumoMaestro(Insumo insumo) {
		return this.insumo.obtenerInsumoMaestro(insumo);
	}

	public boolean existeInsumo(int idInsumo) {
		Insumo insumo = new Insumo();
		insumo.setIdInsumo(idInsumo);
		return (this.obtenerInsumoMaestro(insumo) != null);
	}

	public List<InsumoReporteDTO> obtenerInsumosMasPrestadas(ArbolCategoriaDTO categoriaDTO, Date fechaInicio,
			Date fechaFin) {
		return insumo.obtenerInsumosMasPrestadas(categoriaDTO, fechaInicio, fechaFin);
	}
}