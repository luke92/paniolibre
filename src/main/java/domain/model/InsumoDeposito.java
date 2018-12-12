package domain.model;

import dto.InsumoDTO;
import dto.InsumoDepositoDTO;

public class InsumoDeposito {
	private Integer idDeposito;
	private Insumo insumo;
	private UbicacionDeposito ubicacion;
	private Integer stockNuevo;
	private Integer stockUsado;
	private Integer stockReservado;

	public InsumoDeposito() {
	}

	public InsumoDeposito(Integer idDeposito, UbicacionDeposito ubicacion, Integer stockNuevo, Integer stockUsado,
			Integer stockReservado, Insumo insumo) {
		this.idDeposito = idDeposito;
		this.ubicacion = ubicacion;
		this.stockNuevo = stockNuevo;
		this.stockUsado = stockUsado;
		this.stockReservado = stockReservado;
		this.insumo = insumo;
	}

	public Integer getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Integer idDeposito) {
		this.idDeposito = idDeposito;
	}

	public Integer getStockNuevo() {
		return stockNuevo;
	}

	public void setStockNuevo(Integer stockNuevo) {
		this.stockNuevo = stockNuevo;
	}

	public Integer getStockUsado() {
		return stockUsado;
	}

	public void setStockUsado(Integer stockUsado) {
		this.stockUsado = stockUsado;
	}

	public Integer getStockReservado() {
		return stockReservado;
	}

	public void setStockReservado(Integer stockReservado) {
		this.stockReservado = stockReservado;
	}

	public UbicacionDeposito getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDeposito ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public InsumoDepositoDTO getDTO() {
		InsumoDepositoDTO insumo = new InsumoDepositoDTO();
		insumo.setUbicacion(this.getUbicacion().getDTO());

		InsumoDTO insumoDTO = new InsumoDTO();
		insumoDTO.getIdInsumo().set(this.getInsumo().getIdInsumo());
		insumoDTO.getNombre().set(this.getInsumo().getNombre());
		insumoDTO.getUmbralMinimo().set(this.getInsumo().getUmbralMinimo());
		insumoDTO.getCodigo().set(this.getInsumo().getCodigoInsumo());
		
		insumo.setInsumo(insumoDTO);
		insumo.getStockNuevo().set(this.getStockNuevo());
		insumo.getStockUsado().set(this.getStockUsado());
		insumo.getStockReservado().set(this.getStockReservado());
		return insumo;
	}

	@Override
	public String toString() {
		return "InsumoDeposito [idDeposito=" + idDeposito + ", insumo=" + insumo + ", ubicacion=" + ubicacion
				+ ", stockNuevo=" + stockNuevo + ", stockUsado=" + stockUsado + ", stockReservado=" + stockReservado
				+ "]";
	}

}