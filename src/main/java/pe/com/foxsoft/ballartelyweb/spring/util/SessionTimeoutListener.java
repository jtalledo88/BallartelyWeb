package pe.com.foxsoft.ballartelyweb.spring.util;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;

public class SessionTimeoutListener implements PhaseListener {
	
	private static final long serialVersionUID = 4053548447925271572L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforePhase(final PhaseEvent event) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.getPartialViewContext().isAjaxRequest() || facesContext.getRenderResponse()) { // not ajax or too late
            return;
        }
 
        final HttpServletRequest request = HttpServletRequest.class.cast(facesContext.getExternalContext().getRequest());
        if (request.getDispatcherType() == DispatcherType.FORWARD && getLoginPath().equals(request.getServletPath())) { // isLoginRedirection()
            final String redirect = facesContext.getExternalContext().getRequestContextPath() + request.getServletPath();
            try {
                facesContext.getExternalContext().redirect(redirect);
            } catch (final IOException e) {
                // here use you preferred logging framework to log this error
            }
        }
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	private String getLoginPath() {
        return "/paginas/inicio/login.xhtml";
    }

}
