/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.util.Dao;
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
    private Dao dao = (Dao) s.getAttribute("dao");

    boolean init = true;
    private Gctuser gctuser;
    private List<Cache> caches;
    private Cache selectedCache;
    
    @Inject
    private UserController userCon;

    @PostConstruct
    public void init() {        
        this.gctuser = userCon.getGctusr();
        caches = dao.getCachesFromUser(gctuser);
        sortCaches();
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
        return caches.size() == 0;
    }

    public Cache getSelectedCache() {
        return selectedCache;
    }

    public void setSelectedCache(Cache selectedCache) {
        this.selectedCache = selectedCache;
    }
}
