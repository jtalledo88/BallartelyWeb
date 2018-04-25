package pe.com.foxsoft.ballartelyweb.spring.util;




import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Log4jInit extends HttpServlet {
	
	private static final long serialVersionUID = -974901779809737948L;

	public void init() {
    	try {
    		File fileLog = new File(getServletContext().getInitParameter("log4jpath"));
        	InputStream isLog = new FileInputStream(fileLog);
            Properties properties = new Properties();
            properties.load(isLog);
            PropertyConfigurator.configure(properties);
		} catch (Exception e) {
			System.err.println(e);
		}
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) {
    }
}
