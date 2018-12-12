package dto;

import java.time.LocalDate;

public class AlertaInsumoDTO {
	private int idAlerta;
	private int idDeposito;
	private String nombreDeposito;
	private int idInsumo;
	private String codigoInsumo;
	private String nombreInsumo;
	private int umbralInsumo;
	private int stock;
	private LocalDate fecha;
	private boolean enviadoPorMail;

	public AlertaInsumoDTO() {
	}

	public AlertaInsumoDTO(int idAlerta,int idDeposito, String nombreDeposito, int idInsumo, String nombreInsumo, int umbralInsumo,
			int stock, boolean enviadoPorMail, LocalDate fecha, String codigoInsumo) {
		this.idAlerta = idAlerta;
		this.idDeposito = idDeposito;
		this.nombreDeposito = nombreDeposito;
		this.idInsumo = idInsumo;
		this.nombreInsumo = nombreInsumo;
		this.umbralInsumo = umbralInsumo;
		this.stock = stock;
		this.enviadoPorMail = enviadoPorMail;
		this.fecha = fecha;
		this.codigoInsumo = codigoInsumo;
	}
	
	public int getIdAlerta() {
		return this.idAlerta;
	}

	public int getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(int idDeposito) {
		this.idDeposito = idDeposito;
	}

	public String getNombreDeposito() {
		return nombreDeposito;
	}

	public void setNombreDeposito(String nombreDeposito) {
		this.nombreDeposito = nombreDeposito;
	}

	public int getIdInsumo() {
		return idInsumo;
	}

	public void setIdInsumo(int idInsumo) {
		this.idInsumo = idInsumo;
	}

	public String getNombreInsumo() {
		return nombreInsumo;
	}

	public void setNombreInsumo(String nombreInsumo) {
		this.nombreInsumo = nombreInsumo;
	}

	public int getUmbralInsumo() {
		return umbralInsumo;
	}

	public void setUmbralInsumo(int umbralInsumo) {
		this.umbralInsumo = umbralInsumo;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isEnviadoPorMail() {
		return enviadoPorMail;
	}

	public void setEnviadoPorMail(boolean enviadoPorMail) {
		this.enviadoPorMail = enviadoPorMail;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getCodigoInsumo() {
		return codigoInsumo;
	}

	public void setCodigoInsumo(String codigoInsumo) {
		this.codigoInsumo = codigoInsumo;
	}

	@Override
	public String toString() {
		return "Deposito " + nombreDeposito + " Insumo " + codigoInsumo + " " + nombreInsumo
				+ " tiene de Umbral Minimo " + umbralInsumo + " con stock " + stock + " desde la fecha " + fecha;

	}

}
