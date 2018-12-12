package domain.model;

public class Mail {
	private Integer idMail;
	private String mail;
	private String clave;

	public Mail(Integer idMail, String mail, String clave) {
		super();
		this.idMail = idMail;
		this.mail = mail;
		this.clave = clave;
	}

	public Mail() {
	}

	public Integer getIdMail() {
		return idMail;
	}

	public void setIdMail(Integer idMail) {
		this.idMail = idMail;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}