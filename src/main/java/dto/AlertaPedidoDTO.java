package dto;

import java.time.LocalDate;

public class AlertaPedidoDTO {
	private int idAlerta;
	private LocalDate fecha;
	private boolean enviadoPorMail;
	private int nroOrdenCompra;
	private String proveedor;
	private LocalDate fechaSolicitud;
	private String comentario;
	private LocalDate fechaProbableRecepcion;

	public AlertaPedidoDTO() {
	}

	public AlertaPedidoDTO(int idAlerta, LocalDate fecha, boolean enviadoPorMail, int nroOrdenCompra, String proveedor,
			LocalDate fechaSolicitud, String comentario, LocalDate fechaProbableRecepcion) {
		this.idAlerta = idAlerta;
		this.fecha = fecha;
		this.enviadoPorMail = enviadoPorMail;
		this.nroOrdenCompra = nroOrdenCompra;
		this.proveedor = proveedor;
		this.fechaSolicitud = fechaSolicitud;
		this.comentario = comentario;
		this.fechaProbableRecepcion = fechaProbableRecepcion;
	}

	public int getIdAlerta() {
		return this.idAlerta;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isEnviadoPorMail() {
		return enviadoPorMail;
	}

	public void setEnviadoPorMail(boolean enviadoPorMail) {
		this.enviadoPorMail = enviadoPorMail;
	}

	public int getNroOrdenCompra() {
		return nroOrdenCompra;
	}

	public void setNroOrdenCompra(int nroOrdenCompra) {
		this.nroOrdenCompra = nroOrdenCompra;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFechaProbableRecepcion() {
		return fechaProbableRecepcion;
	}

	public void setFechaProbableRecepcion(LocalDate fechaProbableRecepcion) {
		this.fechaProbableRecepcion = fechaProbableRecepcion;
	}

	@Override
	public String toString() {
		return "Pedido realizado al Proveedor " + proveedor + " con Nro de Orden de Compra " + nroOrdenCompra
				+ " solicitado el dia " + fechaSolicitud + " debia recibirse el dia " + fechaProbableRecepcion
				+ " y tiene de detalle: " + comentario + ". Alerta existente desde la fecha " + fecha;
	}

}
