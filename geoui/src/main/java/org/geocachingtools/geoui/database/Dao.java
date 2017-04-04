/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.geocachingtools.geoui.model.Cache;
import org.geocachingtools.geoui.model.Childwaypoint;
import org.geocachingtools.geoui.model.Coordinate;
import org.geocachingtools.geoui.model.Gctuser;
import org.geocachingtools.geoui.model.Hint;
import org.geocachingtools.geoui.model.Invitekey;
import org.geocachingtools.geoui.model.SessionAttempt;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Schule
 */
public class Dao {

    public void initDb() throws ParseException {
        
        Coordinate coord = new Coordinate();

        Gctuser usr1 = new Gctuser("googleAccount1", "gcaccuntname1", true);
        Gctuser usr2 = new Gctuser("googleAccount2", "gcaccuntname2", false);
        Gctuser usr3 = new Gctuser("googleAccount3", "gcaccuntname3", false);
        Gctuser usr4 = new Gctuser("Rapi1234-GA", "Rapi1234-GC", true);

        Invitekey ik1 = new Invitekey("ABCD", usr1);
        Invitekey ik2 = new Invitekey("EFGH", usr1);
        Invitekey ik3 = new Invitekey("IJKL", usr2);

        Cache ca1 = new Cache("ca1","Gcname1", "GCWaypiont1", "-12.459", "12.456", "message1", usr1, new ArrayList<>(), new ArrayList<>());
        Cache ca2 = new Cache("ca2","Gcname2", "GcWaypoint2", "-12.459", "12.456", "message2", usr1, new ArrayList<>(), new ArrayList<>());
        Cache ca3 = new Cache("ca3","Gcname3", "GcWaypoint3", "-12.459", "12.456", "message3", usr3, new ArrayList<>(), new ArrayList<>());
        Cache ca4 = new Cache("ca4","Toller Cache", "GCABCDE", "-12.459", "12.456", "Juhu du hast es geschafft", usr4, new ArrayList<>(), new ArrayList<>());
        Cache ca5 = new Cache("ca5","Der tollste Cache", "GC12345", coord.hashCoordinate(21.0), coord.hashCoordinate(12.0), "yess u made it", usr4, new ArrayList<>(), new ArrayList<>());
        Cache ca6 = new Cache("ca6","Der allertollste Cache", "GC54321", coord.hashCoordinate(-12.459), coord.hashCoordinate(12.456), "Gratuliere", usr4, new ArrayList<>(), new ArrayList<>());
        Cache ca7 = new Cache("ca7","Testcache", "GC15963", coord.hashCoordinate(21.0), coord.hashCoordinate(12.0), "Gratuliere, du hast meinen Cache geloest!", usr4, new ArrayList<>(), new ArrayList<>());

        Childwaypoint cw1 = new Childwaypoint("Versuche es mit Cesar", coord.hashCoordinate(0.0), coord.hashCoordinate(1.0), ca5);
        Childwaypoint cw2 = new Childwaypoint("Dies ist ein Hinweis", coord.hashCoordinate(0.0), coord.hashCoordinate(2.0), ca5);
        Childwaypoint cw3= new Childwaypoint("Du bist nah dran! Versuche es mit Caesar.", coord.hashCoordinate(0.0), coord.hashCoordinate(2.0), ca7);
        Childwaypoint cw4 = new Childwaypoint("Du hast die Haelfte schon geschafft!", coord.hashCoordinate(2.0), coord.hashCoordinate(1.0), ca7);

        DateFormat formatter1;
        formatter1 = new SimpleDateFormat("mm/DD/yyyy");
        List<SessionAttempt> sal = new ArrayList<>();
        sal.add(new SessionAttempt(1.0, 1.0, false, (Date) formatter1.parse("02/01/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(2.0, 2.0, false, (Date) formatter1.parse("02/02/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(3.0, 3.0, false, (Date) formatter1.parse("02/04/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(1.0, 1.0, false, (Date) formatter1.parse("02/05/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(4.0, 4.0, false, (Date) formatter1.parse("02/07/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(5.0, 5.0, false, (Date) formatter1.parse("02/09/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(1.0, 1.0, false, (Date) formatter1.parse("02/09/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(2.0, 2.0, false, (Date) formatter1.parse("02/10/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.0, 0.0, true, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.0, 0.0, true, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        
        sal.add(new SessionAttempt(0.1, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.2, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.3, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.4, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.5, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.6, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.7, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.8, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.9, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        sal.add(new SessionAttempt(0.11, 0.0, false, (Date) formatter1.parse("02/14/2017"), "127.0.0.1", ca5));
        
        sal.add(new SessionAttempt(2.0, 2.0, false, (Date) formatter1.parse("02/10/2017"), "127.0.0.2", ca5));
        sal.add(new SessionAttempt(7.0, 7.0, false, (Date) formatter1.parse("02/13/2017"), "127.0.0.2", ca5));
        sal.add(new SessionAttempt(1.0, 1.0, false, (Date) formatter1.parse("02/10/2017"), "127.0.0.2", ca5));
        sal.add(new SessionAttempt(4.0, 4.0, false, (Date) formatter1.parse("02/10/2017"), "127.0.0.2", ca5));
        sal.add(new SessionAttempt(0.0, 0.0, true, (Date) formatter1.parse("02/13/2017"), "127.0.0.2", ca5));

        sal.add(new SessionAttempt(1.0, 2.0, false, new Date(), "127.0.0.1", ca6));

        Hint h1 = new Hint(10, "helptext1", ca1);
        Hint h2 = new Hint(5, "helptext2", ca1);
        Hint h3 = new Hint(20, "helptext3", ca2);
        Hint h4 = new Hint(3, "Leider wieder nicht geschafft.. Probiere es vielleicht einmal mit Gemuetlichkeit", ca5);
        Hint h5 = new Hint(3, "Vielleicht hilft dir ja ein Caesar weiter.", ca7);

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
            session.save(ca7);
            session.save(cw1);
            session.save(cw2);
            session.save(cw3);
            session.save(cw4);
            for (SessionAttempt sa : sal) {
                session.save(sa);
            }
            session.save(h1);
            session.save(h2);
            session.save(h3);
            session.save(h4);
            session.save(h5);
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

    public void saveGctusr(Gctuser gu) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;

        try {
            tx = ses.beginTransaction();
            ses.save(gu);
            tx.commit();
        } catch (Exception ex) {
            System.err.println("Exception in saveGctusr()\n" + ex);
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

    public List<Gctuser> getAllGctusrs() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Gctuser> allGctusrs = new ArrayList<>();

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

    public void close() {
        HibernateUtil.close();
    }
}
