/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.database.Dao;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Childwaypoint;
import org.geocachingtools.geoui.model.Coordinate;
import org.geocachingtools.geoui.model.Hint;

/**
 *
 * @author Schule
 */
@Named(value = "changeCacheCon")
@SessionScoped
public class ChangeCacheController implements Serializable {

    private final HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private final Dao dao = (Dao) s.getAttribute("dao");

    @Inject
    private RegisteredCachesControler con;
    
    private Cache cache;
    private Coordinate childcoord;
    private Childwaypoint childwaypoint;
    private Hint attemptHint;

    @PostConstruct
    public void init() {
        resetChild();
        resetAttemptHint();
    }

    public String setChangingCache(Cache cache) {
        resetChild();
        resetAttemptHint();
        this.cache = cache;
        return "changeCache";
    }

    public void addChildcoord() {
        cache.getChildwaypoints().add(new Childwaypoint(childwaypoint.getText(),
                childcoord.hashCoordinate(childcoord.getDoubleCoordinateX()),
                childcoord.hashCoordinate(childcoord.getDoubleCoordinateY()),
                cache));
        resetChild();
    }

    public void removeChildcoord(Childwaypoint child) {
        cache.getChildwaypoints().remove(child);
    }

    public void addAttemptHint() {
        cache.getHints().add(attemptHint);
        resetAttemptHint();
    }

    public void removeAttemptHint(Hint hint) {
        cache.getHints().remove(hint);
    }

    public String save() {
        dao.updateCache(cache);
        
        List<Cache> caches = con.getCaches();
        caches.remove(cache);
        caches.add(cache);
        con.setCaches(caches);
        con.sortCaches();
        
        cache = new Cache();
        return "registeredCaches";
    }

    private void resetChild() {
        this.childwaypoint = new Childwaypoint("", "", "", cache);
        this.childcoord = new Coordinate(true, true, 0, 0, 0, 0, 0, 0);
    }

    private void resetAttemptHint() {
        this.attemptHint = new Hint(1, "", cache);
    }
    
    public void removeCache() {
        dao.deleteCache(cache);
        List<Cache> caches = con.getCaches();
        caches.remove(cache);
        con.setCaches(caches);
    }
    
    /*
    Getter and Setter
    */
    public Hint getAttemptHint() {
        return attemptHint;
    }

    public void setAttemptHint(Hint attemptHint) {
        this.attemptHint = attemptHint;
    }

    public Childwaypoint getChildwaypoint() {
        return childwaypoint;
    }

    public void setChildwaypoint(Childwaypoint childwaypoint) {
        this.childwaypoint = childwaypoint;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Coordinate getChildcoord() {
        return childcoord;
    }

    public void setChildcoord(Coordinate childcoord) {
        this.childcoord = childcoord;
    }
}