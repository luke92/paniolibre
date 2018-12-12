package dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UsuarioDTO {
	private IntegerProperty idUsuario;
	private StringProperty nombre;
	private StringProperty apellido;
	private StringProperty userName;
	private StringProperty clave;
	private StringProperty mail;
	private IntegerProperty activo;
	private BooleanProperty recibeAlertasPorMail;
	private StringProperty userMantis;
	private StringProperty claveMantis;
	
	private void setUserMantis(String userMantis) {
		this.userMantis.set(userMantis);
	}
	
	private void setClaveMantis (String claveMantis) {
		this.claveMantis.set(claveMantis);
	}
	
	public StringProperty getUserMantis() {
		return userMantis;
	}

	public StringProperty getClaveMantis() {
		return claveMantis;
	}

	public UsuarioDTO() {
		this.idUsuario = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
		this.apellido = new SimpleStringProperty();
		this.userName = new SimpleStringProperty();
		this.clave = new SimpleStringProperty();
		this.mail = new SimpleStringProperty();
		this.activo = new SimpleIntegerProperty();
		this.recibeAlertasPorMail = new SimpleBooleanProperty(true);
		this.userMantis = new SimpleStringProperty();
		this.claveMantis = new SimpleStringProperty();
	}

	public UsuarioDTO(int idUsuario, String nombre, String apellido, String userName, String clave, String mail,
			int activo) {
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
		this.nombre = new SimpleStringProperty(nombre);
		this.apellido = new SimpleStringProperty(apellido);
		this.userName = new SimpleStringProperty(userName);
		this.clave = new SimpleStringProperty(clave);
		this.mail = new SimpleStringProperty(mail);
		this.activo = new SimpleIntegerProperty(activo);
		this.userMantis = new SimpleStringProperty();
		this.claveMantis = new SimpleStringProperty();
	}

	public IntegerProperty getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario.set(idUsuario);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public StringProperty getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido.set(apellido);
	}

	public StringProperty getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public StringProperty getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave.set(clave);
	}

	public StringProperty getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail.set(mail);
	}

	public IntegerProperty getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo.set(activo);
	}

	public BooleanProperty getRecibeAlertasPorMail() {
		return recibeAlertasPorMail;
	}
	
	public String getRecibeAlertasPorMailString()
	{
		if(recibeAlertasPorMail.getValue())
			return "Si";
		return "No";
	}
	
	public StringProperty getRecibeAlertasPorMailStringProperty()
	{
		return new SimpleStringProperty(this.getRecibeAlertasPorMailString());
	}

	public void setRecibeAlertasPorMail(boolean recibe) {
		this.recibeAlertasPorMail.set(recibe);
	}

}
