package db;

import models.Console;
import models.Game;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DBConsole {

    private static Session session;

    public static List<Game> availableGames(Console console){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Game> results = null;
        try {
            Criteria cr = session.createCriteria(Game.class);
            cr.createAlias("consolesAvialableFor", "console");
            cr.add(Restrictions.eq("console.id", console.getId()));
            cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            results = cr.list();
        } catch (HibernateException e) {

        } finally {
            session.close();
        }
        return results;
    }

    public static Game gameBeingPlayed(Console console) {
        session = HibernateUtil.getSessionFactory().openSession();
        Game game = null;
        try {
            Criteria cr = session.createCriteria(Game.class);
            cr.add(Restrictions.eq("id", console.getGameBeingPlayed().getId()));
            game = (Game) cr.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return game;
    }


}
