/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.util.Dao;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Coordinate;
import org.geocachingtools.geoui.model.Gctuser;

/**
 *
 * @author Thomas
 */
@Named(value = "newcache")
@RequestScoped
public class CreateCacheBean implements Serializable {

    @Inject
    private RegisteredCachesControler con;

    private Cache cache;
    private Coordinate coordinate;

    private final HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private final Dao dao = (Dao) s.getAttribute("dao");
    private String mess;

    private UIComponent grid;

    @PostConstruct
    public void init() {
        cache = new Cache("","", "", "", "", "", new Gctuser(), new ArrayList<>(), new ArrayList<>());
        coordinate = new Coordinate(true, true, 0, 0, 0, 0, 0, 0);
    }

    public String saveCache() {
        if (coordinate.validateCoordinate()) {
            FacesContext.getCurrentInstance().addMessage(grid.getClientId(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Die Koordinate ist ungueltig", "Die Koordinate ist ungueltig"));
            return "createNewCache";
        }
        cache = new Cache(
                createLink(),
                cache.getGcName(),
                cache.getGcWaypoint().toUpperCase(),
                coordinate.hashCoordinate(coordinate.getDoubleCoordinateX()),
                coordinate.hashCoordinate(coordinate.getDoubleCoordinateY()),
                cache.getMessage(),
                con.getGctusr(),
                new ArrayList<>(),
                new ArrayList<>());
        
        dao.saveCache(cache);

        con.addCache(cache);
        con.sortCaches();

        return "registeredCaches";
    }

    private String createLink() {
        MessageDigest md;
        String c = cache.getGcWaypoint() + con.getGctusr().getGcUsername();
        int i = 0;
        String urlEncoded = "";
        
        try {
            do {
                urlEncoded = Base64.getUrlEncoder().encodeToString((c + i).getBytes("utf-8"));
                i++;
            } while (dao.existsCachelink(urlEncoded));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreateCacheBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return urlEncoded;
    }


    /*
    Getter and Setter
     */
    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public UIComponent getGrid() {
        return grid;
    }

    public void setGrid(UIComponent grid) {
        this.grid = grid;
    }
}
