package services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import util.Fechas;

public class FechasTest {

	@Test
	public void String_a_String_MySQLTest() {
		String anio = "2018";
		String mes = "01";
		String dia = "02";
		String fecha = dia + "/" + mes + "/" + anio;

		String f = Fechas.String_a_String_MySQL(fecha);

		if (mes.substring(0, 1).equals("0"))
			mes = mes.substring(1, 2);
		if (dia.substring(0, 1).equals("0"))
			dia = dia.substring(1, 2);

		String fechaf = anio + "-" + mes + "-" + dia;

		assertTrue(f.equals(fechaf));
	}

}
