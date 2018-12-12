package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import dto.InsumoDepositoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.InsumoDepositoService;
import util.ExpReg;

public class ControladorAjusteStock implements Initializable{

	@FXML
	private BorderPane ventanaAjustarStock;
	
	@FXML
	private BorderPane borderPrinipal;
	
	@FXML
	private AnchorPane barraPrincipal;

	@FXML
	private TextField textDeposito;

	@FXML
	private TextField textInsumo;

	@FXML
	private Button btnAjustar;

	@FXML
	private Button btnDescartar;

	@FXML
	private Spinner<Integer> spnCantidadNuevo;

	@FXML
	private Spinner<Integer> spnCantidadUsado;

	@FXML
	private Spinner<Integer> spnCantidadReservado;

	private InsumoDepositoDTO insumo;

	private InsumoDepositoService service = new InsumoDepositoService(new DAOSQLFactory());

	private double initX;

	private double initY;

	@FXML
	void ajustar(MouseEvent event) {
		insumo.getStockNuevo().set(Integer.parseInt(spnCantidadNuevo.getEditor().getText()));
		insumo.getStockUsado().set(Integer.parseInt(spnCantidadUsado.getEditor().getText()));
		insumo.getStockReservado().set(Integer.parseInt(spnCantidadReservado.getEditor().getText()));
		service.ajustarStock(insumo);
		cerrarVentana();
	}

	@FXML
	void descartar(MouseEvent event) {
		cerrarVentana();
	}

	@FXML
	public void processKeyEvent(KeyEvent ev) {
		if (!ExpReg.contieneSoloNumeros(spnCantidadNuevo.getEditor().getText())) {
			spnCantidadNuevo.getEditor().setText("0");
		}
		if (!ExpReg.contieneSoloNumeros(spnCantidadUsado.getEditor().getText())) {
			spnCantidadUsado.getEditor().setText("0");
		}
		if (!ExpReg.contieneSoloNumeros(spnCantidadReservado.getEditor().getText())) {
			spnCantidadReservado.getEditor().setText("0");
		}

	}

	void cargarCampos(InsumoDepositoDTO insumo) {
		this.insumo = insumo;
		textInsumo.setText(insumo.getInsumo().getNombre().get());
		textDeposito.setText(insumo.getUbicacion().getDeposito().getNombre().get());
		SpinnerValueFactory<Integer> valueFactory = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999999, insumo.getStockNuevo().get());
		spnCantidadNuevo.setValueFactory(valueFactory);
		valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999999, insumo.getStockUsado().get());
		spnCantidadUsado.setValueFactory(valueFactory);
		valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999999,
				insumo.getStockReservado().get());
		spnCantidadReservado.setValueFactory(valueFactory);
	}

	private void cerrarVentana() {
		Stage stage = (Stage) this.ventanaAjustarStock.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.arrastrarVenta2(true);
		
		this.btnDescartar.setOnMouseClicked(event -> {
			Stage stage = (Stage) borderPrinipal.getScene().getWindow();
			stage.close();
		});
		
	}
	
	private void arrastrarVenta2(boolean arrastrar) {

		if (arrastrar) {
			barraPrincipal.setOnMousePressed(event -> {
				Stage stage = (Stage) borderPrinipal.getScene().getWindow();
				initX = event.getSceneX();
				initY = event.getSceneY();
				borderPrinipal.setOpacity(0.7);
			});
			barraPrincipal.setOnMouseDragged(event -> {
				Stage stage = (Stage)borderPrinipal.getScene().getWindow();
				stage.setX(event.getScreenX() - initX);
				stage.setY(event.getScreenY() - initY);

			});
		} else {
			barraPrincipal.setOnMousePressed(event -> {
			});
			barraPrincipal.setOnMouseDragged(event -> {
			});
		}
		barraPrincipal.setOnMouseReleased(event -> {
			borderPrinipal.setOpacity(1.0);
		});

	}
}
