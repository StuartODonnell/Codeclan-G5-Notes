package db;

import models.Child;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DBChild {

    private static Session session;
    private static Transaction transaction;


    public static List<Child> sortByAge() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            cr.addOrder(Order.desc("age"));
            results = cr.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static List<Child> findByRange(String range){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            cr.add(Restrictions.eq("range", range));
            results = cr.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static List<Child> getChildrenGroupAge(int age){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            cr.add(Restrictions.lt("age", age));
            results = cr.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }



    public static Double getAverageAge(){
        session = HibernateUtil.getSessionFactory().openSession();
        Double result = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            cr.setProjection(Projections.avg("age"));
            result = (Double)cr.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static Long getTotalAge(){
        session = HibernateUtil.getSessionFactory().openSession();
        Long result = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            cr.setProjection(Projections.sum("age"));
            result = (Long)cr.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static Child getOldestChild(){
        session = HibernateUtil.getSessionFactory().openSession();
        Integer maxAge = null;
        Child result = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            cr.setProjection(Projections.max("age"));
            maxAge = (Integer)cr.uniqueResult();
            cr = session.createCriteria(Child.class);
            cr.add(Restrictions.eq("age", maxAge));
            result = (Child)cr.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static Child getYoungestChild(){
        session = HibernateUtil.getSessionFactory().openSession();
        Child result = null;
        Integer minAge = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            cr.setProjection(Projections.min("age"));
            minAge = (Integer)cr.uniqueResult();
            cr = session.createCriteria(Child.class);
            cr.add(Restrictions.eq("age", minAge));
            result = (Child)cr.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

}
