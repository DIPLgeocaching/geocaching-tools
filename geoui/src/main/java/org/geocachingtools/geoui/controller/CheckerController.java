package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.database.Dao;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Check;
import org.geocachingtools.geoui.model.Childwaypoint;
import org.geocachingtools.geoui.model.Coordinate;
import org.geocachingtools.geoui.model.Hint;
import org.geocachingtools.geoui.model.SessionAttempt;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Schule
 */
@Named(value = "checkerCon")
@RequestScoped
public class CheckerController implements Serializable {

    //Are need for the Database connection
    private final HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private final Dao dao = (Dao) s.getAttribute("dao");

    @Inject
    private UserchecksController userchecks;

    //Variables the User has to Enter
    private Cache cache;
    private Coordinate coordinate;

    //The displayed message
    private String message;

    //When the attemps of a hint are reached
    private String key;
    private String attemptHint;
    private boolean showAttemptHint;

    //When the puzzel is solved
    private boolean isSolved;
    private MapModel solvedModel;

    //displays the amout of tries left
    private String infoTries;

    private UIComponent grid;
    
    @PostConstruct
    public void init() {
        refresh();
    }

    public void setCurrentCache(Cache cache) {
        refresh();
        System.out.println("hi");
        this.cache = cache;
        this.key = cache.getCheckerlink();
    }

    public boolean exists() {
        return cache != null;
    }

    private boolean checkAttempts() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        DateFormat df = new SimpleDateFormat("HH:mm");

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        Check check = userchecks.cehckForAttempts(ipAddress);

        if (check.getSum() < 6) {
            infoTries = "Du hast noch " + (5 - check.getSum())
                    + " Verscuhe über. Um " + df.format(check.getCal().getTime())
                    + " bekommst du fünf neue Versuche.";
            return true;
        } else {
            infoTries = "Du hast alle Versuche verbraucht. Warte bis " + df.format(check.getCal().getTime())
                    + ". Dort bekommst du fünf neue Versuche.";
            return false;
        }
    }

    public void check() {
        if (coordinate.validateCoordinate()) {
            FacesContext.getCurrentInstance().addMessage(grid.getClientId(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Die Koordinate ist ungültig", "Die Koordinate ist ungültig"));
            return;
        }
        if (!checkAttempts()) {
            message = "Bitte warte bis die Zeit abgelaufen ist.";
            return;
        }

        String x = coordinate.hashCoordinate(coordinate.getDoubleCoordinateX());
        String y = coordinate.hashCoordinate(coordinate.getDoubleCoordinateY());

        message = "Die Koordinaten stimmen leider nicht.";
        attemptHint = "";
        isSolved = false;

        if (x.equals(cache.getCooridnateX())
                && y.equals(cache.getCooridnateY())) {
            message = "Gratuliere! Du hast es geschafft.";
            solvedModel.addOverlay(new Marker(new LatLng(coordinate.getDoubleCoordinateY(),
                    coordinate.getDoubleCoordinateX()),
                    "Der Cache liegt hier!"));
            isSolved = true;
        } else if (!cache.getChildwaypoints().isEmpty()) {
            for (Childwaypoint child : cache.getChildwaypoints()) {
                if (x.equals(child.getCooridnateX())
                        && y.equals(child.getCooridnateY())) {
                    message = "Du hast bestimmte Koordinaten eingegben. Hier ein Hinweis vom Ersteller:" + child.getText();
                }
            }
        }

        userChecked();
        sessionHint();
    }

    private void userChecked() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        MessageDigest md;
        String hashedIP = "";
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] array = md.digest(ipAddress.getBytes(StandardCharsets.UTF_8));
            hashedIP = new String(array);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("NoSuchAlgorithmException in hashCoordinate\n" + ex);
        }

        if (isSolved) {
            dao.saveSessionAttempts(new SessionAttempt(999.0,
                    999.0,
                    isSolved,
                    new Date(),
                    hashedIP,
                    cache));
        } else {
            dao.saveSessionAttempts(new SessionAttempt(coordinate.getDoubleCoordinateX(),
                    coordinate.getDoubleCoordinateY(),
                    isSolved,
                    new Date(),
                    hashedIP,
                    cache));
        }
    }

    /**
     * The purpose of this Method is to set a session variable that has as key
     * the waypoint of the Cache the user is checking and as value the attempt.
     * If the varibale is already set then the value is incremented by one. Also
     * if a specific (set form the user) amount of attempts is reached then a
     * message is displayed
     */
    private void sessionHint() {
        Enumeration<String> e = s.getAttributeNames();
        boolean hasit = false;
        int checks = 1;

        /*
        Checks if there is already a session var which has the waypoint as key.
        This would mean that there already happend some checks with this waypoint
         */
        while (e.hasMoreElements()) {
            String gcname = e.nextElement();
            if (gcname.equals(this.key)) {
                hasit = true;
                checks = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(this.key);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(this.key, ++checks);
            }
        }

        /*
        If there are not checks with this waypoint, a new sessoin var is created with the Wayoint and 1
         */
        if (!hasit) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(this.key, checks);
        }

        /*
        Checks if a hint is registered and it sets the helptext if the attempts are reached
         */
        List<Hint> allHints = cache.getHints();

        if (!allHints.isEmpty()) {
            for (Hint h : allHints) {
                if (h.getAttempts() == checks) {
                    attemptHint = h.getHelptext();
                    showAttemptHint = true;
                }
            }
        }
    }

    public void refresh() {
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("checker");
        cache = null;
        coordinate = new Coordinate(true, true, 0, 0, 0, 0, 0, 0);
        key = null;
        isSolved = false;
        message = "";
        solvedModel = new DefaultMapModel();
        attemptHint = "";
        showAttemptHint = false;
    }

    /*
    GETTER AND SETTER
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getWaypoint() {
        return key;
    }

    public void setWaypoint(String waypoint) {
        this.key = waypoint;
    }

    public boolean isIsSolved() {
        return isSolved;
    }

    public void setIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public MapModel getSolvedModel() {
        return solvedModel;
    }

    public void setSolvedModel(MapModel solvedModel) {
        this.solvedModel = solvedModel;
    }

    public String getAttemptHint() {
        return attemptHint;
    }

    public void setAttemptHint(String attemptHint) {
        this.attemptHint = attemptHint;
    }

    public boolean isShowAttemptHint() {
        return showAttemptHint;
    }

    public void setShowAttemptHint(boolean showAttemptHint) {
        this.showAttemptHint = showAttemptHint;
    }

    public String getInfoTries() {
        return infoTries;
    }

    public void setInfoTries(String infoTries) {
        this.infoTries = infoTries;
    }

    public UIComponent getGrid() {
        return grid;
    }

    public void setGrid(UIComponent grid) {
        this.grid = grid;
    }
}
