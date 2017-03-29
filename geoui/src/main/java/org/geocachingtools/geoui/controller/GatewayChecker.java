/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.database.Dao;
import org.geocachingtools.geoui.model.Cache;

/**
 *
 * @author Thomas
 */
@Named(value = "gateway")
@RequestScoped
public class GatewayChecker implements Serializable {

    //Are need for the Database connection
    private final HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private final Dao dao = (Dao) s.getAttribute("dao");

    @Inject
    private CheckerController con;

    public String onload() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Cache cache = dao.getCertainCache(params.get("id"));

        if (cache.getGcWaypoint() != null) {
            con.setCurrentCache(cache);
        } else {
            return "cacheNotFound";
        }

        return "checker";
    }
}
