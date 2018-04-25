package pe.com.foxsoft.ballartelyweb.spring.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pe.com.foxsoft.ballartelyweb.spring.domain.TipoParametro;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

public class Utilitarios {
	private static String NUMEROS = "0123456789";
	private static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	private static String ESPECIALES = "��@";

	public static String getRuc() {
		return MAYUSCULAS + MINUSCULAS;
	}

	public static String getContrasenia() {
		return getContrasenia(20);
	}

	public static String getContrasenia(int length) {
		return getContrasenia(NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES, length);
	}

	private static String getContrasenia(String key, int length) {
		String pswd = "";
		for (int i = 0; i < length; i++) {
			pswd = pswd + key.charAt((int) (Math.random() * key.length()));
		}
		return pswd;
	}

	public static Timestamp getFechaActual() {
		Date fecha = new Date();
		Timestamp time = new Timestamp(fecha.getTime());
		return time;
	}

	public static String formatoFecha(Date fecha) {
		String fechaActualizacion = "";
		String horaActualizacion = "";

		Calendar calFechaAct = Calendar.getInstance();
		calFechaAct.setTimeInMillis(fecha.getTime());
		fechaActualizacion = calFechaAct.get(5) + "/" + calFechaAct.get(2) + "/" + calFechaAct.get(1);
		horaActualizacion = calFechaAct.get(10) + ":" + calFechaAct.get(12) + ":" + calFechaAct.get(13);

		return fechaActualizacion + " " + horaActualizacion;
	}

	public static boolean compararCadenas(String fijo, String prmt) {
		if (fijo.toUpperCase().toString().startsWith(prmt.toUpperCase())) {
			return true;
		}
		return false;
	}

	public static boolean compararNumeros(String fijo, String prmt) {
		if (fijo.toString().startsWith(prmt.toString())) {
			return true;
		}
		return false;
	}

	public static void mensaje(String titulo, String mensaje) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(titulo, mensaje));
	}

	public static void mensajeInfo(String titutlo, String mensaje) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informaci�n", mensaje));
	}

	public static void mensajeError(String titutlo, String mensaje) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenci�n", mensaje));
	}

	public static void putObjectInSession(String value, Object var) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(true);
		session.setAttribute(value, var);
	}

	public static Object getObjectInSession(String value) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession sessionhttp = (HttpSession) context.getSession(true);
		return sessionhttp.getAttribute(value);
	}

	public static void removeObjectInSession(String value) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		session.removeAttribute(value);
		session.invalidate();
	}
	
	public static void redirectPage(String page) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + page);
		} catch (IOException e) {
			
		}
	}

	public static String remove2(String input) {
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

		Pattern pattern = Pattern.compile("\\P{ASCII}+");
		return pattern.matcher(normalized).replaceAll("");
	}

	public static String quitarTilde(String palabra) {
		String tustring = palabra;
		tustring = tustring.replace('á', 'a');
		tustring = tustring.replace('Á', 'A');
		tustring = tustring.replace('é', 'e');
		tustring = tustring.replace('É', 'E');
		tustring = tustring.replace('í', 'i');
		tustring = tustring.replace('Í', 'I');
		tustring = tustring.replace('ó', 'o');
		tustring = tustring.replace('Ó', 'O');
		tustring = tustring.replace('ú', 'u');
		tustring = tustring.replace('Ú', 'U');

		return tustring.replaceAll(" ", "");
	}

	public static List<TipoParametro> obtenerListaTipoParametros(String cadena) throws BallartelyException {
		try {
			List<TipoParametro> lstValores = new ArrayList<TipoParametro>();
			String[] arrCadena = cadena.split(",");
			TipoParametro tipoParametro = null;
			for (String s : arrCadena) {
				String[] arrValores = s.split(":");
				tipoParametro = new TipoParametro();
				tipoParametro.setItem(arrValores[0]);
				tipoParametro.setDescription(arrValores[1]);

				lstValores.add(tipoParametro);
			}

			return lstValores;
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}
}
