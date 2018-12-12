package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AlertaDTO {
	private StringProperty tipoAlerta;
	private StringProperty detalleAlerta;
	
	public AlertaDTO() {
		this.tipoAlerta = new SimpleStringProperty();
		this.detalleAlerta = new SimpleStringProperty();
	}
	
	public AlertaDTO(String tipoAlerta, String detalleAlerta)
	{
		this.tipoAlerta = new SimpleStringProperty(tipoAlerta);
		this.detalleAlerta = new SimpleStringProperty(detalleAlerta);
	}
	
	public String getTipoAlertaString()
	{
		return this.tipoAlerta.get();
	}
	
	public String getDetalleAlertaString()
	{
		return this.detalleAlerta.get();
	}
	
	public StringProperty getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(String tipoAlerta) {
		this.tipoAlerta.set(tipoAlerta);
	}

	public StringProperty getDetalleAlerta() {
		return detalleAlerta;
	}

	public void setDetalleAlerta(String detalleAlerta) {
		this.detalleAlerta.set(detalleAlerta);
	}
}
