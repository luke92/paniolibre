package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public abstract class Dialogos {

	public static void error(String titulo, String cabecera, String contenido) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecera);
		alert.setContentText(contenido);

		alert.showAndWait();
	}

	public static ChoiceDialog<String> tipoIngresoPedido(String nombreInsumo) {
		List<String> choices = new ArrayList<>();
		choices.add("Incompleto");
		choices.add("Parcial");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Parcial", choices);
		dialog.setTitle("Pedido de Insumo : " + nombreInsumo);
		dialog.setHeaderText("Por favor seleccione el nuevo estado de su pedido");
		dialog.setContentText("Estado : ");
		return dialog;
	}

	public static void advertencia(String titulo, String cabecera, String contenido) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecera);
		alert.setContentText(contenido);

		alert.showAndWait();
	}

	public static boolean confirmacion(String titulo, String cabecera, String contenido, String btnAceptar,
			String btnCancelar) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecera);
		alert.setContentText(contenido);

		ButtonType buttonTypeOne = new ButtonType(btnAceptar);
		ButtonType buttonTypeTwo = new ButtonType(btnCancelar);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne)
			return true;
		else if (result.get() == buttonTypeTwo)
			return false;
		return false;
	}

	public static String ingresarTexto(String titulo, String cabecera, String contenido) {
		String valorIngresado = "";

		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle(titulo);
		dialog.setHeaderText(cabecera);
		dialog.setContentText(contenido);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			valorIngresado = result.get().trim();
		}

		return valorIngresado;
	}
	
	public static void informacion(String titulo, String contenido) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(contenido);

		alert.showAndWait();
	}
}
