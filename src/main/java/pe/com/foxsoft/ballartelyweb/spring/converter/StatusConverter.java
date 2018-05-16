package pe.com.foxsoft.ballartelyweb.spring.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;

@Named
public class StatusConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null)
			return null;
		String statusDesc = null;
		if(Constantes.STATUS_ACTIVE.equals(String.valueOf(value))) {
			statusDesc = "Activo";
		}else {
			statusDesc = "Inactivo";
		}
		
		return statusDesc;
	}

}
