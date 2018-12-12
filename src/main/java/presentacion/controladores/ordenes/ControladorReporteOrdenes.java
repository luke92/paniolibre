package presentacion.controladores.ordenes;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import domain.model.OrdenDeTrabajo;
import dto.OrdenesReporteDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReporteOrdenDeTrabajo;
import services.OrdenDeTrabajoService;

public class ControladorReporteOrdenes implements Initializable {
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
    private CheckBox check_Nueva;

    @FXML
    private CheckBox check_Asignada;

    @FXML
    private CheckBox check_Realizada;

    @FXML
    private CheckBox check_Cerrado;

    @FXML
    private CheckBox check_Suspendida;

    private int nuevo;
    private int asignada;
    private int cerrada;
    private int realizada;
    private int suspendida;

	@FXML
	void descartar(MouseEvent event) {
		Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
		stage.close();
	}

	@FXML
	void generarReporte(MouseEvent event) {
		this.nuevo = 0;
		this.asignada = 0;
		this.cerrada = 0;
		this.suspendida = 0;
		this.realizada = 0;
		if(check_Nueva.isSelected() == true) {
			this.nuevo = 1;
		}
		if(check_Asignada.isSelected()== true) {
			this.asignada = 1;
		}
		if(check_Cerrado.isSelected() == true) {
			this.cerrada = 1;
		}
		if(check_Suspendida.isSelected() == true) {
			this.suspendida = 1;
		}
		if(check_Realizada.isSelected() == true) {
			this.realizada = 1;
		}
		OrdenDeTrabajoService ordenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
		if (this.tipoDeRango.getValue().equals("Personalizado")) {
			Date dateInicio = Date.valueOf(fechaInicio.getValue());
			Date dateFin = Date.valueOf(fechaFin.getValue());
			List<OrdenesReporteDTO> lista = ordenDeTrabajoService.obtenerOrdenDeTrabajoReporte(dateInicio, dateFin, nuevo,asignada,realizada,cerrada,suspendida);
			if (lista.size() != 0) {
				ReporteOrdenDeTrabajo reporteOrdenes = new ReporteOrdenDeTrabajo(lista);
				reporteOrdenes.mostrar();
				Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
				stage.close();
			} else {
				Notifications.create().title("Atencion")
						.text("No hay ninguna Orden de Trabajo dentro del rango seleccionado ").darkStyle()
						.showWarning();
			}
		}
		if (this.tipoDeRango.getValue().equals("Semestral")) {
			if (this.semestral.getValue().equals("Primer Semestre")) {
				Date primerSemestreInicio = Date.valueOf(anioSemestre.getValue() + "-01-01");
				Date primerSemestreFin = Date.valueOf(anioSemestre.getValue() + "-06-30");
				List<OrdenesReporteDTO> listaPrimerSemestre = ordenDeTrabajoService
						.obtenerOrdenDeTrabajoReporte(primerSemestreInicio, primerSemestreFin, nuevo,asignada,realizada,cerrada,suspendida);
				if (listaPrimerSemestre.size() != 0) {
					ReporteOrdenDeTrabajo reporteOrdenes = new ReporteOrdenDeTrabajo(listaPrimerSemestre);
					reporteOrdenes.mostrar();
					Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
					stage.close();
				} else {
					Notifications.create().title("Atencion")
							.text("No hay ninguna Orden de Trabajo dentro del rango seleccionado ").darkStyle()
							.showWarning();
				}
			}
			if (this.semestral.getValue().equals("Segundo Semestre")) {
				Date segundoSemestreInicio = Date.valueOf(anioSemestre.getValue() + "-07-01");
				Date segundoSemestreFin = Date.valueOf(anioSemestre.getValue() + "-12-31");
				List<OrdenesReporteDTO> listaSegundoSemestre = ordenDeTrabajoService
						.obtenerOrdenDeTrabajoReporte(segundoSemestreInicio, segundoSemestreFin, nuevo,asignada,realizada,cerrada,suspendida);
				if (listaSegundoSemestre.size() != 0) {
					ReporteOrdenDeTrabajo reporteOrdenes = new ReporteOrdenDeTrabajo(listaSegundoSemestre);
					reporteOrdenes.mostrar();
					Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
					stage.close();
				} else {
					Notifications.create().title("Atencion")
							.text("No hay ninguna Orden de Trabajo dentro del rango seleccionado ").darkStyle()
							.showWarning();
				}
			}
		}

		if (this.tipoDeRango.getValue().equals("Mensual")) {
			int anioMes = Integer.parseInt(anio.getValue());
			Date fechaInicio = null;
			Date fechaFin = null;
			if (this.meses.getValue().equals("Enero")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.JANUARY, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.JANUARY, 31));
			}
			if (this.meses.getValue().equals("Febrero")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.FEBRUARY, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.JANUARY, 28));
			}
			if (this.meses.getValue().equals("Marzo")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.MARCH, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.MARCH, 31));
			}
			if (this.meses.getValue().equals("Abril")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.APRIL, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.APRIL, 30));
			}
			if (this.meses.getValue().equals("Mayo")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.MAY, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.MAY, 31));
			}
			if (this.meses.getValue().equals("Junio")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.JUNE, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.JUNE, 30));
			}
			if (this.meses.getValue().equals("Julio")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.JULY, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.JULY, 31));
			}
			if (this.meses.getValue().equals("Agosto")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.AUGUST, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.AUGUST, 31));
			}
			if (this.meses.getValue().equals("Septiembre")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.SEPTEMBER, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.SEPTEMBER, 30));
			}
			if (this.meses.getValue().equals("Octubre")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.OCTOBER, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.OCTOBER, 31));
			}
			if (this.meses.getValue().equals("Noviembre")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.NOVEMBER, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.NOVEMBER, 30));
			}
			if (this.meses.getValue().equals("Diciembre")) {
				fechaInicio = Date.valueOf(LocalDate.of(anioMes, Month.DECEMBER, 1));
				fechaFin = Date.valueOf(LocalDate.of(anioMes, Month.DECEMBER, 31));
			}
			List<OrdenesReporteDTO> listaMeses = ordenDeTrabajoService.obtenerOrdenDeTrabajoReporte(fechaInicio,
					fechaFin, nuevo,asignada,realizada,cerrada,suspendida);
			if (listaMeses.size() != 0) {
				ReporteOrdenDeTrabajo reporteOrdenes = new ReporteOrdenDeTrabajo(listaMeses);
				reporteOrdenes.mostrar();
				Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
				stage.close();
			} else {
				Notifications.create().title("Atencion")
						.text("No hay ninguna Orden de Trabajo dentro del rango seleccionado ").darkStyle()
						.showWarning();
			}
		}
		if (this.tipoDeRango.getValue().equals("Anual")) {
			Date anioInicio = Date.valueOf(anio.getValue() + "-01-01");
			Date anioFin = Date.valueOf(anio.getValue() + "-12-31");
			List<OrdenesReporteDTO> listaAnio = ordenDeTrabajoService.obtenerOrdenDeTrabajoReporte(anioInicio, anioFin, nuevo,asignada,realizada,cerrada,suspendida);
			if (listaAnio.size() != 0) {
				ReporteOrdenDeTrabajo reporteOrdenes = new ReporteOrdenDeTrabajo(listaAnio);
				reporteOrdenes.mostrar();
				Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
				stage.close();
			} else {
				Notifications.create().title("Atencion")
						.text("No hay ninguna Orden de Trabajo dentro del rango seleccionado ").darkStyle()
						.showWarning();
			}
		}
	}

	public boolean validateNumero(String numero) {
		if (numero.matches("[0-9]{4}")) {
			return true;
		} else {
			return false;
		}
	}

	@FXML
	void tipoDeRango(ActionEvent event) {
		if (this.tipoDeRango.getValue().equals("Personalizado")) {
			this.fechaInicio.setDisable(false);
			this.fechaFin.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
			this.check_Asignada.setDisable(false);
			this.check_Cerrado.setDisable(false);
			this.check_Nueva.setDisable(false);
			this.check_Realizada.setDisable(false);
			this.check_Suspendida.setDisable(false);
		} else {
			this.fechaInicio.setDisable(true);
			this.fechaFin.setDisable(true);
		}
		if (this.tipoDeRango.getValue().equals("Semestral")) {
			this.anioSemestre.setDisable(false);
			this.semestral.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
			this.check_Asignada.setDisable(false);
			this.check_Cerrado.setDisable(false);
			this.check_Nueva.setDisable(false);
			this.check_Realizada.setDisable(false);
			this.check_Suspendida.setDisable(false);
		} else {
			this.anioSemestre.setDisable(true);
			this.semestral.setDisable(true);
		}
		if (this.tipoDeRango.getValue().equals("Mensual")) {
			this.anio.setDisable(false);
			this.meses.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
			this.check_Asignada.setDisable(false);
			this.check_Cerrado.setDisable(false);
			this.check_Nueva.setDisable(false);
			this.check_Realizada.setDisable(false);
			this.check_Suspendida.setDisable(false);
		} else {
			this.anio.setDisable(true);
			this.meses.setDisable(true);
		}
		if (this.tipoDeRango.getValue().equals("Anual")) {
			this.anio.setDisable(false);
			this.btnGenerarReporte.setDisable(false);
			this.check_Asignada.setDisable(false);
			this.check_Cerrado.setDisable(false);
			this.check_Nueva.setDisable(false);
			this.check_Realizada.setDisable(false);
			this.check_Suspendida.setDisable(false);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		OrdenDeTrabajoService service = new OrdenDeTrabajoService(new DAOSQLFactory());
		OrdenDeTrabajo ordenDeTrabajo = service.obtenerPrimerOrden();
		this.tipoDeRango.getItems().addAll("Personalizado", "Semestral", "Mensual", "Anual");
		this.semestral.getItems().addAll("Primer Semestre", "Segundo Semestre");
		this.meses.getItems().addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
				"Septiembre", "Octubre", "Noviembre", "Diciembre");
		int yearPrimero = ordenDeTrabajo.getFechaInicio().get(Calendar.YEAR);
		ArrayList<String> years_tmp = new ArrayList<String>();
		for (int years = yearPrimero; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
			years_tmp.add(years + "");
		}
		this.anio.getItems().addAll(years_tmp);
		this.anioSemestre.getItems().addAll(years_tmp);
	}
}
