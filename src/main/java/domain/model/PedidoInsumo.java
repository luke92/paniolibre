package domain.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dto.EnumEstadoPedidoDTO;
import dto.PedidoInsumoDTO;
import dto.PedidoInsumoDetalleDTO;
import util.Fechas;

public class PedidoInsumo {
	private Integer idPedidoInsumo;
	private Calendar fechaSolicitud;
	private Calendar fechaProbableRecepcion;
	private String comentario;
	private EnumRecibido recibido;
	private Integer numeroOrdenCompra;
	private Calendar fechaRealRecepcion;
	private String proveedor;
	private List<PedidoInsumoDetalle> detalle;
	private OrdenDeTrabajo ordenDeTrabajo;

	public PedidoInsumo() {
		this.detalle = new ArrayList<PedidoInsumoDetalle>();
	}

	public boolean agregarInsumo(PedidoInsumoDetalle insumo) {
		return this.detalle.add(insumo);
	}

	public boolean borrarInsumo(PedidoInsumoDetalle insumo) {
		return this.detalle.remove(insumo);
	}

	public PedidoInsumo(Integer idPedidoInsumo, Calendar fechaSolicitud, Calendar fechaProbableRecepcion,
			String comentario, EnumRecibido recibido, Integer numeroOrdenCompra, Calendar fechaRealRecepcion,
			String proveedor, List<PedidoInsumoDetalle> detalle, OrdenDeTrabajo ordenDeTrabajo) {
		super();
		this.idPedidoInsumo = idPedidoInsumo;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaProbableRecepcion = fechaProbableRecepcion;
		this.comentario = comentario;
		this.recibido = recibido;
		this.numeroOrdenCompra = numeroOrdenCompra;
		this.fechaRealRecepcion = fechaRealRecepcion;
		this.proveedor = proveedor;
		this.setDetalle(detalle);
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public Integer getIdPedidoInsumo() {
		return idPedidoInsumo;
	}

	public void setIdPedidoInsumo(Integer idPedidoInsumo) {
		this.idPedidoInsumo = idPedidoInsumo;
	}

	public Calendar getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Calendar fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Calendar getFechaProbableRecepcion() {
		return fechaProbableRecepcion;
	}

	public void setFechaProbableRecepcion(Calendar fechaProbableRecepcion) {
		this.fechaProbableRecepcion = fechaProbableRecepcion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public EnumRecibido getRecibido() {
		return recibido;
	}

	public void setRecibido(EnumRecibido recibido) {
		this.recibido = recibido;
	}

	public Integer getNumeroOrdenCompra() {
		return numeroOrdenCompra;
	}

	public void setNumeroOrdenCompra(Integer numeroOrdenCompra) {
		this.numeroOrdenCompra = numeroOrdenCompra;
	}

	public Calendar getFechaRealRecepcion() {
		return fechaRealRecepcion;
	}

	public void setFechaRealRecepcion(Calendar fechaRealRecepcion) {
		this.fechaRealRecepcion = fechaRealRecepcion;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public List<PedidoInsumoDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<PedidoInsumoDetalle> detalle) {
		this.detalle = detalle;
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	@Override
	public String toString() {
		return "PedidoInsumo [idPedidoInsumo=" + idPedidoInsumo + ", fechaSolicitud=" + fechaSolicitud
				+ ", fechaProbableRecepcion=" + fechaProbableRecepcion + ", comentario=" + comentario + ", recibido="
				+ recibido + ", numeroOrdenCompra=" + numeroOrdenCompra + ", fechaRealRecepcion=" + fechaRealRecepcion
				+ ", proveedor=" + proveedor + ", detalle=" + detalle + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPedidoInsumo == null) ? 0 : idPedidoInsumo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoInsumo other = (PedidoInsumo) obj;
		if (idPedidoInsumo == null) {
			if (other.idPedidoInsumo != null)
				return false;
		} else if (!idPedidoInsumo.equals(other.idPedidoInsumo))
			return false;
		return true;
	}

	public PedidoInsumoDTO getDTO() {
		PedidoInsumoDTO pedidoInsumoDTO = new PedidoInsumoDTO();
		pedidoInsumoDTO.setIdPedidoInsumo(this.idPedidoInsumo);
		pedidoInsumoDTO.setNroOrdenCompra(this.numeroOrdenCompra);
		pedidoInsumoDTO.setComentario(this.comentario);
		pedidoInsumoDTO.setProveedor(this.proveedor);
		pedidoInsumoDTO.setFechaProbableRecepcion(Fechas.CalendarTolocalDate(this.fechaProbableRecepcion));
		if (this.fechaRealRecepcion != null)
			pedidoInsumoDTO.setFechaRealRecepcion(Fechas.CalendarTolocalDate(this.fechaRealRecepcion));
		pedidoInsumoDTO.setFechaSolicitud(Fechas.CalendarTolocalDate(this.fechaSolicitud));
		if (recibido.getValor() == 1) {
			pedidoInsumoDTO.setRecibido(EnumEstadoPedidoDTO.PENDIENTE);
		}
		if (recibido.getValor() == 2) {
			pedidoInsumoDTO.setRecibido(EnumEstadoPedidoDTO.PARCIAL);

		}

		List<PedidoInsumoDetalleDTO> detalle = new ArrayList<PedidoInsumoDetalleDTO>();
		for (PedidoInsumoDetalle pedido : this.detalle) {
			detalle.add(pedido.getDTO());
		}
		return pedidoInsumoDTO;
	}

	public PedidoInsumoDTO setDTO(PedidoInsumoDTO pedidoInsumoDTO) {
		pedidoInsumoDTO.setIdPedidoInsumo(this.idPedidoInsumo);
		pedidoInsumoDTO.setNroOrdenCompra(this.numeroOrdenCompra);
		pedidoInsumoDTO.setComentario(this.comentario);
		pedidoInsumoDTO.setFechaProbableRecepcion(Fechas.CalendarTolocalDate(this.fechaProbableRecepcion));
		pedidoInsumoDTO.setFechaRealRecepcion(Fechas.CalendarTolocalDate(this.fechaRealRecepcion));
		pedidoInsumoDTO.setFechaSolicitud(Fechas.CalendarTolocalDate(this.fechaSolicitud));

		List<PedidoInsumoDetalleDTO> detalle = new ArrayList<PedidoInsumoDetalleDTO>();
		for (PedidoInsumoDetalle pedido : this.detalle) {
			detalle.add(pedido.getDTO());
		}
		return pedidoInsumoDTO;

	}

}