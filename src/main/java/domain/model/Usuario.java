package domain.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String userName;
	private String clave;
	private String mail;
	private int activo;
	private boolean recibeAlertasPorMail;
	private StringProperty recibeAlertas;
	private String userMantis;
	private String claveMantis;
	
	public Usuario() {
		this.recibeAlertasPorMail = true;
		this.recibeAlertas = new SimpleStringProperty("Si");
	}

	public Usuario(int idUsuario, String nombre, String apellido, String userName, String clave, String mail,
			int activo) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.userName = userName;
		this.clave = clave;
		this.mail = mail;
		this.activo = activo;
		this.recibeAlertas = new SimpleStringProperty("Si");
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public boolean isRecibeAlertasPorMail() {
		return recibeAlertasPorMail;
	}

	public void setRecibeAlertasPorMail(boolean recibeAlertasPorMail) {
		this.recibeAlertasPorMail = recibeAlertasPorMail;
		if(recibeAlertasPorMail) this.setRecibeAlertas("Si");
		else this.setRecibeAlertas("No");
	}

	public String getRecibeAlertas() {
		return recibeAlertas.get();
	}
	
	public StringProperty getRecibeAlertasStringProperty()
	{
		return recibeAlertas;
	}
	
	public void setRecibeAlertas(String recibeAlertas) {
		this.recibeAlertas.set(recibeAlertas);
	}

	public String getUserMantis() {
		return userMantis;
	}

	public void setUserMantis(String userMantis) {
		this.userMantis = userMantis;
	}

	public String getClaveMantis() {
		return claveMantis;
	}

	public void setClaveMantis(String claveMantis) {
		this.claveMantis = claveMantis;
	}
}