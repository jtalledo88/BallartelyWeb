package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;
import pe.com.foxsoft.ballartelyweb.spring.util.EncriptacionUtil;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class EncriptadorMB {
	
	private String processOption;
	private String processValue;
	private String processResult;

	public void process() {
		try {
			if("".equals(processValue)) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar valor.");
				return;
			}
			if(Constantes.ENCRYPT.equals(processOption)) {
				processResult = EncriptacionUtil.encriptar(Constantes.ENCRIPTATION_KEY, processValue);
			}else {
				processResult = EncriptacionUtil.desencriptar(Constantes.ENCRIPTATION_KEY, processValue);
			}
			
		} catch (BallartelyException e) {
			Utilitarios.mensajeError("", "Ocurrió un error: " + e.getMessage());
		}
	}

	public String getProcessOption() {
		return processOption;
	}

	public void setProcessOption(String processOption) {
		this.processOption = processOption;
	}

	public String getProcessValue() {
		return processValue;
	}

	public void setProcessValue(String processValue) {
		this.processValue = processValue;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	
	
}
