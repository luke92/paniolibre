package presentacion.reportes;

import java.util.List;

import dto.InsumoReporteDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteInsumo {
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;

	// Recibe la lista de personas para armar el reporte

	public ReporteInsumo(List<InsumoReporteDTO> insumos) {
		try {
			this.reporteLleno = JasperFillManager.fillReport("reportes\\ReporteInsumo.jasper", null,
					new JRBeanCollectionDataSource(insumos));
		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}

}
