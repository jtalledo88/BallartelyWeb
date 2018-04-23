package pe.com.foxsoft.reportsweb.jpa.domain;

public class ParametroJPA {
	
	public static final String CLASS_STRING = "java.lang.String";
	public static final String CLASS_INTEGER = "java.lang.Integer";
	public static final String CLASS_DOUBLE = "java.lang.Double";
	
	private String parameterName;
	private String parameterType;
	private Object parameterValue;
	
	public ParametroJPA() {
		
	}
	
	public ParametroJPA(String parameterName, String parameterType, Object parameterValue) {
		this.parameterName = parameterName;
		this.parameterType = parameterType;
		this.parameterValue = parameterValue;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public Object getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}

}
