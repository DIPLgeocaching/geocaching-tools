/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Thomas
 */
@FacesConverter("org.geocachingtools.geoui.converter.StatusConverter")
public class StatusConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if("Aktiv".equals(string))
            return true;
        return false;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (boolean) o ? "Aktiv" : "Inaktiv";
    }


    
}
