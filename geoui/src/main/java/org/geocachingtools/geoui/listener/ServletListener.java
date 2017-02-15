package org.geocachingtools.geoui.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.geocachingtools.geoui.util.Dao;

/**
 * Listener for Web application lifecycle
 * @author Lukas
 */
public class ServletListener implements ServletContextListener {

    private Dao dao;
    
    /**
     * Initializes the Dao DB object and saves it in the servlet context
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dao = new Dao();
        sce.getServletContext().setAttribute("dao", dao);
    }

    /**
     * Closes the Dao DB object 
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dao.close();
    }
}
