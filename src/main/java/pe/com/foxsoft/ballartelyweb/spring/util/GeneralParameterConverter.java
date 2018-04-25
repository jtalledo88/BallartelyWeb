package pe.com.foxsoft.ballartelyweb.spring.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import pe.com.foxsoft.ballartelyweb.jpa.data.GeneralParameter;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.ParametroGeneralService;

@Named
public class GeneralParameterConverter implements Converter{

	@Inject
	private ParametroGeneralService parametroGeneralService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			return parametroGeneralService.obtenerParametroGeneral(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BallartelyException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null)
			return null;
		return String.valueOf(((GeneralParameter)value).getParamId());
	}

	public ParametroGeneralService getParametroGeneralService() {
		return parametroGeneralService;
	}

	public void setParametroGeneralService(ParametroGeneralService parametroGeneralService) {
		this.parametroGeneralService = parametroGeneralService;
	}

}
