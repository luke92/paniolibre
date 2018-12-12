package persistencia.dao.interfaz;

import java.sql.Date;
import java.util.List;

import domain.model.DevolucionHerramienta;
import domain.model.Herramienta;
import domain.model.RetiroHerramienta;
import dto.ArbolCategoriaDTO;
import dto.HerramientaReporteDTO;

public interface HerramientaDAO {

	public boolean insert(Herramienta herramienta);

	public boolean delete(Herramienta herramienta);

	public List<Herramienta> readAll();

	public boolean edit(Herramienta herramienta);

	public int obtenerIdHerramienta(Herramienta herramienta);

	public List<Herramienta> obtenerHerramientasAveriadas();

	public boolean cambiarEstadoEnReparacion(Herramienta herramienta);

	public boolean cambiarEstadoPrestada(Herramienta herramienta);

	public List<Herramienta> obtenerHerramientasDisponibles();

	public List<Herramienta> obtenerHerramientasEnReparacion();

	public boolean cambiarEstadoDisponible(Herramienta herramienta);

	public boolean cambiarEstadoAveriada(Herramienta herramienta);

	public Herramienta obtenerHerramientaMaestro(Herramienta herramienta);

	public List<Herramienta> obtenerHerramientasPrestadas();

	public DevolucionHerramienta obtenerComentarioAveriado(Herramienta herramienta);

	public List<HerramientaReporteDTO> obtenerHerramientasMasPrestadas(ArbolCategoriaDTO categoriaDTO, Date fechaInicio,
			Date fechaFin);
	public RetiroHerramienta obtenerPrimerPrestamoHerramienta();
}
