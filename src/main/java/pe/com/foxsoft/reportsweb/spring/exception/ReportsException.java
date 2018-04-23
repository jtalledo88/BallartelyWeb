package pe.com.foxsoft.reportsweb.spring.exception;

import java.util.Date;

public class ReportsException extends Exception {
	
	private static final long serialVersionUID = 8851798243100862923L;

	public static final int NO_RESULT_ERROR = 0;

	public static final int GENERAL_ERROR = -1;
	
	private int type;
	private Date date;
    private String message;
     
    public ReportsException(int type, String message) {
        super();
        this.type = type;
        this.date = new Date();
        this.message = message;
    }
    
    public int getType() {
		return type;
	}
    
	public void setType(int type) {
		this.type = type;
	}

	public Date getDate() {
        return date;
    }
 
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return "ReportsException [type=" + type + ", date=" + date + ", message=" + message + "]";
    }

}