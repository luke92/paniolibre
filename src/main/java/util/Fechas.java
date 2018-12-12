package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public abstract class Fechas {
	public static Calendar localDateToCalendar(LocalDate fecha) {
		Date date = java.sql.Date.valueOf(fecha);
		Calendar today = Calendar.getInstance();
		today.setTime(date);
		return today;
	}

	public static LocalDate CalendarTolocalDate(Calendar today) {
		LocalDate fecha = LocalDate.of(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1,
				today.get(Calendar.DAY_OF_MONTH));
		return fecha;
	}

	public static Calendar String_a_Fecha(String fechaString) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		Calendar cal = null;
		try {
			date = df.parse(fechaString);
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}

	public static Calendar String_a_Fecha_MySQL(String fechaString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Calendar cal = null;
		try {
			date = df.parse(fechaString);
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return cal;
	}

	public static String Fecha_a_String(Calendar fecha) {
		String fechaString = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		fechaString = format.format(fecha.getTime());
		return fechaString;
	}

	public static String Fecha_a_String_MySQL(Calendar fecha) {
		String fechaString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		fechaString = format.format(fecha.getTime());
		return fechaString;
	}

	public static boolean fechaValida(String fechaString) {
		return (String_a_Fecha(fechaString) == null);
	}

	public static boolean fechaValida(Calendar fecha) {
		return Fecha_a_String(fecha) == null;
	}

	public static Calendar Date_a_Calendar(Date fecha) {
		Calendar cal = null;
		try {
			cal = Calendar.getInstance();
			cal.setTime(fecha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal;
	}

	public static java.sql.Date Calendar_a_DateSQL(Calendar fecha) {
		java.sql.Date dateSQL = null;
		dateSQL = new java.sql.Date(fecha.getTimeInMillis());
		return dateSQL;
	}

	public static String DateSQL_a_String(java.sql.Date fecha) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String text = df.format(fecha);
		return text;
	}

	public static String String_a_String_MySQL(String fecha) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		Calendar cal = null;
		String fechaMySQL = "";
		try {
			date = df.parse(fecha);
			cal = Calendar.getInstance();
			cal.setTime(date);
			fechaMySQL = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return fechaMySQL;
	}

}
