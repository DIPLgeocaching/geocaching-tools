/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.geocachingtools.geoui.model.Check;

/**
 *
 * @author Thomas
 */
@ApplicationScoped
@Named(value = "usercecks")
public class UserchecksController {

    private Map<String, Check> checks;

    @PostConstruct
    public void init() {
        checks = new LinkedHashMap<>();
    }

    public Check cehckForAttempts(String ip) {
        Calendar now = Calendar.getInstance();
        Check check;

        if (checks.containsKey(ip)) {
            check = checks.get(ip);
            if (check.getSum() < 6 && now.before(check.getCal())) {
                check.increase();
            } else {
                checks.remove(ip);
                now.add(Calendar.HOUR_OF_DAY, 1);
                check = new Check(1, now);
                checks.put(ip, check);
            }
        } else {
            now.add(Calendar.HOUR_OF_DAY, 1);
            check = new Check(1, now);
            checks.put(ip, check);
        }
        return check;
    }
}
