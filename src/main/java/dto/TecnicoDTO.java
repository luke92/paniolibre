package dto;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TecnicoDTO {
	private IntegerProperty idTecnico;
	private StringProperty nombre;
	private StringProperty dni;
	private StringProperty legajo;
	private List<EspecialidadDTO> especialidades;
	private IntegerProperty activo;
	private StringProperty apellido;
	private StringProperty etiquetaMantis;

	public TecnicoDTO() {
		this.idTecnico = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
		this.apellido = new SimpleStringProperty();
		this.dni = new SimpleStringProperty();
		this.legajo = new SimpleStringProperty();
		this.especialidades = new ArrayList<EspecialidadDTO>();
		this.activo = new SimpleIntegerProperty();
		this.etiquetaMantis = new SimpleStringProperty();
	}

	public TecnicoDTO(int idTecnico, String nombre, String dni, String legajo, List<EspecialidadDTO> especialidades,
			int activo, String apellido) {
		this.idTecnico = new SimpleIntegerProperty(idTecnico);
		this.nombre = new SimpleStringProperty(nombre);
		this.dni = new SimpleStringProperty(dni);
		this.legajo = new SimpleStringProperty(legajo);
		this.especialidades = especialidades;
		this.activo = new SimpleIntegerProperty(activo);
		this.apellido = new SimpleStringProperty(apellido);
		this.etiquetaMantis = new SimpleStringProperty();
	}
	
	public TecnicoDTO(int idTecnico, String nombre, String dni, String legajo, List<EspecialidadDTO> especialidades,
			int activo, String apellido, String etiquetaMantis) {
		this.idTecnico = new SimpleIntegerProperty(idTecnico);
		this.nombre = new SimpleStringProperty(nombre);
		this.dni = new SimpleStringProperty(dni);
		this.legajo = new SimpleStringProperty(legajo);
		this.especialidades = especialidades;
		this.activo = new SimpleIntegerProperty(activo);
		this.apellido = new SimpleStringProperty(apellido);
		this.etiquetaMantis = new SimpleStringProperty(etiquetaMantis);
	}

	public IntegerProperty getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(int idTecnico) {
		this.idTecnico.set(idTecnico);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public StringProperty getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni.set(dni);
	}

	public StringProperty getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo.set(legajo);
	}

	public List<EspecialidadDTO> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidad(List<EspecialidadDTO> especialidades) {
		this.especialidades = especialidades;
	}

	public IntegerProperty getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo.set(activo);
	}

	public StringProperty getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido.set(apellido);
	}

	@Override
	public String toString() {
		return nombre.get();
	}

	public StringProperty getNombreCompleto() {
		return new SimpleStringProperty(this.toString());
	}

	public String getEspecialidadesString() {
		StringBuilder especialidadesTecnico = new StringBuilder();
		for (EspecialidadDTO especialidad : this.especialidades) {
			especialidadesTecnico.append(especialidad.toString() + ", ");
		}
		return especialidadesTecnico.toString();
	}
	
	public StringProperty getEtiquetaMantis()
	{
		return this.etiquetaMantis;
	}
	
	public String getStringEtiquetaMantis()
	{
		return this.etiquetaMantis.get();
	}
	
	public void setEtiquetaMantis(String etiquetaMantis)
	{
		this.etiquetaMantis.set(etiquetaMantis);
	}

}
