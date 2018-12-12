package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PedidoInsumoDTO {
	private IntegerProperty idPedidoInsumo;
	private IntegerProperty nroOrdenCompra;
	private StringProperty proveedor;
	private LocalDate fechaSolicitud;
	private StringProperty fechaSol;
	private StringProperty comentario;
	private LocalDate fechaProbableRecepcion;
	private LocalDate fechaRealRecepcion;
	private EnumEstadoPedidoDTO recibido;
	private List<PedidoInsumoDetalleDTO> detalle;
	private OrdenTrabajoDTO ordenTrabajoDTO;

	public PedidoInsumoDTO() {
		this.idPedidoInsumo = new SimpleIntegerProperty();
		this.nroOrdenCompra = new SimpleIntegerProperty();
		this.proveedor = new SimpleStringProperty();
		this.fechaSolicitud = LocalDate.now();
		this.comentario = new SimpleStringProperty();
		this.recibido = EnumEstadoPedidoDTO.PENDIENTE;
		this.detalle = new ArrayList<PedidoInsumoDetalleDTO>();
		this.fechaSol = new SimpleStringProperty();
		fechaSol.set(fechaSolicitud.toString());
		this.ordenTrabajoDTO = new OrdenTrabajoDTO();
	}

	public PedidoInsumoDTO(int idPedidoInsumo, int nroOrdenCompra, String proveedor, LocalDate fechaSolicitud,
			String comentario, EnumEstadoPedidoDTO recibido, List<PedidoInsumoDetalleDTO> detalle,
			LocalDate fechaProbableRecepcion, LocalDate fechaRealRecepcion) {
		this.idPedidoInsumo = new SimpleIntegerProperty(idPedidoInsumo);
		this.nroOrdenCompra = new SimpleIntegerProperty(nroOrdenCompra);
		this.proveedor = new SimpleStringProperty(proveedor);
		this.fechaSolicitud = fechaSolicitud;
		this.comentario = new SimpleStringProperty(comentario);
		this.recibido = recibido;
		this.detalle = detalle;
		this.fechaProbableRecepcion = fechaProbableRecepcion;
		this.fechaRealRecepcion = fechaRealRecepcion;
		this.ordenTrabajoDTO = new OrdenTrabajoDTO();
	}

	public EnumEstadoPedidoDTO obtenerEstado(int valor) {
		if (valor == 1) {
			return EnumEstadoPedidoDTO.PENDIENTE;
		}
		if (valor == 2) {
			return EnumEstadoPedidoDTO.PARCIAL;
		}
		if (valor == 3) {
			return EnumEstadoPedidoDTO.COMPLETO;
		}
		if (valor == 4) {
			return EnumEstadoPedidoDTO.INCOMPLETO;
		}
		return null;
	}

	public int getIdPedidoInsumo() {
		return this.idPedidoInsumo.get();
	}

	public void setIdPedidoInsumo(int idPedidoInsumo) {
		this.idPedidoInsumo.set(idPedidoInsumo);
	}

	public Integer getNroOrdenCompra() {
		return this.nroOrdenCompra.get();
	}

	public void setNroOrdenCompra(int nroOrdenCompra) {
		this.nroOrdenCompra.set(nroOrdenCompra);
	}

	public String getProveedor() {
		return this.proveedor.get();
	}

	public void setProveedor(String proveedor) {
		this.proveedor.set(proveedor);
	}

	public String getComentario() {
		return this.comentario.get();
	}

	public void setComentario(String comentario) {
		this.comentario.set(comentario);
	}

	public IntegerProperty getIdPedidoInsumoProperty() {
		return idPedidoInsumo;
	}

	public void setIdPedidoInsumo(IntegerProperty idPedidoInsumo) {
		this.idPedidoInsumo = idPedidoInsumo;
	}

	public IntegerProperty getNroOrdenCompraProperty() {
		return nroOrdenCompra;
	}

	public void setNroOrdenCompra(IntegerProperty nroOrdenCompra) {
		this.nroOrdenCompra = nroOrdenCompra;
	}

	public StringProperty getProveedorProperty() {
		return proveedor;
	}

	public void setProveedor(StringProperty proveedor) {
		this.proveedor = proveedor;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public StringProperty getComentarioProperty() {
		return comentario;
	}

	public void setComentario(StringProperty comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFechaProbableRecepcion() {
		return fechaProbableRecepcion;
	}

	public void setFechaProbableRecepcion(LocalDate fechaProbableRecepcion) {
		this.fechaProbableRecepcion = fechaProbableRecepcion;
	}

	public LocalDate getFechaRealRecepcion() {
		return fechaRealRecepcion;
	}

	public void setFechaRealRecepcion(LocalDate fechaRealRecepcion) {
		this.fechaRealRecepcion = fechaRealRecepcion;
	}

	public EnumEstadoPedidoDTO getRecibido() {
		return recibido;
	}

	public void setRecibido(EnumEstadoPedidoDTO recibido) {
		this.recibido = recibido;
	}

	public List<PedidoInsumoDetalleDTO> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<PedidoInsumoDetalleDTO> detalle) {
		this.detalle = detalle;
	}

	public StringProperty getFechaSol() {
		return fechaSol;
	}

	public void setFechaSol(StringProperty fechaSol) {
		this.fechaSol = fechaSol;
	}

	public OrdenTrabajoDTO getOrdenTrabajoDTO() {
		return ordenTrabajoDTO;
	}

	public void setOrdenTrabajoDTO(OrdenTrabajoDTO ordenTrabajoDTO) {
		this.ordenTrabajoDTO = ordenTrabajoDTO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + ((detalle == null) ? 0 : detalle.hashCode());
		result = prime * result + ((fechaProbableRecepcion == null) ? 0 : fechaProbableRecepcion.hashCode());
		result = prime * result + ((fechaRealRecepcion == null) ? 0 : fechaRealRecepcion.hashCode());
		result = prime * result + ((fechaSol == null) ? 0 : fechaSol.hashCode());
		result = prime * result + ((fechaSolicitud == null) ? 0 : fechaSolicitud.hashCode());
		result = prime * result + ((idPedidoInsumo == null) ? 0 : idPedidoInsumo.hashCode());
		result = prime * result + ((nroOrdenCompra == null) ? 0 : nroOrdenCompra.hashCode());
		result = prime * result + ((ordenTrabajoDTO == null) ? 0 : ordenTrabajoDTO.hashCode());
		result = prime * result + ((proveedor == null) ? 0 : proveedor.hashCode());
		result = prime * result + ((recibido == null) ? 0 : recibido.hashCode());
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
		PedidoInsumoDTO other = (PedidoInsumoDTO) obj;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (detalle == null) {
			if (other.detalle != null)
				return false;
		} else if (!detalle.equals(other.detalle))
			return false;
		if (fechaProbableRecepcion == null) {
			if (other.fechaProbableRecepcion != null)
				return false;
		} else if (!fechaProbableRecepcion.equals(other.fechaProbableRecepcion))
			return false;
		if (fechaRealRecepcion == null) {
			if (other.fechaRealRecepcion != null)
				return false;
		} else if (!fechaRealRecepcion.equals(other.fechaRealRecepcion))
			return false;
		if (fechaSol == null) {
			if (other.fechaSol != null)
				return false;
		} else if (!fechaSol.equals(other.fechaSol))
			return false;
		if (fechaSolicitud == null) {
			if (other.fechaSolicitud != null)
				return false;
		} else if (!fechaSolicitud.equals(other.fechaSolicitud))
			return false;
		if (idPedidoInsumo == null) {
			if (other.idPedidoInsumo != null)
				return false;
		} else if (!idPedidoInsumo.equals(other.idPedidoInsumo))
			return false;
		if (nroOrdenCompra == null) {
			if (other.nroOrdenCompra != null)
				return false;
		} else if (!nroOrdenCompra.equals(other.nroOrdenCompra))
			return false;
		if (ordenTrabajoDTO == null) {
			if (other.ordenTrabajoDTO != null)
				return false;
		} else if (!ordenTrabajoDTO.equals(other.ordenTrabajoDTO))
			return false;
		if (proveedor == null) {
			if (other.proveedor != null)
				return false;
		} else if (!proveedor.equals(other.proveedor))
			return false;
		if (recibido != other.recibido)
			return false;
		return true;
	}
	
	

}
