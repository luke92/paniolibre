package presentacion.reportes;

import java.util.List;

import dto.OrdenesReporteDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteOrdenDeTrabajo {
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;

	// Recibe la lista de personas para armar el reporte

	public ReporteOrdenDeTrabajo(List<OrdenesReporteDTO> ordenes) {
		try {
			this.reporteLleno = JasperFillManager.fillReport("reportes\\ReporteOrdenesDeTrabajo.jasper", null,
					new JRBeanCollectionDataSource(ordenes));
		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}
}
