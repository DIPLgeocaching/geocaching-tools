/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.geocachingtools.geoui.util.Dao;
import org.geocachingtools.geoui.util.HibernateUtil;

/**
 *
 * @author Schule
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("dao", new Dao());
        HibernateUtil.getSessionFactory();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //HibernateUtil wird geschlossen
        Dao dao = (Dao) se.getSession().getAttribute("dao");
        dao.close();        
    }
}
