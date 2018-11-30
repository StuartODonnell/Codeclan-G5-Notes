package db;

import models.Console;
import models.Game;
import models.Owner;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DBGame {

    private static Session session;

    public static List<Console> consolesToPlayOn(Game game){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Console> results = null;
        try {
            Criteria cr = session.createCriteria(Console.class);
            cr.createAlias("games", "game");
            cr.add(Restrictions.eq("game.id", game.getId()));
            cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            results = cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static Console consoleBeingPlayedOn(Game game){
        session = HibernateUtil.getSessionFactory().openSession();
        Console result = null;
        try {
            Criteria cr = session.createCriteria(Console.class);
            cr.add(Restrictions.eq("gameBeingPlayed", game));
            result = (Console)cr.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static List<Owner> ownersWhoFavGame(Game game) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Owner> results = null;
        try {
            Criteria cr = session.createCriteria(Owner.class);
            cr.add(Restrictions.eq("favGame", game));
            results = cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }


}
