/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.database.Dao;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Mistake;
import org.geocachingtools.geoui.model.SessionAttempt;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Thomas
 */
@Named(value = "statsCon")
@SessionScoped
public class StatisticsController implements Serializable {

    //Are required for the Database connection
    private final HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private final Dao dao = (Dao) s.getAttribute("dao");

    @Inject
    private RegisteredCachesControler con;

    private Cache cache;
    private List<Cache> allUserCaches = new ArrayList<>();

    private Date from;
    private Date to;
    private Date now = new Date();
    private List<SessionAttempt> allAttempts = new ArrayList<>();
    private List<SessionAttempt> allAttemptsSpan = new ArrayList<>();
    private List<String> ipaddresses = new ArrayList<>();
    private List<Mistake> mistakes = new ArrayList<>();
    private Map<String, String> allIps = new LinkedHashMap<>();
    
    private boolean noChecks;

    private int correctAttempts;

    @PostConstruct
    public void init() {
        allUserCaches = con.getCaches();
        correctAttempts = 0;
    }

    public String setStatsCache(Cache cache) {
        this.cache = cache;
        correctAttempts = 0;
        allAttemptsSpan = new ArrayList<>();
        allAttempts = dao.getAllSessionAttemptsFromCache(cache.getCheckerlink());

        if (!allAttempts.isEmpty()) {
            noChecks = false;
            HashSet<String> ips = new HashSet<>();
            allAttempts.forEach((attempt) -> {
                ips.add(attempt.getIpaddress());
            });

            ipaddresses.clear();
            int i = 1;
            for (String ip : ips) {
                String s = "IP-Addresse " + i;
                this.allIps.put(s, ip);
                ipaddresses.add(s);
                i++;
            }

            allAttempts.stream().filter((item) -> (item.isSolved())).forEach((_item) -> {
                correctAttempts++;
            });

            sortAttempts();

            to = new Date();
            from = allAttemptsSpan.get(0).getTimestamp();

            getAllMistakes();
        } else {
            noChecks = true;
        }

        return "statistics";
    }

    private void sortAttempts() {
        Collections.sort(allAttempts, new Comparator<SessionAttempt>() {
            @Override
            public int compare(SessionAttempt a1, SessionAttempt a2) {
                return a1.getTimestamp().compareTo(a2.getTimestamp());
            }
        });

        allAttempts.stream().forEach((a) -> {
            allAttemptsSpan.add(a);
        });
    }

    public List<SessionAttempt> attemptsFromIp(String address) {
        List<SessionAttempt> attemptsFromIp = new ArrayList<>();

        for (SessionAttempt attempt : allAttemptsSpan) {
            String s = allIps.get(address);
            if (s.equals(attempt.getIpaddress())) {
                attemptsFromIp.add(attempt);
            }
        }

        return attemptsFromIp;
    }

    public void dateChanged(SelectEvent event) {
        //TODO: Date geht nicht
        allAttemptsSpan.clear();
        for (SessionAttempt a : allAttempts) {
            System.out.println(from + " " + a.getTimestamp() + "  " + to);
            if (a.getTimestamp().after(from) && a.getTimestamp().before(to)) {
                allAttemptsSpan.add(a);
            }
        }
    }

    public void getAllMistakes() {
        mistakes.clear();

        for (SessionAttempt sa : allAttemptsSpan) {
            if (!sa.isSolved()) {
                if (mistakes.isEmpty()) {
                    mistakes.add(new Mistake(sa.getUserinputX(), sa.getUserinputY(), 1));
                } else {
                    boolean found = false;
                    for (Mistake m : mistakes) {
                        if (sa.getUserinputX() == m.getCoordinateX()
                                && sa.getUserinputY() == m.getCoordinateY()) {
                            m.increase();
                            found = true;
                        }
                    }
                    if (!found) {
                        mistakes.add(new Mistake(sa.getUserinputX(), sa.getUserinputY(), 1));
                    }
                }
            }
        }
        Collections.sort(mistakes, new Comparator<Mistake>() {
            @Override
            public int compare(Mistake m1, Mistake m2) {
                return m2.getAmount() - m1.getAmount();
            }
        });

        if (mistakes.size() > 10) {
            for (int i = mistakes.size(); i > 10; i--) {
                mistakes.remove(i - 1);
            }
        }
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public List<Cache> getAllUserCaches() {
        return allUserCaches;
    }

    public void setAllUserCaches(List<Cache> allUserCaches) {
        this.allUserCaches = allUserCaches;
    }

    public List<SessionAttempt> getAllAttempts() {
        return allAttempts;
    }

    public void setAllAttempts(List<SessionAttempt> allAttempts) {
        this.allAttempts = allAttempts;
    }

    public List<String> getIpaddresses() {
        return ipaddresses;
    }

    public void setIpaddresses(List<String> ipaddresses) {
        this.ipaddresses = ipaddresses;
    }

    public List<SessionAttempt> getAllAttemptsSpan() {
        return allAttemptsSpan;
    }

    public void setAllAttemptsSpan(List<SessionAttempt> allAttemptsSpan) {
        this.allAttemptsSpan = allAttemptsSpan;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public int getCorrectAttempts() {
        return correctAttempts;
    }

    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    public List<Mistake> getMistakes() {
        return mistakes;
    }

    public void setMistakes(List<Mistake> mistakes) {
        this.mistakes = mistakes;
    }

    public boolean isNoChecks() {
        return noChecks;
    }

    public void setNoChecks(boolean noChecks) {
        this.noChecks = noChecks;
    }
}
