package presentacion.reportes;

import java.util.List;

import dto.HerramientaReporteDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteHerramienta {

	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;

	// Recibe la lista de personas para armar el reporte

	public ReporteHerramienta(List<HerramientaReporteDTO> herramientas) {

		try {

			this.reporteLleno = JasperFillManager.fillReport("reportes\\ReporteHerramienta.jasper", null,
					new JRBeanCollectionDataSource(herramientas));

		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}

}