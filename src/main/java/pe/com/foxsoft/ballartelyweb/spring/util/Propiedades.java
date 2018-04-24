package pe.com.foxsoft.ballartelyweb.spring.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:${ballartelyweb.ext.properties.dir}/ballartelyweb.properties")
public class Propiedades {

	@Value("${ballartelyweb.parameters.types}")
	private String tiposParametro;
	
	@Value("${ballartelyweb.combo.status}")
	private String comboEstados;

	public String getTiposParametro() {
		return tiposParametro;
	}

	public void setTiposParametro(String tiposParametro) {
		this.tiposParametro = tiposParametro;
	}

	public String getComboEstados() {
		return comboEstados;
	}

	public void setComboEstados(String comboEstados) {
		this.comboEstados = comboEstados;
	}
	
	
}
