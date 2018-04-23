package pe.com.foxsoft.reportsweb.jpa.domain;

public class ParametroJPA {
	
	public static final String CLASS_STRING = "java.lang.String";
	public static final String CLASS_INTEGER = "java.lang.Integer";
	public static final String CLASS_DOUBLE = "java.lang.Double";
	public static final String COMPARE_TYPE_EQUALS = "=";
	public static final String COMPARE_TYPE_LIKE = "like";
	
	private String parameterName;
	private String parameterType;
	private Object parameterValue;
	private String parameterCompareType;
	
	public ParametroJPA() {
		
	}
	
	public ParametroJPA(String parameterName, String parameterType, Object parameterValue, String parameterCompareType) {
		this.parameterName = parameterName;
		this.parameterType = parameterType;
		this.parameterValue = parameterValue;
		this.parameterCompareType = parameterCompareType;
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
	public String getParameterCompareType() {
		return parameterCompareType;
	}
	public void setParameterCompareType(String parameterCompareType) {
		this.parameterCompareType = parameterCompareType;
	}

}
