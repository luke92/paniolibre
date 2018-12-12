package presentacion.controladores.insumos;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import domain.model.RetiroInsumo;
import dto.ArbolCategoriaDTO;
import dto.InsumoReporteDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReporteInsumo;
import services.ArbolCategoriaService;
import services.InsumoService;
import util.Dialogos;

public class ControladorInsumoReporte implements Initializable {
	@FXML
	private DialogPane ventanaAgregarDeposito;

	@FXML
	private Button btnGenerarReporte;

	@FXML
	private Button btnDescartar;

	@FXML
	private DatePicker fechaInicio;

	@FXML
	private DatePicker fechaFin;
	@FXML
	private ComboBox<String> tipoDeRango;

	@FXML
	private ComboBox<String> semestral;

	@FXML
	private ComboBox<String> meses;

	@FXML
	private ComboBox<String> anioSemestre;

	@FXML
	private ComboBox<String> anio;
	@FXML
	private TreeView<ArbolCategoriaDTO> treeViewcategoriaInsumo;
	private ArbolCategoriaService serviceCategoria = new ArbolCategoriaService(new DAOSQLFactory());
	private ObservableList<ArbolCategoriaDTO> per = FXCollections.observableArrayList();
	private TreeItem<ArbolCategoriaDTO> rootArbol;

	private InsumoService insumoService = new InsumoService(new DAOSQLFactory());
	private String personalizado = "Personalizado";
	private String anual = "Anual";
	private String semestralString = "Semestral";
	private String mensual = "Mensual";
	public void llenarTreeView() {
		per.clear();
		ArbolCategoriaDTO arbol = null;
		arbol = serviceCategoria.obtenerArbolCategoriasInsumoDTO();

		per.add(arbol);

		rootArbol = new RecursiveTreeItem<ArbolCategoriaDTO>(per, RecursiveTreeObject::getChildren);
		treeViewcategoriaInsumo.setRoot(rootArbol.getChildren().get(0));
		this.expandTreeView(rootArbol.getChildren().get(0));

	}

	private void expandTreeView(TreeItem<ArbolCategoriaDTO> item) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(true);
			for (TreeItem<ArbolCategoriaDTO> child : item.getChildren()) {
				expandTreeView(child);
			}
		}
	}

	@FXML
	void descartar(MouseEvent event) {
		Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
		stage.close();
	}

	private ArbolCategoriaDTO obtenerCategoriaSeleccionada() {
		ReadOnlyObjectProperty<TreeItem<ArbolCategoriaDTO>> padre = treeViewcategoriaInsumo.getSelectionModel()
				.selectedItemProperty();
		if (padre.get() != null)
			return padre.get().getValue();
		else
			return null;
	}

	@FXML
	void generarReporte(MouseEvent event) {
		ArbolCategoriaDTO categoriaSeleccionada = this.obtenerCategoriaSeleccionada();
		Date dateInicio = null;
		Date dateFin = null;
		if (this.tipoDeRango.getValue().equals(personalizado)) {
			dateInicio = Date.valueOf(fechaInicio.getValue());
			dateFin = Date.valueOf(fechaFin.getValue());
		}
		if (this.tipoDeRango.getValue().equals(semestralString)) {
			if (this.semestral.getValue().equals("Primer Semestre")) {
				dateInicio = Date.valueOf(anioSemestre.getValue() + "-01-01");
				dateFin = Date.valueOf(anioSemestre.getValue() + "-06-30");
			}
			if (this.semestral.getValue().equals("Segundo Semestre")) {
				dateInicio = Date.valueOf(anioSemestre.getValue() + "-07-01");
				dateFin = Date.valueOf(anioSemestre.getValue() + "-12-31");
			}
		}
		if (this.tipoDeRango.getValue().equals(mensual)) {
			int anioMes = Integer.parseInt(anio.getValue());
			if (this.meses.getValue().equals("Enero")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.JANUARY, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.JANUARY, 31));
			}
			if (this.meses.getValue().equals("Febrero")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.FEBRUARY, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.JANUARY, 28));
			}
			if (this.meses.getValue().equals("Marzo")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.MARCH, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.MARCH, 31));
			}
			if (this.meses.getValue().equals("Abril")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.APRIL, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.APRIL, 30));
			}
			if (this.meses.getValue().equals("Mayo")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.MAY, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.MAY, 31));
			}
			if (this.meses.getValue().equals("Junio")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.JUNE, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.JUNE, 30));
			}
			if (this.meses.getValue().equals("Julio")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.JULY, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.JULY, 31));
			}
			if (this.meses.getValue().equals("Agosto")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.AUGUST, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.AUGUST, 31));
			}
			if (this.meses.getValue().equals("Septiembre")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.SEPTEMBER, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.SEPTEMBER, 30));
			}
			if (this.meses.getValue().equals("Octubre")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.OCTOBER, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.OCTOBER, 31));
			}
			if (this.meses.getValue().equals("Noviembre")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.NOVEMBER, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.NOVEMBER, 30));
			}
			if (this.meses.getValue().equals("Diciembre")) {
				dateInicio = Date.valueOf(LocalDate.of(anioMes, Month.DECEMBER, 1));
				dateFin = Date.valueOf(LocalDate.of(anioMes, Month.DECEMBER, 31));
			}
		}

		if (this.tipoDeRango.getValue().equals(anual)) {
			dateInicio = Date.valueOf(anio.getValue() + "-01-01");
			dateFin = Date.valueOf(anio.getValue() + "-12-31");
		}
		if(dateInicio != null && dateFin != null && categoriaSeleccionada != null)
		{
			mostrarReporte(dateInicio, dateFin, categoriaSeleccionada);
		}
		else
		{
			Dialogos.error("Error", "No se puede mostrar el reporte", "Falta seleccionar los filtros necesarios");
		}
		
	}

	public boolean validateNumero(String numero) {
		return numero.matches("[0-9]{4}");
	}

	@FXML
	void tipoDeRango(ActionEvent event) {
		if (this.tipoDeRango.getValue().equals(personalizado)) {
			this.fechaInicio.setDisable(false);
			this.fechaFin.setDisable(false);
			this.treeViewcategoriaInsumo.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
		} else {
			this.fechaInicio.setDisable(true);
			this.fechaFin.setDisable(true);
		}
		if (this.tipoDeRango.getValue().equals(semestralString)) {
			this.anioSemestre.setDisable(false);
			this.semestral.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
			this.treeViewcategoriaInsumo.setDisable(false);
		} else {
			this.anioSemestre.setDisable(true);
			this.semestral.setDisable(true);
		}
		if (this.tipoDeRango.getValue().equals(mensual)) {
			this.anio.setDisable(false);
			this.meses.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
			this.treeViewcategoriaInsumo.setDisable(false);
		} else {
			this.anio.setDisable(true);
			this.meses.setDisable(true);
		}
		if (this.tipoDeRango.getValue().equals(anual)) {
			this.anio.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
			this.treeViewcategoriaInsumo.setDisable(false);
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		InsumoService service = new InsumoService(new DAOSQLFactory());
		RetiroInsumo retiroInsumo = service.obtenerPrimerRetiroInsumo();
		llenarTreeView();
		this.tipoDeRango.getItems().addAll(personalizado, semestralString, mensual, anual);
		this.semestral.getItems().addAll("Primer Semestre", "Segundo Semestre");
		this.meses.getItems().addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
				"Septiembre", "Octubre", "Noviembre", "Diciembre");
		int yearPrimero = retiroInsumo.getFecha().get(Calendar.YEAR);
		ArrayList<String> years_tmp = new ArrayList<>();
		for (int years = yearPrimero; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
			years_tmp.add(years + "");
		}
		this.anio.getItems().addAll(years_tmp);
		this.anioSemestre.getItems().addAll(years_tmp);
	}

	private void mostrarReporte(Date dateInicio, Date dateFin, ArbolCategoriaDTO categoriaSeleccionada) {
		List<InsumoReporteDTO> listaInsumos = insumoService.obtenerInsumosMasPrestadas(categoriaSeleccionada,
				dateInicio, dateFin);
		if (!listaInsumos.isEmpty()) {
			ReporteInsumo reporteInsumo = new ReporteInsumo(listaInsumos);
			reporteInsumo.mostrar();
			Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
			stage.close();
		} else {
			Notifications.create().title("Atencion").text("La categoria seleccionada no posee ningun préstamo")
					.darkStyle().showWarning();

		}
	}
}
