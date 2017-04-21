/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.util;

import java.util.ArrayList;
import java.util.List;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Gctuser;
import org.geocachingtools.geoui.model.Hint;
import org.geocachingtools.geoui.model.Invitekey;
import org.geocachingtools.geoui.model.OAuthData;
import org.geocachingtools.geoui.model.SessionAttempt;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Schule
 */
public class Dao {
    
    private static final String ADMINKEY = "AdminKey";

    static {
        Invitekey key1 = new Invitekey(ADMINKEY);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.save(key1);
            tx.commit();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
    }

    public Dao() {
    }

    public boolean saveGctusr(Gctuser gu) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        boolean succ = false;

        try {
            tx = ses.beginTransaction();
            ses.save(gu);
            tx.commit();
            succ = true;
        } catch (Exception ex) {
            System.err.println("Exception in saveGctusr()\n" + ex);
        } finally {
            ses.close();
        }
        return succ;
    }

    public void saveInvitekey(Invitekey ik) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = ses.beginTransaction();
            ses.save(ik);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in saveInvitekey() \n" + ex);
        } finally {
            ses.close();
        }
    }

    public List<Gctuser> getAllGctusrs() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Gctuser> allGctusrs = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allGctusrs = ses.createQuery("from Gctuser").list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getAllGctusrs\n" + ex);
        } finally {
            ses.close();
        }

        return allGctusrs;
    }

    public List<Invitekey> getAllInvitekeys() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Invitekey> allInvitekeys = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allInvitekeys = ses.createQuery("from Invitekey").list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getAllInvitekeys\n" + ex);
        } finally {
            ses.close();
        }

        return allInvitekeys;
    }

    public Gctuser getCertianGctusr(String googleAccount) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        Gctuser gctusr = null;

        try {
            tx = ses.beginTransaction();
            Query q = ses.createQuery("from Gctusr where GoogleAccount = :ga");
            q.setParameter("ga", googleAccount);
            gctusr = (Gctuser) q.list().get(0);
            tx.commit();
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Benutzer " + googleAccount + " nicht vorhanden!");
        } catch (Exception ex2) {
            System.err.println("Exception in getCertianCctusr\n" + ex2);
        } finally {
            ses.close();
        }

        return gctusr;
    }

    public Invitekey getInviteKey(String key) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = ses.beginTransaction();
            Invitekey ret = (Invitekey) ses.createQuery("from Invitekey where invkey = :key")
                    .setParameter("key", key)
                    .setMaxResults(1)
                    .uniqueResult();
            tx.commit();
            return ret;
        } catch (Exception ex) {
            System.err.println("Exception in isInviteKeyValid\n" + ex);
            return null;
        } finally {
            ses.close();
        }
    }

    /**
     *
     * @param usr the user to be activated
     * @param key the key submitted by the user
     * @return true if the user got activated by the key
     */
    public boolean activateUser(Gctuser usr, String key) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = ses.beginTransaction();
            Query query = ses.createQuery(""
                    + "update Invitekey set usedby_id = :userid "
                    + "where usedby_id = null "
                    + "and invkey = :key");
            query.setParameter("userid", usr.getId());
            query.setParameter("key", key);
            int result = query.executeUpdate();
            ses.refresh(usr);
            
            List<Gctuser> allGctusrs = getAllGctusrs();
            if (allGctusrs.size() == 1) {
                usr.setIsAdmin(true);
            }
            ses.update(usr);

            tx.commit();
            return result == 1;
        } catch (Exception ex) {
            System.err.println("Exception in activateUser\n" + ex);
            return false;
        } finally {
            ses.close();
        }
    }

    public void close() {
        HibernateUtil.close();
    }

    public Gctuser getUserByOAuthData(String id, String provider) {
        //TODO: eigene Query hinzfügen anstannt alle user zu laden
        List<Gctuser> all = getAllGctusrs();
        for (Gctuser usr : all) {
            OAuthData oauth = usr.getOauth();
            if (oauth != null) {
                System.out.println(oauth.getOauthid() + " " + oauth.getProvider());
            }
            if (oauth != null
                    && oauth.getOauthid().equals(id)
                    && oauth.getProvider().equals(provider)) {
                return usr;
            }
        }
        return null;
    }

    public boolean saveOrUpdateGctusr(Gctuser gu) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        boolean succ = false;

        try {
            tx = ses.beginTransaction();
            ses.saveOrUpdate(gu);
            tx.commit();
            succ = true;
        } catch (Exception ex) {
            System.err.println("Exception in saveOrUpdateGctusr()\n" + ex);
        } finally {
            ses.close();
        }
        return succ;
    }
    
    public void saveCache(Cache c) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = ses.beginTransaction();
            ses.save(c);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in saveCache()\n" + ex);
        } finally {
            ses.close();
        }
    }

    public void saveHint(Hint h) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = ses.beginTransaction();
            ses.save(h);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in saveHint()\n" + ex);
        } finally {
            ses.close();
        }
    }
    
    public void saveSessionAttempts(SessionAttempt sa) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = ses.beginTransaction();
            ses.save(sa);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in saveSessionAttempts()\n" + ex);
        } finally {
            ses.close();
        }
    }

    public List<Cache> getAllCaches() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Cache> allCaches = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allCaches = ses.createQuery("from Cache").list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getAllCaches\n" + ex);
        } finally {
            ses.close();
        }

        return allCaches;
    }
    
    public List<Hint> getAllHints() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Hint> allHints = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allHints = ses.createQuery("from Hint").list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getAllHints\n" + ex);
        } finally {
            ses.close();
        }

        return allHints;
    }

    public List<SessionAttempt> getAllSessionAttempts() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<SessionAttempt> allSessionAttempt = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allSessionAttempt = ses.createQuery("from SessionAttempt").list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getSessionAttempts\n" + ex);
        } finally {
            ses.close();
        }

        return allSessionAttempt;
    }

    public List<SessionAttempt> getAllSessionAttemptsFromCache(String link) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<SessionAttempt> allSessionAttempt = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allSessionAttempt = ses.createQuery("from SessionAttempt where Parentcache = :ca").setParameter("ca", link).list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getSessionAttempts\n" + ex);
        } finally {
            ses.close();
        }

        return allSessionAttempt;
    }

    public Gctuser getCertainGctuser(String googleAccount) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        Gctuser gctuser = new Gctuser();

        try {
            tx = ses.beginTransaction();
            gctuser = (Gctuser) ses.createQuery("from Gctuser where GoogleAccount = :ga").setParameter("ga", googleAccount).list().get(0);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getCertianCctuser\n" + ex);
        } finally {
            ses.close();
        }

        return gctuser;
    }
    
    public boolean existsCachelink(String link) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Cache> cache = new ArrayList<>();
        
        try {
            tx = ses.beginTransaction();
            cache = ses.createQuery("from Cache where checkerlink = :li").setParameter("li", link).list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in existsCachelink\n" + ex);
        } finally {
            ses.close();
        }
        
        return !cache.isEmpty();
    }

    public Cache getCertainCache(String link) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        Cache cache = new Cache();
        
        try {
            tx = ses.beginTransaction();
            cache = (Cache) ses.createQuery("from Cache where checkerlink = :li").setParameter("li", link).list().get(0);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getCertianCache\n" + ex);
        } finally {
            ses.close();
        }

        return cache;
    }

    public List<Cache> getCachesFromUser(Gctuser gctuser) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Cache> caches = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            caches = ses.createQuery("from Cache where cache_gctuser_id = :cg").setParameter("cg", gctuser.getId()).list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getCertianCctusr\n" + ex);
        } finally {
            ses.close();
        }

        return caches;
    }

    public boolean existsCertianWaypoint(String waypoint) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        List<Cache> cache = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            cache = ses.createQuery("from Cache where gcWaypoint = :wa").setParameter("wa", waypoint).list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in existsCertianWaypoint\n" + ex);
        } finally {
            ses.close();
        }

        return cache.isEmpty();
    }

    public void updateCache(Cache cache) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = ses.beginTransaction();
            ses.update(cache);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in updateCache\n" + ex);
        } finally {
            ses.close();
        }
    }

    public void deleteCache(Cache cache) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = ses.beginTransaction();
            ses.delete(cache);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in deleteCache\n" + ex);
        } finally {
            ses.close();
        }
    }
}
