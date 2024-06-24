	/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.util.Dao;

/**
 *
 * @author Schule
 */
@FacesValidator("org.org.geocachingtools.geoui.validator.CheckerWaypointValidator")
public class CheckerWaypointValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Dao dao = (Dao) session.getAttribute("dao");

        if (dao.existsCertianWaypoint(o.toString().toUpperCase())) {
            FacesMessage msg = new FacesMessage("Wegpunkt existiert nicht", "Wegpunkt existiert nicht");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}