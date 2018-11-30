package db;

import models.Customer;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DBCustomer {

    private static Transaction transaction;
    private static Session session;

    public static void save(Customer customer){
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void update(Customer customer){
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void delete(Customer customer){
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static  List<Customer> getAll(){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Customer> results = null;
        try {
            Criteria cr = session.createCriteria(Customer.class);
            results = cr.list();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static Customer find(int id){
        session = HibernateUtil.getSessionFactory().openSession();
        Customer result = null;
        try {
            Criteria cr = session.createCriteria(Customer.class);
            cr.add(Restrictions.eq("id", id));
            result = (Customer)cr.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}
