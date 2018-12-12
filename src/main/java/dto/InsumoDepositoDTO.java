package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InsumoDepositoDTO {
	private UbicacionDepositoDTO ubicacion;
	private IntegerProperty stockNuevo;
	private IntegerProperty stockUsado;
	private IntegerProperty stockReservado;
	private InsumoDTO insumo;

	public InsumoDepositoDTO() {
		this.ubicacion = new UbicacionDepositoDTO();
		this.stockNuevo = new SimpleIntegerProperty();
		this.stockUsado = new SimpleIntegerProperty();
		this.stockReservado = new SimpleIntegerProperty();
		this.insumo = new InsumoDTO();
	}

	public InsumoDepositoDTO(UbicacionDepositoDTO ubicacion, IntegerProperty stockNuevo, IntegerProperty stockUsado,
			IntegerProperty stockReservado, InsumoDTO insumo) {
		super();
		this.ubicacion = ubicacion;
		this.stockNuevo = stockNuevo;
		this.stockUsado = stockUsado;
		this.stockReservado = stockReservado;
		this.insumo = insumo;
	}

	public UbicacionDepositoDTO getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDepositoDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	public IntegerProperty getStockNuevo() {
		return stockNuevo;
	}

	public void setStockNuevo(IntegerProperty stockNuevo) {
		this.stockNuevo = stockNuevo;
	}

	public IntegerProperty getStockUsado() {
		return stockUsado;
	}

	public void setStockUsado(IntegerProperty stockUsado) {
		this.stockUsado = stockUsado;
	}

	public IntegerProperty getStockReservado() {
		return stockReservado;
	}

	public void setStockReservado(IntegerProperty stockReservado) {
		this.stockReservado = stockReservado;
	}

	public InsumoDTO getInsumo() {
		return insumo;
	}

	public void setInsumo(InsumoDTO insumo) {
		this.insumo = insumo;
	}
}
