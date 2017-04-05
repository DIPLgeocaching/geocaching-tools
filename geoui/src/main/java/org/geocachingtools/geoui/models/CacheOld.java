/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.models;

import org.geocachingtools.geoui.model.SessionAttempt;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This Class represents a Cache from a User (Gctusr).
 * A Gctuser can register caches which are registered at Geocaching.com.
 * @author Thomas
 */
@Entity
@Table(name = "cacheold")
public class CacheOld implements Serializable {

    @Id
    @Column(name="checkerlink")
    private String checkerlink; //A uniqe key created by gc-waypoint and name of the user
    
    @Column(name = "gcWaypoint")
    private String gcWaypoint; //The offical Waypoint of the Cache
    
    @Column(name = "status")
    private boolean status; //Flag to show if the Cache is acitvated

    @Column(name = "gcName")
    private String gcName; //The name the user sets for his cache

    @Column(name = "coordinateX")
    private String coordinateX; //latidude from the cache

    @Column(name = "coordinateY")
    private String coordinateY;//longitude from the Cache

    @Column(name = "message")
    private String message; //The message that is show when the cache is solved

    @ManyToOne(optional = false)
    @JoinColumn(name = "cache_gctuser_id")
    private GctuserOld cacheowner;//owner of the cache

    @OneToMany(mappedBy = "parentcache", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SessionAttempt> sessions;//all attempts to solve a cache are stored in "sessions"

    @OneToMany(mappedBy = "cachehint",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    private List<HintOld> hints;//all hints that show up when a specific amount of mistakes are made

    @OneToMany(mappedBy = "parentCache",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    private List<ChildwaypointOld> childwaypoints;//all subkoordinates

    /*
    Constructor
    */
    public CacheOld() {
    }

    public CacheOld(String checkerlink, String gcName, String gcWaypoint, String cooridnateX, String cooridnateY, String message, GctuserOld cacheowner, List<HintOld> hints, List<ChildwaypointOld> childwaypoints) {
        this.checkerlink = checkerlink;
        this.status = true;
        this.gcName = gcName;
        this.gcWaypoint = gcWaypoint;
        this.coordinateX = cooridnateX;
        this.coordinateY = cooridnateY;
        this.message = message;
        this.cacheowner = cacheowner;
        this.hints = hints;
        this.childwaypoints = childwaypoints;
    }
    
    /*
    Getter and Setter
    */
    public String getCheckerlink() {
        return checkerlink;
    }

    public void setCheckerlink(String checkerlink) {
        this.checkerlink = checkerlink;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public String getGcWaypoint() {
        return gcWaypoint;
    }

    public void setGcWaypoint(String gcWaypoint) {
        this.gcWaypoint = gcWaypoint;
    }

    public String getCooridnateX() {
        return coordinateX;
    }

    public void setCooridnateX(String cooridnateX) {
        this.coordinateX = cooridnateX;
    }

    public String getCooridnateY() {
        return coordinateY;
    }

    public void setCooridnateY(String cooridnateY) {
        this.coordinateY = cooridnateY;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtherInfo() {
        return message;
    }

    public void setOtherInfo(String otherInfo) {
        this.message = otherInfo;
    }

    public GctuserOld getCacheowner() {
        return cacheowner;
    }

    public void setCacheowner(GctuserOld cacheowner) {
        this.cacheowner = cacheowner;
    }

    public List<SessionAttempt> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionAttempt> sessions) {
        this.sessions = sessions;
    }

    public List<HintOld> getHints() {
        return hints;
    }

    public void setHints(List<HintOld> hints) {
        this.hints = hints;
    }

    public List<ChildwaypointOld> getChildwaypoints() {
        return childwaypoints;
    }

    public void setChildwaypoints(List<ChildwaypointOld> childwaypoints) {
        this.childwaypoints = childwaypoints;
    }

    @Override
    public String toString() {
        return "Cache{" + "checkerlink=" + checkerlink + ", gcWaypoint=" + gcWaypoint + ", status=" + status + ", gcName=" + gcName + ", coordinateX=" + coordinateX + ", coordinateY=" + coordinateY + ", message=" + message + ", cacheowner=" + cacheowner + ", sessions=" + sessions + ", hints=" + hints + ", childwaypoints=" + childwaypoints + '}';
    }
}
