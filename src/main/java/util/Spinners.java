package util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class Spinners {
	private int valorMinimo = 0;
	private int valorMaximo = 0;

	public Spinners() {
		//
	}

	public Spinner<Integer> crearSpinner(int valorMinimo, int valorMaximo, int valorActual) {
		Spinner<Integer> spinnerRet = new Spinner<>();
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		spinnerRet.setEditable(true);
		spinnerRet.setValueFactory(getValueFactory(valorMinimo, valorMaximo, valorActual));
		validarTextoSpinner(spinnerRet);
		return spinnerRet;
	}

	public Spinner<Integer> setValoresSpinner(Spinner<Integer> spinner, int valorMinimo, int valorMaximo,
			int valorActual) {
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		spinner.setEditable(true);
		spinner.setValueFactory(getValueFactory(valorMinimo, valorMaximo, valorActual));
		validarTextoSpinner(spinner);
		return spinner;
	}

	private SpinnerValueFactory<Integer> getValueFactory(int valorMinimo, int valorMaximo, int valorActual) {
		return new SpinnerValueFactory.IntegerSpinnerValueFactory(valorMinimo, valorMaximo, valorActual);
	}

	private void validarTextoSpinner(Spinner<Integer> spinner) {
		spinner.focusedProperty().addListener(getChangeListenerNumeric(spinner));

	}

	private ChangeListener<Boolean> getChangeListenerNumeric(Spinner<Integer> spinner) {
		return new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					if (!ExpReg.contieneSoloNumeros(spinner.getEditor().getText())) {
						spinner.getEditor().setText("0");
					} else {
						if (Integer.parseInt(spinner.getEditor().getText()) > valorMaximo) {
							spinner.getEditor().setText(String.valueOf(valorMaximo));
						}
						if (Integer.parseInt(spinner.getEditor().getText()) < valorMinimo) {
							spinner.getEditor().setText(String.valueOf(valorMinimo));
						}
					}
				}
			}
		};
	}
}
