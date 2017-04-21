/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.database.Dao;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Gctuser;

/**
 *
 * @author Thomas
 */
@Named(value = "registedCacheCon")
@SessionScoped
public class RegisteredCachesControler implements Serializable {
    
    private final HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private final Dao dao = (Dao) s.getAttribute("dao");

    boolean init = true;
    private Gctuser gctuser;
    private List<Cache> caches;
    private boolean noCaches = false;
    private Cache selectedCache;

    @PostConstruct
    public void init() {
        if (init) {
            try {
                dao.initDb();
            } catch (ParseException ex) {
                Logger.getLogger(RegisteredCachesControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.gctuser = dao.getCertainGctuser("Rapi1234-GA");
        caches = new Dao().getCachesFromUser(gctuser);
        sortCaches();
        noCaches = caches.isEmpty();
    }
    
    public void sortCaches() {
        Collections.sort(caches, new Comparator<Cache>() {
            @Override
            public int compare(Cache c1, Cache c2) {
                return c1.getGcWaypoint().compareTo(c2.getGcWaypoint());
            }
        });
    }

    public String cachesFromUser() {
        return "yourRegisteredCaches";
    }
    
    public void addCache(Cache c) {
        caches.add(c);
    }
    
    /*
    Getter und Setter
     */
    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
    
    public Gctuser getGctusr() {
        return gctuser;
    }

    public List<Cache> getCaches() {
        return caches;
    }

    public void setCaches(List<Cache> caches) {
        this.caches = caches;
    }

    public void setGctusr(Gctuser gctuser) {
        this.gctuser = gctuser;
    }

    public boolean isNoCaches() {
        return noCaches;
    }

    public void setNoCaches(boolean noCaches) {
        this.noCaches = noCaches;
    }

    public Cache getSelectedCache() {
        return selectedCache;
    }

    public void setSelectedCache(Cache selectedCache) {
        this.selectedCache = selectedCache;
    }
}
