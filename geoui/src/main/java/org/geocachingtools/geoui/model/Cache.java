/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Thomas
 */
@Entity
@Table(name = "cache")
public class Cache implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "status")
    private boolean status;
    
    @Column(name = "gcName")
    private String gcName;
    
    @Column(name = "gcWaypoint")
    private String gcWaypoint;
    
    @Column(name = "cooridnateX")
    private double cooridnateX;
    
    @Column(name = "cooridnateY")
    private double cooridnateY;
    
    @Column(name = "message")
    private String message;
    
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "cache_gctuser_id")
    private Gctusr cacheowner;
    
    @OneToMany(mappedBy = "cache")
    private List<SessionAttempts> sessions;
    
    @OneToMany(mappedBy = "cachehint")
    private List<Hint> hints;
    
    @OneToMany(mappedBy="parentCache", fetch = FetchType.EAGER)
    private List<Childwaypoint> childwaypoints;

    public Cache() {
    }

    public Cache(String gcName, String gcWaypoint, double cooridnateX, double cooridnateY, String message, Gctusr cacheowner) {
        this.status = true;
        this.gcName = gcName;
        this.gcWaypoint = gcWaypoint;
        this.cooridnateX = cooridnateX;
        this.cooridnateY = cooridnateY;
        this.message = message;
        this.cacheowner = cacheowner;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getCooridnateX() {
        return cooridnateX;
    }

    public void setCooridnateX(double cooridnateX) {
        this.cooridnateX = cooridnateX;
    }

    public double getCooridnateY() {
        return cooridnateY;
    }

    public void setCooridnateY(double cooridnateY) {
        this.cooridnateY = cooridnateY;
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

    public Gctusr getCacheowner() {
        return cacheowner;
    }

    public void setCacheowner(Gctusr cacheowner) {
        this.cacheowner = cacheowner;
    }

    public List<SessionAttempts> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionAttempts> sessions) {
        this.sessions = sessions;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

    public List<Childwaypoint> getChildwaypoints() {
        return childwaypoints;
    }

    public void setChildwaypoints(List<Childwaypoint> childwaypoints) {
        this.childwaypoints = childwaypoints;
    }   
}
