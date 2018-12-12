package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Spinner;
import util.Spinners;

public class InsumoDepositoSpinnerDTO {
	private UbicacionDepositoDTO ubicacion;
	private IntegerProperty stockNuevo;
	private IntegerProperty stockUsado;
	private IntegerProperty stockReservado;
	private InsumoDTO insumo;
	private Spinner<Integer> spnCantidadNuevo;
	private Spinner<Integer> spnCantidadUsado;
	private Spinner<Integer> spnCantidadReservado;

	public InsumoDepositoSpinnerDTO() {
		this.ubicacion = new UbicacionDepositoDTO();
		this.stockNuevo = new SimpleIntegerProperty();
		this.stockUsado = new SimpleIntegerProperty();
		this.stockReservado = new SimpleIntegerProperty();
		this.insumo = new InsumoDTO();
		this.spnCantidadNuevo = new Spinners().crearSpinner(0, 999999, 0);
		this.spnCantidadUsado = new Spinners().crearSpinner(0, 999999, 0);
		this.spnCantidadReservado = new Spinners().crearSpinner(0, 999999, 0);
	}

	public InsumoDepositoSpinnerDTO(UbicacionDepositoDTO ubicacion, int stockNuevo, int stockUsado, int stockReservado,
			InsumoDTO insumo) {
		super();
		this.ubicacion = ubicacion;
		this.stockNuevo = new SimpleIntegerProperty(stockNuevo);
		this.stockUsado = new SimpleIntegerProperty(stockUsado);
		this.stockReservado = new SimpleIntegerProperty(stockReservado);
		this.insumo = insumo;
		this.spnCantidadNuevo = new Spinners().crearSpinner(0, stockNuevo, 0);
		this.spnCantidadUsado = new Spinners().crearSpinner(0, stockUsado, 0);
		this.spnCantidadReservado = new Spinners().crearSpinner(0, stockReservado, 0);
		new Spinners().setValoresSpinner(spnCantidadNuevo, 0, stockNuevo, 0);
		new Spinners().setValoresSpinner(spnCantidadUsado, 0, stockUsado, 0);
		new Spinners().setValoresSpinner(spnCantidadReservado, 0, stockReservado, 0);
	}

	public void setSpnCantidadNuevo(Integer maxValue) {
		this.spnCantidadNuevo = new Spinners().crearSpinner(0, maxValue, 0);
	}

	public void setSpnCantidadReservado(Integer maxValue) {
		new Spinners().setValoresSpinner(spnCantidadReservado, 0, maxValue, 0);
	}

	public void setSpnCantidadUsado(Integer maxValue) {
		new Spinners().setValoresSpinner(spnCantidadUsado, 0, maxValue, 0);
	}

	public Spinner<Integer> getSpnCantidadReservado() {
		return spnCantidadReservado;
	}

	public void setSpnCantidadReservado(Spinner<Integer> spnCantidadReservado) {
		this.spnCantidadReservado = spnCantidadReservado;
	}

	public Spinner<Integer> getSpnCantidadNuevo() {
		return spnCantidadNuevo;
	}

	public void setSpnCantidadNuevo(Spinner<Integer> spnCantidadNuevo) {
		this.spnCantidadNuevo = spnCantidadNuevo;
	}

	public Spinner<Integer> getSpnCantidadUsado() {
		return spnCantidadUsado;
	}

	public void setSpnCantidadUsado(Spinner<Integer> spnCantidadUsado) {
		this.spnCantidadUsado = spnCantidadUsado;
	}

	public Integer getValorSpinnerCantNuevo() {
		return Integer.parseInt(this.spnCantidadNuevo.getEditor().getText());
	}

	public Integer getValorSpinnerCantUsado() {
		return Integer.parseInt(this.spnCantidadUsado.getEditor().getText());
	}

	public Integer getValorSpinnerCantReservado() {
		return Integer.parseInt(this.spnCantidadReservado.getEditor().getText());
	}

	public UbicacionDepositoDTO getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDepositoDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getStockNuevo() {
		return stockNuevo.get();
	}

	public void setStockNuevo(int stockNuevo) {
		this.stockNuevo.set(stockNuevo);
	}

	public int getStockUsado() {
		return stockUsado.get();
	}

	public void setStockUsado(int stockUsado) {
		this.stockUsado.set(stockUsado);
	}

	public int getStockReservado() {
		return stockReservado.get();
	}

	public void setStockReservado(int stockReservado) {
		this.stockReservado.set(stockReservado);
	}

	public InsumoDTO getInsumo() {
		return insumo;
	}

	public void setInsumo(InsumoDTO insumo) {
		this.insumo = insumo;
	}
}