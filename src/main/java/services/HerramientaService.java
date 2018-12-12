package services;

import java.sql.Date;
import java.util.List;

import domain.model.DevolucionHerramienta;
import domain.model.Herramienta;
import domain.model.RetiroHerramienta;
import dto.ArbolCategoriaDTO;
import dto.HerramientaReporteDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.HerramientaDAO;

public class HerramientaService {
	private HerramientaDAO herramienta;

	public HerramientaService(DAOAbstractFactory metodoPersistencia) {
		this.herramienta = metodoPersistencia.createHerramientaDAO();
	}

	public RetiroHerramienta obtenerPrimerRetiroHerramienta() {
		return herramienta.obtenerPrimerPrestamoHerramienta();
	}

	public boolean existe(Herramienta herramienta) {
		return (obtenerIdHerramienta(herramienta) == 0);
	}

	public void agregarHerramienta(Herramienta herramienta) {
		this.herramienta.insert(herramienta);
	}

	public void eliminaHerramienta(Herramienta herramienta) {
		this.herramienta.delete(herramienta);
	}

	public void editarHerramienta(Herramienta herramienta) {
		this.herramienta.edit(herramienta);
	}

	public List<Herramienta> obtenerHerramienta() {
		return this.herramienta.readAll();
	}

	public List<Herramienta> obtenerHerramientaAveriadas() {
		return this.herramienta.obtenerHerramientasAveriadas();
	}

	public List<Herramienta> obtenerHerramientaDisponibles() {
		return this.herramienta.obtenerHerramientasDisponibles();
	}

	public List<Herramienta> obtenerHerramientaEnReparacion() {
		return this.herramienta.obtenerHerramientasEnReparacion();
	}

	public List<Herramienta> obtenerHerramientaEnPrestados() {
		return this.herramienta.obtenerHerramientasPrestadas();
	}

	public int obtenerIdHerramienta(Herramienta herramienta) {
		return this.herramienta.obtenerIdHerramienta(herramienta);
	}

	public boolean cambiarEstadoPrestada(Herramienta herramienta) {
		return this.herramienta.cambiarEstadoPrestada(herramienta);
	}

	public boolean cambiarEstadoDisponible(Herramienta herramienta) {
		return this.herramienta.cambiarEstadoDisponible(herramienta);
	}

	public boolean cambiarEstadoAvariada(Herramienta herramienta) {
		return this.herramienta.cambiarEstadoAveriada(herramienta);
	}

	public void cambiarEstadoEnReparacion(Herramienta herramienta) {
		this.herramienta.cambiarEstadoEnReparacion(herramienta);
	}

	public Herramienta obtenerHerramientaMaestro(Herramienta herramienta) {
		return this.herramienta.obtenerHerramientaMaestro(herramienta);
	}

	public DevolucionHerramienta obtenerComentarioAveriado(Herramienta herramienta) {
		return this.herramienta.obtenerComentarioAveriado(herramienta);
	}

	public List<HerramientaReporteDTO> obtenerHerramientasMasPrestadas(ArbolCategoriaDTO categoriaDTO, Date fechaInicio,
			Date fechaFin) {
		return herramienta.obtenerHerramientasMasPrestadas(categoriaDTO, fechaInicio, fechaFin);
	}
}
