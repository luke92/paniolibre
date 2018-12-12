package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DepositoDTO {

	private IntegerProperty idDeposito;
	private StringProperty nombre;
	private StringProperty comentario;
	private EstadoDTO activo;

	public DepositoDTO() {
		idDeposito = new SimpleIntegerProperty();
		nombre = new SimpleStringProperty();
		comentario = new SimpleStringProperty();
		activo = null;
	}

	public DepositoDTO(int idDeposito, String nombre, String comentario, int activo) {
		this.idDeposito = new SimpleIntegerProperty(idDeposito);
		this.nombre = new SimpleStringProperty(nombre);
		this.comentario = new SimpleStringProperty(comentario);
		this.activo = (activo == 1) ? EstadoDTO.ALTA : EstadoDTO.BAJA;
	}

	public IntegerProperty getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(int idDeposito) {
		this.idDeposito.set(idDeposito);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public StringProperty getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario.set(comentario);
	}

	public EstadoDTO getActivo() {
		return activo;
	}

	public void setActivo(EstadoDTO activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return this.getNombre().get();
	}
}