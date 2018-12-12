package persistencia.dao.interfaz;

import java.sql.Date;
import java.util.List;

import domain.model.OrdenDeTrabajo;
import domain.model.Proyecto;
import domain.model.TipoActividad;
import dto.OrdenDeTrabajoPorTecnicoDTO;
import dto.OrdenesReporteDTO;
import dto.TecnicoDTO;

public interface OrdenDeTrabajoDAO {
	public  OrdenDeTrabajo getOrdenSinDevolucionesPendientes(String idMantis);
	
	public boolean insert(OrdenDeTrabajo ordenDeTrabajo);

	public boolean delete(OrdenDeTrabajo ordenDeTrabajo);

	public boolean edit(OrdenDeTrabajo ordenDeTrabajo);

	public List<OrdenDeTrabajo> readAll();

	public boolean finalizarOrden(OrdenDeTrabajo ordenDeTrabajo);

	public OrdenDeTrabajo getById(OrdenDeTrabajo ordenDeTrabajo);

	public List<TipoActividad> readAllTiposActividad();

	public List<Proyecto> readAllProyectos();

	public int obtenerId(OrdenDeTrabajo ordenDeTrabajo);

	public boolean cambiarEstadoAsignado(OrdenDeTrabajo ordenDeTrabajo);
	
	public List<TecnicoDTO> obtenerTecnicos(OrdenDeTrabajo ordenDeTrabajo);

	public List<OrdenDeTrabajoPorTecnicoDTO> obtenerOrdenPorTecnicos(OrdenDeTrabajo ordenDeTrabajo);

	public List<OrdenesReporteDTO> obtenerOrdenesDeTrabajoPorReporte(Date dateInicio, Date dateFin, int nuevo, int asignada, int realizada,int cerrada, int suspendida);

	public OrdenDeTrabajo obtenerPrimerOrden();

	public boolean cambiarEstadoResuelto(OrdenDeTrabajo ot);

	public boolean cambiarEstadoCerrada(OrdenDeTrabajo ot);

	public boolean cambiarEstadoSuspendida(OrdenDeTrabajo ot);
}