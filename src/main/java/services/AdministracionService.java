package services;

import java.util.ArrayList;
import java.util.List;

import domain.model.Deposito;
import domain.model.Especialidad;
import domain.model.UbicacionDeposito;
import dto.UbicacionDepositoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.DepositoDAO;
import persistencia.dao.interfaz.EspecialidadesDAO;
import persistencia.dao.interfaz.UbicacionDepositoDAO;

public class AdministracionService {
	private DepositoDAO deposito;
	private UbicacionDepositoDAO ubicacion;
	private EspecialidadesDAO especialidad;

	public AdministracionService(DAOAbstractFactory metodoPersistencia) {
		this.deposito = metodoPersistencia.createDepositoDAO();
		this.ubicacion = metodoPersistencia.createUbicacionDepositoDAO();
		this.especialidad = metodoPersistencia.createEspecialidadDAO();
	}

	public boolean existeNombreUbicacion(UbicacionDeposito ubicacionDeposito) {
		return ubicacion.existeNombre(ubicacionDeposito);
	}

	public UbicacionDeposito obtenerUbicacionDeposito(UbicacionDeposito ubicacion) {
		return this.ubicacion.obtenerUbicacionDeposito(ubicacion);
	}

	public void agregarEspecialidad(Especialidad especialidad) {
		this.especialidad.insert(especialidad);
	}

	public void agregarDeposito(Deposito deposito) {
		this.deposito.insert(deposito);
	}

	public void agregarUbicacion(UbicacionDeposito ubicacionDeposito) {
		this.ubicacion.insert(ubicacionDeposito);
	}

	public void eliminarEspecialidad(Especialidad especialidad) {
		this.especialidad.delete(especialidad);
	}

	public void eliminarDeposito(Deposito deposito) {
		this.deposito.delete(deposito);
	}

	public void eliminarUbicacion(UbicacionDeposito ubicacionDeposito) {
		this.ubicacion.delete(ubicacionDeposito);
	}

	public List<Deposito> obtenerDepositos() {
		return this.deposito.readAll();
	}

	public List<UbicacionDeposito> obtenerUbicacionDepositos() {
		return this.ubicacion.readAll();
	}
	
	public List<UbicacionDepositoDTO> obtenerUbicacionDepositosDTO()
	{
		List<UbicacionDepositoDTO> ubicaciones = new ArrayList<>();
		for(UbicacionDeposito ubicacionModel : obtenerUbicacionDepositos()) {
			ubicaciones.add(ubicacionModel.getDTO());
		}
		return ubicaciones;
	}
	
	public List<Especialidad> obtenerEspecialidades() {
		return this.especialidad.readAll();
	}

	public void editarDeposito(Deposito deposito) {
		this.deposito.edit(deposito);
	}

	public void editarUbicacionDeposito(UbicacionDeposito ubicacionDeposito) {
		this.ubicacion.edit(ubicacionDeposito);
	}

	public int obtenerIdDeposito(Deposito deposito) {
		return this.deposito.obtenerIdDeposito(deposito);
	}

	public int obtenerIdUbicacionDeposito(UbicacionDeposito ubicacionDeposito) {
		return this.ubicacion.obtenerIdUbicacionDeposito(ubicacionDeposito);
	}

	public String obtenerNombreDeposito(Deposito deposito) {
		return this.deposito.obtenerNombreDeposito(deposito);
	}
}