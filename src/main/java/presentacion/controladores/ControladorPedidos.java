
package presentacion.controladores;

import java.io.IOException;
import java.util.List;

import dto.PedidoInsumoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import persistencia.dao.mysql.DAOSQLFactory;
import services.PedidoInsumoService;

public class ControladorPedidos {

	@FXML
	private ScrollPane panelPedidos;
	
	@FXML
	private Button btnLista;
	
	@FXML
	private BorderPane borderSecuandario;
	
	@FXML
	private BorderPane borderPrincipal;
	
	@FXML
	private TableView<PedidoInsumoDTO> tablaPedidos;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaID;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaFechaSolicitud;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaFechaProbableRecepcion;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaComentario;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaRecibido;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaNroOrdenCompra;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaFechaRealRecepcion;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaProveedor;

	@FXML
	private Button btnAgregarPedido;

	@FXML
	private TextField txtBusqueda;

	private ObservableList<PedidoInsumoDTO> masterData = FXCollections.observableArrayList();

	private PedidoInsumoService pedidosService = new PedidoInsumoService(new DAOSQLFactory());
	
	
    @FXML
    void abrirPanelLista(MouseEvent event) {
    	this.borderPrincipal.setCenter(borderSecuandario);
    }
	

	@FXML
	void agregarPedido(MouseEvent event) throws IOException {
//		FXMLLoader root;
//		try {
//			root = new FXMLLoader(getClass().getResource("/vistas/agregarpedido.fxml"));
//			Scene scene = new Scene(root.load());
//			ControladorPedidosDetalle controlador = root.getController();
//			controlador.setMasterData(masterData);
//			scene.setFill(Color.TRANSPARENT);
//			Stage stage = new Stage();
//			stage.setScene(scene);
//			stage.initStyle(StageStyle.TRANSPARENT);
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.show();
//
//			// Eventos del teclado
//			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//				@Override
//				public void handle(KeyEvent event) {
//					if (event.getCode() == KeyCode.ESCAPE) {
//						stage.close();
//					}
//				}
//			});
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
		
		FXMLLoader root;
		root = new FXMLLoader(getClass().getResource("/vistas/agregarpedido2.fxml"));
		Scene scene = new Scene(root.load());
		ControladorPedidosDetalle controlador = root.getController();
		controlador.setMasterData(masterData);
		this.borderSecuandario.setTop(null);
		this.borderSecuandario.setCenter(scene.getRoot());
			
	}

	void llenarTablaPedidos() {
		List<PedidoInsumoDTO> pedidosInsumos = pedidosService.obtenerPedidoInsumoDTO();
		for (PedidoInsumoDTO pedidoInsumo : pedidosInsumos) {
			this.masterData.add(pedidoInsumo);
		}

		this.columnaID.setCellValueFactory(cellData -> cellData.getValue().getIdPedidoInsumoProperty().asString());
		this.columnaFechaSolicitud.setCellValueFactory(new PropertyValueFactory<>("fechaSolicitud"));
		this.columnaFechaProbableRecepcion.setCellValueFactory(new PropertyValueFactory<>("fechaProbableRecepcion"));
		this.columnaComentario.setCellValueFactory(cellData -> cellData.getValue().getComentarioProperty());
		this.columnaNroOrdenCompra
				.setCellValueFactory(cellData -> cellData.getValue().getNroOrdenCompraProperty().asString());
		this.columnaFechaRealRecepcion.setCellValueFactory(new PropertyValueFactory<>("fechaRealRecepcion"));
		this.columnaProveedor.setCellValueFactory(cellData -> cellData.getValue().getProveedorProperty());
		this.columnaRecibido.setCellValueFactory(cellData -> cellData.getValue().getRecibido().getEstado());

		this.tablaPedidos.setItems(masterData);

	}
}