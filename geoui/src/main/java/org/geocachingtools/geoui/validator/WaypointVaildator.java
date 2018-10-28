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

/**
 *
 * @author Thomas
 */
@FacesValidator("org.org.geocachingtools.geoui.validator.WaypointValidator")
public class WaypointVaildator implements Validator {

    /**
     * Validiert den eigegebenen Wegpunkt. Dabei wird ueberprueft, ob er 7
     * Zeichen lang ist und mit "GC" beginnt
     * @param fc
     * @param uic
     * @param o der zu ueberpruefende String
     * @throws ValidatorException wenn die Kriterien nicht erfuellt sind
     */
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String waypoint = o.toString().toLowerCase();//toLowerCase, da der Benutzer den Wegpunkt gorss oder klein schrebien koennte
        
        if (waypoint.length() != 7 || !waypoint.startsWith("gc")) {
            FacesMessage msg = new FacesMessage("Waypoint validation failed.", "Der Wegpunkt ist ungültig.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
