package dto;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrdenDeTrabajoPorTecnicoDTO {
	private StringProperty idOrdenDeTrabajo;
	private StringProperty nombreProyecto;
	private List<TecnicoDTO> tecnicos;

	public OrdenDeTrabajoPorTecnicoDTO() {
		this.idOrdenDeTrabajo = new SimpleStringProperty();
		this.nombreProyecto = new SimpleStringProperty();
		this.tecnicos = new ArrayList<>();
	}

	public OrdenDeTrabajoPorTecnicoDTO(String idOrdenDeTrabajo, String nombreProyecto, List<TecnicoDTO> tecnicos) {
		this.idOrdenDeTrabajo = new SimpleStringProperty(idOrdenDeTrabajo);
		this.nombreProyecto = new SimpleStringProperty(nombreProyecto);
		this.tecnicos = tecnicos;
	}

	public StringProperty getIdOrdenDeTrabajo() {
		return idOrdenDeTrabajo;
	}

	public void setIdOrdenDeTrabajo(String idOrdenDeTrabajo) {
		this.idOrdenDeTrabajo.set(idOrdenDeTrabajo);
	}

	public StringProperty getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto.set(nombreProyecto);
	}

	public List<TecnicoDTO> getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(List<TecnicoDTO> tecnicos) {
		this.tecnicos = tecnicos;
	}

	@Override
	public String toString() {
		return "OrdenDeTrabajoPorTecnicoDTO [idOrdenDeTrabajo=" + idOrdenDeTrabajo + ", nombreProyecto="
				+ nombreProyecto + ", tecnicos=" + tecnicos + "]";
	}

}
