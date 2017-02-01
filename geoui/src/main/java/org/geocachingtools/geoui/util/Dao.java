/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Childwaypoint;
import org.geocachingtools.geoui.model.Gctusr;
import org.geocachingtools.geoui.model.Hint;
import org.geocachingtools.geoui.model.Invitekey;
import org.geocachingtools.geoui.model.SessionAttempts;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Schule
 */
public class Dao {

    public Dao() {
        initDb();
    }

    public void initDb() {
        Gctusr usr1 = new Gctusr("informatik.gc@gmail.com", "Backdoor", true);
        Gctusr usr2 = new Gctusr("googleAccount2", "gcaccuntname2", false);
        Gctusr usr3 = new Gctusr("googleAccount3", "gcaccuntname3", false);
        Gctusr usr4 = new Gctusr("Rapi1234-GA", "Rapi1234-GC", true);

        Invitekey ik1 = new Invitekey("ABCD", usr1);
        Invitekey ik2 = new Invitekey("EFGH", usr1);
        Invitekey ik3 = new Invitekey("IJKL", usr2);

        Cache ca1 = new Cache("Gcname1", "GCWaypiont1", -12.459, 12.456, "message1", usr1);
        Cache ca2 = new Cache("Gcname2", "GcWaypoint2", -12.459, 12.456, "message2", usr1);
        Cache ca3 = new Cache("Gcname3", "GcWaypoint3", -12.459, 12.456, "message3", usr3);
        Cache ca4 = new Cache("Toller Cache", "GCABCDE", -12.459, 12.456, "Juhu du hast es geschafft", usr4);
        Cache ca5 = new Cache("Der tollste Cache", "GC12345", 1.0, 0.0, "yess u made it", usr4);
        Cache ca6 = new Cache("Der allertollste Cache", "GC54321", -12.459, 12.456, "Gratuliere", usr4);

        Childwaypoint cw1 = new Childwaypoint("Versuche es mit Cesar", 2.0, 0.0, ca5);
        Childwaypoint cw2 = new Childwaypoint("Dies ist ein Hinweis", 12.000, 12.000, ca5);

        SessionAttempts sa1 = new SessionAttempts(10, new Date(), true, new Date(), ca3);
        SessionAttempts sa2 = new SessionAttempts(100, new Date(), true, new Date(), ca3);
        SessionAttempts sa3 = new SessionAttempts(1000, new Date(), true, new Date(), ca2);

        Hint h1 = new Hint(10, "helptext1", ca1);
        Hint h2 = new Hint(5, "helptext2", ca1);
        Hint h3 = new Hint(20, "helptext3", ca2);
        Hint h4 = new Hint(5, "Bitte lass es gut sein... du kommst nicht drauf...", ca4);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.save(usr1);
            session.save(usr2);
            session.save(usr3);
            session.save(usr4);
            session.save(ik1);
            session.save(ik2);
            session.save(ik3);
            session.save(ca1);
            session.save(ca2);
            session.save(ca3);
            session.save(ca4);
            session.save(ca5);
            session.save(ca6);
            session.save(cw1);
            session.save(cw2);
            session.save(sa1);
            session.save(sa2);
            session.save(sa3);
            session.save(h1);
            session.save(h2);
            session.save(h3);
            session.save(h4);
            tx.commit();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
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

    public boolean saveGctusr(Gctusr gu) {
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

    public void saveSessionAttempts(SessionAttempts sa) {
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

    public List<Gctusr> getAllGctusrs() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Gctusr> allGctusrs = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allGctusrs = ses.createQuery("from Gctusr").list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getAllGctusrs\n" + ex);
        } finally {
            ses.close();
        }

        return allGctusrs;
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

    public List<SessionAttempts> getAllSessionAttempts() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<SessionAttempts> allSessionAttempts = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            allSessionAttempts = ses.createQuery("from SessionAttempt").list();
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getSessionAttempts\n" + ex);
        } finally {
            ses.close();
        }

        return allSessionAttempts;
    }

    public Gctusr getCertianGctusr(String googleAccount) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        Gctusr gctusr = null;

        try {
            tx = ses.beginTransaction();
            Query q = ses.createQuery("from Gctusr where GoogleAccount = :ga");
            q.setParameter("ga", googleAccount);
            gctusr = (Gctusr) q.list().get(0);
            tx.commit();
        } catch (NullPointerException ex) {
            System.out.println("Benutzer " + googleAccount + " nicht vorhanden!");
        } catch (Exception ex2) {
            System.err.println("Exception in getCertianCctusr\n" + ex2);
        } finally {
            ses.close();
        }

        return gctusr;
    }

    public Cache getCertianCache(String waypoint) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        Cache cache = new Cache();

        try {
            tx = ses.beginTransaction();
            cache = (Cache) ses.createQuery("from Cache where gcWaypoint = :ga").setParameter("ga", waypoint).list().get(0);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in getCertianCache\n" + ex);
        } finally {
            ses.close();
        }

        return cache;
    }

    public List<Cache> getCachesFromUser(Gctusr gctusr) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Cache> caches = new ArrayList<>();

        try {
            tx = ses.beginTransaction();
            caches = ses.createQuery("from Cache where cache_gctuser_id = :cg").setParameter("cg", gctusr.getId()).list();
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
            System.err.println("Exception in getCertianUser\n" + ex);
        } finally {
            ses.close();
        }
    }

    public void close() {
        HibernateUtil.close();
    }
}
