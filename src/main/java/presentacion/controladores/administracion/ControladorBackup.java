package presentacion.controladores.administracion;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import persistencia.conexion.Conexion;
import util.Paths;

public class ControladorBackup {

	@FXML
	private TextField txtFilePathRespaldo;

	@FXML
	private TextField txtFilePathRestauracion;

	@FXML
	private Button btnBackup;

	@FXML
	private Button btnRestore;

	@FXML
	private Button btnExaminarBackup;

	@FXML
	private Button btnExaminarRestore;

	@FXML
	private Text lblOperacionBackup;

	@FXML
	private Text lblOperacionRestore;

	@FXML
	private Text lblTitulo;

	@FXML
	void backup(MouseEvent event) {
		limpiarLabels();
		Conexion conexion = Conexion.getConexion();
		if (conexion.backup(txtFilePathRespaldo.getText().trim()))
			lblOperacionBackup.setText("Respaldo realizado con exito!");
		else
			lblOperacionBackup.setText("No se pudo realizar el respaldo!");
	}

	@FXML
	void examinarBackup(MouseEvent event) {
		salvarArchivo();
	}

	@FXML
	void examinarRestore(MouseEvent event) {
		abrirArchivo();
	}

	@FXML
	void restore(MouseEvent event) {
		limpiarLabels();
		Conexion conexion = Conexion.getConexion();
		if (conexion.restore(txtFilePathRestauracion.getText().trim())) {
			lblOperacionRestore.setText("Restauraci\u00f3n realizada con exito!");
		} else
			lblOperacionRestore.setText("No se pudo realizar la restauraci\u00f3n de los datos");
	}

	private void abrirArchivo() {
		Stage stage = new Stage();

		File file = this.selector().showOpenDialog(stage);
		if (file != null) {
			txtFilePathRestauracion.setText(file.getAbsolutePath());
			limpiarLabels();
		}
	}

	private void salvarArchivo() {
		Stage stage = new Stage();
		File file = this.selector().showSaveDialog(stage);

		if (file != null) {
			txtFilePathRespaldo.setText(file.getAbsolutePath());
			limpiarLabels();
		}
	}

	private FileChooser selector() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(Paths.respaldos()));
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
		fileChooser.getExtensionFilters().add(extFilter);
		return fileChooser;
	}

	private void limpiarLabels() {
		lblOperacionBackup.setText("");
		lblOperacionRestore.setText("");
	}
}
