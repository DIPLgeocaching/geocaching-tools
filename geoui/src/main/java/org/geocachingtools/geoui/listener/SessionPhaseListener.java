package org.geocachingtools.geoui.listener;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Listener for the phases of JSF
 * @author Lukas
 */
public class SessionPhaseListener implements PhaseListener {

  private static final String LOGIN = "home.xhtml";

    /**
     * Function is called after a phase and is doing something
     * @param event PhaseEvent
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        //Do anything
    }

    /**
     * Function is called before a phase and shows if the session is still valid
     * and if not it redirects to the login site
     * @param event PhaseEvent
     */
    @Override
    public void beforePhase(PhaseEvent event) {
        
        FacesContext context = event.getFacesContext();
        ExternalContext ext = context.getExternalContext();
        
        HttpServletRequest rq = (HttpServletRequest) ext.getRequest();
        String ref = rq.getHeader("referer");
        HttpSession session = (HttpSession) ext.getSession(false);
        boolean newSession = (session == null) || (session.isNew());
        if (newSession && ref != null) {
            Application app = context.getApplication();
            ViewHandler viewHandler = app.getViewHandler();
           
            UIViewRoot view = viewHandler.createView(context, "/" + LOGIN);
            FacesMessage m = new FacesMessage("Session ist abgelaufen!");
            m.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, m);
            context.setViewRoot(view);
            context.renderResponse();       
        } 
    }

    /**
     * Returns the PhaseID
     * @return PhaseId
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
